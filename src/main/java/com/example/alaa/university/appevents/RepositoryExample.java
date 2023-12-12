//package com.example.alaa.university.appevents;
//
//import com.example.alaa.university.domain.*;
//import com.example.alaa.university.repository.IAddressRepository;
//import com.example.alaa.university.repository.IStudentRepository;
//import com.example.alaa.university.repository.IUniversityRepository;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//import java.util.List;
//
//@Component
//public class RepositoryExample {
//    private IStudentRepository iStudentRepository;
//    private IAddressRepository iAddressRepository;
//    private IUniversityRepository iUniversityRepository;
//
//    public RepositoryExample(IStudentRepository iStudentRepository, IAddressRepository iAddressRepository
//            , IUniversityRepository iUniversityRepository) {
//        this.iStudentRepository = iStudentRepository;
//        this.iAddressRepository = iAddressRepository;
//        this.iUniversityRepository = iUniversityRepository;
//    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void studentRepositoryPlayGround() {
//        //I want to get student id=12 from dataBase
//        System.out.println("I want to get student id=8 from dataBase");
//        Student student = iStudentRepository.get(8L);
//        System.out.println(student.toString());
//        System.out.println("I want to get student does not esist");
//        Student student2 = iStudentRepository.get(1000L);
//        //I want to get university id=1
//        System.out.println("I want to get university id=1");
//        System.out.println(iUniversityRepository.get(1L));
//        System.out.println("I want to get university does not exist");
//        System.out.println(iUniversityRepository.get(8L));
//        //I want to add a new student
//        Address address = iAddressRepository.add(new Address("RamallaNew", "Ramala St", 13));
//        University university3 = new University("Bier Ziet new University",
//                address, UniversityType.GOVERMENTAL,
//                "Bier Ziet@gmail.com", 4000d, null);
//        iUniversityRepository.add(university3);
//        //I want to update previous university
//        // Address address1 = iAddressRepository.add(new Address("RamallaNew", "Ramala St", 13));
//        University university = new University("Bier Ziet Updated University",
//                address, UniversityType.PRIVATE,
//                "Bier Ziet@gmail.com", 5000d, null);
//        iUniversityRepository.update(34L, university);
//        //I want to select all universities
//        System.out.println("select all universities");
//        List<University> universities = iUniversityRepository.getAll();
//        universities.forEach(universityP -> System.out.println(universityP));
////        Address address=iAddressRepository.add(new Address("Alquds updated","Salah Aldien Street ",100));
////Student student1=new Student("Amjad Updated",address,Gender.MALE,false,null,null,
////        Instant.now(),5000d,"Amjad@yahoo.com");
////iStudentRepository.update(48L,student1);
//        List<Student> studentsGetAll = iStudentRepository.getAll();
//        studentsGetAll.forEach(studentIn -> System.out.println(studentIn));
//        List<Student> studentsInUni = iStudentRepository.getAllStudentByUniversityId(1L);
//        System.out.println("Get all students in university 1");
//        studentsInUni.forEach(st -> System.out.println(st));
//        System.out.println("select student address");
//        System.out.println(iAddressRepository.getStudentAddressId(43L));
//        System.out.println(iAddressRepository.getUniversityAddressId(1L));
//        System.out.println("get all addresses");
//        List<Address> addresses = iAddressRepository.getAll();
//        addresses.forEach(add -> System.out.println(add));
//        System.out.println("address update id=25");
//        iAddressRepository.update(25L, new Address("AlraqUpdated", "Farou St", 10));
//        Address newAdd = iAddressRepository.add(new Address("Biet Lahem", "MM street", 9));
//        Student newSt = new Student("Manal Majed", newAdd,
//                Gender.FEMALE, true,
//                null, Instant.now(), null, 6000d, "manal@jmail.com");
//        // iStudentRepository.add(newSt,31L);
//        iStudentRepository.update(49L, newSt);
//        //iUniversityRepository.delete(117L);
//        iStudentRepository.delete(20L);
//        iAddressRepository.delete(32L);
//    }
//}
