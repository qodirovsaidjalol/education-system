package com.education.entity;

import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Education extends Auditable {

    private String logoPath;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    private Address address;

    private String url;

    @Column(nullable = false)
    private String name;


    private String lecithinPath;



    @Column(columnDefinition = "Date default now()")
    private Date payedUntil;

    @Column(columnDefinition = "boolean default false")
    private boolean isBlock;


    private Long owner;

    @Override
    public Long getId() {
        return super.getId();
    }



}
