package com.education.dto.auth;

import com.education.dto.GenericDto;
import com.education.entity.Address;
import com.education.entity.Education;
import com.education.entity.Language;
import com.education.enums.Role;
import com.education.validation.authuser.UniqueUsername;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserUpdateDto extends GenericDto {


    private String imagePath;

    @UniqueUsername
    private String username;

    private String email;

    private String phone;

    private String parentEmail;

}
