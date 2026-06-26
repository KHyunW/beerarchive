package com.example.beerarchive.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.beerarchive.dto.BeerTastingReviewDTO;
import com.example.beerarchive.entity.Account;
import com.example.beerarchive.entity.Beer;
import com.example.beerarchive.entity.BeerTastingReview;
import com.example.beerarchive.repository.AccountRepository;
import com.example.beerarchive.repository.BeerRepository;
import com.example.beerarchive.repository.BeerTastingReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BeerTastingReviewService {

    private final BeerTastingReviewRepository reviewRepository;
    private final BeerRepository beerRepository;
    private final AccountRepository accountRepository;

    // 특정 맥주 리뷰 목록 조회
    public List<BeerTastingReviewDTO> getReviewsByBeer(Long beerId) {
        return reviewRepository.findByBeer_BeerId(beerId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 평균 별점 조회
    public double getAvgRation(Long beerId) {
        Double avg = reviewRepository.findAvgRatingByBeerId(beerId);
        return avg != null ? Math.round(avg * 10) / 10.0 : 0.0;
    }

    // 리뷰 등록
    public void register(Long beerId, Long accountId, double rating, String content) {

        Beer beer = beerRepository.findById(beerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 맥주입니다"));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다"));

        BeerTastingReview review = BeerTastingReview.builder()
                .beer(beer)
                .account(account)
                .rating(rating)
                .content(content)
                .build();

        reviewRepository.save(review);
    }

    // 리뷰 삭제
    public void delete(Long reviewId, Long accountId){
        BeerTastingReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다"));

        // 본인 리뷰만 삭제 가능
        if(!review.getAccount().getAccountId().equals(accountId)){
            throw new IllegalArgumentException("본인의 리뷰만 삭제할 수 있습니다");
        }

        reviewRepository.deleteById(reviewId);
    }

    private BeerTastingReviewDTO toDTO(BeerTastingReview review) {
        return BeerTastingReviewDTO.builder()
                .reviewId(review.getReviewId())
                .beerId(review.getBeer().getBeerId())
                .accountId(review.getAccount().getAccountId())
                .nickname(review.getAccount().getNickname())
                .rating(review.getRating())
                .content(review.getContent())
                .build();
    }

}
