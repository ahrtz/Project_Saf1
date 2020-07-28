package io.ssafy.p.i3a110.dto;
import lombok.Data;

@Data
public class PostDto {
    int id;
    int did;
    int uid;
    String title;
    String content;
    String cDate;
    int priority;
    int cntLike;
    int isTemp;
}
