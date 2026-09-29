package Core.demo.crudDtoDemo.repository;

import Core.demo.crudDtoDemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository  extends JpaRepository<Student, Long> {
    Optional<Student> findByIdAndDeletedIsFalse(Long id);
    List<Student> findByDeletedIsFalse();// with findAll we cannot use And//

    Boolean existsByEmail(String emaiId);
}
// with findBy+ fieldName+ condition //