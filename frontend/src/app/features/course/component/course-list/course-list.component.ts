import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AppErrorHandler } from 'src/app/common/lib/error-resolver';
import { UserNotificationService } from 'src/app/common/service/notification.service';
import { Course, CourseFunctions } from 'src/app/common/types/course';
import { CourseService } from 'src/app/features/course/service/course.service';
import { SubscriptionService } from 'src/app/features/subscription/service/subscription.service';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrl: './course-list.component.scss'
})
export class CourseListComponent implements OnInit {


  coursesState = new CoursesState();

  constructor(private courseService: CourseService, private _snackBar : MatSnackBar, private subscriptionService : SubscriptionService, private sseNotificationService : UserNotificationService) {
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
    let errorHandler = new AppErrorHandler(this._snackBar);
    this.coursesState.loading = true
    this.courseService.getCourses(this.coursesState.pageNumber + 1, this.coursesState.pageSize).subscribe({
      next: courses => this.loadCourses(courses),
      error: error => errorHandler.handle(error)
    })
  }

  loadCourses(courses: Course[]) {
    courses.forEach(course => this.coursesState.putCourse(course))
    this.coursesState.loading = false
    if (courses.length > 0) {
      this.coursesState.pageNumber++
    }
  }


  subscribeToCourse(course : Course ){
    let errorHandler = new AppErrorHandler(this._snackBar);
    this.subscriptionService.subscribeToCourse(course.courseId).subscribe({
      next: subscription => course.subscriptionStatus = subscription.status,
      error: error => errorHandler.handle(error)
    })
  }

}

class CoursesState {
  loading: boolean = false
  courses: Course[] = []
  courseMap: Map<number, Course> = new Map()
  pageNumber: number = 0
  pageSize: number = 10

  putCourse(course: Course): void {
    if (this.updateCourseIfExists(course)) {
      return;
    }
    this.courseMap.set(course.courseId, course);
    this.courses.push(course);
  }

  updateCourseIfExists(course: Course): boolean {
    let existingCourse = this.courseMap.get(course.courseId);
    if (existingCourse) {
      CourseFunctions.copyValues(course, existingCourse);
      return true;
    }
    return false;
  }

}
