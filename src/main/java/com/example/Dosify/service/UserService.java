package com.example.Dosify.service;

import com.example.Dosify.dto.RequestDTO.UserRequestDto;
import com.example.Dosify.dto.ResponseDTO.FindUserbyEmailResponseDto;
import com.example.Dosify.dto.ResponseDTO.UserResponseDto;
import com.example.Dosify.model.User;

import java.util.List;

public interface UserService {

    public UserResponseDto addUser(UserRequestDto userRequestDto);

    public FindUserbyEmailResponseDto findUser(String email)throws Exception;

    public void updateMob(String email,String mob) throws Exception;

    List<User> getNonVaccinatedUsers() throws Exception;

    List<User> getPartiallyVaccinatedUsers() throws Exception;

    List<User> getFullyVaccinatedUsers() throws Exception;

}
