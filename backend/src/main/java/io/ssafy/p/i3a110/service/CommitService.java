package io.ssafy.p.i3a110.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.CommitDao;
import io.ssafy.p.i3a110.dto.CommitListDto;

@Service
public class CommitService {
	@Autowired
	private CommitDao commitDao;

	public List<CommitListDto> getAllCommitsByPost(String pid) {
		return commitDao.getAllCommitsByPost(pid);
	}

	public void addCommit(CommitListDto commit) {
		commitDao.addCommit(commit);
	}

	public void deleteCommit(String id) {
		commitDao.deleteCommit(id);
	}
}
