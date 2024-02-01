package com.example.fitness_app.Room;
import com.example.fitness_app.Booking.BookingRepository;
import com.example.fitness_app.Test.TestDTO;
import com.example.fitness_app.Test.TestDTOSave;
import com.example.fitness_app.Test.TestEntity;
import com.example.fitness_app.Test.TestServiceImpl;
import com.example.fitness_app.exceptions.EntityNotFoundException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    private static final Logger logger = Logger.getLogger(TestServiceImpl.class.getName());

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }






}
