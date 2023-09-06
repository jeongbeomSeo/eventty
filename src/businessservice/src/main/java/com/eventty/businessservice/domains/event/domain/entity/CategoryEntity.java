package com.eventty.businessservice.domains.event.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CategoryEntity {
    private Long id;
    private String name;
}
