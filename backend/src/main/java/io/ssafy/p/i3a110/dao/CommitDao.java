package io.ssafy.p.i3a110.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.CommitListDto;
@Mapper
public interface CommitDao {
	public CommitListDto getHealth();

	public List<CommitListDto> getAllCommitsByPost(String pid);

	public void addCommit(CommitListDto commit);

	public void deleteCommit(String id);

	public int getUidById(String id);
}
