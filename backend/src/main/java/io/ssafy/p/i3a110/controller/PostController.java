package io.ssafy.p.i3a110.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.ssafy.p.i3a110.dto.PostDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.http.request.GetPostRequest;
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

//	  // OLD
//    // 0: blog 1: project 2: all
//    @PostMapping("/posts/all")
//    @ApiOperation(value = "회원별 다이어리 전체 조회")
//    public ArrayList<PostDto> getAllPostByUser(@RequestBody HashMap<String, String> map) {
//    	String uid = map.get("uid");
//    	int type = Integer.parseInt(map.get("type"));
//    	
//    	return postService.getAllPostByUser(uid, type);
//    }
   
    // 0: blog 1: project 2: all
    @PostMapping("/posts/all")
    @ApiOperation(value = "회원별 포스트 전체 조회")
    public List<HashMap<Object, Object>> getAllPostByUser(@RequestBody HashMap<String, String> map) {
    	String uid = map.get("uid");
    	int type = Integer.parseInt(map.get("type"));
    	String keyword = map.get("keyword");
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	List<HashMap<Object, Object>> output = new ArrayList<HashMap<Object,Object>>();
    	ArrayList<PostDto> postList = postService.getAllPostByUser(uid, type, keyword);
    	for(PostDto post : postList) {
    		HashMap<Object, Object> form = objectMapper.convertValue(post, HashMap.class);
    		form.put("userinfo", userService.findUserById(post.getUid()));
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

    @PostMapping("/posts")
    @ApiOperation(value = "포스트 작성")
    public void createPost(HttpSession httpSession, @RequestBody HashMap<String, String> map) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = userService.findUserByEmail(email);
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        Timestamp ts = Timestamp.valueOf(formatter.format(Calendar.getInstance().getTime()));
        System.out.println( " Timestamp : " + ts);

        PostDto post = new PostDto();
        System.out.print(user.getId());
        post.setUid(user.getId());
        post.setDid(Integer.parseInt(map.get("did")));
        post.setTitle(map.get("title"));
        post.setContent(map.get("content"));
        post.setCDate(ts.toString());
        post.setPriority(Integer.parseInt(map.get("priority")));
        post.setCntLike(0);
        post.setIsTemp(Integer.parseInt(map.get("is_temp")));

        postService.createPost(post);
    }

    @PutMapping("/posts")
    @ApiOperation(value = "포스트 수정")
    public void updatePost(HttpSession httpSession, @RequestBody HashMap<String, String> map) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = userService.findUserByEmail(email);

        PostDto post = postService.getPostById(Integer.parseInt(map.get("id")));

        post.setTitle(map.get("title"));
        post.setContent(map.get("content"));
        post.setPriority(Integer.parseInt(map.get("priority")));

        postService.updatePost(post);
    }

    @DeleteMapping("/posts/{id}")
    @ApiOperation(value = "포스트 삭제")
    public void deletePost(@PathVariable int id) {

        postService.deletePost(id);
    }

}
