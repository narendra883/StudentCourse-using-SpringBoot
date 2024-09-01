package app.StudentCourse.controller;

import app.StudentCourse.model.Course;
import app.StudentCourse.model.Student;
import app.StudentCourse.repository.CourseRepository;
import app.StudentCourse.repository.StudentRepository;
import app.StudentCourse.service.CourseService;
import app.StudentCourse.service.StudentService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class StudentCourseController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/addstudent")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return ResponseEntity.ok(studentService.addStudent(student));

    }
    @PostMapping("/{studentId}/addcourse")
    public ResponseEntity<Student> addCourseToStudent(@PathVariable Long studentId, @RequestBody Course course) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);

        if (!studentOpt.isPresent()) {
            return ResponseEntity.badRequest().body(null); // Or you can return a ResponseEntity with an error message or status.
        }

        Student student = studentOpt.get();

        // Check if course exists, if not save it to the database
        Optional<Course> existingCourseOpt = courseRepository.findById(course.getCourseId());
        Course courseToAdd;
        if (existingCourseOpt.isPresent()) {
            courseToAdd = existingCourseOpt.get();
        } else {
            courseToAdd = courseRepository.save(course);
        }

        // Add the course to the student's set of courses
        student.getCourses().add(courseToAdd);
        Student updatedStudent = studentRepository.save(student);

        return ResponseEntity.ok(updatedStudent);
    }



    @GetMapping("/allStudents")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }


}
