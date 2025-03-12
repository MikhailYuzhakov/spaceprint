package ru.yuzhakov.user_gateway.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "spaceprint.user_roles")
public class UserRole {
    @Id
    private Long id;
    private Long userId;
    private Long roleId;
}
