package ru.nsu.fit.directors.businessservice.dto.response;

import lombok.Data;

@Data
public class ResponseBasicEstablishmentInfo {
    private Long id;
    private String name;
    private String category;
    private Float rating;
    private String address;
    private String image;
    private Boolean hasMap;
    private Boolean hasCardPayment;
    private String cuisineCountry;
    private Integer starsCount;
}
