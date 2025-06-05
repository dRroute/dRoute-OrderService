package com.droute.orderservice.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import com.droute.orderservice.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityResponseDto {
    private Long userId;
    private String fullName;
    private String email;
    private Set<Role> roles;
    private String contactNo;
    private String colorHexValue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
