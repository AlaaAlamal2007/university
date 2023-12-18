package com.example.alaa.university.controller;

import com.example.alaa.university.domain.Student;
import com.example.alaa.university.service.IStudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/students")
@RestController
public class StudentController {
    private final IStudentService iStudentServiceC;

    public StudentController(IStudentService iStudentServiceC) {
        this.iStudentServiceC = iStudentServiceC;
    }

    @GetMapping("/{id}")
    Student get(@PathVariable Long id) {
        return iStudentServiceC.get(id);
    }

    @PostMapping("universities/{universityId}")
    @ResponseStatus(HttpStatus.CREATED)
    Student add(@RequestBody Student student, @PathVariable long universityId) {
        return iStudentServiceC.add(student, universityId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Student update(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return iStudentServiceC.update(id, updatedStudent);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        iStudentServiceC.delete(id);
    }

    @GetMapping
    public List<Student> getAll() {
        return iStudentServiceC.getAll();
    }

    @GetMapping("/universities/{id}")
    public List<Student> getAllStudentByUniversityId(@PathVariable Long id) {
        return iStudentServiceC.getAllStudentByUniversityId(id);
    }
}
