package org.forum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.forum.exception.UserException;
import org.forum.model.dto.CommentDto;
import org.forum.model.dto.NewCommentDto;
import org.forum.model.entity.Comment;
import org.forum.model.entity.Post;

import org.forum.model.entity.User;
import org.forum.repository.CommentRepository;
import org.forum.repository.PostRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    public String addComment(Long id, NewCommentDto comment, RedirectAttributes redirect) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            return "redirect:/posts";
        }
        if (!validComment(comment, redirect)) {
            return "redirect:/posts/" + id;
        }
        return createCommentAndSave(post.get(), comment, redirect);
    }

    private String createCommentAndSave(Post post, NewCommentDto comment, RedirectAttributes redirect) {
        Comment newComment = new Comment();
        newComment.setContent(comment.getContent());
        try {
            newComment.setUser(userService.findAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication(), redirect));
        } catch (UserException e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
        newComment.setPost(post);
        if (comment.getParentId() != null) {
            newComment.setParentComment(commentRepository.findById(comment.getParentId()).orElse(null));
        }
        commentRepository.save(newComment);
        redirect.addFlashAttribute("success", "Comment added successfully.");
        return "redirect:/posts/" + post.getId();
    }

    private boolean validComment(NewCommentDto comment, RedirectAttributes redirect) {
        if (comment == null) {
            redirect.addFlashAttribute("error", "Comment cannot be empty.");
            return false;
        } else if (comment.getContent().isEmpty()) {
            redirect.addFlashAttribute("error", "Comment cannot be empty.");
            return false;
        } else if (comment.getContent().length() > 500) {
            redirect.addFlashAttribute("error", "Comment cannot be longer than 500 characters.");
            return false;
        }
        return true;
    }

    public List<CommentDto> getPostComments(Long id) {
        List<Comment> comments = commentRepository.findAllByPost_IdAndParentCommentNullOrderByCreateDate(id);
        return comments.stream().map(CommentDto::fromEntity).toList();
    }

    public void deleteComment(Long id, RedirectAttributes redirect) {
        try {
            User currentUser = userService.findAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication(), redirect);
            if (currentUser.getRole().equals("ADMIN")) {
                commentRepository.deleteById(id);
                return;
            }

            Comment comment = commentRepository.findByIdAndUser(id, currentUser).orElse(null);
            if (comment == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Comment not found or unauthorized.");
            }
            commentRepository.deleteById(id);
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Comment not found or unauthorized.");
        }
    }

    public String getReplies(Long id, Model redirect) {
        List<Comment> comment = commentRepository.findAllByParentComment_Id(id);
        if (comment.isEmpty()) {
            return "redirect:/posts";
        }
        redirect.addAttribute("details", comment.stream().map(CommentDto::fromEntity).toList());
        return "fragments/post :: list-comment";
    }
}
