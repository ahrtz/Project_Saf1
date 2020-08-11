package io.ssafy.p.i3a110;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.service.RateService;
import io.ssafy.p.i3a110.service.UserService;

@Component
public class Scheduler {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RateService rateService;
	
	@Scheduled(cron = "0 0 5 * * *")
	public void doJobsch() {
		List<UserDto> uList = userService.getAllUsers();
		for(UserDto user : uList) {
			
		}
	}
	
}
