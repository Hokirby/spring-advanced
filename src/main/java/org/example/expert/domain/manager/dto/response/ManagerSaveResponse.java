package org.example.expert.domain.manager.dto.response;

import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserResponse;

@Getter
public record ManagerSaveResponse(Long id, UserResponse user) {

}
