package io.ssafy.p.i3a110.dto;
import lombok.Data;

@Data
public class CommitListDto {
    private int id;
    private int uid;
    private int pid;
    private String sha;
    private String author;
    private String msg;
}
