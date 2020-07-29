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

    public ArrayList<PostDto> getPost(String keyword, int isTemp) {
        return postDao.getPost(keyword, isTemp);
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
}
