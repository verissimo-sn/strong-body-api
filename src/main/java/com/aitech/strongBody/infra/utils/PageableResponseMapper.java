package com.aitech.strongBody.infra.utils;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public abstract class PageableResponseMapper {
    public static <T> Map<String, Object> toPagination(Page<T> pageable) {
        Map<String, Object> response = new HashMap<String, Object>();
        var content = pageable.getContent();
        response.put("data", content);
        response.put("currentPage", pageable.getNumber());
        response.put("totalItems", pageable.getTotalElements());
        response.put("totalPages", pageable.getTotalPages());
        return response;
    }
}
