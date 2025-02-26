package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 이메일로_유저를_조회할_수_있다() {
        // given
        String email = "test123@test.com";
        User user = new User(email, "password", UserRole.USER);
        userRepository.save(user);
        // when
        User foundUser = userRepository.findByEmail(email).orElse(null);
        // then
        assertThat(foundUser).isNotNull()
                .usingComparator((a, b) -> (a.getEmail().compareTo(b.getEmail()))).isEqualTo(user)
                .matches(f -> f.getUserRole().equals(UserRole.USER));
    }

    @Test
    void 이메일로_유저의_존재를_조회할_수_있다() {
        // given
        String email = "test123@test.com";
        User user = new User(email, "password", UserRole.USER);
        userRepository.save(user);
        // when
        boolean result = userRepository.existsByEmail(email);
        // then
        assertThat(result).isEqualTo(true);
    }
}