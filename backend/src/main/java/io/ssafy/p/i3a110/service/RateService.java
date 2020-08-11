package io.ssafy.p.i3a110.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.RateDao;
import io.ssafy.p.i3a110.dto.RateDto;

@Service
public class RateService {
	@Autowired
	private RateDao rateDao;

	public RateDto getRateByUid(int uid) {
		return rateDao.getRateByUid(uid);
	}
	
	public void setOdocOfAllUsers(RateDto rate) {
		int uid = rate.getUid();
		if(rateDao.getRateByUid(uid) == null) {
			rateDao.createOdocOfAllUsers(rate);
		}else {
			rateDao.updateOdocOfAllUsers(rate);
		}
	}
	
	public void setOdopOfAllUsers(RateDto rate) {
		int uid = rate.getUid();
		if(rateDao.getRateByUid(uid) == null) {
			rateDao.createOdopOfAllUsers(rate);
		}else {
			rateDao.updateOdopOfAllUsers(rate);
		}
	}
}
