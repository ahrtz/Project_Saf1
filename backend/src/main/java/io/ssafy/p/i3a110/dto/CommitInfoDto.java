package io.ssafy.p.i3a110.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CommitInfoDto {

	private String author;
	private String sha1;
	private Date date;
	private String msg;
	public CommitInfoDto(String author, String sha1, Date date, String msg) {
		this.author = author;
		this.sha1 = sha1;
		this.date = date;
		this.msg = msg;
	}
}
