package com.AmazingDevOpsLMS.repositories;


import com.AmazingDevOpsLMS.model.Student;
import com.AmazingDevOpsLMS.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository 
  extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByStudent(Student student);
}
