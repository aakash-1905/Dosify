package com.example.Dosify.repository;

import com.example.Dosify.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
@Query(value="select * from doctor d where d.age>:x",nativeQuery = true)
 List<Doctor> getdoctoraboveage(int x);

 Optional<Doctor> findByEmailId(String email);
}
