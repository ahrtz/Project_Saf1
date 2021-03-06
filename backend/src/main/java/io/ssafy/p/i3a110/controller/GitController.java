package io.ssafy.p.i3a110.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ssafy.p.i3a110.apihelper.GitHubRestApiHelper;
import io.ssafy.p.i3a110.dto.CommitInfoDto;
import io.ssafy.p.i3a110.dto.RepositoryInfoDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.interceptor.Auth;
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
	public boolean checkOauth(HttpSession session, @RequestBody HashMap<String, String> map) {
		helper = new GitHubRestApiHelper();
		String gitid = map.get("gitId");
		String accesstoken = map.get("accessToken");
		
		if(helper.checkOauth(gitid, accesstoken)) {
			if(session.getAttribute("email") != null) {
				String email = (String)session.getAttribute("email");
				UserDto user = userService.findUserByEmail(email);
				user.setGitId(gitid);
				user.setGitToken(accesstoken);
				userService.authenticateToken(user);
			}
			return true;
		}else {
			return false;
		}
	}
	
	@Auth
	@GetMapping("/gits/repositories")
	@ApiOperation(value = "사용자 Repoitory 전체 조회")
	public Object getAllRepositories(HttpSession session) {
		List<RepositoryInfoDto> list = null;
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		if(user.getIsCertified()==1) {
			if(!helper.checkOauth(user.getGitId(), user.getGitToken())) {
				userService.cancelToken(user.getId());
				return new ResponseEntity<>("AccessToken 오류/만료",HttpStatus.BAD_REQUEST);
			}
			helper = new GitHubRestApiHelper(user.getGitToken());
			list = helper.getAllRepositoryInfo();
			return new ResponseEntity<>(list, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
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
	
	@Auth
	@PostMapping("/gits/commits")
	@ApiOperation(value = "회원 Git Repository 별 Commit 정보 조회")
	public Object getAllCommitsByRepo(HttpSession session, @RequestBody HashMap<String, String> map) {
		List<CommitInfoDto> list = null;
		String email = (String) session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		String repoId = map.get("repoId");
		String sdate = map.get("sdate");
		String edate = map.get("edate");
		
		if(user.getIsCertified()==1) {
			if(!helper.checkOauth(user.getGitId(), user.getGitToken())) {
				userService.cancelToken(user.getId());
				return new ResponseEntity<>("AccessToken 오류/만료",HttpStatus.BAD_REQUEST);
			}
			helper = new GitHubRestApiHelper(user.getGitToken());
			list = helper.getCommitInfoListByPeriod(repoId, sdate, edate);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
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
	public Object getAllCommitCnt(@RequestBody HashMap<String, String> input) {
		int uid = Integer.parseInt(input.get("uid"));
		UserDto user = userService.findUserById(uid);
		String repoId = input.get("repoId").trim();
		Date eDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(eDate);
		cal.add(Calendar.DATE, -84);
		if(user.getIsCertified()==1) {
			if(!helper.checkOauth(user.getGitId(), user.getGitToken())) {
				userService.cancelToken(user.getId());
				return new ResponseEntity<>("AccessToken 오류/만료",HttpStatus.BAD_REQUEST);
			}
			helper = new GitHubRestApiHelper(user.getGitToken());
			List<String> repoIds = new ArrayList<String>();
			if(repoId == null || repoId.equals("")) {
				repoIds = diaryService.getAllWrittenRepoId(user.getId());
			}else {
				repoIds.add(repoId);
			}
			Map<Date, Integer> map = helper.getAllCommitCnt(repoIds, user.getGitId(),new Date(cal.getTimeInMillis()), eDate);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
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
	
	@Auth
	@GetMapping("gits/rate/odoc")
	@ApiOperation(value = "회원 1Day 1Commit 달성률")
	public Object getOdocRate(HttpSession session) {
		String email = (String)session.getAttribute("email");
		UserDto user = userService.findUserByEmail(email);
		if(user.getIsCertified()==1) {
			if(!helper.checkOauth(user.getGitId(), user.getGitToken())) {
				userService.cancelToken(user.getId());
				return new ResponseEntity<>("AccessToken 오류/만료",HttpStatus.BAD_REQUEST);
			}
			helper = new GitHubRestApiHelper(user.getGitToken());
			HashMap<String, String> output = helper.getOdocRate(diaryService.getAllWrittenRepoId(user.getId()));
			return new ResponseEntity<>(output, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
