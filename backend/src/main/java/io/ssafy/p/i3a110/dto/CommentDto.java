package io.ssafy.p.i3a110.dto;
import lombok.Data;

@Data
public class CommentDto {
    int id;
    int uid;
    int pid;
    String content;
    String date;
}
