package com.example.fitness_app.Booking;
import com.example.fitness_app.Room.RoomEntity;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.Null;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookings")
@Entity
@Builder
public class BookingEntity {
    @Id
    @Column(name = "id")
    private final String id = UUID.randomUUID().toString();

    @Column(name = "check_in")
    private LocalDate checkInDate;

    @Column(name = "check_out")
    private LocalDate checkOutDate;

    @Column(name = "guest_fullName")
    private String guestFullName;

    @Column(name = "guest_email")
    private String guestEmail;

    @Column(name = "adults")
    private int NumOfAdults;

    @Column(name = "children")
    private int NumOfChildren;

    @Column(name = "total_guest")
    private int totalNumOfGuest;

    @Column(name = "confirmation_Code")
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

//    public void calculateTotalNumberOfGuest(){
//        this.totalNumOfGuest = this.NumOfAdults + NumOfChildren;
//    }
//
//    public void setNumOfAdults(int numOfAdults) {
//        NumOfAdults = numOfAdults;
//        calculateTotalNumberOfGuest();
//    }
//
//    public void setNumOfChildren(int numOfChildren) {
//        NumOfChildren = numOfChildren;
//        calculateTotalNumberOfGuest();
//    }
//
//    public void setBookingConfirmationCode(String bookingConfirmationCode) {
//        this.bookingConfirmationCode = bookingConfirmationCode;
//    }
}
