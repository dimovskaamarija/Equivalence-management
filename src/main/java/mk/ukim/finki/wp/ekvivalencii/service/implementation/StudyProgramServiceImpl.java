package mk.ukim.finki.wp.ekvivalencii.service.implementation;


import mk.ukim.finki.wp.ekvivalencii.model.StudyProgram;
import mk.ukim.finki.wp.ekvivalencii.model.exceptions.StudyProgramNotFoundException;
import mk.ukim.finki.wp.ekvivalencii.repository.StudyProgramRepository;
import mk.ukim.finki.wp.ekvivalencii.service.interfaces.StudyProgramService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudyProgramServiceImpl implements StudyProgramService {

    private final StudyProgramRepository studyProgramRepository;

    public StudyProgramServiceImpl(StudyProgramRepository studyProgramRepository) {
        this.studyProgramRepository = studyProgramRepository;
    }

    @Override
    public List<StudyProgram> findAll() {
        return this.studyProgramRepository.findAll();
    }

    @Override
    public Optional<StudyProgram> findById(String id) {
        return Optional.ofNullable(this.studyProgramRepository.findById(id).orElseThrow(StudyProgramNotFoundException::new));
    }


}
