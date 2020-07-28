package io.ssafy.p.i3a110.dto;
import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String email;
    private String pwd;
    private String nickname;
    private String gitId;
    private String gitUrl;
    private String desc;
    private String img;
    private String gitTocken;
    private int isSocial;
    private int isCertified;
}
