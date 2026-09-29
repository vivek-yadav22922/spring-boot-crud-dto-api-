package Core.demo.crudDtoDemo.entity;

import Core.demo.crudDtoDemo.dto.CreateStudentRequestDTO;
import Core.demo.crudDtoDemo.dto.CreateStudentResponseDTO;
import Core.demo.crudDtoDemo.dto.UpdateStudentRequestDto;
import Core.demo.crudDtoDemo.dto.UpdateStudentResponseDto;
import Core.demo.crudDtoDemo.exception.DuplicateResourceException;
import Core.demo.crudDtoDemo.exception.ResourceNotFoundException;
import Core.demo.crudDtoDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service // inside say component he use ho raha hai but specialize aanotion hai//
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDTO createStudent(CreateStudentRequestDTO studentReqDTO) {
        Student student = mapToEntity(studentReqDTO);
        if(emailExists(student)){
    throw new DuplicateResourceException("Student with email "
     + student.getEmail() + "already exists");
}

        Student studentResp = studentRepository.save(student);
        return mapToDto(studentResp);// controller dto ko return kar dega//
    }

    public CreateStudentResponseDTO getStudent(Long id) {
        Student studentResp  = studentRepository
        .findById(id)
        .orElseThrow(()->
        new ResourceNotFoundException("Student with id " + id + "not found"));
return mapToDto(studentResp);

    }
    public List<CreateStudentResponseDTO> getAllStudent() {
        List<Student> studentList = studentRepository.findByDeletedIsFalse();// for soft delete


        return studentList.stream().map(this::mapToDto).toList();

    }
// select*from student where deleted= false- agar delete nahi hua hai to data dega//

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentreq) {
        Student existingStudent = studentRepository.
                findByIdAndDeletedIsFalse(id).
                orElseThrow(()->
                         new ResourceNotFoundException("Student with id " + id + "not found"));

        existingStudent.setName(studentreq.getName());
        existingStudent.setRollNo(studentreq.getRollNo());
        existingStudent.setSubject(studentreq.getSubject());
        existingStudent.setAge(studentreq.getAge());
        existingStudent.setDeleted(false);  //when hit soft delete api// database say data permanently na delete ho jay//
        existingStudent.setUpdatedAt(LocalDateTime.now());  //when hit soft delete api// database say data permanently na delete ho jay//


        Student savedStudent = studentRepository.save(existingStudent);

                   return mapToUpdateDto(savedStudent);

    }

    public void deleteStudent(Long id) {

        Student  studentToBeDeleted = studentRepository.
                findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Student with id " + id + "not found"));

        studentRepository.delete(studentToBeDeleted);


    }

    //
    public void deleteStudentSoftly(Long id) {
        // data get
        //delete=1
        //save
        Student  studentToBeDeleted = studentRepository.
                findByIdAndDeletedIsFalse(id).
                orElseThrow(()-> new ResourceNotFoundException("Student with id " + id + "not found"));


        studentToBeDeleted.setDeleted(true);
        studentRepository.save(studentToBeDeleted);

    }

    private Student mapToEntity(CreateStudentRequestDTO studentReqDTO) {
        Student student = new Student();
        student.setName(studentReqDTO.getName());
        student.setAge(studentReqDTO.getAge());
        student.setEmail(studentReqDTO.getEmail());
        student.setRollNo(studentReqDTO.getRollNo());
        student.setSubject(studentReqDTO.getSubject());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        student.setDeleted(false);
        return student;
    }

    private CreateStudentResponseDTO mapToDto(Student student){
        CreateStudentResponseDTO responseDTO= new CreateStudentResponseDTO();
        responseDTO.setId(student.getId());
        responseDTO.setName(student.getName());
        responseDTO.setAge(student.getAge());
        responseDTO.setEmail(student.getEmail());
        responseDTO.setRollNo(student.getRollNo());
        responseDTO.setSubject(student.getSubject());
        responseDTO.setMessage("Student saved successfully");
        responseDTO.setCreatedAt(student.getCreatedAt());
        responseDTO.setUpdatedAt(student.getUpdatedAt());
        return responseDTO;
    }

    private UpdateStudentResponseDto mapToUpdateDto(Student student){

        UpdateStudentResponseDto responseDTO= new UpdateStudentResponseDto();
        responseDTO.setId(student.getId());
        responseDTO.setName(student.getName());
        responseDTO.setAge(student.getAge());
        responseDTO.setEmail(student.getEmail());
        responseDTO.setRollNo(student.getRollNo());
        responseDTO.setSubject(student.getSubject());
        responseDTO.setMessage("Student saved successfully");
        responseDTO.setUpdatedAt(student.getUpdatedAt());
        return responseDTO;


    }
    private boolean emailExists(Student student){
      return   studentRepository.existsByEmail(student.getEmail());

    }
}

