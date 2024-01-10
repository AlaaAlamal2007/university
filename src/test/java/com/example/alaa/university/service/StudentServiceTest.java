package com.example.alaa.university.service;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.domain.Gender;
import com.example.alaa.university.domain.Student;
import com.example.alaa.university.domain.University;
import com.example.alaa.university.exceptions.ArgumentStudentException;
import com.example.alaa.university.exceptions.ResourceStudentIsNotFoundException;
import com.example.alaa.university.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentRepo studentRepo;
    @Mock
    private UniversityRepo universityRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStudentSuccess() {
        Address address1 = new Address("Alwad7209", "AmericanSt60", 60);
        address1.setId(1L);
        Student student = new Student("Tasneem", address1,
                Gender.MALE, false, Instant.parse("1984-02-03T11:25:30.00Z"),
                Instant.parse("2017-02-03T11:25:30.00Z"), Instant.parse("2023-02-03T11:25:30.00Z"), 2000d,
                "MTasneem@gmail.com", 10L);
        student.setId(30L);
        Mockito.when(studentRepo.findById(anyLong())).thenReturn(Optional.of(student));
        Student getStudent = studentService.get(30L);
        assertNotNull(getStudent);
        assertNotNull(getStudent.getAddress());
    }

    @Test
    void getStudentThrowExceptionResourceIsNotFound() {
        ResourceStudentIsNotFoundException studentIsNotFoundException =
                assertThrowsExactly(ResourceStudentIsNotFoundException.class, () ->
                        studentService.get(50L));
        assertEquals("student with id=50 does not found",
                studentIsNotFoundException.getMessage());
    }

    @Test
    void addSuccess() {
        Student student = new Student();
        Address address = new Address("Alwad7209", "AmericanSt60", 60);
        address.setId(1L);
        student.setName("Amjad");
        student.setBirthDate(Instant.parse("1980-02-03T11:25:30.00Z"));
        student.setPaymentFee(4000d);
        student.setAddress(address);
        student.setGraduated(false);
        student.setRegistrationDate(Instant.parse("2000-02-03T11:25:30.00Z"));
        student.setGraduatedDate(null);
        student.setEmail("Amjad@yahoo.com");
        Mockito.when(studentRepo.save(any())).thenAnswer((InvocationOnMock invocation) -> {
            Object firstArgument = invocation.getArgument(0);
            Student studentArg = (Student) firstArgument;
            studentArg.setId(40L);
            return studentArg;
        });
        Student studentAdded = studentService.add(student, 2L);
        assertNotNull(studentAdded);
        assertNotNull(studentAdded.getId());
        assertEquals(40L, studentAdded.getId());
        assertEquals(1L, studentAdded.getAddress().getId());
    }

    @Test
    void validateStudentGetAddressException() {
        Student student = Mockito.mock(Student.class);
        when(student.getAddress()).thenThrow(new ArgumentStudentException("Student must have address "));
        ArgumentStudentException argumentStudentException =
                assertThrowsExactly(ArgumentStudentException.class, () -> student.getAddress());
        assertEquals("Student must have address ", argumentStudentException.getMessage());
    }

    @Test
    void validateStudentGetBirthDateException() {
        Student student = Mockito.mock(Student.class);
        when(student.getBirthDate()).thenThrow(new ArgumentStudentException("Student must have birthdate "));
        ArgumentStudentException argumentStudentException =
                assertThrowsExactly(ArgumentStudentException.class, () -> student.getBirthDate());
        assertEquals("Student must have birthdate ", argumentStudentException.getMessage());
    }

    @Test
    void validateStudentGetPaymentFeeException() {
        Student student = Mockito.mock(Student.class);
        when(student.getPaymentFee()).thenThrow(new ArgumentStudentException("Student fee must be between (2000,10000) "));
        ArgumentStudentException argumentStudentException =
                assertThrowsExactly(ArgumentStudentException.class, () -> student.getPaymentFee());
        assertEquals("Student fee must be between (2000,10000) ", argumentStudentException.getMessage());
    }

    @Test
    void validateStudentGetGraduatedDateException() {
        Student student = Mockito.mock(Student.class);
        when(student.getGraduatedDate()).thenThrow(new ArgumentStudentException("Student registration Date is before graduated date "));
        ArgumentStudentException argumentStudentException =
                assertThrowsExactly(ArgumentStudentException.class, () -> student.getGraduatedDate());
        assertEquals("Student registration Date is before graduated date ", argumentStudentException.getMessage());
    }

    @Test
    void updateSuccess() {
        Address address1 = new Address("Alwad7209", "AmericanSt60", 60);
        address1.setId(1L);
        Student oldStudent = new Student("Tasneem", address1,
                Gender.MALE, false, Instant.parse("1984-02-03T11:25:30.00Z"),
                Instant.parse("2017-02-03T11:25:30.00Z"), Instant.parse("2023-02-03T11:25:30.00Z"), 2000d,
                "MTasneem@gmail.com", 4L);
        oldStudent.setId(10L);
        Address address2 = new Address("Alwad7209", "AmericanSt60", 60);
        address2.setId(2L);
        Student newStudent = new Student("Tarteel", address2,
                Gender.MALE, false, Instant.parse("1984-02-03T11:25:30.00Z"),
                Instant.parse("2017-02-03T11:25:30.00Z"), Instant.parse("2023-02-03T11:25:30.00Z"), 2000d,
                "MTasneem@gmail.com", 10L);
        newStudent.setId(20L);
        University university = new University();
        university.setId(3L);
        Mockito.when(studentRepo.findById(anyLong())).thenReturn(Optional.of(oldStudent));
        doNothing().when(studentRepo).deleteById(anyLong());
        Mockito.when(studentService.add(newStudent, 3L)).thenReturn(newStudent);
        Mockito.when(studentRepo.save(any())).thenReturn(newStudent);
        Student updatedStudent = studentService.update(10L, newStudent);
        assertNotNull(updatedStudent);
        assertEquals(20L, updatedStudent.getId());
        verify(studentRepo).deleteById(10l);
    }

    @Test
    void updateThrowException() {
        ResourceStudentIsNotFoundException studentIsNotFoundException =
                assertThrowsExactly(ResourceStudentIsNotFoundException.class, () ->
                        studentService.update(30L, new Student()));
        assertEquals("student with id=30 does not found",
                studentIsNotFoundException.getMessage());
    }

    @Test
    void delete() {
        Address address = new Address();
        address.setId(20L);
        Student student = new Student();
        student.setId(32L);
        student.setAddress(address);
        Mockito.when(studentRepo.findById(anyLong())).thenReturn(Optional.of(student));
        doNothing().when(studentRepo).deleteById(anyLong());
        studentService.delete(32L);
        verify(studentRepo, times(1)).findById(32L);
        verify(studentRepo, times(1)).deleteById(32L);
    }

    @Test
    void getAll() {
        List<Student> studentList = Arrays.asList(
                new Student(),
                new Student(),
                new Student()
        );
        Mockito.when(studentRepo.findAll()).thenReturn(studentList);
        List<Student> getAllStudent = studentService.getAll();
        assertNotNull(getAllStudent);
        assertEquals(3, getAllStudent.size());
    }

    @Test
    void getAllEmpty() {
        Mockito.when(studentRepo.findAll()).thenReturn(new ArrayList<Student>());
        List<Student> getAllStudent = studentService.getAll();
        assertNotNull(getAllStudent);
        assertEquals(0, getAllStudent.size());
    }

    @Test
    void getAllStudentByUniversityId() {
        List<Student> students = Arrays.asList(
                new Student(),
                new Student(),
                new Student()
        );
        Mockito.when(studentRepo.findAllByUniversityId(anyLong())).thenReturn(students);
        List<Student> studentList = studentService.getAllStudentByUniversityId(13L);
        assertNotNull(studentList);
        assertEquals(3, studentList.size());
    }

    @Test
    void getAllStudentByUniversityIdEmpty() {
        Mockito.when(studentRepo.findAllByUniversityId(anyLong())).thenReturn(new ArrayList<Student>());
        List<Student> studentList = studentService.getAllStudentByUniversityId(13L);
        assertNotNull(studentList);
        assertEquals(0, studentList.size());
    }
}

