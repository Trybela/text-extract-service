package com.avenga.fil.lt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LineContent {

    private String text;
    private Float xAxis;
    private Float yAxis;
    private Integer rowIndex;
    private Integer columnIndex;
}
