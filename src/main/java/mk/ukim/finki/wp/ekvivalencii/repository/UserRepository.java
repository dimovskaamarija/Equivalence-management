package mk.ukim.finki.wp.ekvivalencii.repository;

import mk.ukim.finki.wp.ekvivalencii.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaSpecificationRepository<User, String> {

}
