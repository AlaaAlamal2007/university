package com.example.alaa.university.service;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.domain.Gender;
import com.example.alaa.university.domain.Student;
import com.example.alaa.university.domain.University;
import com.example.alaa.university.exceptions.ArgumentStudentException;
import com.example.alaa.university.exceptions.ResourceStudentIsNotFoundException;
import com.example.alaa.university.repository.IAddressRepository;
import com.example.alaa.university.repository.IStudentRepository;
import com.example.alaa.university.repository.IUniversityRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    @InjectMocks
    private StudentService studentService;
    @Mock
    private IStudentRepository iStuRepository;
    @Mock
    private IAddressRepository iAddRepository;
    @Mock
    private IUniversityRepository iUniRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStudentSuccess() {
        Mockito.when(iStuRepository.get(30L)).thenReturn(new Student());
        Mockito.when(iAddRepository.getStudentAddressId(30L)).thenReturn(new Address());
        Student student = studentService.get(30L);
        assertNotNull(student);
        assertNotNull(student.getAddress());
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
        student.setBirthDate(Instant.parse("1980-02-03T11:25:30.00Z"));
        student.setPaymentFee(4000d);
        student.setAddress(new Address("Tour", "A street", 1));
        Mockito.when(iAddRepository.add(any())).thenAnswer((InvocationOnMock invocation) -> {
            Object firstArgument = invocation.getArgument(0);
            Address addressArg = (Address) firstArgument;
            addressArg.setId(30L);
            return addressArg;
        });
        Mockito.when(iStuRepository.add(any(), anyLong())).thenAnswer((InvocationOnMock invocation) -> {
            Object firstArgument = invocation.getArgument(0);
            Student studentArg = (Student) firstArgument;
            studentArg.setId(40L);
            return studentArg;
        });
        Student studentAdded = studentService.add(student, 2L);
        assertNotNull(studentAdded);
        assertNotNull(studentAdded.getId());
        assertEquals(40L, studentAdded.getId());
        assertEquals(30, studentAdded.getAddress().getId());
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
                "MTasneem@gmail.com");
        oldStudent.setId(10L);
        Address address2 = new Address("Alwad7209", "AmericanSt60", 60);
        address2.setId(2L);
        Student newStudent = new Student("Tarteel", address2,
                Gender.MALE, false, Instant.parse("1984-02-03T11:25:30.00Z"),
                Instant.parse("2017-02-03T11:25:30.00Z"), Instant.parse("2023-02-03T11:25:30.00Z"), 2000d,
                "MTasneem@gmail.com");
        newStudent.setId(20L);
        University university = new University();
        university.setId(3L);
        Mockito.when(iStuRepository.get(anyLong())).thenReturn(oldStudent);
        doNothing().when(iStuRepository).delete(anyLong());
        Mockito.when(iUniRepository.getStudentUniversityId(anyLong())).thenReturn(university);
       Mockito.when(iAddRepository.getStudentAddressId(10L)).thenReturn(address1);
       // Mockito.when(studentService.add(newStudent, 3L)).thenReturn(newStudent);
       Mockito.when(iAddRepository.add(any())).thenReturn(address2);
        Mockito.when(iStuRepository.add(newStudent,3L)).thenReturn(newStudent);
        Student updatedStudent = studentService.update(10L, newStudent);
        assertNotNull(updatedStudent);
        assertEquals(20L, updatedStudent.getId());
        verify(iStuRepository).delete(10l);
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
        Mockito.when(iStuRepository.get(anyLong())).thenReturn(student);
        Mockito.when(iAddRepository.getStudentAddressId(10L)).thenReturn(address);
        Mockito.when(iStuRepository.setStudentUniversityAndAddressIdNull(anyLong())).thenReturn(new Student());
        doNothing().when(iStuRepository).delete(anyLong());
        doNothing().when(iAddRepository).delete(20L);
        studentService.delete(10L);
        verify(iStuRepository).setStudentUniversityAndAddressIdNull(10L);
        verify(iStuRepository).get(10L);
        verify(iAddRepository).delete(20L);
    }

    @Test
    void getAll() {
        List<Student> studentList = Arrays.asList(
                new Student(),
                new Student(),
                new Student()
        );
        Mockito.when(iStuRepository.getAll()).thenReturn(studentList);
        Mockito.when(iAddRepository.get(anyLong())).thenReturn(new Address());
        List<Student> getAllStudent = studentService.getAll();
        assertNotNull(getAllStudent);
        assertEquals(3, getAllStudent.size());
    }

    @Test
    void getAllEmpty() {
        Mockito.when(iStuRepository.getAll()).thenReturn(new ArrayList<Student>());
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
        Mockito.when(iStuRepository.getAllStudentByUniversityId(anyLong())).thenReturn(students);
        List<Student> studentList = studentService.getAllStudentByUniversityId(13L);
        assertNotNull(studentList);
        assertEquals(3, studentList.size());
    }

    @Test
    void getAllStudentByUniversityIdEmpty() {

        Mockito.when(iStuRepository.getAllStudentByUniversityId(anyLong())).thenReturn(new ArrayList<Student>());
        List<Student> studentList = studentService.getAllStudentByUniversityId(13L);
        assertNotNull(studentList);
        assertEquals(0, studentList.size());
    }

    @Test
    void setStudentUniversityAndAddressIdNull() {
        Address address = new Address("Alwad7209", "AmericanSt60", 60);
        address.setId(1L);
        Student student = new Student("Tasneem", address,
                Gender.MALE, false, Instant.parse("1984-02-03T11:25:30.00Z"),
                Instant.parse("2017-02-03T11:25:30.00Z"), Instant.parse("2023-02-03T11:25:30.00Z"), 2000d,
                "MTasneem@gmail.com");
        student.setId(10L);
        Student studentAfterSetNull = new Student("Tasneem", address,
                Gender.MALE, false, Instant.parse("1984-02-03T11:25:30.00Z"),
                Instant.parse("2017-02-03T11:25:30.00Z"), Instant.parse("2023-02-03T11:25:30.00Z"), 2000d,
                "MTasneem@gmail.com");
        studentAfterSetNull.setId(null);
        studentAfterSetNull.setAddress(null);
        Mockito.when(iStuRepository.get(anyLong())).thenReturn(student);
        Mockito.when(iStuRepository.setStudentUniversityAndAddressIdNull(anyLong())).thenReturn(studentAfterSetNull);
        Student student1 = studentService.setStudentUniversityAndAddressIdNull(13L);
        assertNotNull(student1);
        assertEquals(null, student1.getId());
        assertEquals(null, student1.getAddress());
    }

    @Test
    void setStudentUniversityAndAddressIdNullThrowResourceStudentIsNotFound() {
        ResourceStudentIsNotFoundException studentIsNotFoundException =
                assertThrowsExactly(ResourceStudentIsNotFoundException.class, () ->
                        studentService.setStudentUniversityAndAddressIdNull(55L));
        assertEquals("student with id=55 does not found",
                studentIsNotFoundException.getMessage());
    }

    @Test
    void setStudentAddressId() {
        Address address = new Address("Alwad7209", "AmericanSt60", 60);
        address.setId(1L);
        Student student = new Student("Tasneem", address,
                Gender.MALE, false, Instant.parse("1984-02-03T11:25:30.00Z"),
                Instant.parse("2017-02-03T11:25:30.00Z"), Instant.parse("2023-02-03T11:25:30.00Z"), 2000d,
                "MTasneem@gmail.com");
        student.setId(12L);
        Mockito.when(iStuRepository.get(anyLong())).thenReturn(student);
        Mockito.when(iStuRepository.setStudentAddressId(anyLong(), anyLong())).thenReturn(student);
        Student student1 = studentService.setStudentAddressId(12L, 1L);
        assertEquals(12L, student1.getId());
        assertEquals(1L, student1.getAddress().getId());
    }

    @Test
    void setStudentAddressIdTrowExceptionResourceStudentNotFound() {
        ResourceStudentIsNotFoundException studentIsNotFoundException =
                assertThrowsExactly(ResourceStudentIsNotFoundException.class, () ->
                        studentService.setStudentAddressId(40L, 4L));
        assertEquals("student with id=40 does not found",
                studentIsNotFoundException.getMessage());
    }
}
