package mk.ukim.finki.wp.ekvivalencii.service.interfaces;

import jakarta.persistence.*;
import mk.ukim.finki.wp.ekvivalencii.model.EquivalenceStatus;
import mk.ukim.finki.wp.ekvivalencii.model.Student;
import mk.ukim.finki.wp.ekvivalencii.model.StudentEquivalenceRequest;
import mk.ukim.finki.wp.ekvivalencii.model.StudyProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface StudentRequestManagementService  {
    List<StudentEquivalenceRequest> getAllStudentRequestManagement();


    @Enumerated(EnumType.STRING)
    public EquivalenceStatus status = EquivalenceStatus.REQUESTED;


    Optional<StudentEquivalenceRequest> edit(String id, Student student, StudyProgram oldStudyProgram, StudyProgram newStudyProgram, EquivalenceStatus status);

    Page<StudentEquivalenceRequest> filterAndPaginateStudentEquivalenceRequest(String index, int pageNum, int pageSize);

    Page<StudentEquivalenceRequest> list(Specification<StudentEquivalenceRequest> spec, int page, int size);

    Optional<StudentEquivalenceRequest> save(Student student, StudyProgram oldStudyProgram, StudyProgram newStudyProgram,
                                             EquivalenceStatus status);

    void deleteById(String id);

    Optional<StudentEquivalenceRequest> findByIndex(String index);



}
