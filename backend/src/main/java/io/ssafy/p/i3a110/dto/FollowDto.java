package io.ssafy.p.i3a110.dto;
import lombok.Data;

@Data
public class FollowDto {
    private int id;
    private int userFrom;
    private int userTo;
    private int status;
}
