package mk.ukim.finki.wp.ekvivalencii.service.interfaces;

import mk.ukim.finki.wp.ekvivalencii.model.Professor;
import mk.ukim.finki.wp.ekvivalencii.model.exceptions.ProfessorNotFoundException;

import java.util.List;

public interface ProfessorService {


    List<Professor> getAllProfessors();

    Professor getProfessorById(String id) throws ProfessorNotFoundException;
}
