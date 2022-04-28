package com.education.dto.payload;

import com.education.dto.Dto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class PayloadCreateDto implements Dto {
    private Long studentId;
    private Long groupId;

    @Min(value = 0, message = "payment error entered")
    private int amount;
}
