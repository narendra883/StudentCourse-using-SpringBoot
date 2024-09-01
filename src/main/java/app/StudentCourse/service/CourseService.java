package app.StudentCourse.service;

import app.StudentCourse.model.Course;
import app.StudentCourse.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }


    public Optional<Course> findById(Long courseId) {
        return courseRepository.findById(courseId);
    }
}
