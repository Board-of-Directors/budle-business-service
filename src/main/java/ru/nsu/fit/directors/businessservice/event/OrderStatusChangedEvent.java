package ru.nsu.fit.directors.businessservice.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderStatusChangedEvent {
    private int status;
    private long establishmentId;
    private long orderId;
}
