package mk.ukim.finki.wp.ekvivalencii.service.implementation;

import mk.ukim.finki.wp.ekvivalencii.model.Subject;
import mk.ukim.finki.wp.ekvivalencii.model.exceptions.SubjectNotFoundException;
import mk.ukim.finki.wp.ekvivalencii.repository.SubjectRepository;
import mk.ukim.finki.wp.ekvivalencii.service.interfaces.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Subject getSubjectById(String mainSubjectId) {
        return subjectRepository.findById(mainSubjectId)
                .orElseThrow(() -> new SubjectNotFoundException("Main subject not found with id: " + mainSubjectId));
    }

}
