package io.ssafy.p.i3a110.dto;
import lombok.Data;

@Data
public class DiaryDto {
    int id;
    int uid;
    String title;
    String desc;
    String img;
    String gitUrl;
    int isProj;
    String sDate;
    String eDate;
}
