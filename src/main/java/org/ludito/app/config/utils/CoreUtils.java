package org.ludito.app.config.utils;

import org.springframework.data.domain.Sort;

import java.time.ZoneId;

public class CoreUtils {


    public static Sort getSortId() {
        return Sort.by(Sort.Order.by("id"));
    }

    public static Sort getSortByOrderAndId() {
        return Sort.by(Sort.Order.by("order"), Sort.Order.by("id"));
    }

    public static Sort getSortByCreatedDate() {
        return Sort.by(Sort.Order.by("createdAt"));
    }

    public static ZoneId getZoneId() {
        return ZoneId.of("Asia/Tashkent");
    }
}
