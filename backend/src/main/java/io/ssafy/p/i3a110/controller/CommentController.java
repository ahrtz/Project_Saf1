package io.ssafy.p.i3a110.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ssafy.p.i3a110.dto.CommentDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.service.CommentService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/comments/{pid}")
	@ApiOperation(value = "포스트 별 댓글 조회")
	public List<CommentDto> getAllCommentsByPost(@PathVariable String pid){
		return commentService.getAllCommentsByPost(pid);
	}
	
	@PostMapping("/comments")
	@ApiOperation(value = "댓글 작성")
	public void addComment(HttpSession session, @RequestBody CommentDto commentDto) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		int uid = user.getId();
		commentDto.setUid(uid);
		
		commentService.addComment(commentDto);
	}
	
	@DeleteMapping("/comments/{id}")
	@ApiOperation(value = "댓글 삭제")
	public void deleteComment(HttpSession session, @PathVariable String id) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		int uid = user.getId();
		
		commentService.deleteComment(id, uid);
	}
}
