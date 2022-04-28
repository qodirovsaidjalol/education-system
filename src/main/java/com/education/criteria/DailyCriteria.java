package com.education.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyCriteria extends GroupCriteria {
    private Integer page;
    private Integer size;
}
