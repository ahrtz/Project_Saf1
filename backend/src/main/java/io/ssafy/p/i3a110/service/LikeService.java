package io.ssafy.p.i3a110.service;

import io.ssafy.p.i3a110.dao.LikeDao;
import io.ssafy.p.i3a110.dto.LikeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private LikeDao likeDao;

    public int getLikeCnt(int pid) {
        return likeDao.getLikeCnt(pid);
    }

    public LikeDto getLike(int uid, int pid) {
        return likeDao.getLike(uid, pid);
    }

    public void updateLike(LikeDto like) {
        likeDao.updateLike(like);
    }
}