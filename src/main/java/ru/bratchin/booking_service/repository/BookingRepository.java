package ru.bratchin.booking_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bratchin.booking_service.entity.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Query("SELECT b FROM Booking b WHERE " +
            "(b.startDate BETWEEN :startDate AND :endDate) OR " +
            "(b.endDate BETWEEN :startDate AND :endDate) OR " +
            "(b.startDate <= :startDate AND b.endDate >= :endDate)")
    List<Booking> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate);

    @Query("SELECT b FROM Booking b WHERE b.roomId = :roomId AND " +
            "((:startDate BETWEEN b.startDate AND b.endDate) OR " +
            "(:endDate BETWEEN b.startDate AND b.endDate) OR " +
            "(b.startDate <= :startDate AND b.endDate >= :endDate))")
    Optional<Booking> findOverlappingBooking(@Param("roomId") UUID roomId,
                                              @Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);

}