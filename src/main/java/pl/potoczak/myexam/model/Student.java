package pl.potoczak.myexam.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
@DiscriminatorValue("Student")
public class Student extends User {

    @ManyToMany(mappedBy = "students")
    private Collection<Teacher> teachers;

    @ManyToMany(mappedBy = "students")
    private Collection<Test> tests;
}
