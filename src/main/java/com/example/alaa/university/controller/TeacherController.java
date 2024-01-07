package com.example.alaa.university.controller;

import com.example.alaa.university.domain.Subject;
import com.example.alaa.university.domain.Teacher;
import com.example.alaa.university.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/teachers")
@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }

    @GetMapping("/{teacherId}")
    public Teacher getTeacher(@PathVariable Long teacherId) {
        return teacherService.getTeacher(teacherId);
    }

    @GetMapping("/subjects/{teacherId}")
    public List<Subject> getAllSubjectsAssignedToTeacher(@PathVariable Long teacherId) {
        return teacherService.getAllSubjectsAssignedToTeacher(teacherId);
    }

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PutMapping("/{teacherId}")
    public Teacher updateTeacher(@PathVariable Long teacherId) {
        return null;
    }

    @DeleteMapping("/{teacherId}")
    public void deleteTeacher(@PathVariable long teacherId) {
        teacherService.deleteTeacher(teacherId);
    }

    @PutMapping("/update/{teacherId}")
    public Teacher updateTeacher(@PathVariable Long teacherId, @RequestBody Teacher updatedTeacher) {
        return teacherService.updateTeacher(teacherId, updatedTeacher);
    }
}
