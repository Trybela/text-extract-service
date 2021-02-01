package com.avenga.fil.lt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Pages {

    private List<List<LineContent>> content;
}
