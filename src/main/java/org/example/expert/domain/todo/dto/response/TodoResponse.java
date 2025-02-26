package org.example.expert.domain.todo.dto.response;

import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserResponse;

import java.time.LocalDateTime;

@Getter
public record TodoResponse(Long id, String title, String contents, String weather, UserResponse user,
                           LocalDateTime createdAt, LocalDateTime modifiedAt) {

}
