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
import io.ssafy.p.i3a110.service.CommentService;

@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;
	
//	@GetMapping("/comments/{pid}")
//	public List<CommentDto> getAllCommentsByPost(@PathVariable String pid){
//		return commentService.getAllCommentsByPost();
//	}
//	
//	@PostMapping("/comments")
//	public void addComment(HttpSession session, @RequestBody CommentDto commentDto) {
//		commentService.addComment(commentDto);
//	}
//	
//	@DeleteMapping("/comments/{id}")
//	public void deleteComment(HttpSession session, @PathVariable int id) {
//		commentService.deleteComment(id);
//	}
}
