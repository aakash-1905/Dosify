package com.example.Dosify.transformer;

import com.example.Dosify.dto.RequestDTO.CenterRequestDto;
import com.example.Dosify.dto.ResponseDTO.CenterResponseDto;
import com.example.Dosify.model.VaccinationCenter;

public class VaccinationCenterTransformer {

    public static VaccinationCenter CenterRequestDtoToCenter(CenterRequestDto centerRequestDto){

        return VaccinationCenter.builder()
                .name(centerRequestDto.getName())
                .location(centerRequestDto.getLocation())
                .centerType(centerRequestDto.getCenterType())
                .build();
    }

    public static CenterResponseDto CenterToCenterResponseDto(VaccinationCenter center){

        return CenterResponseDto.builder()
                .name(center.getName())
                .location(center.getLocation())
                .centerType(center.getCenterType())
                .build();
    }
}
