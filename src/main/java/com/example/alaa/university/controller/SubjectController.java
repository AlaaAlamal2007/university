package com.example.alaa.university.controller;

import com.example.alaa.university.domain.Subject;
import com.example.alaa.university.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/subjects")
@RestController
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public Subject createSubject(@RequestBody Subject subject) {
        return subjectService.createSubject(subject);
    }

    @GetMapping("/{subjectId}")
    public Subject getSubject(@PathVariable Long subjectId) {
        return subjectService.getSubject(subjectId);
    }

    @PutMapping("/{subjectId}/teachers/{teacherId}")
    public Subject assignTeacherToSubject(@PathVariable Long subjectId,
                                          @PathVariable Long teacherId) {
        return subjectService.assignTeacherToSubject(subjectId, teacherId);
    }

    @DeleteMapping("/{subjectId}/teachers/{teacherId}")
    public void deleteSubjectAssignedToTeacher(@PathVariable Long subjectId,
                                               @PathVariable Long teacherId) {
        subjectService.deleteSubjectAssignedToTeacher(subjectId, teacherId);
    }

    @DeleteMapping("/{subjectId}")
    public void deleteSubject(@PathVariable Long subjectId) {
        subjectService.deleteSubject(subjectId);
    }

    @PutMapping("/update/{subjectId}")
    public Subject updateSubject(@PathVariable Long subjectId,
                                 @RequestBody Subject updatedSubject) {

        return subjectService.updateSubject(subjectId, updatedSubject);
    }

    @GetMapping
    public List<Subject> getAllSubject() {
        return subjectService.getAllSubjects();
    }
}

