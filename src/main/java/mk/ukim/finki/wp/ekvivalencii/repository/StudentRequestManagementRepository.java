package mk.ukim.finki.wp.ekvivalencii.repository;

import mk.ukim.finki.wp.ekvivalencii.model.EquivalenceStatus;
import mk.ukim.finki.wp.ekvivalencii.model.Student;
import mk.ukim.finki.wp.ekvivalencii.model.StudentEquivalenceRequest;
import mk.ukim.finki.wp.ekvivalencii.model.StudyProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRequestManagementRepository  extends JpaSpecificationRepository<StudentEquivalenceRequest,String> {
   Page<StudentEquivalenceRequest> findByStudent(Optional<Student> student, Pageable pageable);

   Page<StudentEquivalenceRequest> findByOldStudyProgram(StudyProgram oldStudyProgram, Pageable pagable);
   Page<StudentEquivalenceRequest> findByNewStudyProgram(StudyProgram newStudyProgram, Pageable pagable);
   Page<StudentEquivalenceRequest> findByStatus(EquivalenceStatus status, Pageable pagable);

}
