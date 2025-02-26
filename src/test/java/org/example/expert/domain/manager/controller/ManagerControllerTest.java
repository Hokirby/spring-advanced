package org.example.expert.domain.manager.controller;

import org.example.expert.domain.common.config.JwtUtil;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest;
import org.example.expert.domain.manager.dto.response.ManagerSaveResponse;
import org.example.expert.domain.manager.service.ManagerService;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ManagerController.class)
class ManagerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ManagerService managerService;

    @MockBean
    JwtUtil jwtUtil;

    @Test
    void 매니져를_저장하면_Ok를_반환한다() throws Exception {
        // given
        long todoId = 1L;
        String email = "test123@test.com";
        AuthUser authUser = new AuthUser(2L, email, UserRole.USER);
        ManagerSaveRequest managerSaveRequest = new ManagerSaveRequest(todoId);
        given(managerService.saveManager(authUser, todoId, managerSaveRequest))
                .willReturn(new ManagerSaveResponse(todoId, new UserResponse(todoId, email)));
        // when & then
        mockMvc.perform(post("/todos/{todoId}/managers"))
                .andExpect(jsonPath("$").value(todoId));
    }

}