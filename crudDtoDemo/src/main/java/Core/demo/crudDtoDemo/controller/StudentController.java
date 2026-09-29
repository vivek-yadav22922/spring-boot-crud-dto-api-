package Core.demo.crudDtoDemo.controller;

import Core.demo.crudDtoDemo.dto.CreateStudentRequestDTO;
import Core.demo.crudDtoDemo.dto.CreateStudentResponseDTO;
import Core.demo.crudDtoDemo.dto.UpdateStudentRequestDto;
import Core.demo.crudDtoDemo.dto.UpdateStudentResponseDto;
import Core.demo.crudDtoDemo.entity.Student;
import Core.demo.crudDtoDemo.entity.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// validation  -> spring-boot-starter-validation

@RestController  //inside RestController available component tags//
@RequestMapping("/api/students") // all end point same only change some places//
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService)
      {   // for construter injection//
        this.studentService = studentService;
    }

    // create student method call//
    @PostMapping  //professional rest api //
    public ResponseEntity<CreateStudentResponseDTO> createStudent(
           @Valid @RequestBody CreateStudentRequestDTO studentRequestDTO){

        CreateStudentResponseDTO createdStudent  = studentService.createStudent(studentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    //read  one Student method call//
    @GetMapping("/{id}")
    public ResponseEntity<CreateStudentResponseDTO> getStudent(@PathVariable Long id){
        CreateStudentResponseDTO studentResp= studentService.getStudent(id);
        return ResponseEntity.ok(studentResp); //small metod//
    }

    // read all student data//
    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDTO>> getAllStudent(){
        List<CreateStudentResponseDTO> studenList= studentService.getAllStudent();

        if(studenList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studenList);
    }

    // update student method call//

    @PutMapping
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@RequestParam Long id,
                                                 @RequestBody UpdateStudentRequestDto studentreq ){
        UpdateStudentResponseDto studentResp= studentService.updateStudent(id,studentreq);
        return ResponseEntity.ok(studentResp);
    }

    // Delete student method call//


    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@RequestParam Long id){
        studentService.deleteStudent(id);


        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/delete-soft")
    public ResponseEntity<String> deleteStudentSoftly(@PathVariable Long id){
        studentService.deleteStudentSoftly(id);

        return ResponseEntity.noContent().build();
    }


}
