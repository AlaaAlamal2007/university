package com.example.alaa.university.controller;

import com.example.alaa.university.domain.Subject;
import com.example.alaa.university.domain.Teacher;
import com.example.alaa.university.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/subjects")
@RestController
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public Subject createSubject(@RequestBody Subject subject) {
        return subjectService.createSubject(subject);
    }

    @GetMapping("/{subjectId}")
    public Subject getSubject(@PathVariable Long subjectId) {
        return subjectService.getSubject(subjectId);
    }

    @PutMapping("/{subjectId}/teachers/{teacherId}")
    public Subject assignTeacherToSubject(@PathVariable Long subjectId, @PathVariable Long teacherId) {
        return subjectService.assignTeacherToSubject(subjectId, teacherId);
    }

    @DeleteMapping("/{subjectId}/teachers/{teacherId}")
    public void deleteSubjectAssignedToTeacher(@PathVariable Long subjectId,
                                               @PathVariable Long teacherId) {
        subjectService.deleteSubjectAssignedToTeacher(subjectId, teacherId);
    }

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{subjectId}/teacher")
    public @ResponseBody List<Teacher> getAllTeacherBySubjectId(@PathVariable Long subjectId) {
        return subjectService.getAllTeacherBySubjectId(subjectId);
    }

    @DeleteMapping("/{subjectId}")
    public void deleteSubject(@PathVariable Long subjectId) {
        subjectService.deleteSubject(subjectId);
    }

    @PutMapping("/update/{subjectId}")
    public Subject updateSubject(@PathVariable Long subjectId, @RequestBody Subject updatedSubject) {

        return subjectService.updateSubject(subjectId, updatedSubject);
    }
}
