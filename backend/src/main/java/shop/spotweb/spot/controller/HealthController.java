package shop.spotweb.spot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.spotweb.spot.dto.HealthDto;
import shop.spotweb.spot.service.HealthService;

@RestController
public class HealthController {
	@Autowired
	private HealthService healthService;
	
	@RequestMapping("/health")
	public HealthDto getHealth() {
		return healthService.getHealth();
		
	}
}
