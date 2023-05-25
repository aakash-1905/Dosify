package com.example.Dosify.service.impl;

import com.example.Dosify.Enum.Gender;
import com.example.Dosify.dto.RequestDTO.UserRequestDto;
import com.example.Dosify.dto.ResponseDTO.FindUserbyEmailResponseDto;
import com.example.Dosify.dto.ResponseDTO.UserResponseDto;
import com.example.Dosify.model.User;
import com.example.Dosify.repository.UserRepository;
import com.example.Dosify.service.UserService;
import com.example.Dosify.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        User user = UserTransformer.UserRequestDtoToUser(userRequestDto);

        // save the object in db
        User savedUser = userRepository.save(user);

        //entity -> response dto
        UserResponseDto userResponseDto = UserTransformer.UserToUserResponseDto(savedUser);

        return userResponseDto;

    }
    @Override
    public FindUserbyEmailResponseDto findUser (String emailid) throws Exception {
        Optional<User> use=userRepository.findByEmailId(emailid);
        if(use.isEmpty())
              throw new Exception("Email not Found");
        User user=use.get();
        FindUserbyEmailResponseDto findUserbyEmailResponseDto=new FindUserbyEmailResponseDto();
        findUserbyEmailResponseDto.setAge(user.getAge());
        findUserbyEmailResponseDto.setName(user.getName());
        findUserbyEmailResponseDto.setEmailId(user.getEmailId());
        findUserbyEmailResponseDto.setGender(user.getGender());
        findUserbyEmailResponseDto.setMobNo(user.getMobNo());
        if(user.isDose1Taken()){
            findUserbyEmailResponseDto.setIsDose1("Dose1 Taken");
        }
        if(!user.isDose1Taken()){
            findUserbyEmailResponseDto.setIsDose1("Dose1 not taken");
        }
        if(user.isDose2Taken()){
            findUserbyEmailResponseDto.setIsDose2("Dose2 Taken");
        }
        else{
            findUserbyEmailResponseDto.setIsDose2("Dose2 not taken");
        }
        return findUserbyEmailResponseDto;
    }

    @Override
    public void updateMob(String email,String mob) throws Exception{
        Optional<User> user=userRepository.findByEmailId(email);
        if(user.isPresent()){
            User curr=user.get();
            curr.setMobNo(mob);
            userRepository.save(curr);
        }
        else{
            throw new Exception("User doesnt Exist cant update mobno");
        }
    }

    @Override
    public List<User> getNonVaccinatedUsers() throws Exception {
        List<User>users=userRepository.userwithnodosetaken();
        if(users.size()==0)
              throw new Exception("No User Found");
        return users;
    }

    @Override
    public List<User> getPartiallyVaccinatedUsers()  throws Exception {
        List<User>users=userRepository.userwithnodose2taken();
        if(users.size()==0)
            throw new Exception("No User Found");
        return users;
    }

    @Override
    public List<User> getFullyVaccinatedUsers() throws Exception{
        List<User>users=userRepository.userwithalldosetaken();
        if(users.size()==0)
            throw new Exception("No User Found");
        return users;
    }
}
