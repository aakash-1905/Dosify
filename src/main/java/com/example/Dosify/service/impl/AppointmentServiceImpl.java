package com.example.Dosify.service.impl;

import com.example.Dosify.Enum.DoseNo;
import com.example.Dosify.PDFGenerator;
import com.example.Dosify.dto.RequestDTO.AppointmentRequestDto;
import com.example.Dosify.dto.ResponseDTO.AppointmentResponseDto;
import com.example.Dosify.dto.ResponseDTO.CenterResponseDto;
import com.example.Dosify.exception.DoctorNotFoundException;
import com.example.Dosify.exception.NotEligibleForDoseException;
import com.example.Dosify.exception.UserNotFoundException;
import com.example.Dosify.model.*;
import com.example.Dosify.repository.DoctorRepository;
import com.example.Dosify.repository.UserRepository;
import com.example.Dosify.service.AppointmentService;
import com.example.Dosify.service.Dose1Service;
import com.example.Dosify.service.Dose2Service;
import com.example.Dosify.transformer.VaccinationCenterTransformer;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    UserRepository userRepository;
@Autowired
    PDFGenerator pdfGenerator;
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    Dose1Service dose1Service;

    @Autowired
    Dose2Service dose2Service;

    @Autowired
    JavaMailSender emailSender;

    @Override
    public AppointmentResponseDto bookAppointment(AppointmentRequestDto appointmentRequestDto) throws Exception {

        Optional<User> optionalUser = userRepository.findById(appointmentRequestDto.getUserId());
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User doesn't exist!");
        }

        Optional<Doctor> optionalDoctor = doctorRepository.findById(appointmentRequestDto.getDoctorId());
        if(!optionalDoctor.isPresent()){
            throw new DoctorNotFoundException("Doctor doesn't exist!!!");
        }

        User user = optionalUser.get();
        Doctor doctor = optionalDoctor.get();

        if(appointmentRequestDto.getDoseNo()== DoseNo.DOSE_1){
            if(user.isDose1Taken())
                  throw new Exception("Dose1 already taken");
            Dose1 dose1 = dose1Service.createDose1(user,appointmentRequestDto.getVaccineType());
            user.setDose1Taken(true);
            user.setDose1(dose1);
        }
        else{
            // DOSE_2
            if(!user.isDose1Taken()){
                throw new NotEligibleForDoseException("Sorry! You are not yet eligible for Dose 2");
            }
            if(user.isDose2Taken())
                throw new Exception("Dose2 already taken");
            Dose2 dose2 = dose2Service.createDose2(user,appointmentRequestDto.getVaccineType());
            user.setDose2Taken(true);
            user.setDose2(dose2);
        }

        Appointment appointment = Appointment.builder()
                .appointmentNo(String.valueOf(UUID.randomUUID()))
                .doseNo(appointmentRequestDto.getDoseNo())
                .user(user)
                .doctor(doctor)
                .build();
        user.getAppointments().add(appointment);



        Certificate certificate=Certificate.builder()
        .appointmentNo(appointment.getAppointmentNo())
                .doseNo(appointment.getDoseNo())
                .isDose1taken(user.isDose1Taken())
                .isDose2taken(user.isDose2Taken())
                .user(user)
                .build();
        user.setCertificate(certificate);
        User savedUser = userRepository.save(user); // save dose1/dose2/certificate and appointment

        Appointment savedAppointment = savedUser
                .getAppointments()
                .get(savedUser.getAppointments().size()-1);
        doctor.getAppointments().add(savedAppointment);
        doctorRepository.save(doctor);

        String address=pdfGenerator.generatePdfReport(appointment);
        System.out.println(address);
        String text = "Congrats!!" + user.getName() + " Your dose "+ appointmentRequestDto.getDoseNo() + " has been booked!!";

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("akashlkog22.com");
        helper.setTo(user.getEmailId());
        helper.setSubject("Appointment Booked !!!");
        helper.setText(text);

        FileSystemResource file
                = new FileSystemResource(new File(address));
        helper.addAttachment("Invoice", file);
        emailSender.send(message);
        // prepare response dto
        CenterResponseDto centerResponseDto
                = VaccinationCenterTransformer
                .CenterToCenterResponseDto(doctor.getVaccinationCenter());

        return AppointmentResponseDto.builder()
                .userName(user.getName())
                .appointmentNo(appointment.getAppointmentNo())
                .dateOfAppointment(savedAppointment.getDateOfAppointment())
                .doseNo(appointment.getDoseNo())
                .centerResponseDto(centerResponseDto)
                .doctorName(doctor.getName())
                .vaccineType(appointmentRequestDto.getVaccineType())
                .build();
    }
}
