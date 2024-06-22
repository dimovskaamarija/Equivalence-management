package mk.ukim.finki.wp.ekvivalencii.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudentGrades {

    @Id
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
        this(student, subject);
        this.gradeDate = gradeDate;
        this.grade = grade;
        this.ectsGrade = ectsGrade;
    }

    public StudentGrades(Subject subject) {
        this.subject = subject;
        this.id = subject.getId();
    }
}
