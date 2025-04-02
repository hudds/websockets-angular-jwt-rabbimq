export interface UserNotification {
    topic : UserNotificationTopic
    content : any
}

export enum UserNotificationTopic {
    SUBSCRIPTION_UPDATE,
    COURSE_UPDATE
}

export function isUserNotification(value: any): value is UserNotification {
    if (typeof value !== 'object' || value === null) {
        return false;
    }

    if (!('topic' in value) || !('content' in value)) {
        return false;
    }

    if (!Object.values(UserNotificationTopic).includes(value.topic)) {
        return false;
    }

    return true;
}
