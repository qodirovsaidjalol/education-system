package com.education.entity;

import com.education.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthUser extends Auditable implements GrantedAuthority {

    @Column(nullable = false)
    private String fullName;

    private String imagePath;

    @Column(unique = true, nullable = false)
    private String username;

    @Column
    private String password;

    @ManyToOne(optional = false)
    private Education education;

    @ManyToOne(optional = false)
    private Address address;

    @ManyToOne
    private Language language;


    private String email;

    private String phone;

   // @Column(columnDefinition = "text default STUDENT")
    @Enumerated(EnumType.STRING)
    private Role role=Role.STUDENT;

    @Column(columnDefinition = "boolean default false")
    boolean isBlock;

    @Column(columnDefinition = "boolean default false")
    boolean isActive;

    private String parentEmail;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Payload> payloads;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Daily> dailies;

    @Builder(builderMethodName = "childBuilder")
    public AuthUser(Long id,Education education, Long createdBy, Long updatedBy, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted, String username, String password, String fullName, String phone, String chatId, Role role,Address address) {
        super(id, createdBy, updatedBy, createdAt, updatedAt, deleted);
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.role = role;
        this.address=address;
        this.education=education;
        this.isBlock=false;
        this.isActive=true;
    }

    public String getAuthority() {
        return role.name();
    }
}
