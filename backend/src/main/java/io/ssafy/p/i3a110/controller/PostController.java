package io.ssafy.p.i3a110.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.ssafy.p.i3a110.dto.PostDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.http.request.GetPostRequest;
import io.ssafy.p.i3a110.interceptor.Auth;
import io.ssafy.p.i3a110.service.DiaryService;
import io.ssafy.p.i3a110.service.LikeService;
import io.ssafy.p.i3a110.service.PostService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private DiaryService diaryService;
    
    @PostMapping("/posts/all")
    @ApiOperation(value = "회원 별 포스트 전체 조회")
    public List<HashMap<Object, Object>> getAllPostByUser(@RequestBody HashMap<String, String> map) {
    	String uid = map.get("uid");
    	int isProj = Integer.parseInt(map.get("isProj"));
    	String keyword = map.get("keyword");
    	int isTemp = Integer.parseInt(map.get("isTemp"));
    	String lim = map.get("limit");
    	int limit = 0;
    	if(lim==null || lim.equals("")) limit = 0;
    	else limit = Integer.parseInt(lim);
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	List<HashMap<Object, Object>> output = new ArrayList<HashMap<Object,Object>>();
    	ArrayList<PostDto> postList = postService.getAllPostByUser(uid, isProj, keyword, isTemp, limit);
    	for(PostDto post : postList) {
    		HashMap<Object, Object> form = objectMapper.convertValue(post, HashMap.class);
    		UserDto writer = userService.findUserById(post.getUid());
    		HashMap<String, String> userinfo = new HashMap<String, String>();
    		userinfo.put("id", String.valueOf(writer.getId()));
    		userinfo.put("email", writer.getEmail());
    		userinfo.put("nickname", writer.getNickname());
    		userinfo.put("img", writer.getImg());
    		userinfo.put("intro", writer.getIntro());
    		
    		form.put("userinfo", userinfo);
    		form.put("dName", diaryService.getDiary(String.valueOf(post.getDid())).getTitle());
    		output.add(form);
    	}
    	return output;
    }
    
    @PostMapping("/posts/{did}")
    @ApiOperation(value = "다이어리 포스트 조회")
    public ArrayList<PostDto> getPost(@PathVariable int did, @RequestBody GetPostRequest map) {
        String keyword = Optional.ofNullable(map.getKeyword()).orElse("");
        int isTemp = Optional.ofNullable(map.getIsTemp()).orElse(0);

        return postService.getPost(did, keyword, isTemp);
    }

    @GetMapping("/posts/{id}")
    @ApiOperation(value = "상세 조회")
    public PostDto getPostById(@PathVariable int id) {
        return postService.getPostById(id);
    }

    @Auth
    @PostMapping("/posts")
    @ApiOperation(value = "포스트 작성")
    public Object createPost(HttpSession httpSession, @RequestBody PostDto post) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = userService.findUserByEmail(email);
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        Timestamp ts = Timestamp.valueOf(formatter.format(Calendar.getInstance().getTime()));
        System.out.println( " Timestamp : " + ts);
        
        post.setUid(user.getId());
        post.setCDate(ts.toString());
        postService.createPost(post);
		return new ResponseEntity<>(post.getId(), HttpStatus.OK);
    }

    @Auth
    @PutMapping("/posts")
    @ApiOperation(value = "포스트 수정")
    public Object updatePost(HttpSession httpSession, @RequestBody HashMap<String, String> map) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = userService.findUserByEmail(email);

        PostDto post = postService.getPostById(Integer.parseInt(map.get("id")));
        
        if(user.getId() == post.getUid()) {
	        post.setTitle(map.get("title"));
	        post.setContent(map.get("content"));
	        post.setPriority(Integer.parseInt(map.get("priority")));
	        post.setIsTemp(Integer.parseInt(map.get("isTemp")));
	        postService.updatePost(post);
	        return new ResponseEntity<>(HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @Auth
    @DeleteMapping("/posts/{id}")
    @ApiOperation(value = "포스트 삭제")
    public Object deletePost(HttpSession session, @PathVariable int id) {
        String email = (String) session.getAttribute("email");
        UserDto user = userService.findUserByEmail(email);
        
        if(user.getId() == postService.getPostById(id).getUid()) {
        	postService.deletePost(id);
        	return new ResponseEntity<>(HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
