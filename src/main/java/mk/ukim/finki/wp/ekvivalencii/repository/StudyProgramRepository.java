package mk.ukim.finki.wp.ekvivalencii.repository;


import mk.ukim.finki.wp.ekvivalencii.model.StudyProgram;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyProgramRepository extends JpaSpecificationRepository<StudyProgram, String> {

}
