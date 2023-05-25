package com.example.Dosify.service;

import com.example.Dosify.Enum.CenterType;
import com.example.Dosify.dto.RequestDTO.CenterRequestDto;
import com.example.Dosify.dto.ResponseDTO.CenterResponseDto;
import com.example.Dosify.dto.ResponseDTO.DoctorResponseDto;

import java.util.List;

public interface VaccinationCenterService {

    public CenterResponseDto addCenter(CenterRequestDto centerRequestDto);

    List<DoctorResponseDto> getDoctorsAtACenter(int id) throws Exception;

    List<DoctorResponseDto> maleDoctorsAtCenter(int id) throws Exception;
//    pdfDir=D:/PdfReportRepo/
//    reportFileName=Employee-Report
//            reportFileNameDateFormat=dd_MMMM_yyyy
//    localDateFormat="dd MMMM yyyy HH:mm:ss"
//    logoImgPath=D:/img_JTO_logo.jpg
//            logoImgScale=50,50
//    currencySymbol=$
//            table_noOfColumns=4
//    table.columnNames=Emp Id,Emp Name,Emp Dept,Emp Sal
    List<DoctorResponseDto> femaleDoctorsAtCenter(int id) throws Exception;



    List<CenterResponseDto> centerType(CenterType centerType) throws Exception;
}
