package com.example.alaa.university.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "subjects")
public class Subject extends AbstractAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "marks_obtained")
    private Double marksObtained;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "subjects")
    private Set<Teacher> teachers;

    public Subject() {
    }

    public Subject(String subjectName, Double marksObtained, Set<Teacher> teachers) {
        this.name = subjectName;
        this.marksObtained = marksObtained;
        this.teachers = teachers;
    }

    public Double getMarksObtained() {
        return marksObtained;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarksObtained(Double marksObtained) {
        this.marksObtained = marksObtained;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", marksObtained=" + marksObtained +
                ", teachers=" + teachers +
                '}';
    }
}

