package com.example.alaa.university.appevents;

import com.example.alaa.university.domain.*;
import com.example.alaa.university.service.IAddressService;
import com.example.alaa.university.service.IStudentService;
import com.example.alaa.university.service.IUniversityService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.Instant;
import java.util.Arrays;
//@Component
public class ServiceExample {
    private IUniversityService iUniServiceEx;
    private IAddressService iAddServiceEx;
    private IStudentService istServiceEx;

    public ServiceExample(IUniversityService iUniServiceEx,
                          IAddressService iAddServiceEx,
                          IStudentService istService) {
        this.iUniServiceEx = iUniServiceEx;
        this.iAddServiceEx = iAddServiceEx;
        this.istServiceEx = istService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void universityServicePlayGround() {
        System.out.println("get university id =2");
        University university = iUniServiceEx.get(2L);
        System.out.println(university);
        // System.out.println("get university does not exist");
        Address address = new Address("Qunber7209", "AmericanSt60", 60);
        Student stUpdated = new Student("AKamal Qasem", address,
                Gender.MALE, false, Instant.parse("1984-02-03T11:25:30.00Z"),
                Instant.parse("2017-02-03T11:25:30.00Z"), Instant.parse("2023-02-03T11:25:30.00Z"), 2000d,
                "Mazen@gmail.com");
        //istServiceEx.add(stUpdated,54L);
        //istServiceEx.update(75L,stUpdated);
        //iAddServiceEx.add(address);
        // Address address1=iAddServiceEx.get(217L);
        //System.out.println(address1);
        //istServiceEx.delete(61L);
        // **********************************
        Address address1 = new Address("Alwad7209", "AmericanSt60", 60);
        Student st1 = new Student("Tasneem", address1,
                Gender.MALE, false, Instant.parse("1984-02-03T11:25:30.00Z"),
                Instant.parse("2017-02-03T11:25:30.00Z"), Instant.parse("2023-02-03T11:25:30.00Z"), 2000d,
                "MTasneem@gmail.com");
        Address address2 = new Address("Alwad7209", "AmericanSt60", 60);
        Student st2 = new Student("Tarteel", address2,
                Gender.MALE, false, Instant.parse("1984-02-03T11:25:30.00Z"),
                Instant.parse("2017-02-03T11:25:30.00Z"), Instant.parse("2023-02-03T11:25:30.00Z"), 2000d,
                "MTasneem@gmail.com");
        University uni = new University("Musanara Uni",
                new Address("Alquds", "Azhraa St", 20),
                UniversityType.GOVERMENTAL, "Alqahera@gmail.com", 7000d,
                Instant.now(), Arrays.asList(st1, st2));
        //iUniServiceEx.add(uni);
        iUniServiceEx.getStudentUniversityId(37L);
        //************************************************
        // iUniServiceEx.delete(62L);
        // iUniServiceEx.update(31L,uni);

        // List<Student> students=istServiceEx.getAll();
        // students.forEach(student->System.out.println(student));
        //List<Student> students=istServiceEx.getAllStudentByUniversityId(2L);
        //students.forEach(student -> System.out.println(student));
        // istServiceEx.add(stUpdated,54);
        //      System.out.println(iUniServiceEx.get(100L));
        //        System.out.println("get address exist");
        //       // Address address=iAddressService.get(200L);
        //        //System.out.println(address);
        //        System.out.println(istServiceEx.get(17L));
        ////System.out.println(iAddService.getStudentAddressId(17L));
        //        System.out.println("get university without student");
        // System.out.println(iUniServiceEx.get(34L));
        //        List<Student>  studentsToadd=new ArrayList<>();
        //
        // ********************

        //System.out.println("I want to delete student # 67");
        //istService.delete(65L);
        //     Student stt=istService.setStudentUniversityIdNull(65L);
        //     System.out.println(stt);
        //     istService.delete(65L);
        //University university1=iUniService.get(52L);
        //System.out.println(university1);
        //iUniService.delete(52L);
        // iUniService.delete(53L);
        //        List<Student> students=istService.getAllStudentByUniversityId(52L);
        //        students.forEach(st->System.out.println(st));

    }
}
