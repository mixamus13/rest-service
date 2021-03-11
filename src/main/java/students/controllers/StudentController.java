package students.controllers;

import org.springframework.web.bind.annotation.*;
import students.models.Student;
import students.repositories.StudentRepository;

import java.util.Optional;

@RequestMapping("/api")
@RestController
public class StudentController {

    StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/greeting")
    public String getHelloWorld() {
        return "Hello World!";
    }

    @GetMapping("/allStudents")
    public Iterable<Student> getAllStudents() {
        Iterable<Student> studentsCollection = repository.findAll();
        return studentsCollection;
    }

    @GetMapping("/students/{id}")
    public Student getStudent(@RequestParam(value = "id") Long id) {
        Student student = new Student();
        Optional<Student> optionalStudent = repository.findById(id);
        if (optionalStudent.isPresent()) {
            student = optionalStudent.get();
        }
        return student;
    }

    @PostMapping("/students")
    public Student addStudent(@RequestParam(value = "firstname") String firstname,
                              @RequestParam(value = "lastname") String lastname) {
        Student newStudent = new Student(firstname, lastname);

        repository.save(newStudent);

        return newStudent;
    }

    @DeleteMapping("/students")
    public Student deleteStudent(@RequestParam(value = "id") Long id) {
        Student studentToDelete = new Student();
        Optional<Student> optionalStudent = repository.findById(id);
        if (optionalStudent.isPresent()) {
            studentToDelete = optionalStudent.get();
            repository.deleteById(studentToDelete.getId());
        }
        return studentToDelete;
    }

    @PutMapping("/students")
    public Student editStudent(@RequestParam(value = "id") Long id,
                               @RequestParam(value = "firstname", required = false) String firstname,
                               @RequestParam(value = "lastname", required = false) String lastname) {
        Optional<Student> optionalStudent = repository.findById(id);
        Student student = new Student();
        if (optionalStudent.isPresent()) {
            student = optionalStudent.get();
            if (firstname != null && !student.getFirstname().equals(firstname)) {
                student.setFirstname(firstname);
            }
            if (lastname != null && !student.getLastname().equals(lastname)) {
                student.setLastname(lastname);
            }
            repository.save(student);
        }
        return student;
    }

}