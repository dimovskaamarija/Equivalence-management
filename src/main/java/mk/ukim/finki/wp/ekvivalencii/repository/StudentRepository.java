package mk.ukim.finki.wp.ekvivalencii.repository;


import mk.ukim.finki.wp.ekvivalencii.model.Student;

import java.util.Optional;

public interface StudentRepository extends JpaSpecificationRepository<Student, String> {
    Student findByIndex(String index);
}
