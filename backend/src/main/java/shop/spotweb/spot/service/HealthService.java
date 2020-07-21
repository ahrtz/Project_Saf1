package shop.spotweb.spot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.spotweb.spot.dao.HealthDao;
import shop.spotweb.spot.dto.HealthDto;

@Service
public class HealthService {
	@Autowired
	private HealthDao healthDao;
	
	public HealthDto getHealth() {
		return healthDao.getHealth();
	}
}
