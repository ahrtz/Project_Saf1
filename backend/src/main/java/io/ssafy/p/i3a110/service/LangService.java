package io.ssafy.p.i3a110.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.LangDao;
import io.ssafy.p.i3a110.dto.LangDto;

@Service
public class LangService {
	@Autowired
	private LangDao langDao;

	public void createLang(LangDto lang) {
		langDao.createLang(lang);
	}

	public ArrayList<String> getLanguagesByDid(int did) {
		return langDao.getLanguagesByDid(did);
	}
}
