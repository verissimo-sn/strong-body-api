package com.aitech.strongBody.infra.utils;

import com.aitech.strongBody.infra.database.mongo.model.ExerciseDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Unit")
@SpringBootTest
@DisplayName("[Util] PageableResponseMapper")
public class PageableResponseMapperTest {

    @Test
    @DisplayName("Should return pageable entity list")
    void shouldReturnPageableEntityList() {
        final var fakePageableList = new PageImpl<>(List.of(new ExerciseDocument()));
        final var response = PageableResponseMapper.toPagination(fakePageableList);

        assertEquals(fakePageableList.getContent(), response.get("data"));
        assertEquals(fakePageableList.getNumber(), response.get("currentPage"));
        assertEquals(fakePageableList.getTotalElements(), response.get("totalItems"));
        assertEquals(fakePageableList.getTotalPages(), response.get("totalPages"));
    }
}
