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
		List<UserDto> uList = userService.getAllGitUsers();
		GitHubRestApiHelper checkHelper = new GitHubRestApiHelper();
		for(UserDto user : uList) {
			int uid = user.getId();
			String gitId = user.getGitId();
			String accessToken = user.getGitToken();
			if(!checkHelper.checkOauth(gitId, accessToken)) continue;
			GitHubRestApiHelper helper = new GitHubRestApiHelper(accessToken);
			Map<String, String> map = helper.getOdocRate(diaryService.getAllWrittenRepoId(uid), gitId);
			RateDto rate = new RateDto();
			rate.setUid(uid);
			rate.setOdocCnt(map.get("days"));
			rate.setOdocRate(map.get("rate"));
			rateService.setOdocOfAllUsers(rate);
		}
		
    	Calendar cal = Calendar.getInstance();
    	Date eDate = new Date(cal.getTimeInMillis());
    	cal.add(Calendar.MONTH, -3);
    	Date sDate = new Date(cal.getTimeInMillis());
    	cal.setTimeInMillis(eDate.getTime()-sDate.getTime());
    	int days = cal.get(Calendar.DAY_OF_YEAR)-1;
    	
		uList = userService.getAllUsers();
		for(UserDto user : uList) {
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
		System.out.println("SETTING DONE");
	}
}
