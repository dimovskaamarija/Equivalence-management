package mk.ukim.finki.wp.ekvivalencii.service.interfaces;

import mk.ukim.finki.wp.ekvivalencii.model.DTO.StudentDto;
import mk.ukim.finki.wp.ekvivalencii.model.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Optional<Student> getStudentById(String id);
    Page<Student> find(Integer page, Integer size, String nameOrIndex, String studyProgramCode);
    List<StudentDto> importStudents(List<StudentDto> students);
    List<Student> getAllStudents();
}
