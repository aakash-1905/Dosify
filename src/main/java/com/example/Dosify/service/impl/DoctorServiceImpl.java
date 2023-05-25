package com.example.Dosify.service.impl;

import com.example.Dosify.dto.RequestDTO.DoctorRequestDto;
import com.example.Dosify.dto.ResponseDTO.DoctorResponseDto;
import com.example.Dosify.exception.CenterNotPresentException;
import com.example.Dosify.model.Appointment;
import com.example.Dosify.model.Doctor;
import com.example.Dosify.model.VaccinationCenter;
import com.example.Dosify.repository.AppointmentRepository;
import com.example.Dosify.repository.CenterRepository;
import com.example.Dosify.repository.DoctorRepository;
import com.example.Dosify.service.DoctorService;
import com.example.Dosify.transformer.DoctorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    CenterRepository centerRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
@Autowired
DoctorRepository doctorRepository;
    public DoctorResponseDto addDoctor(DoctorRequestDto doctorRequestDto) throws CenterNotPresentException {

       Optional<VaccinationCenter> optionalCenter = centerRepository.findById(doctorRequestDto.getCenterId());
       if(!optionalCenter.isPresent()){
           throw new CenterNotPresentException("Invalid center id!");
       }

       VaccinationCenter center = optionalCenter.get();

       // dto -> entity
        Doctor doctor = DoctorTransformer.DoctorRequestDtoToDoctor(doctorRequestDto);
        doctor.setVaccinationCenter(center);
        // add doctor to current list of doctors at that center
        center.getDoctors().add(doctor);

        VaccinationCenter savedCenter = centerRepository.save(center);  // saves both center and doctor

        // prepare response Dto
        return DoctorTransformer.DoctorToDoctorResponseDto(doctor);
    }



    @Override
    public List<DoctorResponseDto> getDocWithAtLeastXAppointMents(int x) throws Exception {
        List<Appointment>docs= appointmentRepository.findAll();
            List<DoctorResponseDto>ans=new ArrayList<>();
        for(Appointment app:docs){
               Doctor d=app.getDoctor();
               if(d.getAppointments().size()>x){
                   DoctorResponseDto drd=DoctorTransformer.DoctorToDoctorResponseDto(d);
                   ans.add(drd);
               }

        }
        if(ans.size()==0)
            throw new Exception("No doctor found with atleast " +x+ " appointments");
        return ans;
    }

    @Override
    public String updateMailOfDoc(String email, String e) throws Exception {
        Optional<Doctor>doc=doctorRepository.findByEmailId(email);
        if(doc.isEmpty())
             throw new Exception("Doctor not found with"+" "+email+" as email");
        Doctor doctor=doc.get();
        doctor.setEmailId(e);
        doctorRepository.save(doctor);
        return "Email updated";
    }
}
