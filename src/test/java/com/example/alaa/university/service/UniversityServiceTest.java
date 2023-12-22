package com.example.alaa.university.service;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.domain.Student;
import com.example.alaa.university.domain.University;
import com.example.alaa.university.domain.UniversityType;
import com.example.alaa.university.exceptions.ArgumentUniversityException;
import com.example.alaa.university.exceptions.ResourceUniversityIsNotFoundException;
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

class UniversityServiceTest {
    @InjectMocks
    private UniversityService universityService;
    @Mock
    private IUniversityRepository iUniversityRepository;
    @Mock
    private IAddressService iAddressService;
    @Mock
    private IStudentService iStudService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUniversityWithoutStudent() {
        Mockito.when(iAddressService.getUniversityAddressId(anyLong())).thenReturn(new Address());
        Mockito.when(iUniversityRepository.get(50L)).thenReturn(new University());
        University university = universityService.get(50L);
        assertNotNull(university);
        assertNotNull(university.getStudents());
        assertEquals(0, university.getStudents().size());
    }

    @Test
    void getUniversityWithStudent() {
        Mockito.when(iAddressService.getUniversityAddressId(anyLong())).thenReturn(new Address());
        Mockito.when(iUniversityRepository.get(50L)).thenReturn(new University());
        Mockito.when(iStudService.getAllStudentByUniversityId(50L)).thenReturn(
                Arrays.asList(
                        new Student(),
                        new Student(),
                        new Student())
        );
        University university = universityService.get(50L);
        assertNotNull(university);
        assertEquals(3, university.getStudents().size());
    }

    @Test
    void getUniversityThrowException() {
        ResourceUniversityIsNotFoundException universityIsNotFoundException =
                assertThrowsExactly(ResourceUniversityIsNotFoundException.class,
                        () -> universityService.get(55L));
        assertEquals("university with id=55 does not exist",
                universityIsNotFoundException.getMessage());
    }

    @Test
    void addSuccessWithoutStudents() {
        Address address = new Address("Almukaber", "American street",
                43);
        University university = new University("Alblqa", address,
                UniversityType.GOVERMENTAL, "Alblqa@gmail.com", 5000d,
                Instant.parse("2017-02-03T11:25:30.00Z"), null);
        Mockito.when(iAddressService.add(any())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    Object firstArgument = invocation.getArgument(0);
                    Address uniAddress = (Address) firstArgument;
                    address.setId(10L);
                    return uniAddress;
                });
        Mockito.when(iUniversityRepository.add(any())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    Object firstArgument = invocation.getArgument(0);
                    University uniAdded = (University) firstArgument;
                    uniAdded.setId(102L);
                    return uniAdded;
                });
        University universityAdded = universityService.add(university);
        assertNotNull(universityAdded);
        assertEquals(102L, universityAdded.getId());
        assertEquals(0, universityAdded.getStudents().size());
        Mockito.verify(iStudService, never()).add(any(), anyLong());
        assertEquals("Almukaber", universityAdded.getAddress().getCityName());
    }

    @Test
    void addSuccessWithStudents() {
        Address address = new Address("Almukaber", "American street",
                43);
        List<Student> studentList = Arrays.asList(
                new Student(),
                new Student(),
                new Student());
        University university = new University("Alblqa", address,
                UniversityType.GOVERMENTAL, "Alblqa@gmail.com", 5000d,
                Instant.parse("2017-02-03T11:25:30.00Z"), studentList);
        Mockito.when(iAddressService.add(any())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    Object firstArgument = invocation.getArgument(0);
                    Address uniAddress = (Address) firstArgument;
                    address.setId(10L);
                    return uniAddress;
                });
        Mockito.when(iUniversityRepository.add(any())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    Object firstArgument = invocation.getArgument(0);
                    University uniAdded = (University) firstArgument;
                    uniAdded.setId(102L);
                    return uniAdded;
                });
        University universityAdded = universityService.add(university);
        assertNotNull(universityAdded);
        assertNotNull(universityAdded.getId());
        assertEquals(102L, universityAdded.getId());
        assertEquals(3, universityAdded.getStudents().size());
        assertEquals(Instant.parse("2017-02-03T11:25:30.00Z"),
                universityAdded.getStartOperatingDate());
        Mockito.verify(iUniversityRepository).add(any());
    }

    @Test
    void addUniversityGetAddressThrowException() {
        University university = Mockito.mock(University.class);
        Mockito.when(university.getName())
                .thenThrow(new ArgumentUniversityException("university must have an address"));
        ArgumentUniversityException argumentUniversityException =
                assertThrowsExactly(ArgumentUniversityException.class, () ->
                        university.getName());
        assertEquals("university must have an address", argumentUniversityException.getMessage());
    }

    @Test
    void addUniversityGetStudyCoastThrowException() {
        University university = Mockito.mock(University.class);
        Mockito.when(university.getStudyCost())
                .thenThrow(new ArgumentUniversityException("university cost must be greater than 2000"));
        ArgumentUniversityException argumentUniversityException =
                assertThrowsExactly(ArgumentUniversityException.class, () ->
                        university.getStudyCost());
        assertEquals("university cost must be greater than 2000", argumentUniversityException.getMessage());
    }

    @Test
    void updateSuccess() {
        University oldUniversity = new University();
        oldUniversity.setId(5L);
        University university = new University();
        university.setId(12L);
        Mockito.when(iUniversityRepository.get(anyLong())).thenReturn(oldUniversity);
        doNothing().when(iUniversityRepository).delete(anyLong());
        Mockito.when(iUniversityRepository.add(any())).thenReturn(university);
        University updatedUniversity = universityService.update(10L, oldUniversity);
        assertNotNull(updatedUniversity);
        assertEquals(12L, updatedUniversity.getId());
        verify(iUniversityRepository).delete(10L);
    }

    @Test
    void updatedThrowResourceUniversityIsNotFound() {
        ResourceUniversityIsNotFoundException universityIsNotFoundException =
                assertThrowsExactly(ResourceUniversityIsNotFoundException.class, () ->
                        universityService.update(5L, new University()));
        assertEquals("university with id=5 does not exist",
                universityIsNotFoundException.getMessage());
    }

    @Test
    void deleteUniversityWithoutStudents() {
        Address address = new Address("Almukaber", "American street",
                43);
        address.setId(10L);
        List<Student> students = new ArrayList<Student>();
        University university = new University("Alblqa", address,
                UniversityType.GOVERMENTAL, "Alblqa@gmail.com", 5000d,
                Instant.parse("2017-02-03T11:25:30.00Z"), students);
        university.setId(20L);
        Mockito.when(iUniversityRepository.get(anyLong())).thenReturn(university);
        Mockito.when(iStudService.getAllStudentByUniversityId(anyLong())).thenReturn(university.getStudents());
        doNothing().when(iStudService).delete(anyLong());
        doNothing().when(iAddressService).delete(anyLong());
        doNothing().when(iUniversityRepository).delete(anyLong());
        universityService.delete(20L);
        verify(iAddressService, times(1)).delete(anyLong());
        verify(iUniversityRepository, times(1)).delete(20L);
        verify(iStudService, never()).delete(anyLong());
    }

    @Test
    void deleteUniversityWithStudents() {
        Address address = new Address();
        address.setId(10L);
        Student student1 = new Student();
        student1.setId(1L);
        Student student2 = new Student();
        student2.setId(2L);
        Student student3 = new Student();
        student3.setId(3L);
        List<Student> students = Arrays.asList(
                student1,
                student2,
                student3
        );
        University university = new University("Alblqa", address,
                UniversityType.GOVERMENTAL, "Alblqa@gmail.com", 5000d,
                Instant.parse("2017-02-03T11:25:30.00Z"), students);
        university.setId(20L);
        Mockito.when(iUniversityRepository.get(anyLong())).thenReturn(university);
        Mockito.when(iStudService.getAllStudentByUniversityId(anyLong())).thenReturn(university.getStudents());
        doNothing().when(iStudService).delete(1L);
        doNothing().when(iStudService).delete(2L);
        doNothing().when(iStudService).delete(3L);
        doNothing().when(iAddressService).delete(anyLong());
        doNothing().when(iUniversityRepository).delete(anyLong());
        universityService.delete(20L);
        verify(iAddressService, times(1)).delete(anyLong());
        verify(iUniversityRepository, times(1)).delete(20L);
        verify(iStudService, times(3)).delete(any());
    }

    @Test
    void deleteUniversityThrowException() {
        ResourceUniversityIsNotFoundException universityIsNotFoundException =
                assertThrowsExactly(ResourceUniversityIsNotFoundException.class,
                        () -> universityService.delete(50L));
        assertEquals("university with id=50 does not exist", universityIsNotFoundException.getMessage());
    }

    @Test
    void setUniversityAddressIdNull() {
        Address address = new Address("Almukaber", "American street",
                43);
        address.setId(10L);
        List<Student> students = new ArrayList<Student>();
        University university = new University("Alblqa", address,
                UniversityType.GOVERMENTAL, "Alblqa@gmail.com", 5000d,
                Instant.parse("2017-02-03T11:25:30.00Z"), students);
        university.setId(20L);
        Mockito.when(iUniversityRepository.get(anyLong())).thenReturn(university);
        universityService.delete(20L);
        verify(iUniversityRepository).setUniversityAddressIdNull(20L);
    }

    @Test
    void getStudentUniversityId() {
        University university = new University();
        university.setId(10L);
        university.setName("AlBlqaa");
        Student student = new Student();
        student.setId(102L);
        student.setName("Murad");
        Mockito.when(iStudService.get(anyLong())).thenReturn(student);
        Mockito.when(iUniversityRepository.getStudentUniversityId(anyLong())).thenReturn(university);
        University universityStudent = universityService.getStudentUniversityId(102L);
        assertNotNull(universityStudent);
        assertEquals("AlBlqaa", universityStudent.getName());
        verify(iStudService, times(1)).get(102L);
    }

    @Test
    void getAll() {
        University university1 = new University();
        University university2 = new University();
        List<University> universities = Arrays.asList(university1, university2);
        Mockito.when(iUniversityRepository.getAll()).thenReturn(universities);
        List<University> universitesFindAll = universityService.getAll();
        assertNotNull(universitesFindAll);
        assertEquals(2, universitesFindAll.size());
    }

    @Test
    void getAllEmptyCase() {
        List<University> universities = new ArrayList<>();
        Mockito.when(iUniversityRepository.getAll()).thenReturn(universities);
        List<University> universitesFindAll = universityService.getAll();
        assertNotNull(universitesFindAll);
        assertEquals(0, universitesFindAll.size());
    }
}