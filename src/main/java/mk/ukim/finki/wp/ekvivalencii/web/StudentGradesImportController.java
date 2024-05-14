package mk.ukim.finki.wp.ekvivalencii.web;

import jakarta.persistence.criteria.Join;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.ekvivalencii.model.DTO.StudentGradesDto;
import mk.ukim.finki.wp.ekvivalencii.model.Student;
import mk.ukim.finki.wp.ekvivalencii.model.StudentGrades;
import mk.ukim.finki.wp.ekvivalencii.model.Subject;
import mk.ukim.finki.wp.ekvivalencii.repository.ImportRepository;
import mk.ukim.finki.wp.ekvivalencii.service.interfaces.StudentGradesService;
import mk.ukim.finki.wp.ekvivalencii.service.interfaces.StudentService;
import mk.ukim.finki.wp.ekvivalencii.service.interfaces.SubjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static mk.ukim.finki.wp.ekvivalencii.service.specifications.FieldFilterSpecification.filterEqualsV;

@Controller
public class StudentGradesImportController {
    private final StudentGradesService studentGradesService;
    private final StudentService studentService;
    private final ImportRepository importRepository;
    private final SubjectService subjectService;

    public StudentGradesImportController(StudentGradesService studentGradesService, StudentService studentService, ImportRepository importRepository, SubjectService subjectService) {
        this.studentGradesService = studentGradesService;
        this.studentService = studentService;
        this.importRepository = importRepository;
        this.subjectService = subjectService;
    }

    @GetMapping("/ekvivalencii/student-grades/{id}")
    public String getStudentGrades(@PathVariable String id,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer results,
                                   @RequestParam(required = false) String index,
                                   @RequestParam(required = false) String subjectId,
                                   Model model) {

        Page<StudentGrades> studentGradesPage;

        Specification<StudentGrades> spec = Specification.where(filterEquals(StudentGrades.class, "student.index", index));

        if (subjectId != null && !subjectId.isEmpty()) {
            spec = spec.and(filterEqualsV(StudentGrades.class, "subject.id", subjectId));
        }

        studentGradesPage = studentGradesService.list(spec, pageNum, results);

        model.addAttribute("page", studentGradesPage);

        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        List<StudentGrades> studentGrades = this.studentGradesService.getAllStudentGrades();
        model.addAttribute("studentGrades", studentGrades);

        model.addAttribute("id", id);
        return "list";
    }

    private <T> Specification<T> filterEquals(Class<T> entityClass, String attributeName, String attributeValue) {
        if (attributeValue != null && !attributeValue.isEmpty()) {
            return (root, query, criteriaBuilder) -> {
                Join<T, Student> studentJoin = root.join("student");
                return criteriaBuilder.equal(studentJoin.get("index"), attributeValue);
            };
        } else {
            return null;
        }
    }

    @GetMapping("/ekvivalencii/student-grades/{id}/add")
    public String showAddStudentGradeForm(@PathVariable String id,
                                          Model model) {
        List<Student> students = studentService.getAllStudents();
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("id", id);
        model.addAttribute("studentGrade", new StudentGrades());
        model.addAttribute("students", students);
        model.addAttribute("subjects", subjects);
        return "add";
    }

    @PostMapping("/ekvivalencii/student-grades/{id}/save")
    public String addStudentGrade(@PathVariable String id,
                                  @RequestParam Student student,
                                  @RequestParam Subject subject,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime gradeDate,
                                  @RequestParam Short grade,
                                  @RequestParam String ectsGrade) {
        this.studentGradesService.save(student, subject, gradeDate, grade, ectsGrade);
        return "redirect:/ekvivalencii/student-grades/{id}";
    }

    @PostMapping("/ekvivalencii/student-grades/{id}/delete")
    public String deleteSubject(@PathVariable String id) {
        this.studentGradesService.deleteById(id);
        return "redirect:/ekvivalencii/student-grades/{id}";
    }

    @GetMapping("/ekvivalencii/student-grades/{id}/edit")
    public String editStudentGrades(@PathVariable String id, Model model) {
        if (this.studentGradesService.findByIndex(id).isPresent()) {
            StudentGrades studentGrades = this.studentGradesService.findByIndex(id).get();
            List<Student> students = this.studentService.getAllStudents();
            List<Subject> subjects = this.subjectService.getAllSubjects();

            model.addAttribute("students", students);
            model.addAttribute("subjects", subjects);
            model.addAttribute("studentGrades", studentGrades);
            return "edit";
        }
        return "/ekvivalencii/student-grades/{id}";
    }

    @PostMapping("/ekvivalencii/student-grades/{id}/edit")
    public String editStudentGrades(@PathVariable String id,
                                    @RequestParam Student student,
                                    @RequestParam Subject subject,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime gradeDate,
                                    @RequestParam Short grade,
                                    @RequestParam String ectsGrade) {
        this.studentGradesService.edit(id, student, subject, gradeDate, grade, ectsGrade);
        return "redirect:/ekvivalencii/student-grades/{id}";
    }

    @PostMapping("/ekvivalencii/student-grades/{id}/import")
    public String importCourseStudentGrades(@PathVariable String id,
                                            @RequestParam("file") MultipartFile file,
                                            HttpServletResponse response,
                                            Model model) {
        try {
            List<StudentGradesDto> grades = importRepository.readPreferences(file, StudentGradesDto.class);
            List<StudentGradesDto> invalidGrades = studentGradesService.importGradesFromCsv(grades);

            if (!invalidGrades.isEmpty()) {
                String fileName = "invalid_grades_" + ".tsv";
                response.setContentType("text/tab-separated-values");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

                try (OutputStream outputStream = response.getOutputStream()) {
                    importRepository.writePreferences(StudentGradesDto.class, invalidGrades, outputStream);
                }
            }

            return "redirect:/ekvivalencii/student-grades/{id}";
        } catch (IOException e) {
            model.addAttribute("error", "An error occurred while processing the file.");
            return "errorPage";
        }
    }

    @GetMapping("/ekvivalencii/student-grades/{id}/sample-tsv")
    public void sampleTsv(@PathVariable String id,
                          HttpServletResponse response,
                          Model model) {
        List<StudentGradesDto> example = new ArrayList<>();
        example.add(new StudentGradesDto("F23L2W096", "1", "2023-04-10T10:39:37", (short) 8, "72", null));
        example.add(new StudentGradesDto("F23L2W096", "1", "2023-04-10T10:39:37", (short) 10, "7", null));

        doExport(response, example);
        model.addAttribute("id", id);
    }

    @GetMapping("/ekvivalencii/student-grades/{id}/export")
    public void export(Model model,
                       @PathVariable String id,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "100000") Integer results,
                       @RequestParam(required = false) String index,
                       @RequestParam(required = false) String subjectId,
                       @RequestParam(required = false) String gradeDate,
                       @RequestParam(required = false) Short grade,
                       @RequestParam(required = false) String ectsGrade,
                       HttpServletResponse response) {

        Page<StudentGrades> studentGradesPage;

        Specification<StudentGrades> spec = Specification.where(filterEquals(StudentGrades.class, "student.index", index));

        if (subjectId != null && !subjectId.isEmpty()) {
            spec = spec.and(filterEqualsV(StudentGrades.class, "subject.id", subjectId));
        }

        studentGradesPage = studentGradesService.list(spec, pageNum, results);

        doExport(response, studentGradesPage.getContent().stream().map(sg -> new StudentGradesDto(
                sg.getSubject().getId(),
                sg.getStudent().getIndex(),
                sg.getGradeDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                sg.getGrade(),
                sg.getEctsGrade(),
                null
        )).collect(Collectors.toList()));

        model.addAttribute("id", id);
    }

    public void doExport(HttpServletResponse response, List<StudentGradesDto> data) {
        String fileName = "student_grades.tsv";
        response.setContentType("text/tab-separated-values");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        try (OutputStream outputStream = response.getOutputStream()) {
            importRepository.writeEnrollments(StudentGradesDto.class, data, outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

