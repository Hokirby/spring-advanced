package org.example.expert.domain.auth.controller;

import org.example.expert.domain.auth.dto.request.SigninRequest;
import org.example.expert.domain.auth.dto.request.SignupRequest;
import org.example.expert.domain.auth.dto.response.SigninResponse;
import org.example.expert.domain.auth.dto.response.SignupResponse;
import org.example.expert.domain.auth.exception.AuthException;
import org.example.expert.domain.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthException.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void 회원가입_후_JWT_토큰을_반환한다() throws Exception {
        // given
        String email = "test123@test.com";
        SignupRequest signupRequest = new SignupRequest(email, "password", "user");
        given(authService.signup(signupRequest)).willReturn(new SignupResponse("bearerToken"));
        // when & then
        mockMvc.perform(post("/auth/signup"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.bearerToken").value("bearerToken"));
    }

    @Test
    void 로그인_후_JWT_토큰을_반환한다() throws Exception {
        // given
        String email = "test123@test.com";
        SigninRequest signinRequest = new SigninRequest(email, "password");
        given(authService.signin(signinRequest)).willReturn(new SigninResponse("bearerToken"));
        // when & then
        mockMvc.perform(post("/auth/signin"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.bearerToken").value("bearerToken"));
    }
}