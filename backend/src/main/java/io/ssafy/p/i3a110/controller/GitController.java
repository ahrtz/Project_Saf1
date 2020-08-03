package io.ssafy.p.i3a110.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
		String gitid = map.get("gitId");
		String accesstoken = map.get("accessToken");
		helper = new GitHubRestApiHelper();
		return helper.checkOauth(gitid, accesstoken);
	}
	
	@GetMapping("/gits/repositories")
	@ApiOperation(value = "사용자 Repoitory 전체 조회")
	public List<RepositoryInfoDto> getAllRepositories(HttpSession session) {
		List<RepositoryInfoDto> list = null;
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		if(user.getIsCertified()==1) {
			helper = new GitHubRestApiHelper(user.getGitToken());
			list = helper.getAllRepositoryInfo();
		}else {
			
		}
		return list;
	}
	
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
	
	@PostMapping("/gits/commits")
	@ApiOperation(value = "Repoitory 전체 Commit 조회")
	public List<CommitInfoDto> getAllCommitsByRepo(HttpSession session, @RequestBody HashMap<String, String> map) {
		List<CommitInfoDto> list = null;
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		String repoName = map.get("repoName");
		String sDate = map.get("sDate");
		String eDate = map.get("eDate");
		
		if(user.getIsCertified()==1) {
			helper = new GitHubRestApiHelper(user.getGitToken());
			list = helper.getCommitInfoListByPeriod(repoName, sDate, eDate);
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
	
	@PostMapping("/gits/commits/cnt")
	@ApiOperation(value = "Repo Commit 수 조회")
	public HashMap<Date, Integer> getAllCommitCnt(HttpSession session, @RequestBody HashMap<String, String> input) {
		HashMap<Date, Integer> map = new HashMap<Date, Integer>();
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		String repoName = input.get("repoName");
		if(user.getIsCertified()==1) {
			helper = new GitHubRestApiHelper(user.getGitToken());
			if(repoName == null) {
				map = helper.getAllCommitCnt(diaryService.getAllWrittenProjectName(user.getId()));
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
	
}
