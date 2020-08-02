package io.ssafy.p.i3a110.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ssafy.p.i3a110.dto.CommitListDto;
import io.ssafy.p.i3a110.service.CommitService;
import io.swagger.annotations.ApiOperation;

@RestController
public class CommitController {
	@Autowired
	private CommitService commitService;
	
	@GetMapping("/commits/{pid}")
	@ApiOperation(value = "Post 별 Commit 조회")
	public List<CommitListDto> getAllCommitsByPost(@PathVariable String pid) {
		return commitService.getAllCommitsByPost(pid);
	}
	
	@PostMapping("/commits")
	@ApiOperation(value = "포스트에 Commit 추가")
	public void addCommit(@RequestBody CommitListDto commit) {
		commitService.addCommit(commit);
	}
	
	@DeleteMapping("/commits/{id}")
	@ApiOperation(value = "Commit 삭제")
	public void deleteCommit(@PathVariable String id) {
		commitService.deleteCommit(id);
	}
}
