package mk.ukim.finki.wp.ekvivalencii.service.implementation;

import mk.ukim.finki.wp.ekvivalencii.model.EquivalenceStatus;
import mk.ukim.finki.wp.ekvivalencii.model.Student;
import mk.ukim.finki.wp.ekvivalencii.model.StudentEquivalenceRequest;
import mk.ukim.finki.wp.ekvivalencii.model.StudyProgram;
import mk.ukim.finki.wp.ekvivalencii.repository.StudentRepository;
import mk.ukim.finki.wp.ekvivalencii.repository.StudentRequestManagementRepository;
import mk.ukim.finki.wp.ekvivalencii.repository.StudyProgramRepository;
import mk.ukim.finki.wp.ekvivalencii.service.interfaces.StudentRequestManagementService;
import mk.ukim.finki.wp.ekvivalencii.service.interfaces.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentRequestManagementServiceImpl  implements StudentRequestManagementService {
    private final StudentRequestManagementRepository studentRequestManagementRepository;
    private final StudentRepository studentRepository;
    private final StudyProgramRepository studyProgramRepository;
    private final StudentService studentService;

    public StudentRequestManagementServiceImpl(StudentRequestManagementRepository studentRequestManagementRepository, StudentRepository studentRepository, StudyProgramRepository studyProgramRepository, StudentService studentService) {
        this.studentRequestManagementRepository = studentRequestManagementRepository;
        this.studentRepository = studentRepository;


        this.studyProgramRepository = studyProgramRepository;
        this.studentService = studentService;
    }

   // @Override
//    public List<StudentEquivalenceRequest> getAllStudentRequestManagement() {
//        return this.studentRequestManagementRepository.findAll();
//    }

    @Override
    public List<StudentEquivalenceRequest> getAllStudentRequestManagement() {
        return this.studentRequestManagementRepository.findAll();
    }

    @Override
    public Optional<StudentEquivalenceRequest> edit(String id, Student student, StudyProgram oldStudyProgram, StudyProgram newStudyProgram, EquivalenceStatus status) {
        StudentEquivalenceRequest request=this.studentRequestManagementRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        request.setStudent(student);
        request.setOldStudyProgram(oldStudyProgram);
        request.setNewStudyProgram(newStudyProgram);
        request.setStatus(status);
        return Optional.of(this.studentRequestManagementRepository.save(request));
    }

    @Override
    public Page<StudentEquivalenceRequest> filterAndPaginateStudentEquivalenceRequest(String index, int pageNum, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);

        if (index != null && !index.isEmpty()) {
            Optional<Student> student=studentService.getStudentById(index);
            return studentRequestManagementRepository.findByStudent(student, pageRequest);
        } else {
            return studentRequestManagementRepository.findAll(pageRequest);
        }
    }
    @Override
    public Page<StudentEquivalenceRequest> list(Specification<StudentEquivalenceRequest> spec, int page, int size) {
        return studentRequestManagementRepository.findAll(spec, PageRequest.of(page - 1, size));
    }

    @Override
    public Optional<StudentEquivalenceRequest> save(String id, Student student, StudyProgram oldStudyProgram, StudyProgram newStudyProgram, EquivalenceStatus status) {
     //StudentEquivalenceRequest studentEquivalenceRequest=new StudentEquivalenceRequest(student,oldProgram,newProgram);
        return Optional.of(this.studentRequestManagementRepository.save(new StudentEquivalenceRequest(id,student,oldStudyProgram,newStudyProgram,status)));
    }
    @Override
    public void deleteById(String id) {
        this.studentRequestManagementRepository.deleteById(id);
    }

    @Override
    public Optional<StudentEquivalenceRequest> findByIndex(String index) {
        return this.studentRequestManagementRepository.findById(index);
    }


}

