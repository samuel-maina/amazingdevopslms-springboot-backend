/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AmazingDevOpsLMS.controllers;

import com.AmazingDevOpsLMS.model.Course;
import com.AmazingDevOpsLMS.services.CourseService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author samuel
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/v1/courses/")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<?> getCourses() {
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }

    @PostMapping("/{batch_id}")
    public ResponseEntity<?> saveCourse(@RequestBody Course course,@PathVariable Long batch_id) {
        System.out.println(course+"AZA");
        return new ResponseEntity<>(courseService.saveCourse(course,batch_id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable String id) {
        courseService.deleteCourseById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findCourseById(@PathVariable String id) {
        return new ResponseEntity<>(courseService.findCourseById(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> UpdateCourseById(@RequestBody Course course, @PathVariable String id) {
        courseService.updateCourseById(course, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("configure/all")
    public ResponseEntity<?> configure() {
        return new ResponseEntity<>(courseService.batchInsert(), HttpStatus.OK);
    }
    @GetMapping("/program/{id}")
    public ResponseEntity<?> findCoursesByProgramsId(@PathVariable String id) {
        return new ResponseEntity<>(courseService.findCoursesByProgramId(id), HttpStatus.OK);
    }
}
