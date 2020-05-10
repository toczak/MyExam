package pl.potoczak.myexam.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@DiscriminatorValue("Teacher")
public class Teacher extends User {

    @ManyToMany
    @JoinTable(name = "teachers_students",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Collection<Student> students;

    @OneToMany(mappedBy = "teacher")
    private Collection<Test> tests;

    @OneToMany(mappedBy = "teacher")
    private Collection<Question> questions;
}
