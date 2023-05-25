package com.example.Dosify.service;

import com.example.Dosify.dto.RequestDTO.AppointmentRequestDto;
import com.example.Dosify.dto.ResponseDTO.AppointmentResponseDto;

public interface AppointmentService {

    public AppointmentResponseDto bookAppointment(AppointmentRequestDto appointmentRequestDto) throws Exception;
}
