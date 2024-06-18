package mk.ukim.finki.wp.ekvivalencii.repository;

import mk.ukim.finki.wp.ekvivalencii.model.Student;
import mk.ukim.finki.wp.ekvivalencii.model.StudentGrades;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentGradesRepository extends JpaSpecificationRepository<StudentGrades, String> {
    Page<StudentGrades> findByStudent(Optional<Student> student, Pageable pageable);

}
