package mk.ukim.finki.wp.ekvivalencii.service.interfaces;


import mk.ukim.finki.wp.ekvivalencii.model.StudyProgram;

import java.util.List;
import java.util.Optional;

public interface StudyProgramService {

    List<StudyProgram> findAll();

    Optional<StudyProgram> findById(String id);

}
