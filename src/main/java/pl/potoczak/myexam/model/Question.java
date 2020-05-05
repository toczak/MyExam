package pl.potoczak.myexam.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Question {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @OneToMany(mappedBy = "question")
    private Collection<Answer> answers;

    @ManyToMany(mappedBy = "questions")
    private Collection<Test> tests;

    @OneToOne
    private Answer correctAnswer;
}
