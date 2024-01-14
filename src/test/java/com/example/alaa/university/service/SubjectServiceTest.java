package com.example.alaa.university.service;

import com.example.alaa.university.domain.Subject;
import com.example.alaa.university.domain.Teacher;
import com.example.alaa.university.exceptions.ResourceSubjectIsNotFoundException;
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

class SubjectServiceTest {
    @InjectMocks
    private SubjectService subjectService;
    @Mock
    private SubjectRepo subjectRepo;
    @Mock
    private TeacherRepo teacherRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSubject() {
        Subject subject = new Subject();
        Subject newSubject = new Subject();
        newSubject.setId(30L);
        Mockito.when(subjectRepo.save(any())).thenReturn(newSubject);
        Subject savedSubject = subjectService.createSubject(subject);
        assertNotNull(savedSubject);
        assertEquals(30L, savedSubject.getId());
    }

    @Test
    void getSubjectSuccess() {
        Subject subject = new Subject();
        subject.setId(12L);
        Mockito.when(subjectRepo.findById(anyLong())).thenReturn(Optional.of(subject));
        Subject getSubject = subjectService.getSubject(12L);
        assertNotNull(getSubject);
        assertEquals(12L, getSubject.getId());
    }

    @Test
    void getSubjectThrowResourceSubjectIsNotFoundMethod() {
        ResourceSubjectIsNotFoundException resourceSubjectIsNotFoundException =
                assertThrowsExactly(ResourceSubjectIsNotFoundException.class,
                        () -> subjectService.getSubject(15L));
        assertEquals("Subject does not found id=15",
                resourceSubjectIsNotFoundException.getMessage());
    }

    @Test
    void assignTeacherToSubjectSuccess() {
        Subject subject = new Subject();
        subject.setId(12L);
        subject.setName("Math");
        subject.setTeachers(new HashSet<>());
        Teacher teacher = new Teacher();
        teacher.setId(45L);
        teacher.setSubjects(new HashSet<>());
        Mockito.when(subjectRepo.findById(anyLong())).thenReturn(Optional.of(subject));
        Mockito.when(teacherRepo.findById(anyLong())).thenReturn(Optional.of(teacher));
        Mockito.when(subjectRepo.save(any())).thenReturn(subject);
        Subject getSubject = subjectService.assignTeacherToSubject(12L, 45L);
        assertEquals(1, teacher.getSubjects().size());
        assertNotNull(getSubject);
        assertEquals(12L, getSubject.getId());
    }

    @Test
    void deleteSubjectAssignedToTeacher() {
        Subject subject = new Subject();
        subject.setId(12L);
        subject.setName("Math");
        subject.setTeachers(new HashSet<>());
        Teacher teacher = new Teacher();
        teacher.setId(45L);
        teacher.setSubjects(new HashSet<>());
        Mockito.when(subjectRepo.findById(anyLong())).thenReturn(Optional.of(subject));
        Mockito.when(teacherRepo.findById(anyLong())).thenReturn(Optional.of(teacher));
        Mockito.when(subjectRepo.save(any())).thenReturn(subject);
        subjectService.deleteSubjectAssignedToTeacher(12L, 45L);
        assertEquals(0, teacher.getSubjects().size());
    }

    @Test
    void deleteSubjectWithoutTeacher() {
        Subject subject = new Subject();
        subject.setId(12L);
        Mockito.when(subjectRepo.findById(anyLong())).thenReturn(Optional.of(subject));
        doNothing().when(subjectRepo).deleteById(anyLong());
        subjectService.deleteSubject(12L);
        verify(subjectRepo).deleteById(12L);
    }

    @Test
    void deleteSubjectWithTeacher() {
        Subject subject = new Subject();
        Set<Teacher> teachers = new HashSet<>();
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setSubjects(new HashSet<>());
        teachers.add(teacher1);
        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teachers.add(teacher2);
        teacher2.setSubjects(new HashSet<>());
        subject.setId(12L);
        subject.setTeachers(teachers);
        Mockito.when(subjectRepo.findById(anyLong())).thenReturn(Optional.of(subject));
        Mockito.when(subjectRepo.findById(anyLong())).thenReturn(Optional.of(subject));
        Mockito.when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher1));
        Mockito.when(teacherRepo.findById(2L)).thenReturn(Optional.of(teacher2));
        Mockito.when(teacherRepo.save(teacher1)).thenReturn(teacher1);
        Mockito.when(teacherRepo.save(teacher2)).thenReturn(teacher2);
        doNothing().when(subjectRepo).deleteById(anyLong());
        subjectService.deleteSubject(12L);
        verify(subjectRepo).deleteById(12L);
        verify(teacherRepo, times(2)).save(any());
    }

    @Test
    void updateSubject() {
        Subject subject = new Subject();
        Set<Teacher> teachers = new HashSet<>();
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setSubjects(new HashSet<>());
        teachers.add(teacher1);
        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teachers.add(teacher2);
        teacher2.setSubjects(new HashSet<>());
        subject.setId(12L);
        subject.setTeachers(teachers);
        Subject newSubject = new Subject();
        newSubject.setId(13L);
        newSubject.setName("history");
        newSubject.setTeachers(new HashSet<>());
        List<Teacher> teacherList = Arrays.asList(teacher1, teacher2);
        Mockito.when(subjectRepo.findById(12L)).thenReturn(Optional.of(subject));
        Mockito.when(teacherRepo.findTeachersBySubjectsId(12L)).thenReturn(teacherList);
        Mockito.when(subjectRepo.findById(12L)).thenReturn(Optional.of(subject));
        Mockito.when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher1));
        Mockito.when(teacherRepo.save(teacher1)).thenReturn(teacher1);
        Mockito.when(subjectRepo.findById(12L)).thenReturn(Optional.of(subject));
        Mockito.when(teacherRepo.findById(2L)).thenReturn(Optional.of(teacher2));
        Mockito.when(teacherRepo.save(teacher2)).thenReturn(teacher2);
        doNothing().when(subjectRepo).deleteById(12L);
        Mockito.when(subjectRepo.findById(13L)).thenReturn(Optional.of(newSubject));
        Mockito.when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher1));
        Mockito.when(subjectRepo.save(newSubject)).thenReturn(newSubject);
        Mockito.when(subjectRepo.findById(13L)).thenReturn(Optional.of(newSubject));
        Mockito.when(teacherRepo.findById(2L)).thenReturn(Optional.of(teacher2));
        Mockito.when(subjectRepo.save(newSubject)).thenReturn(newSubject);
        Subject updatedSubject = subjectService.updateSubject(12L, newSubject);
        assertEquals(2, updatedSubject.getTeachers().size());
        assertEquals(13L, updatedSubject.getId());
    }

    @Test
    void getAllSubjectsEmpty() {
        List<Subject> subjects = Arrays.asList();
        Mockito.when(subjectRepo.findAll()).thenReturn(subjects);
        List<Subject> getAllSubjects = subjectService.getAllSubjects();
        assertNotNull(getAllSubjects);
        assertEquals(0, getAllSubjects.size());
    }

    @Test
    void getAllSubjects() {
        List<Subject> subjects = Arrays.asList(
                new Subject(),
                new Subject()
        );
        Mockito.when(subjectRepo.findAll()).thenReturn(subjects);
        List<Subject> getAllSubjects = subjectService.getAllSubjects();
        assertNotNull(getAllSubjects);
        assertEquals(2, getAllSubjects.size());
    }
}
