package mk.ukim.finki.wp.ekvivalencii.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudentGrades {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    public String id;

    @ManyToOne
    public Student student;

    @ManyToOne
    public Subject subject;

    public LocalDateTime gradeDate;

    public Short grade;

    public String ectsGrade;

    public StudentGrades(Student student, Subject subject) {
        this.student = student;
        this.subject = subject;
        this.id = String.format("%s_%s", student.getIndex(), subject.getId());
    }

    public StudentGrades(Student student, Subject subject, LocalDateTime gradeDate, Short grade, String ectsGrade) {
        this.student = student;
        this.subject = subject;
        this.gradeDate = gradeDate;
        this.grade = grade;
        this.ectsGrade = ectsGrade;
    }

    public StudentGrades(Subject subject) {
        this.subject = subject;
        this.id = subject.getId();
    }
}
