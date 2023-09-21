package com.eventty.businessservice.event.domain.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    Concert(1L, "Concert"),
    Classical(2L, "Classical"),
    Exhibition(3L, "Exhibition"),
    Sports(4L, "Sports"),
    Camping(5L, "Camping"),
    Children(6L, "Children"),
    Movie(7L, "Movie"),
    IT(8L, "IT"),
    Culture(9L, "Culture"),
    TOPIC(10L, "TOPIC");

    private final Long id;
    private final String name;

    public static String getNamefromId(Long id) {
        for (Category category : Category.values()) {
            if (category.getId().equals(id)) {
                return category.name;
            }
        }
        return null;
    }

}
