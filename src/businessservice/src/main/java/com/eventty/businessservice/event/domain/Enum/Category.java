package com.eventty.businessservice.event.domain.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    콘서트(1L, "콘서트"),
    클래식(2L, "클래식"),
    전시(3L, "전시"),
    스포츠(4L, "스포츠"),
    캠핑(5L, "캠핑"),
    아동(6L, "아동"),
    영화(7L, "영화"),
    IT(8L, "IT"),
    교양(9L, "교양"),
    TOPIC(10L, "TOPIC");

    private final Long id;
    private final String name;

}
