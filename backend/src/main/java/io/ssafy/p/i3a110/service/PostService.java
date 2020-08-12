package io.ssafy.p.i3a110.service;

import io.ssafy.p.i3a110.dao.PostDao;
import io.ssafy.p.i3a110.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class PostService {
    @Autowired
    private PostDao postDao;
    
	public ArrayList<PostDto> getAllPostByUser(String uid, int isProj, String keyword, int isTemp, int limit) {
		if(uid==null || uid.equals("")) {
			return postDao.getAllPost(isProj, keyword, isTemp, limit);
		}else {
			return postDao.getAllPostByUser(uid, isProj, keyword, isTemp, limit);
		}
	}

    public ArrayList<PostDto> getPost(int did, String keyword, int isTemp) {
        return postDao.getPost(did, keyword, isTemp);
    }
    public PostDto getPostById(int id) {
        return postDao.getPostById(id);
    }
    public PostDto getPostByTitle(String keyword) {return postDao.getPostByTitle(keyword);}
    public int createPost(PostDto post) {
        return postDao.createPost(post);
    }
    public void updatePost(PostDto post) {
        postDao.updatePost(post);
    }
    public void deletePost(int id) {
        postDao.deletePost(id);
    }
    
    public HashMap<Object, Object> getLastPostDate(int id){
    	return postDao.getlastPostDate(id);
    }
    
    public int getOdopRate(int uid){
    	return postDao.getOdopRate(uid);
    }

	public Map<Date, Integer> getAllPostCnt(int uid, int did) {
		Date eDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(eDate);
		cal.add(Calendar.DATE, -84);
		Date sDate = new Date(cal.getTimeInMillis());
		eDate = cutTime(eDate);
		sDate = cutTime(sDate);
		Map<Date, Integer> output = getDateMap(sDate, eDate);
		
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> days = postDao.getAllPostCnt(uid, did);
		for(Map<String, Object> day : days) {
			int cnt = ((Long) day.get("cnt")).intValue();
			try {
				Date date = form.parse((String) day.get("date"));
				output.put(date, cnt);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
    
	private Date cutTime(Date x) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(x);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
    	return new Date(calendar.getTimeInMillis());
	}
	
	private Map<Date, Integer> getDateMap(Date sDate, Date eDate){
		Map<Date, Integer> map = new TreeMap<Date, Integer>();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(eDate.getTime()-sDate.getTime());
    	int days = calendar.get(Calendar.DAY_OF_YEAR);
    	calendar.setTime(sDate);
    	for (int i = 0; i < days-1; i++) {
			map.put(new Date(calendar.getTimeInMillis()), 0);
			calendar.add(Calendar.DATE, 1);
		}
		return map;
	}
}
