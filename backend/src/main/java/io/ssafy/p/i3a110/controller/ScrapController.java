package io.ssafy.p.i3a110.controller;

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

import io.ssafy.p.i3a110.dto.ScrapDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.interceptor.Auth;
import io.ssafy.p.i3a110.service.ScrapService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class ScrapController {
	@Autowired
	private ScrapService scrapService;
	@Autowired
	private UserService userService;

	@GetMapping("/scraps/{uid}")
	@ApiOperation(value = "회원 스크랩 정보 조회")
	public List<ScrapDto> getAllScrapsByUser(@PathVariable int uid) {
		return scrapService.getAllScrapsByUser(uid);
	}
	
	@Auth
	@PostMapping("/scraps")
	@ApiOperation(value = "스크랩 생성")
	public Object createScrap(HttpSession session, @RequestBody ScrapDto scrapDto) {
		String email = (String)session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		int uid = user.getId();
		scrapDto.setUid(uid);
		
		scrapService.createScrap(scrapDto);
		return new ResponseEntity<>(HttpStatus.OK);
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
