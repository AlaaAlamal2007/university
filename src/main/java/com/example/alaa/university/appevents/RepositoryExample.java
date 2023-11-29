package com.example.alaa.university.appevents;

import com.example.alaa.university.UniversityApplication;
import com.example.alaa.university.domain.*;
import com.example.alaa.university.repository.IAddressRepository;
import com.example.alaa.university.repository.IStudentRepository;
import com.example.alaa.university.repository.IUniversityRepository;
import com.example.alaa.university.repository.StudentRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class RepositoryExample {
    private IStudentRepository iStudentRepository;
    private IAddressRepository iAddressRepository;
    private IUniversityRepository iUniversityRepository;

    public RepositoryExample(IStudentRepository iStudentRepository, IAddressRepository iAddressRepository
            , IUniversityRepository iUniversityRepository) {
        this.iStudentRepository = iStudentRepository;
        this.iAddressRepository = iAddressRepository;
        this.iUniversityRepository = iUniversityRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void studentRepositoryPlayGround() {
        //   I want to get student id=12 from dataBase
//        System.out.println("I want to get student id=12 from dataBase");
//        Student student = iStudentRepository.get(11L);
//        System.out.println(student);
        //I want to get all students from my dataBase
//        List<Address> addresses = iAddressRepository.getAll();
//        addresses.forEach(add -> {
//            System.out.println(add);
//        });
//        //I want to get all students
//        List<Student> students = iStudentRepository.getAll();
//        students.forEach(myStudent -> {
//            System.out.println(myStudent);
//        });
//        //I want to get address
//        System.out.println(" get Address #13 from database");
//        Address address = iAddressRepository.get(13L);
//        System.out.println(address);
//        // I want to cereate a new address
//
//        Address newAddressToBeAddesTDB = new
//                Address("Selwan3", "ALmanar Street", 3);
//
//        Address studentAddress=iAddressRepository.add(newAddressToBeAddesTDB);
//        //I want to add a student to database
////
//        Student studentToBeAddedToDataBase =
//                new Student("Angiela Murven", studentAddress, Gender.MALE,
//                        false, Instant.now(), Instant.now()
//                        , Instant.now(), 5000d, "mazen@gmail.com");
//        Student studentReturned = iStudentRepository.add(studentToBeAddedToDataBase, 8L);
////        System.out.println("student afer addes to database");
//        System.out.println(studentReturned);
        //Now I want to update the previous student data where id=4
//        System.out.println("we update student id=19");
//        System.out.println("student data before updated" + iStudentRepository.get(19L));
//        Address adjustedAddress = new Address("Almsrara88", "Alhrian", 3);
//
//
//        Student updatedStudent = new Student("Mazen Mazen mneer", adjustedAddress, Gender.MALE,
//                false, Instant.now(), Instant.now()
//                , Instant.now(), 5000d, "mazen@gmail.com");
//        Student studentAfterExcute = iStudentRepository.update(19L, updatedStudent);
//        System.out.println("student data after updated" + studentAfterExcute);
//Now we want to delete student 2
         //iStudentRepository.delete(16L);
//
//        //I want to get universit by id=1;
//        System.out.println(iUniversityRepository.get(1L));
//        //I want to add a new university
//        //first create address
//        Address addressUni = iAddressRepository.add(
//                new Address("AlqudsAlMukaber", "Amr St", 1)
//        );
//        University newUniversity =
//                new University("AlBlqa", addressUni, UniversityType.PRIVATE,
//                        "Anajah@gmail.com", 5000d, Instant.now());
//        System.out.println(iUniversityRepository.add(newUniversity));
//        //Now I want to update university id=2
//        System.out.println(iUniversityRepository.get(2L));
//        University universityUpdated = new University("Alzrqa University",
//                new Address("AlzrqaAlJadeda", "Jordan street", 80),
//                UniversityType.GOVERMENTAL, "AlzrqaUniversity@gmail.com", 5000d,
//                Instant.now());
//        System.out.println(iUniversityRepository.update(2L,universityUpdated));
        System.out.println("Now I want all universities");

        System.out.println(iUniversityRepository.getAll());

        System.out.println("I want all students in university i=8");
        List<Student> students=iStudentRepository.getStudentsinUni(8L);
        students.forEach(studentIn->
                System.out.println(studentIn));
System.out.println("I want to delete university id=8");
iUniversityRepository.delete(8L);

    }


}
