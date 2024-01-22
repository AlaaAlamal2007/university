package com.example.alaa.university.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends AbstractAuditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "teacher_subject",
            joinColumns = {@JoinColumn(name = "teacher_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "subject_id", referencedColumnName = "id")})
    @JsonIgnore
    private Set<Subject> subjects;

    public Teacher() {
    }

    public Teacher(String name, Set<Subject> subjects) {
        this.name = name;
        this.subjects = subjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
        subject.getTeachers().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subjects=" + subjects +
                '}';
    }

    public void removeSubject(Long subjectId) {
        Subject subject = this.subjects
                .stream()
                .filter(sub -> sub.getId() == subjectId)
                .findFirst()
                .orElse(null);
        if (subject != null) {
            this.subjects.remove(subject);
            subject.getTeachers().remove(this);
        }
    }



}
