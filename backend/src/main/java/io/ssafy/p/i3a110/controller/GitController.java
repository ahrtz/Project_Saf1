package io.ssafy.p.i3a110.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ssafy.p.i3a110.apihelper.GitHubRestApiHelper;
import io.ssafy.p.i3a110.dto.CommitInfoDto;
import io.ssafy.p.i3a110.dto.RepositoryInfoDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.service.DiaryService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class GitController {
	
	private GitHubRestApiHelper helper;
	@Autowired
	private UserService userService;
	@Autowired
	private DiaryService diaryService;

	@PostMapping("/gits")
	@ApiOperation(value = "Git AccessToken 검증")
	public boolean checkOauth(@RequestBody HashMap<String, String> map) {
		String gitid = map.get("gitid");
		String accesstoken = map.get("accesstoken");
		helper = new GitHubRestApiHelper();
		return helper.checkOauth(gitid, accesstoken);
	}
	
	@GetMapping("/gits/repositories")
	@ApiOperation(value = "사용자 Repoitory 전체 조회")
	public List<RepositoryInfoDto> getAllRepositories(HttpSession session) {
		List<RepositoryInfoDto> list = null;
		int uid = (int) session.getAttribute("id");
		UserDto user = userService.findUserById(uid);
		if(user.getIsCertified()==1) {
			helper = new GitHubRestApiHelper(user.getGitToken());
			list = helper.getAllRepositoryInfo();
		}else {
			
		}
		return list;
	}
	
	@PostMapping("/gits/commits/cnt")
	@ApiOperation(value = "Repo Commit 수 조회")
	public HashMap<Date, Integer> getAllCommitCnt(HttpSession session, @RequestBody HashMap<String, String> input) {
		HashMap<Date, Integer> map = new HashMap<Date, Integer>();
		int uid = (int) session.getAttribute("id");
		UserDto user = userService.findUserById(uid);
		String repoName = input.get("reponame");
		if(user.getIsCertified()==1) {
			helper = new GitHubRestApiHelper(user.getGitToken());
			if(repoName == null) {
				map = helper.getAllCommitCnt(diaryService.getAllWrittenProjectName(uid));
			}else {
				map = helper.getCommitCnt(repoName);
			}
		}else {
			
		}
		return map;
	}
	
//	//Test
//	@PostMapping("/gits/commits/cnt")
//	@ApiOperation(value = "Repo Commit 수 조회")
//	public HashMap<Date, Integer> getAllCommitCnt(HttpSession session, @RequestBody HashMap<String, String> m) {
//		HashMap<Date, Integer> map = new HashMap<Date, Integer>();
//		ArrayList<String> list = new ArrayList<String>();
//		list.add("private");
//		String token = m.get("token");
//		System.out.println(token);
//		helper = new GitHubRestApiHelper(token);
//		map = helper.getAllCommitCnt(list);
//		return map;
//	}
	
	@PostMapping("/gits/commits")
	@ApiOperation(value = "Repoitory 전체 Commit 조회")
	public List<CommitInfoDto> getAllCommitsByRepo(HttpSession session, @RequestBody HashMap<String, String> map) {
		List<CommitInfoDto> list = null;
		int uid = (int) session.getAttribute("id");
		UserDto user = userService.findUserById(uid);
		String repoName = map.get("reponame");
		String sDate = map.get("sdate");
		String eDate = map.get("edate");
		
		if(user.getIsCertified()==1) {
			helper = new GitHubRestApiHelper(user.getGitToken());
			if(sDate == null || eDate == null) {
				list = helper.getAllCommitInfoList(repoName);
			}else {
				list = helper.getCommitInfoListByPeriod(repoName, sDate, eDate);
			}
		}else {
			
		}
		return list;
	}
	
//	//Test
//	@PostMapping("/gits/commits")
//	@ApiOperation(value = "Repoitory 전체 Commit 조회")
//	public List<CommitInfoDto> getAllCommitsByRepo(HttpSession session, @RequestBody HashMap<String, String> map) {
//		List<CommitInfoDto> list = null;
//		String repoName = map.get("reponame");
//		helper = new GitHubRestApiHelper(map.get("token"));
//		list = helper.getAllCommitInfoList(repoName);
//		return list;
//	}
	
//	//Test
//	@PostMapping("/gits/repositories")
//	@ApiOperation(value = "사용자 Repoitory 전체 조회")
//	public List<RepositoryInfoDto> getAllRepositories(@RequestBody HashMap<String, String> map) {
//		List<RepositoryInfoDto> list = null;
//		helper = new GitHubRestApiHelper(map.get("token"));
//		list = helper.getAllRepositoryInfo();
//		
//		return list;
//	}
}
