package io.ssafy.p.i3a110.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.LangDto;
@Mapper
public interface LangDao {

	public void createLang(LangDto lang);

	public ArrayList<String> getLanguagesByDid(int did);
}
