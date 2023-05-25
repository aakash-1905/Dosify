package com.example.Dosify.dto.ResponseDTO;

import com.example.Dosify.Enum.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class FindUserbyEmailResponseDto {
    String name;

    int age;

    String emailId;

    String mobNo;

    Gender gender;
    String IsDose1;
    String IsDose2;
}
