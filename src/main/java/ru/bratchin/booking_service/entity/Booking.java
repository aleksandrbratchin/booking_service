package ru.bratchin.booking_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс Booking представляет сущность бронирования в системе.
 */
@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Booking {

    /**
     * Уникальный идентификатор бронирования.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Идентификатор отеля.
     */
    @Column(name = "hotel_id", nullable = false)
    private UUID hotelId;

    /**
     * Идентификатор номера.
     */
    @Column(name = "room_id", nullable = false)
    private UUID roomId;

    /**
     * Идентификатор клиента.
     */
    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    /**
     * Дата начала бронирования.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    /**
     * Дата окончания бронирования.
     */
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

}
