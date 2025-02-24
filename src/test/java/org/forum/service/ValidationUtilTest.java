package org.forum.service;

import org.forum.model.dto.NewCommentDto;
import org.forum.model.dto.PostDto;
import org.forum.model.dto.UserDto;
import org.forum.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validComment() {
        NewCommentDto validComment = new NewCommentDto("This is a valid comment.", null);
        assertTrue(ValidationUtil.validComment(validComment, redirectAttributes));

        NewCommentDto emptyComment = new NewCommentDto("", null);
        assertFalse(ValidationUtil.validComment(emptyComment, redirectAttributes));

        var longCommentText = new char[800];
        NewCommentDto longComment = new NewCommentDto(new String(longCommentText), null);
        assertFalse(ValidationUtil.validComment(longComment, redirectAttributes));
    }

    @Test
    void validPost() {
        var validPost = new PostDto("This is a valid post.", "Valid content.");
        assertTrue(ValidationUtil.validPost(validPost, redirectAttributes));

        var emptyTitlePost = new PostDto("", null);
        assertFalse(ValidationUtil.validPost(emptyTitlePost, redirectAttributes));

        var emptyContentPost = new PostDto("This is a invalid post.", "");
        assertFalse(ValidationUtil.validPost(emptyContentPost, redirectAttributes));

        var longTitleText = new char[150];
        var longTitlePost = new PostDto(new String(longTitleText), "Valid content.");
        assertFalse(ValidationUtil.validPost(longTitlePost, redirectAttributes));

        var longContentText = new char[1100];
        var longContentPost = new PostDto("This is a valid title.", new String(longContentText));
        assertFalse(ValidationUtil.validPost(longContentPost, redirectAttributes));
    }

    @Test
    void validateUser() {
        UserDto validUserDto = new UserDto("validUsername", "validEmail@example.com", "validPassword", "validPassword");

        assertTrue(ValidationUtil.validateUser(validUserDto, null, redirectAttributes));
        assertFalse(ValidationUtil.validateUser(validUserDto, new User(), redirectAttributes));

        validUserDto.setConfirmPassword("differentPassword");
        assertFalse(ValidationUtil.validateUser(validUserDto, null, redirectAttributes));
    }
}