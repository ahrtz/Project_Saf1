package io.ssafy.p.i3a110.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.LikeDao;
import io.ssafy.p.i3a110.dao.PostDao;
import io.ssafy.p.i3a110.dto.LikeDto;

@Service
public class LikeService {
    @Autowired
    private LikeDao likeDao;
    @Autowired
    private PostDao postDao;

    public LikeDto getLike(int uid, int pid) {
        return likeDao.getLike(uid, pid);
    }

    public void updateLike(LikeDto like) {
    	if(like.getStatus()==1) postDao.plusLike(like.getPid());
    	else postDao.minusLike(like.getPid());
    	
    	if(likeDao.getLike(like.getUid(), like.getPid()) == null) {
    		likeDao.makeLike(like);
    	}else {
    		likeDao.updateLike(like);
    	}
    }
    
	public List<HashMap<Object, Object>> getLikeCntByType(String type) {
		if(type.equals("0")) {
			return likeDao.getLikeCntByUser();
		}else{
			return likeDao.getLikeCntByDiary();
		}
	}
}
