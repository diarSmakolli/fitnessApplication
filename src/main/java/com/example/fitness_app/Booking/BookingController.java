package com.example.fitness_app.Booking;
import com.example.fitness_app.Test.TestDTO;
import com.example.fitness_app.Test.TestDTOSave;
import com.example.fitness_app.Test.TestEntity;
import org.hibernate.tool.schema.internal.SchemaCreatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@Validated
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }






}
