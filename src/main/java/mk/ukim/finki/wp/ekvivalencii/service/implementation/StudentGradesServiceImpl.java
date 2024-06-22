package mk.ukim.finki.wp.ekvivalencii.service.implementation;

import mk.ukim.finki.wp.ekvivalencii.model.DTO.StudentGradesDto;
import mk.ukim.finki.wp.ekvivalencii.model.Student;
import mk.ukim.finki.wp.ekvivalencii.model.StudentGrades;
import mk.ukim.finki.wp.ekvivalencii.model.Subject;
import mk.ukim.finki.wp.ekvivalencii.repository.StudentGradesRepository;
import mk.ukim.finki.wp.ekvivalencii.repository.StudentRepository;
import mk.ukim.finki.wp.ekvivalencii.repository.SubjectRepository;
import mk.ukim.finki.wp.ekvivalencii.service.interfaces.StudentGradesService;
import mk.ukim.finki.wp.ekvivalencii.service.interfaces.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentGradesServiceImpl implements StudentGradesService {

    private final StudentGradesRepository studentGradesRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final StudentService studentService;

    public StudentGradesServiceImpl(StudentGradesRepository studentGradesRepository, StudentRepository studentRepository, SubjectRepository subjectRepository, StudentService studentService) {
        this.studentGradesRepository = studentGradesRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.studentService = studentService;
    }

    @Override
    public List<StudentGrades> getAllStudentGrades() {
        return this.studentGradesRepository.findAll();
    }

    @Override
    public Page<StudentGrades> list(Specification<StudentGrades> spec, int page, int size) {
        return studentGradesRepository.findAll(spec, PageRequest.of(page - 1, size));
    }

    @Override
    public Optional<StudentGrades> save(Student student, Subject subject, LocalDateTime gradeDate, Short grade, String ectsGrade) {
        return Optional.of(this.studentGradesRepository.save(new StudentGrades(student, subject, gradeDate, grade, ectsGrade)));
    }


    @Override
    public Optional<StudentGrades> edit(String id, Student student, Subject subject, LocalDateTime gradeDate, Short grade, String ectsGrade) {
        StudentGrades studentGrades = this.studentGradesRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        studentGrades.setStudent(student);
        studentGrades.setSubject(subject);
        studentGrades.setGradeDate(gradeDate);
        studentGrades.setGrade(grade);
        studentGrades.setEctsGrade(ectsGrade);
        return Optional.of(this.studentGradesRepository.save(studentGrades));
    }

    @Override
    public Optional<StudentGrades> findByIndex(String index) {
        return this.studentGradesRepository.findById(index);
    }

    @Override
    public void deleteById(String id) {
        this.studentGradesRepository.deleteById(id);
    }

    @Override
    public StudentGrades saveStudentDto(Subject subject,
                                        Student student,
                                        String gradeDate,
                                        Short grade,
                                        String ectsGrade) {
        StudentGrades studentGrades = studentGradesRepository.findById(subject.getId())
                .orElse(new StudentGrades(subject));

        studentGrades.setStudent(student);
        studentGrades.setSubject(subject);
        studentGrades.setGradeDate(LocalDateTime.parse(gradeDate));
        studentGrades.setGrade(grade);
        studentGrades.setEctsGrade(ectsGrade);

        return studentGradesRepository.save(studentGrades);
    }

    @Override
    public Page<StudentGrades> filterAndPaginateStudentGrades(String studentIndex, int pageNum, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);

        if (studentIndex != null && !studentIndex.isEmpty()) {
            Optional<Student> student = studentService.getStudentById(studentIndex);
            return studentGradesRepository.findByStudent(student, pageRequest);
        } else {
            return studentGradesRepository.findAll(pageRequest);
        }
    }

    @Override
    public List<StudentGradesDto> importGradesFromCsv(List<StudentGradesDto> importStudentGrades) {
        List<StudentGradesDto> invalidGrades = new ArrayList<>();

        for (StudentGradesDto dto : importStudentGrades) {
            Optional<Student> studentOptional = studentRepository.findById(dto.getStudent());
            Optional<Subject> subjectOptional = subjectRepository.findById(dto.getSubject());

            if (studentOptional.isPresent() && subjectOptional.isPresent()) {
                Student student = studentOptional.get();
                Subject subject = subjectOptional.get();

                LocalDateTime gradeDate;
                try {
                    gradeDate = LocalDateTime.parse(dto.getGradeDate());
                } catch (DateTimeParseException e) {
                    dto.setMessage("Invalid grade date format");
                    invalidGrades.add(dto);
                    continue;
                }

                StudentGrades studentGrades = new StudentGrades(student, subject, gradeDate, dto.getGrade(), dto.getEctsGrade());
                studentGrades.setId(String.format("%s_%s", student.getIndex(), subject.getId()));
                studentGradesRepository.save(studentGrades);
            } else {
                dto.setMessage("Student or subject not found");
                invalidGrades.add(dto);
            }
        }
        return invalidGrades;
    }

    @Override
    public StudentGrades saveStudentGrades(StudentGrades studentGrades) {
        return studentGradesRepository.save(studentGrades);
    }
}
