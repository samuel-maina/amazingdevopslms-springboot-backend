/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.AmazingDevOpsLMS.repositories;
import com.AmazingDevOpsLMS.model.Program;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author samuel
 */
public interface ProgramRepository extends PagingAndSortingRepository<Program,String>{
  @Query("select p from Program p join p.student s where s.email=?1")
public List<Program>getStudentPrograms(String email);  
}
