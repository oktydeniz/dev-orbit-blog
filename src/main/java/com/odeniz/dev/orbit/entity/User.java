package com.odeniz.dev.orbit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.odeniz.dev.orbit.View;
import com.odeniz.dev.orbit.enums.Gender;
import com.odeniz.dev.orbit.enums.Role;
import com.odeniz.dev.orbit.model.UserRegisterRequest;
import com.odeniz.dev.orbit.util.CommonUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends DateIDBaseEntity {

    @Serial
    private static final long serialVersionUID = 1697222697222020020L;

    @Column(name = "user_name")
    @JsonView(View.Public.class)
    private String userName;

    @Column(name = "email", unique = true, nullable = false)
    @JsonView(View.Public.class)
    private String email;

    @Column(name = "password")
    @JsonView(View.Private.class)
    @JsonIgnore
    private String password;

    @JsonView(View.Public.class)
    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonView(View.Public.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @JsonView(View.Public.class)
    @Column(name = "is_enable")
    private boolean isEnable;

    @JsonView(View.Public.class)
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @JsonView(View.Public.class)
    @Column(name = "is_admin")
    private boolean isAdmin;

    @JsonView(View.Public.class)
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "verification_code")
    @JsonView(View.Private.class)
    private String verificationCode;

    @Column(name = "birth_date")
    @JsonView(View.Public.class)
    private LocalDate birthDate;

    @Column(name = "avatar_url")
    @JsonView(View.Public.class)
    private String avatarUrl;

    @Column(name = "author_title")
    @JsonView(View.Public.class)
    private String title;

    public User(UserRegisterRequest request){
        this.userName = request.getUserName();
        this.email = request.getEmail();
        this.role = Role.USER;
        this.phoneNumber = request.getPhoneNumber();
        this.birthDate = request.getBirthDate();
        this.gender = request.getGender();
        this.isEnable = false;
        this.verificationCode = CommonUtil.createVerificationCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User other)) {
            return false;
        }
        return Objects.equals(getId(), other.getId());
    }


}