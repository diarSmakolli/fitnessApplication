package com.example.fitness_app.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingDTO mapToDTO(BookingEntity bookingEntity) {
        return BookingDTO.builder()
                .id(bookingEntity.getId())
                .checkInDate(bookingEntity.getCheckInDate())
                .checkOutDate(bookingEntity.getCheckOutDate())
                .guestFullName(bookingEntity.getGuestFullName())
                .guestEmail(bookingEntity.getGuestEmail())
                .NumOfAdults(bookingEntity.getNumOfAdults())
                .NumOfChildren(bookingEntity.getNumOfChildren())
                .totalNumOfGuest(bookingEntity.getTotalNumOfGuest())
                .bookingConfirmationCode(bookingEntity.getBookingConfirmationCode())
                .room(bookingEntity.getRoom())
                .build();
    }

    public BookingEntity mapToEntity(BookingDTOSave bookingDTO) {
        return BookingEntity.builder()
                .checkInDate(bookingDTO.getCheckOutDate())
                .checkOutDate(bookingDTO.getCheckOutDate())
                .guestEmail(bookingDTO.getGuestEmail())
                .guestEmail(bookingDTO.getGuestEmail())
                .NumOfAdults(bookingDTO.getNumOfAdults())
                .NumOfChildren(bookingDTO.getNumOfChildren())
                .totalNumOfGuest(bookingDTO.getTotalNumOfGuest())
                .bookingConfirmationCode(bookingDTO.getBookingConfirmationCode())
                .build();
    }



}
