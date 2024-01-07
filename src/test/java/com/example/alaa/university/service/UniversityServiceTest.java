package com.example.alaa.university.service;

import com.example.alaa.university.domain.*;

import com.example.alaa.university.exceptions.ArgumentUniversityException;
import com.example.alaa.university.exceptions.ResourceUniversityIsNotFoundException;

import com.example.alaa.university.repository.StudentRepo;
import com.example.alaa.university.repository.UniversityRepo;
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

class UniversityServiceTest {
    @InjectMocks
    private UniversityService universityService;
    @Mock
    private UniversityRepo universityRepo;
    @Mock
    private IStudentService iStudService;
    @Mock
    private StudentRepo studentRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUniversityWithoutStudent() {
        Address address = new Address();
        address.setId(10L);
        address.setCityName("Almukaber");
        address.setStreetName("American street");
        address.setStreetNumber(43);
        University university = new University("Alblqa", address,
                UniversityType.GOVERMENTAL, "Alblqa@gmail.com", 5000d,
                Instant.parse("2017-02-03T11:25:30.00Z"), Arrays.asList());
        university.setId(50L);
        Mockito.when(universityRepo.findById(anyLong())).thenReturn(Optional.of(university));
        University getUniversity = universityService.get(50L);
        assertNotNull(getUniversity);
        assertNotNull(getUniversity.getStudents());
        assertEquals(getUniversity.getId(), 50L);
        assertEquals(0, getUniversity.getStudents().size());
    }

    @Test
    void getUniversityWithStudent() {
        Address address = new Address();
        address.setId(10L);
        address.setCityName("Almukaber");
        address.setStreetName("American street");
        address.setStreetNumber(43);
        Student st1 = new Student();
        st1.setId(1L);
        Student st2 = new Student();
        st2.setId(2L);
        Student st3 = new Student();
        st3.setId(3L);
        List<Student> students = Arrays.asList(
                st1,
                st2,
                st3);
        University universityWithStudent = new University("Alblqa", address,
                UniversityType.GOVERMENTAL, "Alblqa@gmail.com", 5000d,
                Instant.parse("2017-02-03T11:25:30.00Z"), students);
        universityWithStudent.setId(30L);
        Mockito.when(universityRepo.findById(30L)).thenReturn(Optional.of(universityWithStudent));
        Mockito.when(studentRepo.findAllByUniversityId(30L)).thenReturn(students);
        University getUniversity = universityService.get(30L);
        assertNotNull(getUniversity);
        assertEquals(3, getUniversity.getStudents().size());
        assertEquals(30L, getUniversity.getId());
    }

    @Test
    void getUniversityThrowException() {
        ResourceUniversityIsNotFoundException universityIsNotFoundException =
                assertThrowsExactly(ResourceUniversityIsNotFoundException.class,
                        () -> universityService.get(70L));
        assertEquals("university with id=70 does not exist",
                universityIsNotFoundException.getMessage());
    }

    @Test
    void addSuccessWithoutStudents() {
        Address address = new Address();
        address.setId(10L);
        address.setCityName("Almukaber");
        address.setStreetName("American street");
        address.setStreetNumber(43);
        University university = new University("Alblqa", address,
                UniversityType.GOVERMENTAL, "Alblqa@gmail.com", 5000d,
                Instant.parse("2017-02-03T11:25:30.00Z"), Arrays.asList());
        university.setId(50L);
        Mockito.when(universityRepo.save(any())).thenAnswer(
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
    }


    @Test
    void addSuccessWithStudents() {
        Address address = new Address();
        address.setId(10L);
        address.setCityName("Almukaber");
        address.setStreetName("American street");
        address.setStreetNumber(43);
        Student st1 = new Student();
        st1.setId(1L);
        Student st2 = new Student();
        st2.setId(2L);
        Student st3 = new Student();
        st3.setId(3L);
        List<Student> students = Arrays.asList(
                st1,
                st2,
                st3);
        University universityWithStudent = new University("Alblqa", address,
                UniversityType.GOVERMENTAL, "Alblqa@gmail.com", 5000d,
                Instant.parse("2017-02-03T11:25:30.00Z"), students);
        universityWithStudent.setId(30L);
        Mockito.when(universityRepo.save(any())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    Object firstArgument = invocation.getArgument(0);
                    University uniAdded = (University) firstArgument;
                    uniAdded.setId(102L);
                    return uniAdded;
                });
        University universityAdded = universityService.add(universityWithStudent);
        assertNotNull(universityAdded);
        assertNotNull(universityAdded.getId());
        assertEquals(102L, universityAdded.getId());
        assertEquals(3, universityAdded.getStudents().size());
        assertEquals(Instant.parse("2017-02-03T11:25:30.00Z"),
                universityAdded.getStartOperatingDate());
        Mockito.verify(universityRepo).save(any());
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
        Address address = new Address("oldasdf", "oldasd st", 1);
        address.setId(12L);
        University oldUniversity = new University("Alblqa", address,
                UniversityType.GOVERMENTAL, "Alblqa@gmail.com", 5000d,
                Instant.parse("2017-02-03T11:25:30.00Z"), null);
        oldUniversity.setId(10L);
        oldUniversity.setAddress(address);
        Address newAddress = new Address("newasdf", "newasd st", 1);
        newAddress.setId(13L);
        University newUniversity = new University("Alblqa", newAddress,
                UniversityType.GOVERMENTAL, "Alblqa@gmail.com", 5000d,
                Instant.parse("2017-02-03T11:25:30.00Z"), Arrays.asList(new Student(), new Student()));
        newUniversity.setId(12L);
        newUniversity.setAddress(newAddress);
        Mockito.when(universityRepo.findById(10L)).thenReturn(Optional.of(oldUniversity));
        Mockito.when(universityRepo.save(any())).thenReturn(newUniversity);
        doNothing().when(universityRepo).deleteById(10L);
        University updatedUniversity = universityService.update(10L, newUniversity);
        assertNotNull(updatedUniversity);
        assertEquals(12L, updatedUniversity.getId());
        verify(universityRepo).deleteById(10L);
        verify(iStudService, times(2)).add(any(), anyLong());
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
        Address address = new Address();
        address.setId(10L);
        address.setCityName("Almukaber");
        address.setStreetName("American street");
        address.setStreetNumber(43);
        University university = new University("Alblqa", address,
                UniversityType.GOVERMENTAL, "Alblqa@gmail.com", 5000d,
                Instant.parse("2017-02-03T11:25:30.00Z"), Arrays.asList());
        university.setId(50L);
        Mockito.when(universityRepo.findById(20L)).thenReturn(Optional.of(university));
        Mockito.when(studentRepo.findAllByUniversityId(anyLong())).thenReturn(university.getStudents());
        doNothing().when(iStudService).delete(anyLong());
        doNothing().when(studentRepo).deleteAllStudentByUniversityId(anyLong());
        doNothing().when(universityRepo).deleteById(anyLong());
        universityService.delete(20L);
        verify(universityRepo, times(1)).deleteById(20L);
        verify(iStudService, never()).delete(anyLong());
    }

    @Test
    void deleteUniversityWithStudents() {
        Address address = new Address();
        address.setId(10L);
        address.setCityName("Almukaber");
        address.setStreetName("American street");
        address.setStreetNumber(43);
        Student st1 = new Student();
        st1.setId(1L);
        Student st2 = new Student();
        st2.setId(2L);
        Student st3 = new Student();
        st3.setId(3L);
        List<Student> students = Arrays.asList(
                st1,
                st2,
                st3);
        University universityWithStudent = new University("Alblqa", address,
                UniversityType.GOVERMENTAL, "Alblqa@gmail.com", 5000d,
                Instant.parse("2017-02-03T11:25:30.00Z"), students);
        universityWithStudent.setId(30L);
        Mockito.when(universityRepo.findById(anyLong())).thenReturn(Optional.of(universityWithStudent));
        Mockito.when(studentRepo.findAllByUniversityId(anyLong())).thenReturn(universityWithStudent.getStudents());
        doNothing().when(studentRepo).deleteAllStudentByUniversityId(anyLong());
        doNothing().when(universityRepo).deleteById(30L);
        universityService.delete(30L);
        verify(universityRepo, times(1)).deleteById(30L);
    }

    @Test
    void deleteUniversityThrowException() {
        ResourceUniversityIsNotFoundException universityIsNotFoundException =
                assertThrowsExactly(ResourceUniversityIsNotFoundException.class,
                        () -> universityService.delete(50L));
        assertEquals("university with id=50 does not exist", universityIsNotFoundException.getMessage());
    }

    @Test
    void getAll() {
        University university1 = new University();
        University university2 = new University();
        List<University> universities = Arrays.asList(university1, university2);
        Mockito.when(universityRepo.findAll()).thenReturn(universities);
        List<University> universitesFindAll = universityService.getAll();
        assertNotNull(universitesFindAll);
        assertEquals(2, universitesFindAll.size());
    }

    @Test
    void getAllEmptyCase() {
        List<University> universities = new ArrayList<>();
        Mockito.when(universityRepo.findAll()).thenReturn(universities);
        List<University> universitesFindAll = universityService.getAll();
        assertNotNull(universitesFindAll);
        assertEquals(0, universitesFindAll.size());
    }
}