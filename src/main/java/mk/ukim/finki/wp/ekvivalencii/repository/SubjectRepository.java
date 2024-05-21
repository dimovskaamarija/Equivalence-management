package mk.ukim.finki.wp.ekvivalencii.repository;

import mk.ukim.finki.wp.ekvivalencii.model.Subject;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaSpecificationRepository<Subject, String> {

}
