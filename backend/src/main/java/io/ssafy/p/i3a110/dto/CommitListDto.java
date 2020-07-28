package io.ssafy.p.i3a110.dto;
import lombok.Data;

@Data
public class CommitListDto {
    int id;
    int uid;
    int pid;
    String sha;
    String author;
    String msg;
}
