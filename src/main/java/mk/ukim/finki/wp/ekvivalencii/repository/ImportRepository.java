package mk.ukim.finki.wp.ekvivalencii.repository;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ImportRepository {


    <T> List<T> readEnrolments(MultipartFile file, Class<T> clazz);

    <T> void writeEnrollments(Class<T> clazz, List<T> invalidEnrollments, OutputStream outputStream) throws IOException;

    <T> List<T> readPreferences(MultipartFile file, Class<T> clazz);

    <T> void writePreferences(Class<T> clazz, List<T> invalidPreferences, OutputStream outputStream) throws IOException;
}
