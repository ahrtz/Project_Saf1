package io.ssafy.p.i3a110.dto;

import java.util.Date;

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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSha1() {
		return sha1;
	}
	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "CommitInfo [author=" + author + ", sha1=" + sha1 + ", date=" + date + ", msg=" + msg + "]";
	}
}
