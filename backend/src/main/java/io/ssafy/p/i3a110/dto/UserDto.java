package io.ssafy.p.i3a110.dto;
import lombok.Data;

@Data
public class UserDto {
    int id;
    String email;
    String pwd;
    String nickname;
    String gitId;
    String gitUrl;
    String desc;
    String img;
    String gitTocken;
    int isSocial;
    int isCertified;
}
