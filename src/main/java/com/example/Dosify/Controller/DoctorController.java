package com.example.Dosify.Controller;

import com.example.Dosify.dto.RequestDTO.DoctorRequestDto;
import com.example.Dosify.dto.ResponseDTO.DoctorResponseDto;
import com.example.Dosify.exception.CenterNotPresentException;
import com.example.Dosify.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @PostMapping("/add")
    public ResponseEntity addDoctor(@RequestBody DoctorRequestDto doctorRequestDto){

        try {
            DoctorResponseDto doctorResponseDto = doctorService.addDoctor(doctorRequestDto);
            return new ResponseEntity(doctorResponseDto,HttpStatus.CREATED);
        } catch (CenterNotPresentException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // get all the doctors who have more than x appointment
    @GetMapping("/doctor-with-at-least-appointments")
    public ResponseEntity getDocWithAtLeastXAppointments(@RequestParam int x){
        try{
            List<DoctorResponseDto> docs=doctorService.getDocWithAtLeastXAppointMents(x);
            return new ResponseEntity(docs,HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }


    //update the details based on email id of the doctor
    @PutMapping("/update-email")
    public ResponseEntity updateMailOfDoc(@RequestParam String email,@RequestParam String newEmail){
        try{

            return new ResponseEntity(doctorService.updateMailOfDoc(email,newEmail),HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.OK);
        }
    }
}
