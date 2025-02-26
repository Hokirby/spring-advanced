package org.example.expert.domain.comment.service;

import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.comment.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentAdminServiceTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentAdminService commentAdminService;

    @Test
    void 댓글_아이디로_댓글을_삭제한다() {
        // given
        long commentId = 1L;
        doNothing().when(commentRepository).deleteById(commentId);
        // when
        commentAdminService.deleteComment(commentId);
        // then
        verify(commentRepository, times(1)).deleteById(commentId);
    }
}