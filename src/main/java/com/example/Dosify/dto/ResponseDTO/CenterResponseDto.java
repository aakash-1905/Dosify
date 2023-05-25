package com.example.Dosify.dto.ResponseDTO;

import com.example.Dosify.Enum.CenterType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CenterResponseDto {

    String name;

    String location;

    CenterType centerType;
}
