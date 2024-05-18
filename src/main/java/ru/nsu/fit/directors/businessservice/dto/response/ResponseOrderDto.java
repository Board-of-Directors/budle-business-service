package ru.nsu.fit.directors.businessservice.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResponseOrderDto {
    private Long id;
    private Integer spotNumber;
    private LocalDateTime bookingTime;
    private Integer guestCount;
    private String status;
    private boolean isPreOrder;
    private String guestName;
    private List<ActionDto> businessActions;
}
