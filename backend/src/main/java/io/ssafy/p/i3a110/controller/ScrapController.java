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
import io.ssafy.p.i3a110.dto.ScrapDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.interceptor.Auth;
import io.ssafy.p.i3a110.service.DiaryService;
import io.ssafy.p.i3a110.service.PostService;
import io.ssafy.p.i3a110.service.ScrapService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class ScrapController {
	@Autowired
	private ScrapService scrapService;
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
	private DiaryService diaryService;

	@Auth
	@GetMapping("/scraps")
	@ApiOperation(value = "회원 스크랩 정보 조회")
	public List<HashMap<Object, Object>> getAllScrapsByUser(HttpSession session) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		
    	ObjectMapper objectMapper = new ObjectMapper();
    	List<HashMap<Object, Object>> output = new ArrayList<HashMap<Object,Object>>();
		List<ScrapDto> scrapList = scrapService.getAllScrapsByUser(user.getId());
		for(ScrapDto scrap : scrapList) {
			HashMap<Object, Object> form = objectMapper.convertValue(scrap, HashMap.class);
			PostDto post = postService.getPostById(scrap.getPid());
			form.put("postinfo", post);
    		form.put("dName", diaryService.getDiary(String.valueOf(post.getDid())).getTitle());
    		
    		output.add(form);
		}
		
		return output;
	}
	
	@Auth
	@PostMapping("/scraps")
	@ApiOperation(value = "스크랩 생성 및 수정")
	public Object createScrap(HttpSession session, @RequestBody ScrapDto scrapDto) {
		String email = (String)session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		int uid = user.getId();
		scrapDto.setUid(uid);
		if(scrapService.getScrap(uid, scrapDto.getPid())==null) {
			scrapService.createScrap(scrapDto);
		}else {
			scrapService.updateScrap(scrapDto);
		}
		return new ResponseEntity<>(scrapDto.getId(), HttpStatus.OK);
	}
	
	@Auth
	@DeleteMapping("scraps/{pid}")
	@ApiOperation(value = "스크랩 삭제")
	public Object delelteScrap(HttpSession session, @PathVariable int pid) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		int uid = user.getId();
		
		scrapService.deleteScrap(uid, pid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
