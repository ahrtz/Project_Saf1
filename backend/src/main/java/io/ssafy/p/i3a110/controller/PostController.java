package io.ssafy.p.i3a110.controller;

import io.ssafy.p.i3a110.dto.PostDto;
import io.ssafy.p.i3a110.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/posts")
    @ApiOperation(value = "다이어리 포스트 조회")
    public ArrayList<PostDto> getPost(@RequestBody HashMap<String, String> map) {
        String keyword = map.get("keyword");

        return postService.getPost(keyword);
    }
}
