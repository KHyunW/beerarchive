package com.example.beerarchive.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beerarchive.dto.AccountDTO;
import com.example.beerarchive.dto.ReviewCommentDTO;
import com.example.beerarchive.service.ReviewCommentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class ReviewCommentController {
    
    private final ReviewCommentService commentService;

    // 댓글 목록 조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<List<ReviewCommentDTO>> getComments(@PathVariable Long reviewId){
        return ResponseEntity.ok(commentService.getComments(reviewId));
    }

    // 댓글 등록
    @PostMapping("/{reviewId}")
    public ResponseEntity<?> register(@PathVariable Long reviewId,
                                        @RequestBody Map<String, String>body,
                                        HttpSession session){
        AccountDTO loginUser = (AccountDTO) session.getAttribute("loginUser");
        if(loginUser == null){
            return ResponseEntity.status(401).body("로그인이 필요합니다");
        }

        String content = body.get("content");
        if(content == null || content.trim().isEmpty()){
            return ResponseEntity.badRequest().body("댓글 내용을 입력해주세요");
        }

        ReviewCommentDTO dto = commentService.register(reviewId, loginUser.getAccountId(), content);
        return ResponseEntity.ok(dto);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable Long commentId, HttpSession session){
        AccountDTO loginUser = (AccountDTO) session.getAttribute("loginUser");
        if(loginUser == null){
            return ResponseEntity.status(401).body("로그인이 필요합니다");
        }
        try {
            commentService.delete(commentId, loginUser.getAccountId());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
