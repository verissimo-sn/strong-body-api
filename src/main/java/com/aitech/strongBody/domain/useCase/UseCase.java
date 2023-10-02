package com.aitech.strongBody.domain.useCase;

public interface UseCase<O> {
    O execute(Object input);
}
