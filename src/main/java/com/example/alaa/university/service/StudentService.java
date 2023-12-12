package com.example.alaa.university.service;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.domain.Student;
import com.example.alaa.university.domain.University;
import com.example.alaa.university.exceptions.ArgumentStudentException;
import com.example.alaa.university.exceptions.ResourceStudentIsNotFoundException;
import com.example.alaa.university.repository.IAddressRepository;
import com.example.alaa.university.repository.IStudentRepository;
import com.example.alaa.university.repository.IUniversityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements IStudentService {
    private final IStudentRepository iStuRepository;
    private final IAddressRepository iAddRepository;
    private final IUniversityRepository iUniRepository;

    public StudentService(IStudentRepository iStuRepository,
                          IAddressRepository iAddRepository,
                          IUniversityRepository iUniRepository) {
        this.iStuRepository = iStuRepository;

        this.iAddRepository = iAddRepository;

        this.iUniRepository = iUniRepository;
    }

    @Override
    public Student get(Long id) {
        Student student = iStuRepository.get(id);
        if (student == null) {
            throw new ResourceStudentIsNotFoundException("student with id=" +
                    id + " does not found");
        }
        Address address = iAddRepository.getStudentAddressId(id);
        student.setAddress(address);
        return student;
    }

    @Override
    public Student add(Student student, long universityId) {
        validateStudent(student);
        Address address = student.getAddress();
        Address newAddress = iAddRepository.add(address);
        Student stTobeAdded = iStuRepository.add(student, universityId);
        Long stTobeAddedId = stTobeAdded.getId();
        iStuRepository.setStudentAddressId(stTobeAddedId, newAddress.getId());
        return get(stTobeAddedId);
    }

    private static void validateStudent(Student student) {
        if (student.getAddress() == null) {
            throw new ArgumentStudentException("Student must have address ");
        }
        if (student.getBirthDate() == null) {
            throw new ArgumentStudentException("Student must have birthdate ");
        }
        if (student.getPaymentFee() <= 1000d && student.getPaymentFee() >= 2000d) {
            throw new ArgumentStudentException("Student fee must be between (2000,10000) ");
        }
        if (student.getGraduatedDate() != null) {
            if (student.getRegistrationDate().isAfter(student.getGraduatedDate())) {
                throw new ArgumentStudentException("Student registration Date is before graduated date ");
            }
        }
    }

    @Override
    public Student update(Long id, Student updatedStudent) {
        Student student = get(id);
        University university = iUniRepository.getStudentUniversityId(id);
        Long universityId = university.getId();
        delete(id);
        Student newStudent = add(updatedStudent, universityId);
        return get(newStudent.getId());
    }

    @Override
    public void delete(Long id) {
        Student st = get(id);
        Long stAddressId = st.getAddress().getId();
        iStuRepository.setStudentUniversityAndAddressIdNull(id);
        iStuRepository.delete(id);
        iAddRepository.delete(stAddressId);
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = iStuRepository.getAll();
        students.forEach(student -> {
            Address address = iAddRepository.getStudentAddressId(student.getId());
            student.setAddress(address);
        });
        return students;
    }

    @Override
    public List<Student> getAllStudentByUniversityId(Long id) {
        List<Student> students = iStuRepository.getAllStudentByUniversityId(id);
        students.forEach(st -> st.setAddress(iAddRepository.getStudentAddressId(st.getId())));
        return students;
    }

    @Override
    public Student setStudentUniversityAndAddressIdNull(Long studentId) {
        get(studentId);
        iStuRepository.setStudentUniversityAndAddressIdNull(studentId);
        return get(studentId);
    }

    @Override
    public Student setStudentAddressId(Long studentId, Long addressId) {
        get(studentId);
        iStuRepository.setStudentAddressId(studentId, addressId);
        return get(studentId);
    }
}
