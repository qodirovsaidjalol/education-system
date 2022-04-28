package com.education.dto.payload;

import com.education.dto.GenericDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayloadDto extends GenericDto {
    private int amount;
    private String studentName;
    private String groupName;
    private String dateOfPayment;
}
