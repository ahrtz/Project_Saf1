package io.ssafy.p.i3a110.dto;
import java.util.Date;

import lombok.Data;

@Data
public class DiaryDto {
    private int id;
    private int uid;
    private String gitName;
    private String title;
    private String intro;
    private String img;
    private String gitUrl;
    private String repoId;
    private int isProj;
    private Date sDate;
    private Date eDate;
}
