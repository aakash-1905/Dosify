package com.example.Dosify.model;

import com.example.Dosify.Enum.DoseNo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String appointmentNo;
    @CreationTimestamp
    Date dateOfAppointment;

    @Enumerated(EnumType.STRING)
    DoseNo doseNo;

    boolean isDose1taken;

    boolean isDose2taken;

    @OneToOne
    @JoinColumn
    User user;

}
