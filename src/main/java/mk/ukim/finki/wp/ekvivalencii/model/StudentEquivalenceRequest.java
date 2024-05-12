package mk.ukim.finki.wp.ekvivalencii.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudentEquivalenceRequest {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    public String id;

    @ManyToOne
    public Student student;

    @ManyToOne
    public StudyProgram oldStudyProgram;

    @ManyToOne
    public StudyProgram newStudyProgram;

    @Enumerated(EnumType.STRING)
    public EquivalenceStatus status = EquivalenceStatus.REQUESTED;

    public StudentEquivalenceRequest(Student student, StudyProgram oldStudyProgram, StudyProgram newStudyProgram) {
        this.student = student;
        this.oldStudyProgram = oldStudyProgram;
        this.newStudyProgram = newStudyProgram;

        this.id = String.format("%s_%s_%s", student.getIndex(), oldStudyProgram.getCode(), newStudyProgram.getCode());
    }

    public StudentEquivalenceRequest(Student student, StudyProgram oldStudyProgram, StudyProgram newStudyProgram, EquivalenceStatus status) {
        this.student = student;
        this.oldStudyProgram = oldStudyProgram;
        this.newStudyProgram = newStudyProgram;
        this.status = status;
    }
}
