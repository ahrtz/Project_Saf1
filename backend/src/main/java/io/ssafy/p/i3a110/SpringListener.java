package io.ssafy.p.i3a110;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import io.ssafy.p.i3a110.apihelper.GitHubRestApiHelper;
import io.ssafy.p.i3a110.dto.RateDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.service.DiaryService;
import io.ssafy.p.i3a110.service.PostService;
import io.ssafy.p.i3a110.service.RateService;
import io.ssafy.p.i3a110.service.UserService;


public class SpringListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserService userService;
	@Autowired
	private RateService rateService;
	@Autowired
	private DiaryService diaryService;
	@Autowired
	private PostService postService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		List<UserDto> uList = userService.getAllGitUsers();
		GitHubRestApiHelper checkHelper = new GitHubRestApiHelper();
		for(UserDto user : uList) {
			int uid = user.getId();
			String gitId = user.getGitId();
			String accessToken = user.getGitToken();
			if(!checkHelper.checkOauth(gitId, accessToken)) continue;
			GitHubRestApiHelper helper = new GitHubRestApiHelper(accessToken);
			Map<String, String> map = helper.getOdocRate(diaryService.getAllWrittenProjectName(uid), gitId);
			RateDto rate = new RateDto();
			rate.setUid(uid);
			rate.setOdoc_cnt(map.get("days"));
			rate.setOdoc_rate(map.get("rate"));
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
	    	String odop_cnt = String.format("%d/%d", doPostDay, days);
	    	String odop_rate = String.format("%.2f", (double)doPostDay/days*100);
	    	rate.setOdop_cnt(odop_cnt);
	    	rate.setOdop_rate(odop_rate);
	    	rateService.setOdopOfAllUsers(rate);
		}
		System.out.println("SETTING DONE");
	}

}
