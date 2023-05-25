package com.example.Dosify.dto.ResponseDTO;

import com.example.Dosify.Enum.DoseNo;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CerificateResponseDto {
    String appointmentNo;
    @CreationTimestamp
    Date dateOfAppointment;


    DoseNo doseNo;

    String userName;
}
