package com.education.dto.education;

import com.education.dto.Dto;
import com.education.dto.address.AddressCreateDto;
import com.education.validation.authuser.PhoneUnique;
import com.education.validation.authuser.PhoneValid;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EducationCreateDto implements Dto {

    // private MultipartFile logo;


    @PhoneValid(message = "{invalid.phone}")
    @PhoneUnique
    private String phone;

   // @Email(regexp = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b", message = "{email.invalid}")
    private String email;

    private AddressCreateDto address;

    private String url;

    private String name;

    //private MultipartFile lecithin;

    private String payedUntil;
    @Min(value = 10,message = "max 10")
    private Long ownerId;

}
