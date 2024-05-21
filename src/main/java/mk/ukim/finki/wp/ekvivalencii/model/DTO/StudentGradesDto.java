package mk.ukim.finki.wp.ekvivalencii.model.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"subject", "student", "gradeDate", "grade", "ectsGrade", "message"})
public class StudentGradesDto {
    public String subject;
    public String student;
    public String gradeDate;
    public Short grade;
    public String ectsGrade;
    public String message;
}
