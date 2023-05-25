package com.example.Dosify.service;

import com.example.Dosify.dto.RequestDTO.DoctorRequestDto;
import com.example.Dosify.dto.ResponseDTO.DoctorResponseDto;
import com.example.Dosify.exception.CenterNotPresentException;

import java.util.List;

public interface DoctorService {

    public DoctorResponseDto addDoctor(DoctorRequestDto doctorRequestDto) throws CenterNotPresentException;


    List<DoctorResponseDto> getDocWithAtLeastXAppointMents(int x) throws Exception;

    String updateMailOfDoc(String email, String e) throws Exception;
}
