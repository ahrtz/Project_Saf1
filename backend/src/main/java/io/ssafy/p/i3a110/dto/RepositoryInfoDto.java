package io.ssafy.p.i3a110.dto;


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
	public String getRepoName() {
		return repoName;
	}
	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	@Override
	public String toString() {
		return "RepositoryInfo [repoName=" + repoName + ", owner=" + owner + ", url=" + url + ", isPrivate=" + isPrivate
				+ "]";
	}
}
