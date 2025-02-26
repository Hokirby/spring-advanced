package org.example.expert.domain.comment.controller;

import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.example.expert.domain.comment.service.CommentService;
import org.example.expert.domain.common.dto.AuthUser;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentControllerTest.class)
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;

    @Test
    void 댓글을_저장하면_OK를_반환한다() throws Exception {
        // given
        long todoId = 1L;
        String email = "test123@test.com";
        AuthUser authUser = new AuthUser(todoId, email, UserRole.USER);
        CommentSaveRequest commentSaveRequest = new CommentSaveRequest("comment");
        given(commentService.saveComment(authUser, todoId, commentSaveRequest))
                .willReturn(new CommentSaveResponse(todoId, "comment", new UserResponse(todoId, email)));
        // when & then
        mockMvc.perform(post("/todos/{todoId}/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0]").value(todoId))
                .andExpect(jsonPath("$[1]").value("comment"))
                .andExpect(jsonPath("$[2].length()").value(2))
                .andExpect(jsonPath("$[2][0]").value(todoId))
                .andExpect(jsonPath("$[2][1]").value(email));
    }

    @Test
    void getComments() {
    }
}