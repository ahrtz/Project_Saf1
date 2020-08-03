package io.ssafy.p.i3a110.dto;
import lombok.Data;

@Data
public class CommentDto {
    private int id;
    private int uid;
    private int pid;
    private String content;
    private String cDate;
}
