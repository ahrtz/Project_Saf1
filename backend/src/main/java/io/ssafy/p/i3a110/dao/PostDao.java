package io.ssafy.p.i3a110.dao;

import io.ssafy.p.i3a110.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface PostDao {
    public ArrayList<PostDto> getPost(String keyword, int isTemp);
    public PostDto getPostById(int id);
    public void createPost(PostDto post);
    public void updatePost(PostDto post);
    public void deletePost(int id);
}
