import { SubscriptionStatusValue } from "src/app/features/subscription/types/subscription";

export interface Course {
    courseId:number;
    name:string;
    slots:number;
    createdAt?:string;
    updatedAt?:string;
    subscriptionCount:number;
    subscribed?:boolean;
    latestSubscription?:string;
    subscriptionStatus?:SubscriptionStatusValue
}

export function isCourse(value: any): value is Course {
    if (typeof value !== 'object' || value === null) {
        return false;
    }

    if (
        typeof value.courseId !== 'number' ||
        typeof value.name !== 'string' ||
        typeof value.slots !== 'number' ||
        typeof value.subscriptionCount !== 'number'
    ) {
        return false;
    }

    if (
        value.createdAt !== undefined && typeof value.createdAt !== 'string' ||
        value.updatedAt !== undefined && typeof value.updatedAt !== 'string' ||
        value.subscribed !== undefined && typeof value.subscribed !== 'boolean' ||
        value.latestSubscription !== undefined && typeof value.latestSubscription !== 'string' ||
        value.subscriptionStatus !== undefined && !Object.values(SubscriptionStatusValue).includes(value.subscriptionStatus)
    ) {
        return false;
    }

    return true;
}


export class CourseFunctions {
    static copyValues(origin : Course, target : Course) : void{
        target.createdAt = origin.createdAt
        target.updatedAt = origin.updatedAt
        target.latestSubscription = origin.latestSubscription
        target.name = origin.name
        target.slots = origin.slots
        target.subscribed = origin.subscribed
        target.subscriptionCount = origin.subscriptionCount
    }
}
