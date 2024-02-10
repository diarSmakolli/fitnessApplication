package com.example.fitness_app.Payments;
import com.example.fitness_app.common.CommonResponseDTO;
import com.example.fitness_app.exceptions.InternalServerErrorException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.fitness_app.exceptions.BadRequestException;
import com.example.fitness_app.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/payments")
@Validated
public class PaymentsController {

    private final PaymentsService paymentsService;

    @Autowired
    public PaymentsController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved test by ID")
    @ApiResponse(responseCode = "404", description = "Test not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<PaymentsEntity> findPaymentById(@PathVariable String id) {
        PaymentsEntity paymentDTO = paymentsService.findPaymentById(id);
        return ResponseEntity.ok(paymentDTO);
    }

    @PostMapping("/save/user/{userId}")
    @ApiResponse(responseCode = "201", description = "Test created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<PaymentsDTO> savePaymentByUserId(@RequestBody PaymentsDTOSave paymentsDTOSave, @RequestParam String userId) {

        if(userId == null || userId.isBlank() || userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        PaymentsDTO savedPayment = paymentsService.createPayment(paymentsDTOSave, userId);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(savedPayment);

    }
    



}
