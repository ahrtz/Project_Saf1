package io.ssafy.p.i3a110.service;

import io.ssafy.p.i3a110.dao.PostDao;
import io.ssafy.p.i3a110.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PostService {
    @Autowired
    private PostDao postDao;

    public ArrayList<PostDto> getPost(int did, String keyword, int isTemp) {
        return postDao.getPost(did, keyword, isTemp);
    }
    public PostDto getPostById(int id) {
        return postDao.getPostById(id);
    }
    public void createPost(PostDto post) {
        postDao.createPost(post);
    }
    public void updatePost(PostDto post) {
        postDao.updatePost(post);
    }
    public void deletePost(int id) {
        postDao.deletePost(id);
    }
	public ArrayList<PostDto> getAllPostByUser(String uid, int type, String keyword) {
		if(uid==null || uid.equals("")) {
			switch (type) {
			case 0:
				return postDao.getAllBlogPost(keyword);
			case 1:
				return postDao.getAllProjectPost(keyword);
			default:
				return postDao.getAllDiaryPost(keyword);
			}
		}else {
			switch (type) {
			case 0:
				return postDao.getAllBlogPostByUser(uid, type, keyword);
			case 1:
				return postDao.getAllProjectPostByUser(uid, type, keyword);
			default:
				return postDao.getAllDiaryPostByUser(uid, type, keyword);
			}
		}
	}
}
