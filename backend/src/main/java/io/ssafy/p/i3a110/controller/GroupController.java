package io.ssafy.p.i3a110.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ssafy.p.i3a110.dto.GroupDto;
import io.ssafy.p.i3a110.service.GroupService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class GroupController {
	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;	
	@GetMapping("/groups/{lid}")
	@ApiOperation(value = "관리자 별 그룹 정보 조회")
	public GroupDto getGroupInfoByLeader(@PathVariable String lid) {
		return groupService.getGroupInfoByLeader(lid);
	}

	@PostMapping("/groups")
	@ApiOperation(value = "그룹 생성")
	public void createGroup(@RequestBody GroupDto groupDto) {
		groupService.createGroup(groupDto);
	}
	
	@PutMapping("/groups/{id}")
	@ApiOperation(value = "그룹 수정")
	public Object updateGroup(HttpSession session, @PathVariable String id, @RequestBody GroupDto groupDto) {
		String email = (String)session.getAttribute("email");
		int uid = userService.findUserByEmail(email).getId();
		if(uid == groupService.getGroupInfoById(id).getLid()) {
			groupService.updateGroup(id, groupDto);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/groups/{id}")
	@ApiOperation(value = "그룹 삭제")
	public Object deleteGroup(HttpSession session, @PathVariable String id) {
		String email = (String)session.getAttribute("email");
		int uid = userService.findUserByEmail(email).getId();
		if(uid == groupService.getGroupInfoById(id).getLid()) {
			groupService.deleteGroup(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
