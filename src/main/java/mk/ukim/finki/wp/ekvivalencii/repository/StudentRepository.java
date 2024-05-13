package mk.ukim.finki.wp.ekvivalencii.repository;

import mk.ukim.finki.wp.ekvivalencii.model.Student;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface StudentRepository extends JpaSpecificationRepository<Student, String> {

}
