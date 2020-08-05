package io.ssafy.p.i3a110.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.ScrapDao;
import io.ssafy.p.i3a110.dto.ScrapDto;

@Service
public class ScrapService {
	@Autowired
	private ScrapDao scrapDao;

	public List<ScrapDto> getAllScrapsByUser(int uid) {
		return scrapDao.getAllScrapsByUser(uid);
	}
	
	public ScrapDto getScrap(int uid, int pid) {
		return scrapDao.getScrap(uid, pid);
	}

	public void createScrap(ScrapDto scrapDto) {
		scrapDao.createScrap(scrapDto);
	}

	public void updateScrap(ScrapDto scrapDto) {
		scrapDao.updateScrap(scrapDto);
	}
	
	public void deleteScrap(int uid, int pid) {
		scrapDao.deleteScrap(uid, pid);
	}

}
