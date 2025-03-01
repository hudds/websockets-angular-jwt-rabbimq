import { Component, Input } from '@angular/core';
import { Course } from 'src/app/common/types/course';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrl: './course.component.scss'
})
export class CourseComponent {

  @Input("course") course? : Course


  get vacancies() : number {
    return (this.course?.slots ?? 0) - (this.course?.subscriptionCount ?? 0)
  }

}
