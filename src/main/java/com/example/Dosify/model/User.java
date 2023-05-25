package com.example.Dosify.model;

import com.example.Dosify.Enum.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name="user")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="name")
    String name;

    @Column(name="age")
    int age;

    @Column(name = "email_id",unique = true,nullable = false)
    String emailId;

    @Column(name="mob_no",unique = true,nullable = false)
    String mobNo;

    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(name="is_dose1_taken")
    boolean isDose1Taken;

    @Column(name="is_dose2_taken")
    boolean isDose2Taken;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Appointment> appointments = new ArrayList<>();

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    Dose1 dose1;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    Dose2 dose2;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    Certificate certificate;
}
