import { Component, OnInit } from '@angular/core';
import { Course, CourseFunctions } from 'src/app/common/types/course';
import { CourseService } from 'src/app/features/course/service/course.service';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrl: './course-list.component.scss'
})
export class CourseListComponent implements OnInit {


  coursesState = new CoursesState();

  constructor(private courseService: CourseService) { 
    this.coursesState.pageNumber = -1
    this.coursesState.pageSize = 12
  }

  ngOnInit(): void {
    this.loadNextPage()
    this.courseService.courseSubject.subscribe(course => {
      if (course) {
        this.updateCourse(course)
      }
    })
  }

  updateCourse(courseUpdated: Course) {
    this.coursesState.updateCourseIfExists(courseUpdated);
  }

  loadNextPage() {
    this.coursesState.loading = true
    this.courseService.getCourses(this.coursesState.pageNumber+1, this.coursesState.pageSize).subscribe(courses => {
      courses.forEach(course => this.coursesState.putCourse(course))
      this.coursesState.loading = false
      if(courses.length > 0){
        this.coursesState.pageNumber++
      }
    })
  }
  
}

class CoursesState {
  loading: boolean = false
  courses: Course[] = []
  courseMap: Map<number, Course> = new Map()
  pageNumber: number = 0
  pageSize: number = 10

  putCourse(course : Course) : void{
    if(this.updateCourseIfExists(course)){
      return;
    }
    this.courseMap.set(course.courseId, course);
    this.courses.push(course);
  }

  updateCourseIfExists(course : Course) : boolean {
    let existingCourse = this.courseMap.get(course.courseId);
    if(existingCourse){
      CourseFunctions.copyValues(course, existingCourse);
      return true;
    }
    return false;
  }

}
