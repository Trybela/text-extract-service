package com.avenga.fil.lt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "LT_EXTRACT_DATA")
public class ExtractDataEntity {

    @Id
    @Column(name = "LT_EXTRACT_DATA_KEY")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String documentName;

    private Integer pageCount;

    private LocalDateTime extractDateTime;

    public static ExtractDataEntity from(String userId, String documentName, Integer pageCount){
        return ExtractDataEntity.builder()
                .userId(userId)
                .documentName(documentName)
                .pageCount(pageCount)
                .extractDateTime(LocalDateTime.now())
                .build();
    }

}
