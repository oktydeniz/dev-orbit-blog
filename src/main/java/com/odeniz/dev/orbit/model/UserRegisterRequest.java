package com.odeniz.dev.orbit.model;

import com.odeniz.dev.orbit.enums.Gender;
import com.odeniz.dev.orbit.enums.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRegisterRequest {

    private String userName;

    private String email;

    private String password;

    private String phoneNumber;

    private Gender gender;

    private LocalDate birthDate;

    private Boolean isActiveMarketing;

    private Boolean isActiveAnalytics;

    private Boolean isActiveAdverting;

    private Long buildingId;

    private Role role;

}
