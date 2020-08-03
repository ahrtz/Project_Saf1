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
    public void updateLike(HttpSession httpSession, @RequestBody HashMap<String, String> map) {
        System.out.println("#1");
        int uid = (int) httpSession.getAttribute("id");
        System.out.println(httpSession.toString());
        System.out.println("#2");
        int pid = Integer.parseInt(map.get("pid"));
        System.out.println("#3");
        LikeDto like = likeService.getLike(uid, pid);
        System.out.println("#4");
        like.setStatus(Integer.parseInt(map.get("status")));
        System.out.println("#5");

        likeService.updateLike(like);
    }
}

