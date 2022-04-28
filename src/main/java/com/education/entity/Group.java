package com.education.entity;

import com.education.enums.Label;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group extends Auditable {


    @ManyToOne
    private Education education;

    private Double price;

    private String name;

    @ManyToMany
    private List<AuthUser> users;

}
