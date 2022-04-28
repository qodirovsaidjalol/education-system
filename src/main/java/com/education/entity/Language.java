package com.education.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Language extends Auditable {

    private String name;
    @Enumerated(EnumType.STRING)
    private com.education.enums.Language code= com.education.enums.Language.UZ;

}
