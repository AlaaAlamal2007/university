package com.example.alaa.university.service;

import com.example.alaa.university.domain.Student;
import com.example.alaa.university.exceptions.ArgumentStudentException;
import com.example.alaa.university.exceptions.ResourceStudentIsNotFoundException;
import com.example.alaa.university.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements IStudentService {
    private final StudentRepo studentRepo;
    private final UniversityRepo universityRepo;

    public StudentService(StudentRepo studentRepo, UniversityRepo universityRepo
    ) {
        this.studentRepo = studentRepo;
        this.universityRepo = universityRepo;
    }

    @Override
    public Student get(Long id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(
                        () -> new ResourceStudentIsNotFoundException("student with id=" +
                                id + " does not found")
                );
        return student;
    }

    @Override
    public Student add(Student student, long universityId) {
        validateStudent(student);
        Student savedStudent = new Student();
        savedStudent.setName(student.getName());
        savedStudent.setAddress(student.getAddress());
        savedStudent.setGender(student.getGender());
        savedStudent.setGraduated(student.getGraduated());
        savedStudent.setBirthDate(student.getBirthDate());
        savedStudent.setRegistrationDate(student.getRegistrationDate());
        savedStudent.setGraduatedDate(student.getGraduatedDate());
        savedStudent.setPaymentFee(student.getPaymentFee());
        savedStudent.setEmail(student.getEmail());
        savedStudent.setUniversityId(universityId);
        savedStudent = studentRepo.save(savedStudent);
        return savedStudent;
    }

    private static void validateStudent(Student student) {
        if (student.getAddress() == null) {
            throw new ArgumentStudentException("Student must have address ");
        }
        if (student.getBirthDate() == null) {
            throw new ArgumentStudentException("Student must have birthdate ");
        }
        if (student.getPaymentFee() < 2000d) {
            throw new ArgumentStudentException("Student fee must be grater than 2000 ");
        }
        if (student.getGraduatedDate() != null) {
            if (student.getRegistrationDate().isAfter(student.getGraduatedDate())) {
                throw new ArgumentStudentException("Student registration Date is before graduated date ");
            }
        }
    }

    @Override
    public Student update(Long id, Student updatedStudent) {
        Student student = studentRepo.findById(id).orElseThrow(
                () -> new ResourceStudentIsNotFoundException("student with id=" +
                        id + " does not found")
        );
        Long universityId=student.getUniversityId();
        student.setName(updatedStudent.getName());
        student.setGender(updatedStudent.getGender());
        student.setGraduated(updatedStudent.getGraduated());
        student.setPaymentFee(updatedStudent.getPaymentFee());
        student.setEmail(updatedStudent.getEmail());
        student.setUniversityId(universityId);
        student.setAddress(updatedStudent.getAddress());
        student.setBirthDate(updatedStudent.getBirthDate());
        student.setRegistrationDate(updatedStudent.getRegistrationDate());
        student.setGraduatedDate(updatedStudent.getGraduatedDate());
        return studentRepo.saveAndFlush(student);
    }

    @Override
    public void delete(Long id) {
        Student st = studentRepo.findById(id).orElseThrow(
                () -> new ResourceStudentIsNotFoundException("student with id=" +
                        id + " does not found")
        );
        studentRepo.deleteById(id);
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = studentRepo.findAll();
        return students;
    }

    @Override
    public List<Student> getAllStudentByUniversityId(Long id) {
        List<Student> students = studentRepo.findAllByUniversityId(id);
        return students;
    }
}

