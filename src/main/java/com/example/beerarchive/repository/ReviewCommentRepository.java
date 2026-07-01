package com.example.beerarchive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.beerarchive.entity.ReviewComment;

@Repository
public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long>{
    
    List<ReviewComment> findByReview_ReviewIdOrderByCreatedAtAsc(Long reviewId);
}
