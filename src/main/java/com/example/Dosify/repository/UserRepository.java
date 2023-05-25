package com.example.Dosify.repository;

import com.example.Dosify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "select * from user s where s.email_id=:email",nativeQuery = true)
    Optional<User> findByEmailId(String email);
@Query(value="select * from user s where s.is_dose1_taken=false;  ",nativeQuery = true)
    List<User> userwithnodosetaken();
    @Query(value="select * from user s where (s.is_dose1_taken=true and is_dose2_taken=false) or s.is_dose1_taken=false;",nativeQuery = true)
    List<User> userwithnodose2taken();
    @Query(value="select * from user s where s.is_dose1_taken=true and is_dose2_taken=true; ",nativeQuery = true)
    List<User> userwithalldosetaken();
//     findByMobNo(String mob);
}
