package ru.nsu.fit.directors.businessservice.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class EstablishmentListDto {
    private List<ResponseBasicEstablishmentInfo> establishments;
    private Integer count;
}
