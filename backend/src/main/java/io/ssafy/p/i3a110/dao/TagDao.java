package io.ssafy.p.i3a110.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.TagDto;
@Mapper
public interface TagDao {

	public List<TagDto> getAllTagsByPost(String pid);

	public List<TagDto> getTagsByKeyword(String keyword);

	public List<HashMap<Object, Object>> getTopNTags(String uid, int num);

	public void addTag(TagDto tagDto);

	public void deleteTag(String id);

	public TagDto getTagById(String id);
}
