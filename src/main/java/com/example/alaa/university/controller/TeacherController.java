package com.example.alaa.university.controller;

import com.example.alaa.university.domain.Subject;
import com.example.alaa.university.domain.Teacher;
import com.example.alaa.university.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/teachers")
@RestController
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

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

    @PutMapping("/updateTeacherName/{teacherId}/{name}")
    public String updateTeacherInfo(@PathVariable Long teacherId,
                                    @PathVariable String name) {
        return teacherService.updateTeacherJustInformation(teacherId, name) + "updated";
    }

    @DeleteMapping("/{teacherId}")
    public void deleteTeacher(@PathVariable long teacherId) {
        teacherService.deleteTeacher(teacherId);
    }

    @PutMapping("/update/{teacherId}")
    public Teacher updateTeacher(@PathVariable Long teacherId,
                                 @RequestBody Teacher updatedTeacher) {
        return teacherService.updateTeacher(teacherId, updatedTeacher);
    }

    @GetMapping("/subjectId/{subjectId}")
    public @ResponseBody List<Teacher> getAllTeacherBySubjectId(@PathVariable("subjectId") Long subjectId) {
        return teacherService.getAllTeacherBySubjectId(subjectId);
    }
}

