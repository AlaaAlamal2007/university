package com.example.alaa.university.service;

import com.example.alaa.university.domain.Subject;
import com.example.alaa.university.domain.Teacher;
import com.example.alaa.university.exceptions.ResourceSubjectIsNotFoundException;
import com.example.alaa.university.exceptions.ResourceTeacherIsNotFoundException;
import com.example.alaa.university.jms.JmsTeacherSender;
import com.example.alaa.university.repository.SubjectRepo;
import com.example.alaa.university.repository.TeacherRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TeacherService {
    private final TeacherRepo teacherRepo;
    private final SubjectRepo subjectRepo;
    private final SubjectService subjectService;
    private final JmsTeacherSender jmsTeacherSender;

    public TeacherService(TeacherRepo teacherRepo, SubjectRepo subjectRepo, SubjectService subjectService, JmsTeacherSender jmsTeacherSender) {
        this.teacherRepo = teacherRepo;
        this.subjectRepo = subjectRepo;
        this.subjectService = subjectService;
        this.jmsTeacherSender = jmsTeacherSender;
    }

    public Teacher createTeacher(Teacher teacher) {
        Teacher savedTeacher = teacherRepo.save(teacher);
        jmsTeacherSender.sendTeacherAddedMessage(savedTeacher);
        return savedTeacher;
    }

    public Teacher getTeacher(Long teacherId) {
        return teacherRepo.findById(teacherId).orElseThrow(
                () -> new ResourceTeacherIsNotFoundException("" +
                        "teacher does not found id=" + teacherId)
        );
    }

    public List<Subject> getAllSubjectsAssignedToTeacher(Long teacherId) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new ResourceTeacherIsNotFoundException("" +
                        "teacher does not found id=" + teacherId));
        List<Subject> subjects = subjectRepo.findSubjectsByTeachersId(teacherId);
        return subjects;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepo.findAll();
    }

    public void deleteTeacher(long teacherId) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new ResourceTeacherIsNotFoundException("" +
                        "teacher does not found id=" + teacherId));
        Set<Subject> subjects = teacher.getSubjects();
        if (subjects != null && !subjects.isEmpty()) {
            subjects.stream()
                    .filter(sub -> sub != null)
                    .forEach(sub ->
                            subjectService.deleteSubjectAssignedToTeacher(sub.getId(), teacherId)
                    );
        }
        teacherRepo.deleteById(teacherId);
    }

    public Teacher updateTeacher(Long teacherId, Teacher updatedTeacher) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new ResourceTeacherIsNotFoundException("" +
                        "teacher does not found id=" + teacherId));
        teacher.setName(updatedTeacher.getName());
        return teacherRepo.saveAndFlush(teacher);
    }

    public List<Teacher> getAllTeacherBySubjectId(Long subjectId) {
        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(
                        () -> new ResourceSubjectIsNotFoundException("Subject does not found id=" + subjectId)
                );
        List<Teacher> teachers = teacherRepo.findTeachersBySubjectsId(subjectId);
        return teachers;
    }

    public Teacher updateTeacherJustInformation(Long teacherId, String name) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new ResourceTeacherIsNotFoundException("" +
                        "teacher does not found id=" + teacherId));
        teacher.setName(name);
        return teacherRepo.saveAndFlush(teacher);
    }
}




