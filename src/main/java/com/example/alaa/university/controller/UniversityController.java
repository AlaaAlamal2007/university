package com.example.alaa.university.controller;

import com.example.alaa.university.domain.University;
import com.example.alaa.university.service.IUniversityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/universities")
@RestController
public class UniversityController {
    private final IUniversityService iUniversityServiceC;

    public UniversityController(IUniversityService iUniversityServiceC) {
        this.iUniversityServiceC = iUniversityServiceC;
    }

    @GetMapping("/{id}")
    public University get(@PathVariable Long id) {
        return iUniversityServiceC.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public University add(@RequestBody University university) {
        return iUniversityServiceC.add(university);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public University update(@PathVariable Long id, @RequestBody University updatedUniversity) {
        return iUniversityServiceC.update(id, updatedUniversity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        iUniversityServiceC.delete(id);

    }

    @GetMapping("students/{studentId}")
    public University getStudentUniversityId(@PathVariable Long studentId) {
        return iUniversityServiceC.getStudentUniversityId(studentId);
    }

    @GetMapping
    public List<University> getAll() {
        return iUniversityServiceC.getAll();
    }
}
