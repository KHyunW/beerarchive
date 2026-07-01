package com.example.beerarchive.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCommentDTO {
    
    private Long commentId;
    private Long reviewId;
    private Long accountId;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
}
