package ru.yuzhakov.user_gateway.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("spaceprint.roles")
public class Role {
    @Id
    private Long id;
    private String name;
    private String description;
}
