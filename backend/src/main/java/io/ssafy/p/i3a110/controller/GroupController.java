package io.ssafy.p.i3a110.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.ssafy.p.i3a110.dto.OrganizationDto;
import io.ssafy.p.i3a110.service.OrganizationService;
import io.swagger.annotations.ApiOperation;

@RestController
public class OrganizationController {
	@Autowired
	private OrganizationService organizationService;

//	@GetMapping("/groups/{lid}")
//	@ApiOperation(value = "관리자 별 그룹 정보 조회")
//	public OrganizationDto getGroupInfoByLeader(@PathVariable String lid) {
//		return organizationService.getOrganization();
//	}
//	
//	@GetMapping("/groups/{id}/users/{uid}")
//	@ApiOperation(value = "그룹 회원 단일 조회")
//	public OrganizationDto getGroupInfoByLeader(@PathVariable String lid) {
//		return organizationService.getOrganization();
//	}
//
//	@GetMapping("/groups/{lid}")
//	@ApiOperation(value = "관리자 별 그룹 정보 조회")
//	public OrganizationDto getGroupInfoByLeader(@PathVariable String lid) {
//		return organizationService.getOrganization();
//	}
//	
//	@GetMapping("/groups/{lid}")
//	@ApiOperation(value = "관리자 별 그룹 정보 조회")
//	public OrganizationDto getGroupInfoByLeader(@PathVariable String lid) {
//		return organizationService.getOrganization();
//	}
}
