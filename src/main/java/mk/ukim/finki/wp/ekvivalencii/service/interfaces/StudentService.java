package mk.ukim.finki.wp.ekvivalencii.service.interfaces;

import mk.ukim.finki.wp.ekvivalencii.model.DTO.StudentDto;
import mk.ukim.finki.wp.ekvivalencii.model.Student;
import mk.ukim.finki.wp.ekvivalencii.model.StudentGrades;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Optional<Student> getStudentById(String id);

    List<Student> getAllStudents();
}
