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

import io.ssafy.p.i3a110.dto.CommitListDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.interceptor.Auth;
import io.ssafy.p.i3a110.service.CommitService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class CommitController {
	@Autowired
	private CommitService commitService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/commits/{pid}")
	@ApiOperation(value = "Post 별 Commit 조회")
	public List<CommitListDto> getAllCommitsByPost(@PathVariable String pid) {
		return commitService.getAllCommitsByPost(pid);
	}
	
	@Auth
	@PostMapping("/commits")
	@ApiOperation(value = "포스트에 Commit 추가")
	public Object addCommit(HttpSession session, @RequestBody CommitListDto commit) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		commit.setUid(user.getId());
		commitService.addCommit(commit);
		return new ResponseEntity<>(commit.getId(), HttpStatus.OK);
	}
	
	@Auth
	@DeleteMapping("/commits/{id}")
	@ApiOperation(value = "Commit 삭제")
	public Object deleteCommit(HttpSession session, @PathVariable String id) {
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		
		if(user.getId() == commitService.getUidById(id)) {
			commitService.deleteCommit(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
