package io.ssafy.p.i3a110.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.TagDao;
import io.ssafy.p.i3a110.dto.HealthDto;
import io.ssafy.p.i3a110.dto.TagDto;

@Service
public class TagService {
	@Autowired
	private TagDao tagDao;

	public List<TagDto> getAllTagsByPost(String pid) {
		return null;
	}

	public List<TagDto> getTagsByKeyword(String keyword) {
		return null;
	}

	public List<TagDto> getTopNTags(String uid, String num) {
		return null;
	}

	public void addTag(TagDto tagDto) {
		
	}

	public void deleteTag(String pid) {
		
	}
	
}
