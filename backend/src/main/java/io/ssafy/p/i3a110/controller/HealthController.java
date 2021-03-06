package io.ssafy.p.i3a110.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ssafy.p.i3a110.dto.HealthDto;
import io.ssafy.p.i3a110.service.HealthService;

@RestController
public class HealthController {
	@Autowired
	private HealthService healthService;

	@RequestMapping("/health")
	public HealthDto getHealth() {
		return healthService.getHealth();
	}

	@GetMapping("/test")
	public String test() {
		return "Success";
	}
}
