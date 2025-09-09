package com.jetnotifier.notification.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jetnotifier.notification.api.dto.response.NotificationResponse;
import com.jetnotifier.notification.domain.enums.NotificationPriority;
import com.jetnotifier.notification.domain.enums.NotificationStatus;
import com.jetnotifier.notification.domain.enums.NotificationType;
import com.jetnotifier.notification.service.AdminService;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private AdminService adminService;

    @Test
    void getAllNotificationTest() throws Exception {
        NotificationResponse response = new NotificationResponse();
        response.setId("1");
        response.setUserId("user123");
        response.setTitle("Test Message Title");
        response.setMessage("Test Message");
        response.setType(NotificationType.EMAIL);
        response.setPriority(NotificationPriority.LOW);
        response.setStatus(NotificationStatus.SENT);
        response.setCreatedAt(LocalDateTime.now());

        Page<NotificationResponse> mockPage =
                new PageImpl<>(Collections.singletonList(response), PageRequest.of(0, 10), 1);

        Mockito.when(adminService.getAllNotifications(any())).thenReturn(mockPage);

        mockMvc.perform(get("/api/admin/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].userId").value("user123"))
                .andExpect(jsonPath("$.content[0].title").value("Test Message Title"))
                .andExpect(jsonPath("$.content[0].message").value("Test Message"))
                .andExpect(jsonPath("$.content[0].type").value("EMAIL"))
                .andExpect(jsonPath("$.content[0].priority").value("HIGH"))
                .andExpect(jsonPath("$.content[0].status").value("SENT"));
    }
}
