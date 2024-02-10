package com.example.fitness_app.Payments;
import lombok.*;

import javax.persistence.Column;
import javax.validation.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsDTOSave {

    private double amount;

}
