package io.ssafy.p.i3a110.service;

import io.ssafy.p.i3a110.dao.PostDao;
import io.ssafy.p.i3a110.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

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

}
