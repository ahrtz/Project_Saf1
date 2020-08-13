package io.ssafy.p.i3a110.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.ssafy.p.i3a110.dto.CommentDto;
import io.ssafy.p.i3a110.dto.PostDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.interceptor.Auth;
import io.ssafy.p.i3a110.service.CommentService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	
//	//OLD
//	@GetMapping("/comments/{pid}")
//	@ApiOperation(value = "포스트 별 댓글 조회")
//	public List<CommentDto> getAllCommentsByPost(@PathVariable String pid){
//		return commentService.getAllCommentsByPost(pid);
//	}
	
	@GetMapping("/comments/{pid}")
	@ApiOperation(value = "포스트 별 댓글 조회")
	public List<HashMap<Object, Object>> getAllCommentsByPost(@PathVariable String pid){
		ObjectMapper objectMapper = new ObjectMapper();
    	List<HashMap<Object, Object>> output = new ArrayList<HashMap<Object,Object>>();
    	List<CommentDto> commentList = commentService.getAllCommentsByPost(pid);
    	for(CommentDto comment : commentList) {
    		HashMap<Object, Object> form = objectMapper.convertValue(comment, HashMap.class);
    		long cdate = (long) form.get("cdate");
    		form.put("cdate", new Date(cdate));
    		UserDto writer = userService.findUserById(comment.getUid());
    		HashMap<String, String> userinfo = new HashMap<String, String>();
    		userinfo.put("id", String.valueOf(writer.getId()));
    		userinfo.put("email", writer.getEmail());
    		userinfo.put("nickname", writer.getNickname());
    		userinfo.put("img", writer.getImg());
    		userinfo.put("intro", writer.getIntro());
    		
    		form.put("userinfo", userinfo);
    		output.add(form);
    	}
    	return output;
	}
	
	@Auth
	@PostMapping("/comments")
	@ApiOperation(value = "댓글 작성")
	public Object addComment(HttpSession session, @RequestBody CommentDto comment) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		
		String content = comment.getContent();
		if(content.equals("") || content.length()>100) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		int uid = user.getId();
		comment.setUid(uid);
		comment.setCDate(Calendar.getInstance().getTime());
		commentService.addComment(comment);
		return new ResponseEntity<>(comment.getId(), HttpStatus.OK);
	}
	
	@Auth
	@DeleteMapping("/comments/{id}")
	@ApiOperation(value = "댓글 삭제")
	public Object deleteComment(HttpSession session, @PathVariable String id) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		int uid = user.getId();
		
		commentService.deleteComment(id, uid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
