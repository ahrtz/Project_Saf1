package io.ssafy.p.i3a110.controller;

import io.ssafy.p.i3a110.dto.LikeDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.service.LikeService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

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

    @GetMapping("/likes/{pid}")
    @ApiOperation(value = "사용자 포스트 좋아요 여부 조회")
    public LikeDto getLike(HttpSession httpSession, @PathVariable int pid) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = userService.findUserByEmail(email);
        int uid = user.getId();

        return likeService.getLike(uid, pid);
    }

    @PutMapping("/likes")
    @ApiOperation(value = "좋아요 수정")
    public void updateLike(HttpSession httpSession, @RequestBody LikeDto like) {
    	String email = (String) httpSession.getAttribute("email");
    	UserDto user = userService.findUserByEmail(email);
    	int uid = user.getId();
    	like.setUid(uid);
        if(likeService.getLike(uid, like.getPid()) == null) {
        	likeService.makeLike(like);
        }else {
        	likeService.updateLike(like);
        }

    }
}

