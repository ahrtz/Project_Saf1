package io.ssafy.p.i3a110;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.ssafy.p.i3a110.service.InitService;

@Component
public class Scheduler {
	
	@Autowired
	private InitService initService;
	
	@Scheduled(cron = "* * 5 * * ?")
	public void setRate() {
		initService.setRate();
	}
}
