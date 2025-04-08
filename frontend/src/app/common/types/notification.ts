export interface UserNotification {
    topic : UserNotificationTopic
    content : any
}

export enum UserNotificationTopic {
    SUBSCRIPTION_UPDATE = 'SUBSCRIPTION_UPDATE',
    COURSE_UPDATE = 'COURSE_UPDATE'
}

export function isUserNotification(value: any): value is UserNotification {
    if (typeof value !== 'object' || value === null) {
        return false;
    }

    if (!('topic' in value) || !('content' in value)) {
        return false;
    }

    if (typeof value.topic !== 'string' || !Object.values(UserNotificationTopic).includes(value.topic as UserNotificationTopic)) {
      return false;
    }

    return true;
}
