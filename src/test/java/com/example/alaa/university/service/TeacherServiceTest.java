package com.example.alaa.university.service;

import com.example.alaa.university.domain.Subject;
import com.example.alaa.university.domain.Teacher;
import com.example.alaa.university.exceptions.ResourceTeacherIsNotFoundException;
import com.example.alaa.university.repository.SubjectRepo;
import com.example.alaa.university.repository.TeacherRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TeacherServiceTest {
    @InjectMocks
    private TeacherService teacherService;
    @Mock
    private TeacherRepo teacherRepo;
    @Mock
    private SubjectRepo subjectRepo;
    @Mock
    private SubjectService subjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTeacherSuccess() {
        Teacher teacher = new Teacher();
        teacher.setId(10L);
        Mockito.when(teacherRepo.save(any())).thenReturn(teacher);
        Teacher savedTeacher = teacherService.createTeacher(new Teacher());
        assertNotNull(teacher);
        assertEquals(10L, savedTeacher.getId());
    }

    @Test
    void getTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(12L);
        Mockito.when(teacherRepo.findById(12L)).thenReturn(Optional.of(teacher));
        Teacher getTeacher = teacherService.getTeacher(12L);
        assertNotNull(getTeacher);
        assertEquals(12L, getTeacher.getId());
    }

    @Test
    void getTeacherThrowResourceTeacherIsNotFound() {
        ResourceTeacherIsNotFoundException resourceTeacherIsNotFoundException =
                assertThrowsExactly(ResourceTeacherIsNotFoundException.class,
                        () -> teacherService.getTeacher(20L));
        assertEquals("teacher does not found id=20",
                resourceTeacherIsNotFoundException.getMessage());
    }

    @Test
    void getAllSubjectsAssignedToTeacherSuccess() {
        Teacher teacher = new Teacher();
        teacher.setId(12l);
        List<Subject> subjects = Arrays.asList(
                new Subject(),
                new Subject(),
                new Subject()
        );
        Mockito.when(teacherRepo.findById(anyLong())).thenReturn(Optional.of(teacher));
        Mockito.when(subjectRepo.findSubjectsByTeachersId(anyLong())).thenReturn(subjects);
        List<Subject> subjectsAssignedToTeacher = teacherService.getAllSubjectsAssignedToTeacher(12L);
        assertNotNull(subjectsAssignedToTeacher);
        assertEquals(3, subjectsAssignedToTeacher.size());
    }

    @Test
    void getAllSubjectsAssignedToTeacherThrowResoureNotFoundException() {
        ResourceTeacherIsNotFoundException resourceTeacherIsNotFoundException =
                assertThrowsExactly(ResourceTeacherIsNotFoundException.class,
                        () -> teacherService.getAllSubjectsAssignedToTeacher(13L));
        assertEquals("teacher does not found id=13",
                resourceTeacherIsNotFoundException.getMessage());
    }

    @Test
    void getAllTeachers() {
        List<Teacher> teacherList = Arrays.asList(
                new Teacher(),
                new Teacher(),
                new Teacher()
        );
        Mockito.when(teacherRepo.findAll()).thenReturn(teacherList);
        List<Teacher> getAllTeachers = teacherService.getAllTeachers();
        assertNotNull(getAllTeachers);
        assertEquals(3, getAllTeachers.size());
    }

    @Test
    void getAllTeacherEmpty() {
        Mockito.when(teacherRepo.findAll()).thenReturn(Arrays.asList());
        List<Teacher> getAllTeachers = teacherService.getAllTeachers();
        assertNotNull(getAllTeachers);
        assertEquals(0, getAllTeachers.size());
    }

    @Test
    void deleteTeacherWithoutSubjects() {
        Teacher teacher = new Teacher();
        teacher.setId(10L);
        Mockito.when(teacherRepo.findById(anyLong())).thenReturn(Optional.of(teacher));
        doNothing().when(teacherRepo).deleteById(anyLong());
        teacherService.deleteTeacher(10L);
        verify(teacherRepo, times(1)).deleteById(10L);
    }

    @Test
    void deleteTeacherWithSubjects() {
        Teacher teacher = new Teacher();
        teacher.setId(10L);
        Set<Subject> subjects = new HashSet<>();
        Subject subject1 = new Subject();
        subject1.setId(11L);
        subjects.add(subject1);
        Subject subject2 = new Subject();
        subject2.setId(12L);
        subjects.add(subject2);
        Subject subject3 = new Subject();
        subject3.setId(13L);
        subjects.add(subject3);
        teacher.setSubjects(subjects);
        Mockito.when(teacherRepo.findById(anyLong())).thenReturn(Optional.of(teacher));
        doNothing().when(subjectService).deleteSubjectAssignedToTeacher(11L, 10L);
        doNothing().when(subjectService).deleteSubjectAssignedToTeacher(12L, 10L);
        doNothing().when(subjectService).deleteSubjectAssignedToTeacher(13L, 10L);
        doNothing().when(teacherRepo).deleteById(anyLong());
        teacherService.deleteTeacher(10L);
        verify(teacherRepo, times(1)).deleteById(10L);
        verify(subjectService, times(1)).deleteSubjectAssignedToTeacher(11L, 10L);
        verify(subjectService, times(1)).deleteSubjectAssignedToTeacher(12L, 10L);
        verify(subjectService, times(1)).deleteSubjectAssignedToTeacher(13L, 10L);
    }

    @Test
    void updateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(10L);
        Teacher newTeacher = new Teacher();
        newTeacher.setId(47L);
        Mockito.when(teacherRepo.findById(anyLong())).thenReturn(Optional.of(teacher));
        Mockito.when(teacherService.createTeacher(any())).thenReturn(newTeacher);
        Teacher updatedTeacher = teacherService.updateTeacher(10L, newTeacher);
        assertNotNull(updatedTeacher);
        assertEquals(47L, updatedTeacher.getId());
    }

    @Test
    void getAllTeacherBySubjectIdSuccess() {
        Subject subject = new Subject();
        subject.setId(23L);
        List<Teacher> teachers = Arrays.asList(
                new Teacher(),
                new Teacher(),
                new Teacher()
        );
        Mockito.when(subjectRepo.findById(anyLong())).thenReturn(Optional.of(subject));
        Mockito.when(teacherRepo.findTeachersBySubjectsId(anyLong())).thenReturn(teachers);
        List<Teacher> getAllTeachersSubject = teacherService.getAllTeacherBySubjectId(23L);
        assertNotNull(getAllTeachersSubject);
        assertEquals(3, getAllTeachersSubject.size());
    }

    @Test
    void updateTeacherJustInformation() {
        Teacher teacher = new Teacher();
        teacher.setId(12L);
        teacher.setName("Muna");
        Mockito.when(teacherRepo.findById(12L)).thenReturn(Optional.of(teacher));
        Mockito.when(teacherRepo.saveAndFlush(any())).thenReturn(teacher);
        assertEquals("Muna", teacher.getName());
    }
}

