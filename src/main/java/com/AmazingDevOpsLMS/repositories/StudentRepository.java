/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.AmazingDevOpsLMS.repositories;

import com.AmazingDevOpsLMS.model.Program;
import com.AmazingDevOpsLMS.model.Student;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author samuel
 */
public interface StudentRepository extends CrudRepository<Student,String> {
     @Query("select S.programs from Student S  where S.email=?1")
public List<Program>getStudentPrograms(String email); 
}
