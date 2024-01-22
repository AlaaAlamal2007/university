package com.example.alaa.university.service;

import com.example.alaa.university.domain.Subject;
import com.example.alaa.university.domain.Teacher;
import com.example.alaa.university.exceptions.ResourceSubjectIsNotFoundException;
import com.example.alaa.university.exceptions.ResourceTeacherIsNotFoundException;
import com.example.alaa.university.repository.SubjectRepo;
import com.example.alaa.university.repository.TeacherRepo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SubjectService {
    private final SubjectRepo subjectRepository;
    private final TeacherRepo teacherRepo;

    public SubjectService(SubjectRepo subjectRepository,
                          TeacherRepo teacherRepo) {
        this.subjectRepository = subjectRepository;
        this.teacherRepo = teacherRepo;
    }

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject getSubject(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(
                        () -> new ResourceSubjectIsNotFoundException("Subject does not found id=" + subjectId)
                );
    }

    public Subject assignTeacherToSubject(Long subjectId, Long teacherId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceSubjectIsNotFoundException("Subject does not found id=" + subjectId)
        );
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(
                () -> new ResourceTeacherIsNotFoundException("teacher does not found id=" + teacherId)
        );
        teacher.addSubject(subject);
        return subjectRepository.save(subject);
    }

    public void deleteSubjectAssignedToTeacher(Long subjectId, Long teacherId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceSubjectIsNotFoundException("Subject does not found id=" + subjectId)
        );
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(
                () -> new ResourceTeacherIsNotFoundException("teacher does not found id=" + teacherId)
        );
        teacher.removeSubject(subject.getId());
        teacherRepo.save(teacher);
    }

    public void deleteSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(
                        () -> new ResourceSubjectIsNotFoundException("Subject does not found id=" + subjectId)
                );
        Set<Teacher> teachers = subject.getTeachers();
        if (teachers != null && !teachers.isEmpty()) {
            teachers.forEach(teacher ->
                    deleteSubjectAssignedToTeacher(subjectId, teacher.getId()));
        }

        subjectRepository.deleteById(subjectId);
    }

    public Subject updateSubject(Long subjectId, Subject updatedSubject) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(
                        () -> new ResourceSubjectIsNotFoundException("Subject does not found id=" + subjectId)
                );
        subject.setName(updatedSubject.getName());
        subject.setMarksObtained(updatedSubject.getMarksObtained());
        return subjectRepository.saveAndFlush(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
}

