package com.education.criteria;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
@Getter
@Setter
public class GenericCriteria {
    private Integer size;
    private Integer page;

    public Integer getPage() {
        if (Objects.isNull(page))
            page = 0;
        return page;
    }

    public Integer getSize() {
        if (Objects.isNull(size))
            size = 10;
        return size;
    }
}
