package ru.bratchin.booking_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingService bookingService;

    private Booking booking;
    private BookingCreateDTO bookingCreateDTO;
    private BookingRequestDTO bookingRequestDTO;
    private BookingResponseDTO bookingResponseDTO;
    private UUID bookingId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingId = UUID.randomUUID();
        booking = new Booking(bookingId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
            LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        bookingCreateDTO = new BookingCreateDTO(booking.getHotelId(), booking.getRoomId(), booking.getCustomerId(),
            booking.getStartDate(), booking.getEndDate());
        bookingRequestDTO = new BookingRequestDTO(bookingId, booking.getHotelId(), booking.getRoomId(),
            booking.getCustomerId(), booking.getStartDate(), booking.getEndDate());
        bookingResponseDTO = new BookingResponseDTO(bookingId, null, null, null,
            booking.getStartDate(), booking.getEndDate());
    }

    @Test
    void getBookingsByDateRange_ReturnsListOfBookings() {
        when(bookingRepository.findByDateRange(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(List.of(booking));
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(bookingResponseDTO);

        List<BookingResponseDTO> bookings = bookingService.getBookingsByDateRange(
            LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));

        assertThat(bookings).isNotEmpty();
        assertThat(bookings).hasSize(1);
        assertThat(bookings.get(0)).isEqualTo(bookingResponseDTO);
    }

    @Test
    void getAllBookings_ReturnsAllBookings() {
        when(bookingRepository.findAll()).thenReturn(List.of(booking));
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(bookingResponseDTO);

        List<BookingResponseDTO> bookings = bookingService.getAllBookings();

        assertThat(bookings).isNotEmpty();
        assertThat(bookings).hasSize(1);
        assertThat(bookings.get(0)).isEqualTo(bookingResponseDTO);
    }

    @Test
    void getBookingById_ReturnsBookingIfExists() {
        when(bookingRepository.findById(any(UUID.class))).thenReturn(Optional.of(booking));
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(bookingResponseDTO);

        BookingResponseDTO foundBooking = bookingService.getBookingById(bookingId);

        assertThat(foundBooking).isEqualTo(bookingResponseDTO);
    }

    @Test
    void getBookingById_ReturnsNullIfBookingNotFound() {
        when(bookingRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        BookingResponseDTO foundBooking = bookingService.getBookingById(bookingId);

        assertThat(foundBooking).isNull();
    }

    @Test
    void createBooking_ReturnsCreatedBooking() {
        when(bookingMapper.toEntity(any(BookingCreateDTO.class))).thenReturn(booking);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(bookingResponseDTO);

        BookingResponseDTO createdBooking = bookingService.createBooking(bookingCreateDTO);

        assertThat(createdBooking).isEqualTo(bookingResponseDTO);
    }

    @Test
    void createBooking_ThrowsExceptionIfRoomIsBooked() {
        when(bookingRepository.findOverlappingBooking(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Optional.of(booking));

        assertThatThrownBy(() -> bookingService.createBooking(bookingCreateDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The room is already booked for the given period.");
    }

    @Test
    void updateBooking_ReturnsUpdatedBooking() {
        when(bookingRepository.existsById(any(UUID.class))).thenReturn(true);
        when(bookingMapper.toEntity(any(BookingRequestDTO.class))).thenReturn(booking);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(bookingResponseDTO);

        BookingResponseDTO updatedBooking = bookingService.updateBooking(bookingId, bookingRequestDTO);

        assertThat(updatedBooking).isEqualTo(bookingResponseDTO);
    }

    @Test
    void updateBooking_ReturnsNullIfBookingNotFound() {
        when(bookingRepository.existsById(any(UUID.class))).thenReturn(false);

        BookingResponseDTO updatedBooking = bookingService.updateBooking(bookingId, bookingRequestDTO);

        assertThat(updatedBooking).isNull();
    }

    @Test
    void deleteBooking_DeletesBookingIfExists() {
        doNothing().when(bookingRepository).deleteById(any(UUID.class));

        bookingService.deleteBooking(bookingId);

        verify(bookingRepository, times(1)).deleteById(bookingId);
    }

    @Test
    void isRoomBooked_ReturnsTrueIfRoomIsBooked() {
        when(bookingRepository.findOverlappingBooking(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Optional.of(booking));

        boolean result = bookingService.isRoomBooked(booking.getRoomId(), booking.getStartDate(), booking.getEndDate());

        assertThat(result).isTrue();
    }

    @Test
    void isRoomBooked_ReturnsFalseIfRoomIsNotBooked() {
        when(bookingRepository.findOverlappingBooking(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Optional.empty());

        boolean result = bookingService.isRoomBooked(booking.getRoomId(), booking.getStartDate(), booking.getEndDate());

        assertThat(result).isFalse();
    }
}