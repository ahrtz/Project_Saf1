package io.ssafy.p.i3a110.dto;
import lombok.Data;

@Data
public class DiaryDto {
    private int id;
    private int uid;
    private String title;
    private String desc;
    private String img;
    private String gitUrl;
    private int isProj;
    private String sDate;
    private String eDate;
}
