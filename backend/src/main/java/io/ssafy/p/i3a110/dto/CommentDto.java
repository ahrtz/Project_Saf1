package io.ssafy.p.i3a110.dto;
import java.util.Date;

import lombok.Data;

@Data
public class CommentDto {
    private int id;
    private int uid;
    private int pid;
    private String content;
    private Date cDate;
}
