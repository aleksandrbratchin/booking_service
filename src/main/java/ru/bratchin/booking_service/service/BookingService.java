package ru.bratchin.booking_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bratchin.booking_service.dto.BookingCreateDTO;
import ru.bratchin.booking_service.dto.BookingRequestDTO;
import ru.bratchin.booking_service.dto.BookingResponseDTO;
import ru.bratchin.booking_service.entity.Booking;
import ru.bratchin.booking_service.mapper.BookingMapper;
import ru.bratchin.booking_service.repository.BookingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    public List<BookingResponseDTO> getBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByDateRange(startDate, endDate).stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookingResponseDTO getBookingById(UUID id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toDTO)
                .orElse(null);
    }

    public BookingResponseDTO createBooking(BookingCreateDTO bookingCreateDTO) {
        if (isRoomBooked(bookingCreateDTO.roomId(), bookingCreateDTO.startDate(), bookingCreateDTO.endDate())) {
            throw new IllegalArgumentException("The room is already booked for the given period.");
        }
        Booking booking = bookingMapper.toEntity(bookingCreateDTO);
        return bookingMapper.toDTO(bookingRepository.save(booking));
    }

    public BookingResponseDTO updateBooking(UUID id, BookingRequestDTO bookingRequestDTO) {
        Booking booking1 = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException());
        Booking booking = bookingMapper.toEntity(bookingRequestDTO);
        booking1.setCustomerId(booking.getCustomerId());
        booking1.setHotelId(booking.getHotelId());
        booking1.setRoomId(booking.getRoomId());
        booking1.setStartDate(booking.getStartDate());
        booking1.setEndDate(booking.getEndDate());
        return bookingMapper.toDTO(bookingRepository.save(booking1));
    }

    public void deleteBooking(UUID id) {
        bookingRepository.deleteById(id);
    }

    public boolean isRoomBooked(UUID roomId, LocalDateTime startDate, LocalDateTime endDate) {
        Optional<Booking> booking = bookingRepository.findOverlappingBooking(roomId, startDate, endDate);
        return booking.isPresent();
    }
}
