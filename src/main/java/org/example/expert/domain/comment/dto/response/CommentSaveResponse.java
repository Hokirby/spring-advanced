package org.example.expert.domain.comment.dto.response;

import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserResponse;

public record CommentSaveResponse(Long id, String contents, UserResponse user) {

}
