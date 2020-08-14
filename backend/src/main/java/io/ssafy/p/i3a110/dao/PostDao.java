package io.ssafy.p.i3a110.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.PostDto;

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
	
	public void plusLike(int id);
	
	public void minusLike(int id);
	
	public int getOdopRate(int uid);

	public List<Map<String, Object>> getAllPostCnt(int uid, int did, int isProj);
}
