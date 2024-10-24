package com.AmazingDevOpsLMS.services;

import com.AmazingDevOpsLMS.exceptions.ResourceNotFoundException;
import com.AmazingDevOpsLMS.exceptions.StudentNotFoundException;
import com.AmazingDevOpsLMS.model.Image;
import com.AmazingDevOpsLMS.model.Program;
import com.AmazingDevOpsLMS.model.Student;
import com.AmazingDevOpsLMS.repositories.ImageRepository;
import com.AmazingDevOpsLMS.repositories.ProgramRepository;
import com.AmazingDevOpsLMS.repositories.StudentRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ImageRepository imageRepository;

    public Iterable<Program> getPrograms() {
        return programRepository.findAll();
    }

    public Program saveProgram(Program program) {
        Image image = program.getImage();
        image.setId(RandomString.make(64));
        Image tempImage = imageRepository.save(image);
        program.setImage(tempImage);
        program.setId(RandomString.make(64));
        Program temp = programRepository.save(program);
        notificationService.save(temp);
        return temp;
    }

    public void deleteProgramById(String Id) {
        Program program = this.findProgramById(Id);
        programRepository.delete(program);
    }

    public void updateProgramById(Program program, String Id) {

        Program program_ = this.findProgramById(Id);
        program_.setName(program.getName());
        program_.setDescription(program.getDescription());
        programRepository.save(program_);

    }

    public Program findProgramById(String Id) {
        Optional<Program> program = programRepository.findById(Id);
        if (program.isPresent()) {
            return program.get();
        } else {
            throw new ResourceNotFoundException("");
        }
    }

    public void applyProgram(String programId, Principal principal) {
        Student student = studentRepository.findById(principal.getName()).orElseThrow(() -> new StudentNotFoundException(""));
        Program program = programRepository.findById(programId).orElseThrow(() -> new ResourceNotFoundException(""));
        List<Student> students = program.getStudent();
        students.add(student);
        program.setStudent(students);
        programRepository.save(program);
    }

    public List<Program> getStudentPrograms(Principal principal) {
        return programRepository.getStudentPrograms(principal.getName());
    }

}
