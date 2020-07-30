package io.ssafy.p.i3a110.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.CommentDao;
import io.ssafy.p.i3a110.dto.CommentDto;
import io.ssafy.p.i3a110.dto.HealthDto;

@Service
public class CommentService {
	@Autowired
	private CommentDao commentDao;

	public List<CommentDto> getAllCommentsByPost(String pid) {
		return commentDao.getAllCommentsByPost(pid);
	}

	public void addComment(CommentDto commentDto) {
		commentDao.addComment(commentDto);
	}

	public void deleteComment(String id) {
		commentDao.deleteComment(id);
	}
	
}
