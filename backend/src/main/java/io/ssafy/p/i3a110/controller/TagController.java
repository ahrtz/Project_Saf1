package io.ssafy.p.i3a110.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.ssafy.p.i3a110.dto.PostDto;
import io.ssafy.p.i3a110.dto.TagDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.interceptor.Auth;
import io.ssafy.p.i3a110.service.PostService;
import io.ssafy.p.i3a110.service.TagService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class TagController {
	@Autowired
	private TagService tagService;
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;

	@GetMapping("/tags/{pid}")
	@ApiOperation(value = "포스트 별 태그 조회")
	public List<TagDto> getAllTagsByPost(@PathVariable String pid){
		return tagService.getAllTagsByPost(pid);
	}
	
	@PostMapping("/tags")
	@ApiOperation(value = "태그 검색")
	public List<HashMap<Object, Object>> getTagsByKeyword(@RequestBody HashMap<String, String> map){
		String keyword = map.get("keyword");
		List<TagDto> tags = tagService.getTagsByKeyword(keyword);
		
		ObjectMapper objectMapper = new ObjectMapper();
    	List<HashMap<Object, Object>> output = new ArrayList<HashMap<Object,Object>>();
		for(TagDto tag : tags) {
			HashMap<Object, Object> form = objectMapper.convertValue(tag, HashMap.class);
			PostDto postinfo = postService.getPostById(tag.getPid());
			form.put("postinfo", postinfo);
			
    		UserDto writer = userService.findUserById(postinfo.getUid());
    		HashMap<String, String> userinfo = new HashMap<String, String>();
    		userinfo.put("id", String.valueOf(writer.getId()));
    		userinfo.put("email", writer.getEmail());
    		userinfo.put("nickname", writer.getNickname());
    		userinfo.put("img", writer.getImg());
    		userinfo.put("intro", writer.getIntro());
    		form.put("userinfo", userinfo);
    		
			output.add(form);
		}
		return output;
	}
	
	@PostMapping("/tags/rank")
	@ApiOperation(value = "회원 별 태그 상위 num개 조회")
	public List<HashMap<Object, Object>> getTopNTags(@RequestBody HashMap<String, Integer> map){
		int did = map.get("did");
		int uid = map.get("uid");
		int num = map.get("num");
		return tagService.getTopNTags(did,uid,num);
	}
	
	@Auth
	@PostMapping("/tags/add")
	@ApiOperation(value = "태그 생성")
	public Object addTag(HttpSession session, @RequestBody TagDto tagDto){
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		PostDto post = postService.getPostById(tagDto.getPid());
		if(user.getId()==post.getUid()) {
			tagService.addTag(tagDto);
			return new ResponseEntity<>(tagDto.getId(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@Auth
	@DeleteMapping("/tags/{id}")
	@ApiOperation(value = "태그 삭제")
	public Object deleteTag(HttpSession session, @PathVariable String id){
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		TagDto tag = tagService.getTagById(id);
		PostDto post = postService.getPostById(tag.getPid());
		if(user.getId()==post.getUid()) {
			tagService.deleteTag(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
