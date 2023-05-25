package com.example.Dosify.service;

import com.example.Dosify.Enum.VaccineType;
import com.example.Dosify.model.Dose2;
import com.example.Dosify.model.User;

public interface Dose2Service {

    public Dose2 createDose2(User user, VaccineType vaccineType);
}
