package ru.yuzhakov.user_gateway.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("spaceprint.users")
public class User {
    @Id
    @Column("id")
    private Long id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("middle_name")
    private String middleName;

    @Column("username")
    private String username;

    @Column("password")
    private String password;

    @Column("phone_number")
    private String phoneNumber;

    @Column("email")
    private String email;

    @Column("enabled")
    private Boolean isEnabled;

    @Column("created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    @Column("updated_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime updatedAt;

    @Transient
    private String formattedCreatedAt;

    @Transient
    private Set<Role> roles = new HashSet<>();
}
