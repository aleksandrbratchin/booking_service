package ru.bratchin.booking_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bratchin.booking_service.dto.BookingRequestDTO;
import ru.bratchin.booking_service.dto.BookingResponseDTO;
import ru.bratchin.booking_service.service.BookingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Tag(name = "Бронирования", description = "Операции с бронированиями")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    @Operation(summary = "Поиск бронирований за определенное время", description = "Возвращает список бронирований, которые происходят в указанный период времени")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список бронирований за указанный период времени")
    })
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByDateRange(
            @Parameter(description = "Дата и время начала периода для поиска", required = true)
            @RequestParam LocalDateTime startDate,
            @Parameter(description = "Дата и время окончания периода для поиска", required = true)
            @RequestParam LocalDateTime endDate) {
        List<BookingResponseDTO> bookings = bookingService.getBookingsByDateRange(startDate, endDate);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/All")
    @Operation(summary = "Получить список всех бронирований", description = "Возвращает список всех бронирований")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список всех бронирований")
    })
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить бронирование по ID", description = "Возвращает бронирование по заданному ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Бронирование найдено"),
            @ApiResponse(responseCode = "404", description = "Бронирование не найдено")
    })
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable UUID id) {
        BookingResponseDTO bookingRequestDTO = bookingService.getBookingById(id);
        return bookingRequestDTO != null ? ResponseEntity.ok(bookingRequestDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Создать новое бронирование", description = "Создает новое бронирование на основе переданных данных")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Бронирование создано")
    })
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        BookingResponseDTO createdBooking = bookingService.createBooking(bookingRequestDTO);
        return ResponseEntity.status(201).body(createdBooking);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить бронирование", description = "Удаляет бронирование по заданному ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Бронирование удалено")
    })
    public ResponseEntity<Void> deleteBooking(@PathVariable UUID id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Проверка доступности номера",
            description = "Проверяет, забронирован ли номер в указанный период времени.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Доступность номера успешно проверена",
                    content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные входные параметры",
                    content = @Content)
    })
    @GetMapping("/check")
    public ResponseEntity<?> checkRoomAvailability(
            @Parameter(description = "UUID номера для проверки", required = true)
            @RequestParam UUID roomId,
            @Parameter(description = "Дата и время начала периода для проверки", required = true)
            @RequestParam LocalDateTime startDate,
            @Parameter(description = "Дата и время окончания периода для проверки", required = true)
            @RequestParam LocalDateTime endDate) {
        try {
            boolean isBooked = bookingService.isRoomBooked(roomId, startDate, endDate);
            return ResponseEntity.ok(isBooked);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
