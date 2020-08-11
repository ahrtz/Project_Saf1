package io.ssafy.p.i3a110;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.ssafy.p.i3a110.apihelper.GitHubRestApiHelper;
import io.ssafy.p.i3a110.dto.RateDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.service.DiaryService;
import io.ssafy.p.i3a110.service.RateService;
import io.ssafy.p.i3a110.service.UserService;

@Component
public class Scheduler {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RateService rateService;
	@Autowired
	private DiaryService diaryService;
	
	@Scheduled(cron = "0 0 5 * * *")
	public void doJobsch() {
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
		
		uList = userService.getAllUsers();
//		for(UserDto user : uList) {
//			int uid = user.getId();
//	    	int days = postService.getOdopRate(id);
//	    	HashMap<String, String> output = new HashMap<String, String>();
//			output.put("days", String.format("%d/%d", days, 84));
//			output.put("rate", String.format("%.2f", (double)days/84*100));
//			return output;
//		}
	}
	
}
