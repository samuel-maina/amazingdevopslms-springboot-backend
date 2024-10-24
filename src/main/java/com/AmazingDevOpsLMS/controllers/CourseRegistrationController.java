/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AmazingDevOpsLMS.controllers;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author samuel
 */
@RestController
@RequestMapping("/api/v1/course_registration/")
@CrossOrigin(origins="*")
public class CourseRegistrationController  {
    
/*
    @PostMapping("/{courseId}")
    public ResponseEntity<?>saveStudentCourse(@PathVariable String courseId,Principal principal){
        return new ResponseEntity<>(courseRegistrationService.registerUserCourses(principal, courseId),HttpStatus.OK);
    }
     @DeleteMapping("/{courseId}")
    public ResponseEntity<?>dropStudentCourse(@PathVariable String courseId,Principal principal){
        courseRegistrationService.dropStudentCourse(principal, courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
*/
}
