package io.ssafy.p.i3a110.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.CommentDao;
import io.ssafy.p.i3a110.dto.HealthDto;

@Service
public class CommentService {
	@Autowired
	private CommentDao CommentDao;
	
}
