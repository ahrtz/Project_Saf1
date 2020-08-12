package io.ssafy.p.i3a110.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.TagDao;
import io.ssafy.p.i3a110.dto.TagDto;

@Service
public class TagService {
	@Autowired
	private TagDao tagDao;

	public List<TagDto> getAllTagsByPost(String pid) {
		return tagDao.getAllTagsByPost(pid);
	}

	public List<TagDto> getTagsByKeyword(String keyword) {
		return tagDao.getTagsByKeyword(keyword);
	}

	public List<HashMap<Object, Object>> getTopNTags(int did, int uid, int num) {
		return tagDao.getTopNTags(did,uid,num);
	}

	public void addTag(TagDto tagDto) {
		tagDao.addTag(tagDto);
	}

	public void deleteTag(String id) {
		tagDao.deleteTag(id);
	}

	public TagDto getTagById(String id) {
		return tagDao.getTagById(id);
	}
	
}
