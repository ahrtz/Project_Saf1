package io.ssafy.p.i3a110.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ssafy.p.i3a110.dto.TagDto;
import io.ssafy.p.i3a110.service.TagService;
import io.swagger.annotations.ApiOperation;

@RestController
public class TagController {
	@Autowired
	private TagService tagService;

	@GetMapping("/tags/{pid}")
	@ApiOperation(value = "포스트 별 태그 조회")
	public List<TagDto> getAllTagsByPost(@PathVariable String pid){
		return tagService.getAllTagsByPost(pid);
	}
	
	@PostMapping("/tags")
	@ApiOperation(value = "태그 검색")
	public List<TagDto> getTagsByKeyword(@RequestBody HashMap<String, String> map){
		String keyword = map.get("keyword");
		return tagService.getTagsByKeyword(keyword);
	}
	
	@GetMapping("/tags")
	@ApiOperation(value = "회원 별 태그 상위 num개 조회")
	public List<TagDto> getTopNTags(String uid, String num){
		return tagService.getTopNTags(uid,num);
	}
	
	@PostMapping("/tags/add")
	@ApiOperation(value = "태그 생성")
	public void addTag(@RequestBody TagDto tagDto){
		tagService.addTag(tagDto);
	}
	
	@DeleteMapping("/tags/{id}")
	@ApiOperation(value = "태그 삭제")
	public void deleteTag(@PathVariable String pid){
		tagService.deleteTag(pid);
	}
	
}
