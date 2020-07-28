package io.ssafy.p.i3a110.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.HealthDao;
import io.ssafy.p.i3a110.dto.HealthDto;

@Service
public class HealthService {
	@Autowired
	private HealthDao healthDao;
	
	public HealthDto getHealth() {
		return healthDao.getHealth();
	}
}
