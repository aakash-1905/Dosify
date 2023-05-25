package com.example.Dosify.repository;

import com.example.Dosify.Enum.CenterType;
import com.example.Dosify.model.VaccinationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CenterRepository extends JpaRepository<VaccinationCenter,Integer> {
    List<VaccinationCenter> findByCenterType(CenterType centerType);
}
