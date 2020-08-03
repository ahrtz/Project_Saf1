package io.ssafy.p.i3a110.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.TagDto;
@Mapper
public interface TagDao {

	public List<TagDto> getAllTagsByPost(String pid);

	public List<TagDto> getTagsByKeyword(String keyword);

	public List<TagDto> getTopNTags(String uid, String num);

	public void addTag(TagDto tagDto);

	public void deleteTag(String id);
}
