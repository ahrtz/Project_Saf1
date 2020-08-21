package io.ssafy.p.i3a110.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import io.ssafy.p.i3a110.service.CommitService;
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
    @Autowired
    private CommitService commitService;
    
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
    		long cdate = (long) form.get("cdate");
    		form.put("cdate", new Date(cdate));
    		form.put("commitCnt", commitService.getCntByPid(String.valueOf(post.getId())));
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
    public List<HashMap<Object, Object>> getPost(@PathVariable int did, @RequestBody GetPostRequest map) {
        String keyword = Optional.ofNullable(map.getKeyword()).orElse("");
        int isTemp = Optional.ofNullable(map.getIsTemp()).orElse(0);
    	ObjectMapper objectMapper = new ObjectMapper();
    	List<HashMap<Object, Object>> output = new ArrayList<HashMap<Object,Object>>();
    	ArrayList<PostDto> postList = postService.getPost(did, keyword, isTemp);
    	for(PostDto post : postList) {
    		HashMap<Object, Object> form = objectMapper.convertValue(post, HashMap.class);
    		long cdate = (long) form.get("cdate");
    		form.put("cdate", new Date(cdate));
    		form.put("commitCnt", commitService.getCntByPid(String.valueOf(post.getId())));
    		output.add(form);
    	}
    	
        return output;
    }

    @GetMapping("/posts/{id}")
    @ApiOperation(value = "상세 조회")
    public HashMap<Object, Object> getPostById(@PathVariable int id) {
    	PostDto post = postService.getPostById(id);
    	ObjectMapper objectMapper = new ObjectMapper();
    	HashMap<Object, Object> form = objectMapper.convertValue(post, HashMap.class);
		long cdate = (long) form.get("cdate");
		form.put("cdate", new Date(cdate));
		UserDto writer = userService.findUserById(post.getUid());
		HashMap<String, String> userinfo = new HashMap<String, String>();
		userinfo.put("id", String.valueOf(writer.getId()));
		userinfo.put("email", writer.getEmail());
		userinfo.put("nickname", writer.getNickname());
		userinfo.put("img", writer.getImg());
		userinfo.put("intro", writer.getIntro());
		
		form.put("userinfo", userinfo);
        return form;
    }

    @Auth
    @PostMapping("/posts")
    @ApiOperation(value = "포스트 작성")
    public Object createPost(HttpSession httpSession, @RequestBody PostDto post) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = userService.findUserByEmail(email);
        
        if(post.getContent().equals("")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        
        post.setUid(user.getId());
        post.setCDate(Calendar.getInstance().getTime());
        post.setCntLike(0);
        postService.createPost(post);
		return new ResponseEntity<>(post.getId(), HttpStatus.OK);
    }

    @Auth
    @PutMapping("/posts")
    @ApiOperation(value = "포스트 수정")
    public Object updatePost(HttpSession httpSession, @RequestBody PostDto post) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = userService.findUserByEmail(email);
        if(post.getContent().equals("")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        PostDto oldPost = postService.getPostById(post.getId());
        if(user.getId() == oldPost.getUid()) {
        	oldPost.setTitle(post.getTitle());
        	oldPost.setContent(post.getContent());
        	oldPost.setPriority(post.getPriority());
        	oldPost.setIsTemp(post.getIsTemp());
	        postService.updatePost(oldPost);
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
    
    @PostMapping("/posts/cnt")
    @ApiOperation(value = "Diary에 작성된 전체 Post 수 조회 (84일)")
    public Map<Date, Integer> getAllPostCnt(@RequestBody HashMap<String, Integer> input) {
    	int uid = input.get("uid");
    	int did = input.get("did");
    	int isProj = input.get("isProj");
    	
    	return postService.getAllPostCnt(uid, did, isProj); 
    }
    
    @GetMapping("/posts/rate/odop")
    @ApiOperation(value = "회원 1Day 1Post 달성률")
    public HashMap<String, String> getOdopRate(HttpSession session, String uid) {
    	String email = (String) session.getAttribute("email");
    	UserDto user = userService.findUserByEmail(email);
    	int id = 0;
    	if(user!=null) id = user.getId();
    	if(uid!=null) id = Integer.parseInt(uid);
    	int days = postService.getOdopRate(id);
    	HashMap<String, String> output = new HashMap<String, String>();
		output.put("days", String.format("%d/%d", days, 84));
		output.put("rate", String.format("%.2f", (double)days/84*100));
		return output;
    }
}
