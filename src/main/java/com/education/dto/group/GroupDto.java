package com.education.dto.group;

import com.education.dto.GenericDto;
import com.education.dto.auth.AuthUserDto;
import com.education.enums.Label;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto extends GenericDto {

    private Long educationId;

    private String name;

    private Double price;

}
