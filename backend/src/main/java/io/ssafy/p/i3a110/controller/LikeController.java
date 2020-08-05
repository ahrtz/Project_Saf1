package io.ssafy.p.i3a110.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ssafy.p.i3a110.dto.LikeDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.interceptor.Auth;
import io.ssafy.p.i3a110.service.LikeService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class LikeController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private UserService userService;

    @GetMapping("/likes/total/{pid}")
    @ApiOperation(value = "포스트 좋아요 수 조회")
    public int getLikeCnt(@PathVariable int pid) {
        return likeService.getLikeCnt(pid);
    }
    
    @PostMapping("/likes/total")
    @ApiOperation(value = "포스트 좋아요 수 조회")
    public List<HashMap<Object, Object>> getLikeCntByType(@RequestBody HashMap<String, String> map) {
    	String type = map.get("type");
    	return likeService.getLikeCntByType(type);
    }

    @Auth
    @GetMapping("/likes/{pid}")
    @ApiOperation(value = "사용자 포스트 좋아요 여부 조회")
    public LikeDto getLike(HttpSession httpSession, @PathVariable int pid) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = userService.findUserByEmail(email);
        int uid = user.getId();

        return likeService.getLike(uid, pid);
    }

    @Auth
    @PutMapping("/likes")
    @ApiOperation(value = "좋아요 생성 및 수정")
    public Object updateLike(HttpSession httpSession, @RequestBody LikeDto like) {
    	String email = (String) httpSession.getAttribute("email");
    	UserDto user = userService.findUserByEmail(email);
    	int uid = user.getId();
    	like.setUid(uid);
        if(likeService.getLike(uid, like.getPid()) == null) {
        	likeService.makeLike(like);
        }else {
        	likeService.updateLike(like);
        }
		return new ResponseEntity<>(like.getId(), HttpStatus.OK);
    }
}

