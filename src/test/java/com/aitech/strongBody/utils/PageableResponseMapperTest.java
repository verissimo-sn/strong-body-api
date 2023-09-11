package com.aitech.strongBody.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;

import com.aitech.strongBody.infra.database.model.ExerciseDocument;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PageableResponseMapperTest {

    @Test
    void shouldReturnPageableEntityList() {
        final var fakePageableList = new PageImpl<>(List.of(new ExerciseDocument()));
        final var response = PageableResponseMapper.toPagination(fakePageableList);

        assertEquals(fakePageableList.getContent(), response.get("data"));
        assertEquals(fakePageableList.getNumber(), response.get("currentPage"));
        assertEquals(fakePageableList.getTotalElements(), response.get("totalItems"));
        assertEquals(fakePageableList.getTotalPages(), response.get("totalPages"));
    }
}
