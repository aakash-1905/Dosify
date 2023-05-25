package com.example.Dosify.repository;

import com.example.Dosify.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate,Integer> {
   Optional<Certificate> findByUserId(int id) ;
   Optional<Certificate>findByAppointmentNo(String no);
}
