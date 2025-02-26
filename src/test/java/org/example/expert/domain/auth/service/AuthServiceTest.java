package org.example.expert.domain.auth.service;

import jakarta.transaction.Transactional;
import org.example.expert.domain.common.config.PasswordEncoder;
import org.example.expert.domain.auth.dto.request.SigninRequest;
import org.example.expert.domain.auth.dto.request.SignupRequest;
import org.example.expert.domain.auth.dto.response.SigninResponse;
import org.example.expert.domain.auth.dto.response.SignupResponse;
import org.example.expert.domain.auth.exception.AuthException;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AuthService authService;

    @Test
    void 회원가입을_성공한다() {
        // given
        String email = "test123@test.com";
        SignupRequest signupRequest = new SignupRequest(email, "password", "user");
        given(userRepository.existsByEmail(email)).willReturn(false);
        // when
        SignupResponse signupResponse = authService.signup(signupRequest);
        // then
        assertThat(signupResponse).isNotNull();
        assertThat(signupResponse.getBearerToken()).isNotBlank();
        User savedUser = userRepository.findByEmail(email).orElse(null);
        assertThat(savedUser).isNotNull()
                .matches(s -> s.getEmail().equals(email))
                .matches(s -> s.getPassword().equals("password"))
                .matches(s -> s.getUserRole().equals(UserRole.of("user")));
    }

    @Test
    void 회원가입시_이메일이_중복될_떄_InvalidRequesstException을_던진다() {
        // given
        String email = "test123@test.com";
        SignupRequest signupRequest = new SignupRequest(email, "password", "user");
        User user = new User(email, "password", UserRole.USER);
        userRepository.save(user);
        given(userRepository.existsByEmail(email)).willReturn(true);
        // when & then
        assertThrows(InvalidRequestException.class,
                () -> authService.signup(signupRequest), "이미 존재하는 이메일입니다.");
    }

    @Test
    void 로그인을_성공한다() {
        // given
        String email = "test123@test.com";
        SigninRequest signinRequest = new SigninRequest(email, "password");
        User user = new User(email, "password", UserRole.USER);
        given(passwordEncoder.matches("password", user.getPassword())).willReturn(true);
        // when
        SigninResponse signinResponse = authService.signin(signinRequest);
        // then
        assertThat(signinResponse).isNotNull();
        assertThat(signinResponse.getBearerToken()).isNotBlank();
    }

    @Test
    void 로그인시_이메일로_회원조회시_존재하지_않을_때_InvalidException을_던진다() {
        // given
        String email = "test123@test.com";
        SignupRequest signupRequest = new SignupRequest(email, "password", "user");
        given(authService.signup(signupRequest)).willReturn(new SignupResponse(anyString()));
        SigninRequest signinRequest = new SigninRequest(email, "password");
        // when & then
        assertThrows(InvalidRequestException.class,
                () -> authService.signin(signinRequest), "가입되지 않은 유저입니다.");
    }

    @Test
    void 로그인시_이메일과_비밀번호가_일치하지_않을_때_401을_반환한다() {
        // given
        String email = "test123@test.com";
        User user = new User(email, "password", UserRole.USER);
        userRepository.save(user);
        SigninRequest signinRequest = new SigninRequest(email, anyString());
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        // when & then
        assertThrows(AuthException.class,
                () -> authService.signin(signinRequest), "잘못된 비밀번호입니다.");
    }
}