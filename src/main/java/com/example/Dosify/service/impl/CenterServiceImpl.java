package com.example.Dosify.service.impl;

import com.example.Dosify.Enum.CenterType;
import com.example.Dosify.Enum.Gender;
import com.example.Dosify.dto.RequestDTO.CenterRequestDto;
import com.example.Dosify.dto.ResponseDTO.CenterResponseDto;
import com.example.Dosify.dto.ResponseDTO.DoctorResponseDto;
import com.example.Dosify.model.Doctor;
import com.example.Dosify.model.VaccinationCenter;
import com.example.Dosify.repository.CenterRepository;
import com.example.Dosify.service.VaccinationCenterService;
import com.example.Dosify.transformer.DoctorTransformer;
import com.example.Dosify.transformer.VaccinationCenterTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CenterServiceImpl implements VaccinationCenterService {

    @Autowired
    CenterRepository centerRepository;

    @Override
    public CenterResponseDto addCenter(CenterRequestDto centerRequestDto) {
        // dto -> entity
        VaccinationCenter vaccinationCenter = VaccinationCenterTransformer.CenterRequestDtoToCenter(centerRequestDto);
        // save to your db
        VaccinationCenter savedCenter = centerRepository.save(vaccinationCenter);
        // entity -> response dto
        return VaccinationCenterTransformer.CenterToCenterResponseDto(savedCenter);
    }

    @Override
    public List<DoctorResponseDto> getDoctorsAtACenter(int id) throws Exception {
        Optional<VaccinationCenter>center=centerRepository.findById(id);
        if(center.isEmpty())
            throw new Exception("Center not Found");
        List<DoctorResponseDto>ans=new ArrayList<>();
        for(Doctor d:center.get().getDoctors()){
            DoctorResponseDto DrD= DoctorTransformer.DoctorToDoctorResponseDto(d);
            ans.add(DrD);
        }
        return ans;
    }

    @Override
    public List<DoctorResponseDto> maleDoctorsAtCenter(int id) throws Exception {
        Optional<VaccinationCenter>center=centerRepository.findById(id);
        if(center.isEmpty())
            throw new Exception("Center not Found");
        List<DoctorResponseDto>ans=new ArrayList<>();
        for(Doctor d:center.get().getDoctors()){
            if(d.getGender()== Gender.MALE){
                DoctorResponseDto DrD= DoctorTransformer.DoctorToDoctorResponseDto(d);
                ans.add(DrD);
            }
        }
        return ans;
    }

    @Override
    public List<DoctorResponseDto> femaleDoctorsAtCenter(int id) throws Exception {
        Optional<VaccinationCenter>center=centerRepository.findById(id);
        if(center.isEmpty())
            throw new Exception("Center not Found");
        List<DoctorResponseDto>ans=new ArrayList<>();
        for(Doctor d:center.get().getDoctors()){
            if(d.getGender()== Gender.FEMALE){
                DoctorResponseDto DrD= DoctorTransformer.DoctorToDoctorResponseDto(d);
                ans.add(DrD);
            }
        }
        return ans;
    }



    @Override
    public List<CenterResponseDto> centerType(CenterType centerType) throws Exception {
        List<VaccinationCenter>center=centerRepository.findByCenterType(centerType);
        if(center.isEmpty())
            throw new Exception("Center not Found");
        List<CenterResponseDto>ans=new ArrayList<>();
        for(VaccinationCenter vc:center){
            CenterResponseDto cd=VaccinationCenterTransformer.CenterToCenterResponseDto(vc);
            ans.add(cd);
        }
        return ans;
    }
}
