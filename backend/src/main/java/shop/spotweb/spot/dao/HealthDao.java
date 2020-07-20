package shop.spotweb.spot.dao;

import org.apache.ibatis.annotations.Mapper;

import shop.spotweb.spot.dto.HealthDto;
@Mapper
public interface HealthDao {
	public HealthDto getHealth();
}
