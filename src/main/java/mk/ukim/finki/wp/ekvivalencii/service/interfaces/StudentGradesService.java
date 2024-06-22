package mk.ukim.finki.wp.ekvivalencii.service.interfaces;

import mk.ukim.finki.wp.ekvivalencii.model.DTO.StudentGradesDto;
import mk.ukim.finki.wp.ekvivalencii.model.Student;
import mk.ukim.finki.wp.ekvivalencii.model.StudentGrades;
import mk.ukim.finki.wp.ekvivalencii.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentGradesService {
    List<StudentGrades> getAllStudentGrades();

    Page<StudentGrades> list(Specification<StudentGrades> spec, int page, int size);

    Optional<StudentGrades> save(Student student, Subject subject, LocalDateTime gradeDate, Short grade, String ectsGrade);

    Optional<StudentGrades> edit(String id, Student student, Subject subject, LocalDateTime gradeDate, Short grade, String ectsGrade);

    Optional<StudentGrades> findByIndex(String index);

    void deleteById(String id);

    StudentGrades saveStudentGrades(StudentGrades studentGrades);
    List<StudentGradesDto> importGradesFromCsv(List<StudentGradesDto> importStudentGrades);

    StudentGrades saveStudentDto(Subject subject,
                                 Student student,
                                 String gradeDate,
                                 Short grade,
                                 String ectsGrade);

    Page<StudentGrades> filterAndPaginateStudentGrades(String studentIndex, int pageNum, int pageSize);
}
