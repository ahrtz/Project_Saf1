package io.ssafy.p.i3a110.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.apihelper.GitHubRestApiHelper;
import io.ssafy.p.i3a110.dto.RateDto;
import io.ssafy.p.i3a110.dto.UserDto;

@Service
public class InitService {
	@Autowired
	private UserService userService;
	@Autowired
	private RateService rateService;
	@Autowired
	private DiaryService diaryService;
	@Autowired
	private PostService postService;
	
	@PostConstruct
	public void setRate() {
		System.out.println("Start Setting Rate...");
		List<UserDto> uList = userService.getAllGitUsers();
		int userCnt = uList.size();
		int idx = 0;
		System.out.println("OneDayOneCommit Start...");
		GitHubRestApiHelper checkHelper = new GitHubRestApiHelper();
		for(UserDto user : uList) {
			System.out.println(String.format("%d/%d ...", ++idx,userCnt));
			int uid = user.getId();
			String gitId = user.getGitId();
			String accessToken = user.getGitToken();
			if(!checkHelper.checkOauth(gitId, accessToken)) {
				System.out.println(String.format("Please Check User DB Table uid = %d", uid));
				userService.cancelToken(uid);
				continue;
			}
			GitHubRestApiHelper helper = new GitHubRestApiHelper(accessToken);
			Map<String, String> map = helper.getOdocRate(diaryService.getAllWrittenRepoId(uid), gitId);
			RateDto rate = new RateDto();
			rate.setUid(uid);
			rate.setOdocCnt(map.get("days"));
			rate.setOdocRate(map.get("rate"));
			rateService.setOdocOfAllUsers(rate);
		}
		System.out.println("OneDayOneCommit Done...");
    	Calendar cal = Calendar.getInstance();
    	Date eDate = new Date(cal.getTimeInMillis());
    	cal.add(Calendar.MONTH, -3);
    	Date sDate = new Date(cal.getTimeInMillis());
    	cal.setTimeInMillis(eDate.getTime()-sDate.getTime());
    	int days = cal.get(Calendar.DAY_OF_YEAR)-1;
    	
		uList = userService.getAllUsers();
		userCnt = uList.size();
		idx = 0;
		System.out.println("OneDayOnePost Start...");
		for(UserDto user : uList) {
			System.out.println(String.format("%d/%d ...", ++idx,userCnt));
			int uid = user.getId();
	    	int doPostDay = postService.getOdopRate(uid);
	    	RateDto rate = new RateDto();
	    	rate.setUid(uid);
	    	String odopCnt = String.format("%d/%d", doPostDay, days);
	    	String odopRate = String.format("%.2f", (double)doPostDay/days*100);
	    	rate.setOdopCnt(odopCnt);
	    	rate.setOdopRate(odopRate);
	    	rateService.setOdopOfAllUsers(rate);
		}
		System.out.println("OneDayOnePost Done...");
		System.out.println("SETTING DONE");
	}
}
