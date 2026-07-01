package com.example.beerarchive.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.beerarchive.dto.ReviewCommentDTO;
import com.example.beerarchive.entity.Account;
import com.example.beerarchive.entity.BeerTastingReview;
import com.example.beerarchive.entity.ReviewComment;
import com.example.beerarchive.repository.AccountRepository;
import com.example.beerarchive.repository.BeerTastingReviewRepository;
import com.example.beerarchive.repository.ReviewCommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommentService {
    
    private final ReviewCommentRepository commentRepository;
    private final BeerTastingReviewRepository reviewRepository;
    private final AccountRepository accountRepository;

    // 댓글 목록 조회
    public List<ReviewCommentDTO> getComments(Long reviewId){
        return commentRepository.findByReview_ReviewIdOrderByCreatedAtAsc(reviewId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    //  댓글 등록
    public ReviewCommentDTO register(Long reviewId, Long accountId, String content){
        BeerTastingReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다"));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        ReviewComment comment = ReviewComment.builder()
                .review(review)
                .account(account)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        return toDTO(commentRepository.save(comment));
    }

    // 댓글 삭제
    public void delete(Long commentId, Long accountId){
        ReviewComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다"));

        if(!comment.getAccount().getAccountId().equals(accountId)){
            throw new IllegalArgumentException("본인 댓글만 삭제할 수 있습니다");
        }
        
        commentRepository.delete(comment);
    }

    // 관리자 삭제
    public void adminDelete(Long commentId){
        commentRepository.deleteById(commentId);
    }

    private ReviewCommentDTO toDTO(ReviewComment comment){
        return ReviewCommentDTO.builder()
                .commentId(comment.getCommentId())
                .reviewId(comment.getReview().getReviewId())
                .accountId(comment.getAccount().getAccountId())
                .nickname(comment.getAccount().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

}
