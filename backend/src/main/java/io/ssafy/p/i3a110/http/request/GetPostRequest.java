package io.ssafy.p.i3a110.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetPostRequest {

    private String keyword;
    private Integer isTemp;
}
