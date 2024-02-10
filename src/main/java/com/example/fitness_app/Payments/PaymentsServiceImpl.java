package com.example.fitness_app.Payments;
import com.example.fitness_app.ExerciseSession.ExerciseSessionDTO;
import com.example.fitness_app.ExerciseSession.ExerciseSessionEntity;
import com.example.fitness_app.Payments.PaymentsEntity.PaymentStatus;
import com.example.fitness_app.Test.TestServiceImpl;
import com.example.fitness_app.User.UserEntity;
import com.example.fitness_app.User.UserRepository;
import com.example.fitness_app.common.CommonResponseDTO;
import com.example.fitness_app.common.ValidationUtilsDTO;
import com.example.fitness_app.exceptions.BadRequestException;
import com.example.fitness_app.exceptions.EntityNotFoundException;
import com.example.fitness_app.exceptions.InternalServerErrorException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PaymentsServiceImpl implements PaymentsService {

    private static final Logger logger = Logger.getLogger(TestServiceImpl.class.getName());
    
    private final PaymentsMapper paymentsMapper;

    private final PaymentsRepository paymentsRepository;

    private final UserRepository userRepository;

    public PaymentsServiceImpl(PaymentsMapper paymentsMapper, PaymentsRepository paymentsRepository, UserRepository userRepository) {
        this.paymentsMapper = paymentsMapper;
        this.paymentsRepository = paymentsRepository;
        this.userRepository = userRepository;
    }



    @Override
    @Transactional
    public PaymentsDTO createPayment(PaymentsDTOSave paymentsDTO, String userId) {
        try {

            UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

            PaymentsEntity payment = paymentsMapper.mapToEntity(paymentsDTO);
            payment.setUser(user);
            payment.setCreatedAt(new Date());
            payment.setPaymentDate(new Date());
            payment.setCreatedBy(user.getFirstname());
            payment.setStatus(PaymentStatus.PAID);
            
            PaymentsEntity savedPayment = paymentsRepository.save(payment);

            return paymentsMapper.mapToDTO(savedPayment);

        } catch (DataAccessException ex) {
            logAndThrowInternalServerError("Error saving exercise", ex);
            return null;
        } catch (BadRequestException ex) {
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
            return null;
        }
    }




    @Override
    @Transactional(readOnly = true)
    public PaymentsEntity findPaymentById(String id) {
        PaymentsEntity payment = paymentsRepository.findById(id)
            .orElseThrow(() -> {
                logger.warning("Exercise not found: " + id);
                return new EntityNotFoundException("Exercise not found");
            });
        
        
        checkPaymentIsDeleted(payment, id);
        
        return payment;
    }

    private void checkPaymentIsDeleted(PaymentsEntity payment, String id) {
        if(payment.getDeletedAt() != null) {
            logger.warning("Exercise has been deleted:" + id);
            throw new EntityNotFoundException("Exercise not found!");
        }
    }

    private void logAndThrowEntityNotFoundException(String message) {
        logger.warning(message);
        throw new EntityNotFoundException(message);
    }

    private void logAndThrowInternalServerError(String message, Exception ex) {
        logger.severe(message + ": " + ex.getMessage());
        throw new InternalServerErrorException(message);
    }

    private void logAndThrowBadRequest(String message) {
        logger.warning(message);
        throw new BadRequestException(message);
    }

    private CommonResponseDTO<PaymentsDTO> buildCommonResponse(List<PaymentsDTO> paymentDTOs, Page<PaymentsEntity> paymentsPage) {

        return CommonResponseDTO.<PaymentsDTO>builder()
                .list(paymentDTOs)
                .totalItems(paymentsPage.getTotalElements())
                .currentPage(paymentsPage.getNumber())
                .pageNumber(paymentsPage.getNumber())
                .pageSize(paymentsPage.getSize())
                .build();
    }

    


}
