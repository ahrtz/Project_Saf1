package io.ssafy.p.i3a110.dao;

import io.ssafy.p.i3a110.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface PostDao {
    public ArrayList<PostDto> getPost(int did, String keyword, int isTemp);
    public PostDto getPostById(int id);
    public void createPost(PostDto post);
    public void updatePost(PostDto post);
    public void deletePost(int id);
	public ArrayList<PostDto> getAllBlogPostByUser(String uid, int type, String keyword);
	public ArrayList<PostDto> getAllProjectPostByUser(String uid, int type, String keyword);
	public ArrayList<PostDto> getAllDiaryPostByUser(String uid, int type, String keyword);
	public ArrayList<PostDto> getAllBlogPost(String keyword);
	public ArrayList<PostDto> getAllProjectPost(String keyword);
	public ArrayList<PostDto> getAllDiaryPost(String keyword);
}
