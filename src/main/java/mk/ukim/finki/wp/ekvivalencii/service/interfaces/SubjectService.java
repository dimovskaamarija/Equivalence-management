package mk.ukim.finki.wp.ekvivalencii.service.interfaces;

import mk.ukim.finki.wp.ekvivalencii.model.Subject;
import mk.ukim.finki.wp.ekvivalencii.model.exceptions.SubjectNotFoundException;

import java.util.List;

public interface SubjectService {
    List<Subject> getAllSubjects();

    Subject getSubjectById(String mainSubjectId) throws SubjectNotFoundException;
}