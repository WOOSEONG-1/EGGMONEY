package com.ssafy.eggmoney.notification.dto.request;

import com.ssafy.eggmoney.notification.entity.NotificationType;
import lombok.Getter;

@Getter
public class NotificationRequest {
    private NotificationType notificationType;
    private String message;
    private Long receiveUser;
}