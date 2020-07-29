package io.ssafy.p.i3a110.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.ssafy.p.i3a110.dto.CommitListDto;
import io.ssafy.p.i3a110.service.CommitService;

@RestController
public class CommitController {
	@Autowired
	private CommitService commitService;
	
	//포스트 별 추가된 커밋 조회
	@GetMapping("/commits/{pid}")
	public List<CommitListDto> getAllCommitsByPost(@PathVariable String pid) {
		return commitService.getAllCommitsByPost(pid);
	}
	
//	@RequestMapping("/health")
//	public HealthDto getHealth() {
//		return healthService.getHealth();
//	}
	
	@GetMapping("/test")
	public String test() {
		return "Success";
	}
}
