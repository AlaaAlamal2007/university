package com.example.alaa.university.service;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.domain.Student;
import com.example.alaa.university.domain.University;
import com.example.alaa.university.exceptions.ArgumentUniversityException;
import com.example.alaa.university.exceptions.ResourceUniversityIsNotFoundException;
import com.example.alaa.university.repository.IUniversityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniversityService implements IUniversityService {
    private final IUniversityRepository iUniversityRepository1;
    private final IAddressService iAddreService;
    private final IStudentService iStudService;

    public UniversityService(IUniversityRepository iUniversityRepository,

                             IAddressService iAddreService, IStudentService iStudService) {
        this.iUniversityRepository1 = iUniversityRepository;

        this.iAddreService = iAddreService;
        this.iStudService = iStudService;
    }

    @Override
    public University get(Long id) {
        University university = iUniversityRepository1.get(id);
        if (university == null) {
            throw new ResourceUniversityIsNotFoundException(
                    "university with id=" + id + " does not exist");
        }
        Address address = iAddreService.getUniversityAddressId(id);
        university.setAddress(address);
        List<Student> students = iStudService.getAllStudentByUniversityId(id);
        university.setStudents(students);
        return university;
    }

    @Override
    public University add(University university) {
        if (university.getAddress() == null) {
            throw new ArgumentUniversityException("university must have an address");
        }
        if (university.getStudyCost() < 2000d) {
            throw new ArgumentUniversityException("university cost must be" +
                    "greater than 2000");
        }
        Address address = university.getAddress();
        Address newAdressToDataBase = iAddreService.add(address);
        University uniToBeAdded = iUniversityRepository1.add(university);
        Long universityId = uniToBeAdded.getId();
        iUniversityRepository1.setUniversityAddressId(universityId, newAdressToDataBase.getId());
        List<Student> students = university.getStudents();
        if (students == null) {
            students = new ArrayList<Student>();
        }
        if (!students.isEmpty()) {
            get(universityId);
            students.forEach(studentEntry -> {
                iStudService.add(studentEntry, universityId);
            });
        }
        return get(universityId);
    }

    @Override
    public University update(Long id, University updatedUniversity) {
        get(id);
        delete(id);
        University university = add(updatedUniversity);
        return get(university.getId());
    }

    @Override
    public void delete(Long id) {
        University uni = get(id);
        Long addressUni = uni.getAddress().getId();
        List<Student> students = iStudService.getAllStudentByUniversityId(id);
        if (!students.isEmpty()) {
            students.forEach(student -> iStudService.delete(student.getId()));
        }
        iUniversityRepository1.setUniversityAddressIdNull(id);
        iAddreService.delete(addressUni);
        iUniversityRepository1.delete(id);
    }

    @Override
    public University setUniversityAddressIdNull(Long universityId) {
        get(universityId);
        University university = iUniversityRepository1.setUniversityAddressIdNull(universityId);
        return university;
    }

    @Override
    public University getStudentUniversityId(Long studentId) {
        iStudService.get(studentId);
        University university = iUniversityRepository1.getStudentUniversityId(studentId);
        return university;
    }

    @Override
    public List<University> getAll() {
        List<University> universities = iUniversityRepository1.getAll();
        if (universities == null) {
            universities = new ArrayList<>();
        }
        return universities;
    }
}
