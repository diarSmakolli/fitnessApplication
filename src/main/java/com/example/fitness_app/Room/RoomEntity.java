package com.example.fitness_app.Room;
import com.example.fitness_app.Booking.BookingEntity;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.Null;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "rooms")
@Entity
@Builder
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked = false;
    @Lob
    private Blob photo;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookingEntity> bookings;

    public RoomEntity() {
        this.bookings = new ArrayList<>();
    }

    public void addBooking(BookingEntity booking) {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10);
        booking.setBookingConfirmationCode(bookingCode);
    }
}