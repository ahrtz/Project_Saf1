package io.ssafy.p.i3a110.dto;

import lombok.Data;

@Data
public class RepositoryInfoDto {
	private String repoName;
	private String owner;
	private String url;
	private Boolean isPrivate;
	public RepositoryInfoDto(String repoName, String owner, String url, Boolean isPrivate) {
		this.repoName = repoName;
		this.owner = owner;
		this.url = url;
		this.isPrivate = isPrivate;
	}
}
