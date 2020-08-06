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
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.interceptor.Auth;
import io.ssafy.p.i3a110.service.GroupService;
import io.ssafy.p.i3a110.service.PostService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class GroupController {
	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	
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
		
		GroupRelationDto check = groupService.checkMember(id, user.getId());
		if(check != null) {
			GroupDto group = groupService.getGroupInfoById(id);
			ObjectMapper objectMapper = new ObjectMapper();
			HashMap<Object, Object> output = objectMapper.convertValue(group, HashMap.class);
    		UserDto leader = userService.findUserById(group.getLid());
    		output.put("lName", leader.getNickname());
    		output.put("lEmail", leader.getEmail());
    		output.put("mCnt", groupService.getMemberCntById(group.getId()));
			List<String> uList = new ArrayList<String>();
			uList = groupService.getUserListById(id);
			
			List<HashMap<Object, Object>> list = new ArrayList<HashMap<Object, Object>>();
			for(String uid : uList) {
				UserDto tUser = userService.findUserById(Integer.parseInt(uid));
	    		HashMap<Object, Object> userinfo = new HashMap<Object, Object>();
	    		userinfo.put("id", String.valueOf(tUser.getId()));
	    		userinfo.put("email", tUser.getEmail());
	    		userinfo.put("nickname", tUser.getNickname());
	    		userinfo.put("img", tUser.getImg());
	    		userinfo.put("intro", tUser.getIntro());
	    		userinfo.put("lastPost", postService.getLastPostDate(Integer.parseInt(uid)));
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
	@PutMapping("/groups/user")
	@ApiOperation(value = "그룹 회원 삭제")
	public Object withdrawGroup(HttpSession session, @RequestBody HashMap<String, String> map) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		String oid = map.get("oid");
		int uid = Integer.parseInt(map.get("uid"));
		
		if(groupService.getGroupInfoById(oid).getLid() == user.getId() && uid!=user.getId()) {
			GroupRelationDto delRelation = new GroupRelationDto();
			delRelation.setOid(Integer.parseInt(oid));
			delRelation.setUid(uid);
			groupService.withdrawGroup(delRelation);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@Auth
	@PutMapping("/groups")
	@ApiOperation(value = "그룹 수정")
	public Object updateGroup(HttpSession session, @RequestBody GroupDto groupDto) {
		String email = (String)session.getAttribute("email");
		int uid = userService.findUserByEmail(email).getId();
		
		if(uid == groupService.getGroupInfoById(String.valueOf(groupDto.getId())).getLid() && uid == groupDto.getLid()) {
			groupService.updateGroup(groupDto);
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
	
	@Auth
	@PostMapping("/groups/top")
	@ApiOperation(value = "그룹 상위 회원 조회")
	public Object getTopNUserByType(HttpSession session, @RequestBody HashMap<String, String> map) {
		String email = (String) session.getAttribute("email");
		int uid = userService.findUserByEmail(email).getId();
		int oid = Integer.parseInt(map.get("oid"));
		int type = Integer.parseInt(map.get("type"));
		int cnt = Integer.parseInt(map.get("cnt"));
		GroupRelationDto check = groupService.checkMember(map.get("oid"), uid);
		if(check==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {
			List<HashMap<String, String>> output = groupService.getTopNUserByType(oid,type,cnt);
			return new ResponseEntity<>(output, HttpStatus.OK);
		}
	}
}
