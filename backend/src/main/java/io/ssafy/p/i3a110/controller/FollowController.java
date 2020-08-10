package io.ssafy.p.i3a110.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ssafy.p.i3a110.dto.FollowDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.interceptor.Auth;
import io.ssafy.p.i3a110.service.FollowService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class FollowController {
	@Autowired
	private FollowService followService;
	@Autowired
	private UserService userService;

	@Auth
	@PostMapping("/follows")
	@ApiOperation(value = "회원 팔로워/팔로잉 확인")
	public Object getFollowInfo(HttpSession session, @RequestBody HashMap<String, String> map) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		int uid = user.getId();
		int type = Integer.parseInt(map.get("type"));
		
		HashMap<Object, Object> output = new HashMap<Object, Object>();
		
		List<Integer> uList = followService.getFollowInfo(type, uid);
		output.put("total", String.valueOf(uList.size()));
		List<HashMap<String, String>> form = new ArrayList<HashMap<String, String>>();
		for(Integer u : uList) {
    		UserDto followUser = userService.findUserById(u);
    		HashMap<String, String> userinfo = new HashMap<String, String>();
    		userinfo.put("id", String.valueOf(followUser.getId()));
    		userinfo.put("email", followUser.getEmail());
    		userinfo.put("nickname", followUser.getNickname());
    		userinfo.put("img", followUser.getImg());
    		userinfo.put("intro", followUser.getIntro());
    		form.add(userinfo);
		}
		output.put("userinfo", form);
		return output;
	}
	
	@Auth
	@PutMapping("/follows")
	@ApiOperation(value = "팔로잉 생성 및 수정")
	public Object updateFollow(HttpSession session, @RequestBody FollowDto follow) {
		String email = (String)session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		follow.setUserFrom(user.getId());
		followService.updateFollow(follow);
		return new ResponseEntity<>(follow.getId(), HttpStatus.OK);
	}
}
