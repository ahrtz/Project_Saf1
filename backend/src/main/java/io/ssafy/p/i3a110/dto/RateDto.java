package io.ssafy.p.i3a110.dto;
import lombok.Data;

@Data
public class RateDto {
    private int id;
    private int uid;
    private String odoc_cnt;
    private String odoc_rate;
    private String odop_cnt;
    private String odop_rate;
}
