package org.example.expert.domain.auth.dto.response;

import lombok.Getter;

@Getter
public record SignupResponse(String bearerToken) {

}
