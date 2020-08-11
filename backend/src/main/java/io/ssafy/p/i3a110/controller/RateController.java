package io.ssafy.p.i3a110.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ssafy.p.i3a110.service.RateService;
import io.swagger.annotations.ApiOperation;

@RestController
public class RateController {
	@Autowired
	private RateService rateService;

    @PostMapping("/rate")
    @ApiOperation(value = "회원 1Day 1Commit 달성률")
    public void getRateByUid(@RequestBody HashMap<String, Integer> map) {
    	int uid = map.get("uid");
    	rateService.getRateByUid(uid);
    }
}
