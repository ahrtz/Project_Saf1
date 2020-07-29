package io.ssafy.p.i3a110.dto;

import java.util.List;

import lombok.Data;

@Data
public class RepositoryInfoDto {
	private String repoName;
	private String owner;
	private String url;
	private Boolean isPrivate;
	private List<String> languages;

	public RepositoryInfoDto(String repoName, String owner, String url, Boolean isPrivate,
			List<String> languages){
		this.repoName = repoName;
		this.owner = owner;
		this.url = url;
		this.isPrivate = isPrivate;
		this.languages = languages;
	}
}
