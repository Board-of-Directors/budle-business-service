package ru.nsu.fit.directors.businessservice.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseOrderDto {
    private Integer spotNumber;
    private LocalDateTime bookingTime;
    private Integer guestCount;
    private String guestName;
    private String status;
    private boolean isPreOrder;
}
