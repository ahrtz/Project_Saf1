package io.ssafy.p.i3a110.dto;
import java.util.Date;

import lombok.Data;

@Data
public class PostDto {
    private int id;
    private int did;
    private int uid;
    private String title;
    private String content;
    private Date cDate;
    private int priority;
    private int cntLike;
    private int isTemp;
}
