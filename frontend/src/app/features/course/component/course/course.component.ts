import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Course } from 'src/app/common/types/course';
import { SubscriptionStatusValue } from 'src/app/features/subscription/types/subscription';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrl: './course.component.scss'
})
export class CourseComponent {

  @Input("course") course? : Course

  @Output("subscriptionEvent") subscriptionEvent = new EventEmitter<Course>()

  get vacancies() : number {
    return (this.course?.slots ?? 0) - (this.course?.subscriptionCount ?? 0)
  }


  subscribe() {
    if(this.course){
      this.subscriptionEvent.next(this.course);
    }
  }

  shouldRenderSubscribeButton() : boolean{
    let statusShouldNotRender = new Set<SubscriptionStatusValue>();
    statusShouldNotRender.add(SubscriptionStatusValue.SUCCESS);
    statusShouldNotRender.add(SubscriptionStatusValue.PENDING);
    return !(this.course?.subscribed) && (this.course?.subscriptionStatus == null || !statusShouldNotRender.has(this.course?.subscriptionStatus))
  }

  getStatus() : string {
    if(this.course?.subscriptionStatus){
      let statusMap = new Map<SubscriptionStatusValue, string>();
      statusMap.set(SubscriptionStatusValue.CANCELED, "Inscrição cancelada");
      statusMap.set(SubscriptionStatusValue.SUCCESS, "Inscrito");
      statusMap.set(SubscriptionStatusValue.ERROR, "Ocorreu um erro ao inscrever");
      statusMap.set(SubscriptionStatusValue.PENDING, "Inscrição em andamento");
      return statusMap.get(this.course.subscriptionStatus) ?? "";
    }
    return "";
  }

  shouldRenderStatus(){
    return this.getStatus() !== "";
  }

}
