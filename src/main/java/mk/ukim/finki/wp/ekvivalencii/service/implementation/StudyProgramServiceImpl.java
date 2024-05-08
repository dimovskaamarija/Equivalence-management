package mk.ukim.finki.wp.ekvivalencii.service.implementation;


import mk.ukim.finki.wp.ekvivalencii.model.StudyProgram;
import mk.ukim.finki.wp.ekvivalencii.repository.StudyProgramRepository;
import mk.ukim.finki.wp.ekvivalencii.service.interfaces.StudyProgramService;
import org.springframework.stereotype.Service;

import java.util.List;

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




}
