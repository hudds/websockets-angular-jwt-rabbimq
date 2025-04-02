import { Course, isCourse } from "src/app/common/types/course";

export interface CourseSubscription {
    subscriptionId: number,
    course: Course
}


export interface SubscriptionStatus {
    status : SubscriptionStatusValue
    courseId : number
    subscriptionId: number
}


export enum SubscriptionStatusValue{
    PENDING,
    SUCCESS,
    CANCELED,
    ERROR
}


export function isCourseSubscription(value: any): value is CourseSubscription {
    if (typeof value !== 'object' || value === null) {
        return false;
    }

    if (
        typeof value.subscriptionId !== 'number' ||
        !isCourse(value.course)
    ) {
        return false;
    }

    return true;
}

export function isSubscriptionStatus(value: any): value is SubscriptionStatus {
    if (typeof value !== 'object' || value === null) {
        return false;
    }

    if (
        typeof value.courseId !== 'number' ||
        typeof value.subscriptionId !== 'number' ||
        !Object.values(SubscriptionStatusValue).includes(value.status)
    ) {
        return false;
    }

    return true;
}