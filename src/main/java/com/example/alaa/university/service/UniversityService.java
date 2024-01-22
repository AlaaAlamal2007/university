package com.example.alaa.university.service;

import com.example.alaa.university.domain.Student;
import com.example.alaa.university.domain.University;
import com.example.alaa.university.exceptions.ArgumentUniversityException;
import com.example.alaa.university.exceptions.ResourceUniversityIsNotFoundException;
import com.example.alaa.university.repository.AddressRepo;
import com.example.alaa.university.repository.StudentRepo;
import com.example.alaa.university.repository.UniversityRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniversityService implements IUniversityService {
    private final UniversityRepo universityRepo;
    private final AddressRepo addressRepo;
    private final StudentRepo studentRepo;

    private final IStudentService iStudService;

    public UniversityService(UniversityRepo universityRepo,
                             AddressRepo addressRepo, StudentRepo studentRepo,
                             IStudentService iStudService) {
        this.universityRepo = universityRepo;
        this.addressRepo = addressRepo;
        this.studentRepo = studentRepo;
        this.iStudService = iStudService;
    }

    @Override
    public University get(Long id) {
        University university = universityRepo.findById(id).orElseThrow(
                () -> new ResourceUniversityIsNotFoundException(
                        "university with id=" + id + " does not exist")
        );
        List<Student> students = studentRepo.findAllByUniversityId(id);
        if (!students.isEmpty() && students != null) {
            university.setStudents(students);
        } else {
            List<Student> studentList = new ArrayList<>();
            university.setStudents(studentList);
        }
        return university;
    }

    @Override
    public University add(University university) {
        if (university.getAddress() == null) {
            throw new ArgumentUniversityException("university must have an address");
        }
        if (university.getStudyCost() < 2000d) {
            throw new ArgumentUniversityException("university cost must be " +
                    "greater than 2000");
        }
        University savedUniversity = new University();
        university.setName(university.getName());
        university.setAddress(university.getAddress());
        university.setUniversityType(university.getUniversityType());
        university.setEmail(university.getEmail());
        university.setStudyCost(university.getStudyCost());
        university.setStartOperatingDate(university.getStartOperatingDate());
        university.setStudents(university.getStudents());
        savedUniversity = universityRepo.save(university);
        Long uniId = savedUniversity.getId();
        if (university.getStudents() != null && !university.getStudents().isEmpty()) {
            List<Student> students = new ArrayList<>(university.getStudents());
            students.forEach(st -> {
                iStudService.add(st, uniId);
            });
        }
        return savedUniversity;
    }

    @Override
    public University update(Long id, University updatedUniversity) {
        University university = get(id);
        if (university == null) {
            throw new ResourceUniversityIsNotFoundException(
                    "university with id=" + id + " does not exist");
        }
        if (university.getStudents() != null && !university.getStudents().isEmpty()) {
            studentRepo.deleteAllStudentByUniversityId(id);
        }
        university.setName(updatedUniversity.getName());
        university.setAddress(updatedUniversity.getAddress());
        university.setUniversityType(updatedUniversity.getUniversityType());
        university.setEmail(updatedUniversity.getEmail());
        university.setStartOperatingDate(updatedUniversity.getStartOperatingDate());
        if (updatedUniversity.getStudents() != null && !updatedUniversity.getStudents().isEmpty()) {
            List<Student> students = new ArrayList<>(updatedUniversity.getStudents());
            students.forEach(st -> {
                iStudService.add(st, id);
            });
        }
        return universityRepo.saveAndFlush(university);
    }

    @Override
    public void delete(Long id) {
        University uni = get(id);
        if (uni == null) {
            throw new
                    ResourceUniversityIsNotFoundException("university with id=" + id + " does not exist");
        }
        Long uniId = uni.getId();
        if (uni.getStudents() != null && !uni.getStudents().isEmpty()) {
            studentRepo.deleteAllStudentByUniversityId(uniId);
        }
        universityRepo.deleteById(id);
    }

    @Override
    public List<University> getAll() {
        List<University> universities = universityRepo.findAll();
        if (universities == null) {
            universities = new ArrayList<>();
        }
        return universities;
    }
}

