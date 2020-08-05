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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.ssafy.p.i3a110.dto.GroupDto;
import io.ssafy.p.i3a110.dto.GroupRelationDto;
import io.ssafy.p.i3a110.dto.PostDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.interceptor.Auth;
import io.ssafy.p.i3a110.service.GroupService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class GroupController {
	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	
	@Auth
	@GetMapping("/groups")
	@ApiOperation(value = "회원 별 그룹 정보 조회")
	public List<HashMap<Object, Object>> getGroupListByLeader(HttpSession session) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		
    	ObjectMapper objectMapper = new ObjectMapper();
    	List<HashMap<Object, Object>> output = new ArrayList<HashMap<Object,Object>>();
    	List<GroupDto> groupList = groupService.getGroupInfoByUser(String.valueOf(user.getId()));
    	for(GroupDto group: groupList) {
    		HashMap<Object, Object> form = objectMapper.convertValue(group, HashMap.class);
    		UserDto leader = userService.findUserById(group.getLid());
    		form.put("lName", leader.getNickname());
    		form.put("lEmail", leader.getEmail());
    		form.put("mCnt", groupService.getMemberCntById(group.getId()));
    		output.add(form);
    	}
    	return output;
	}
	
	@Auth
	@GetMapping("/groups/{id}")
	@ApiOperation(value = "그룹 정보 상세 조회")
	public Object getGroupInfoById(HttpSession session, @PathVariable String id) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		GroupRelationDto group = groupService.getGroupInfoByIdAndUser(id, user.getId());
		if(group != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			HashMap<Object, Object> output = objectMapper.convertValue(group, HashMap.class);
			List<String> uList = new ArrayList<String>();
			uList = groupService.getUserListById(id);
			
			List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
			for(String uid : uList) {
				UserDto tUser = userService.findUserById(Integer.parseInt(uid));
	    		HashMap<String, String> userinfo = new HashMap<String, String>();
	    		userinfo.put("id", String.valueOf(tUser.getId()));
	    		userinfo.put("email", tUser.getEmail());
	    		userinfo.put("nickname", tUser.getNickname());
	    		userinfo.put("img", tUser.getImg());
	    		userinfo.put("intro", tUser.getIntro());
	    		list.add(userinfo);
			}
			output.put("userinfo", list);
			return new ResponseEntity<>(output,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@Auth
	@PostMapping("/groups")
	@ApiOperation(value = "그룹 생성")
	public Object createGroup(HttpSession session, @RequestBody GroupDto groupDto) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		groupDto.setLid(user.getId());
		groupService.createGroup(groupDto);
		
		return new ResponseEntity<>(groupDto.getId(), HttpStatus.OK);
	}

	@Auth
	@PostMapping("/groups/user")
	@ApiOperation(value = "그룹 회원 추가")
	public Object inviteGroup(HttpSession session, @RequestBody HashMap<String, String> map) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		
		int uid = userService.findUserByEmail(map.get("email")).getId();
		String oid = map.get("oid");
		if(groupService.getGroupInfoById(oid).getLid() == user.getId()) {
			GroupRelationDto groupRelationDto = new GroupRelationDto();
			groupRelationDto.setUid(uid);
			groupRelationDto.setOid(Integer.parseInt(oid));
			
			groupService.inviteGroup(groupRelationDto);
			return new ResponseEntity<>(groupRelationDto.getId(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@Auth
	@DeleteMapping("/groups/user")
	@ApiOperation(value = "그룹 회원 삭제")
	public Object withdrawGroup(HttpSession session, @RequestBody GroupRelationDto groupRelationDto) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		String oid = String.valueOf(groupRelationDto.getOid());
		if(groupService.getGroupInfoById(oid).getLid() == groupRelationDto.getUid()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(groupService.getGroupInfoById(oid).getLid() == user.getId() || groupRelationDto.getUid()==user.getId()) {
			groupService.withdrawGroup(groupRelationDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@Auth
	@PutMapping("/groups/{id}")
	@ApiOperation(value = "그룹 수정")
	public Object updateGroup(HttpSession session, @PathVariable String id, @RequestBody GroupDto groupDto) {
		String email = (String)session.getAttribute("email");
		int uid = userService.findUserByEmail(email).getId();
		
		if(uid == groupService.getGroupInfoById(id).getLid()) {
			groupService.updateGroup(id, groupDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@Auth
	@DeleteMapping("/groups/{id}")
	@ApiOperation(value = "그룹 삭제")
	public Object deleteGroup(HttpSession session, @PathVariable String id) {
		String email = (String)session.getAttribute("email");
		int uid = userService.findUserByEmail(email).getId();
		
		if(uid == groupService.getGroupInfoById(id).getLid()) {
			groupService.deleteGroup(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
