package io.ssafy.p.i3a110.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.CommentDto;

@Mapper
public interface CommentDao {

	List<CommentDto> getAllCommentsByPost(String pid);

	void addComment(CommentDto commentDto);

	void deleteComment(String id, int uid);

}
