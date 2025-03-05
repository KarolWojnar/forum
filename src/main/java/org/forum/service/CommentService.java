package org.forum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.forum.exception.ForumException;
import org.forum.exception.PostException;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    public long addComment(Long id, NewCommentDto comment, RedirectAttributes redirect) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new PostException("", "/posts");
        }

        if (!post.get().isActive()) {
            throw new PostException("Post is archived and cannot be commented on.", "/posts");
        }

        if (!ValidationUtil.validComment(comment, redirect)) {
            throw new ForumException(redirect.getAttribute("error") != null
                    ? Objects.requireNonNull(redirect.getAttribute("error")).toString()
                    : "", "/posts/" + id);
        }
        createCommentAndSave(post.get(), comment, redirect);
        return post.get().getId();
    }

    private void createCommentAndSave(Post post, NewCommentDto comment, RedirectAttributes redirect) {
        Comment newComment = new Comment();
        newComment.setContent(comment.getContent());
        newComment.setUser(userService.findAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication()));

        newComment.setPost(post);
        if (comment.getParentId() != null) {
            newComment.setParentComment(commentRepository.findById(comment.getParentId()).orElse(null));
        }
        commentRepository.save(newComment);
        redirect.addFlashAttribute("success", "Comment added successfully.");
    }

    public List<CommentDto> getPostComments(Long id) {
        List<Comment> comments = commentRepository.findAllByPost_IdAndParentCommentNullOrderByCreateDate(id);
        return comments.stream().map(CommentDto::fromEntity).toList();
    }

    public void deleteComment(Long id) {
        User currentUser = userService.findAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication());
        Comment comment = commentRepository.findById(id).orElse(null);

        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Comment not found or unauthorized.");
        }

        if (currentUser.getRole().equals("ADMIN") || currentUser.equals(comment.getUser())) {
            if (comment.getParentComment() != null) {
                Comment parent = comment.getParentComment();
                parent.getReplies().remove(comment);
                commentRepository.save(parent);
            }
            commentRepository.delete(comment);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized to delete this comment.");
        }
    }

    public List<Comment> getReplies(Long id) {
         return commentRepository.findAllByParentComment_Id(id);
    }
}
