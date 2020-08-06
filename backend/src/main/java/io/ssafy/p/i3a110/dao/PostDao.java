package io.ssafy.p.i3a110.dao;

import io.ssafy.p.i3a110.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface PostDao {
	
	public ArrayList<PostDto> getAllPost(int isProj, String keyword, int isTemp, int limit);
	
	public ArrayList<PostDto> getAllPostByUser(String uid, int isProj, String keyword, int isTemp, int limit);
	
    public ArrayList<PostDto> getPost(int did, String keyword, int isTemp);
    
    public PostDto getPostById(int id);
    
    public PostDto getPostByTitle(String keyword);
    
    public int createPost(PostDto post);
    
    public void updatePost(PostDto post);
    
    public void deletePost(int id);

	public HashMap<Object, Object> getlastPostDate(int id);
}
