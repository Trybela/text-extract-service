package com.avenga.fil.lt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExcelSheet {

    private String name;
    private List<List<String>> content;
}
