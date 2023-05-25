package com.example.Dosify.Controller;

import com.example.Dosify.Enum.CenterType;
import com.example.Dosify.dto.RequestDTO.CenterRequestDto;
import com.example.Dosify.dto.ResponseDTO.CenterResponseDto;
import com.example.Dosify.service.VaccinationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/center")
public class VaccinationCenterController {

    @Autowired
    VaccinationCenterService vaccinationCenterService;

    @PostMapping("/add")
    public ResponseEntity addVaccinationCenter(@RequestBody CenterRequestDto centerRequestDto){
        CenterResponseDto centerResponseDto = vaccinationCenterService.addCenter(centerRequestDto);
        return new ResponseEntity(centerResponseDto, HttpStatus.CREATED);
    }

    // give the list of all doctors at a particular center(centerId)
     @GetMapping("/doctors-at-centre")
     public ResponseEntity getDoctorsAtACenter(@RequestParam int id){
        try{
            return new ResponseEntity(vaccinationCenterService.getDoctorsAtACenter(id),HttpStatus.OK);
        }
        catch(Exception  e){
             return new ResponseEntity(e.getMessage(),HttpStatus.OK);
         }
     }
    // give the list of all male doctors at a particular center(centerId)
    @GetMapping("/male-doctors-at-centre")
    public ResponseEntity maleDoctorsAtCenter(@RequestParam int id){
        try{
            return new ResponseEntity(vaccinationCenterService.maleDoctorsAtCenter(id),HttpStatus.OK);
        }
        catch(Exception  e){
            return new ResponseEntity(e.getMessage(),HttpStatus.OK);
        }
    }
    // give the list of all females doctors at a particular center(centerId)
    @GetMapping("/female-doctors-at-center")
    public ResponseEntity femaleDoctorsAtCenter(@RequestParam int id){
        try{
            return new ResponseEntity(vaccinationCenterService.femaleDoctorsAtCenter(id),HttpStatus.OK);
        }
        catch(Exception  e){
            return new ResponseEntity(e.getMessage(),HttpStatus.OK);
        }
    }

    // give all centers of a particular centerType
    @GetMapping("/centers-of-type")
    public ResponseEntity centerType(@RequestParam CenterType centerType){
        try{
            return new ResponseEntity(vaccinationCenterService.centerType(centerType),HttpStatus.OK);
        }
        catch(Exception  e){
            return new ResponseEntity(e.getMessage(),HttpStatus.OK);
        }
    }

}
