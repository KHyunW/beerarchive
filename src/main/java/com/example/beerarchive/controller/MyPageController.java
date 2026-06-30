package com.example.beerarchive.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.beerarchive.dto.AccountDTO;
import com.example.beerarchive.dto.BeerDTO;
import com.example.beerarchive.dto.BeerTastingReviewDTO;
import com.example.beerarchive.entity.BeerLike;
import com.example.beerarchive.entity.BeerPairingVote;
import com.example.beerarchive.repository.BeerLikeRepository;
import com.example.beerarchive.repository.BeerPairingVoteRepository;
import com.example.beerarchive.service.AccountService;
import com.example.beerarchive.service.BeerService;
import com.example.beerarchive.service.BeerTastingReviewService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
    
    private final BeerService beerService;
    private final BeerTastingReviewService reviewService;
    private final BeerLikeRepository beerLikeRepository;
    private final BeerPairingVoteRepository beerPairingVoteRepository;
    private final AccountService accountService;

    // 마이페이지 메인
    @GetMapping
    public String myPage(HttpSession session, Model model){
        AccountDTO loginUser = (AccountDTO) session.getAttribute("loginUser");
        if(loginUser == null){
            return "redirect:/auth/login";
        }

        Long accountId = loginUser.getAccountId();

        model.addAttribute("account", loginUser);

        // 내가 등록한 맥주 목록
        List<BeerDTO> myBeers = beerService.getBeerList(null, null).stream()
                .filter(b -> b.getAccountId().equals(accountId))
                .collect(Collectors.toList());
        model.addAttribute("myBeers", myBeers);

        // 내가 쓴 리뷰 목록
        List<BeerTastingReviewDTO> myReviews = reviewService.getAllReviews().stream()
                .filter(r -> r.getAccountId().equals(accountId))
                .collect(Collectors.toList());
        model.addAttribute("myReviews", myReviews);

        // 내가 좋아요 누른 맥주 목록
        List<BeerLike> myLikes = beerLikeRepository.findByAccount_AccountId(accountId);
        List<BeerDTO> likedBeers = myLikes.stream()
                .map(like -> beerService.getBeer(like.getBeer().getBeerId()))
                .collect(Collectors.toList());
        model.addAttribute("likedBeers", likedBeers);

        // 내가 추천한 안주 페어링 목록
        List<BeerPairingVote> myVotes = beerPairingVoteRepository.findByAccount_AccountId(accountId);
        model.addAttribute("myVotes", myVotes);

        return "mypage/main";
    }

    // 비밀번호 변경 폼
    @GetMapping("/password")
    public String passwordForm(HttpSession session){
        if(session.getAttribute("loginUser") == null){
            return "redirect:/auth/login";
        }
        return "mypage/password";
    }

    // 비밀번호 변경 처리
    @PostMapping("/password")
    public String changePassword(@RequestParam String currentPassword,
                                @RequestParam String newPassword,
                                HttpSession session,
                                Model model){
        AccountDTO loginUser = (AccountDTO) session.getAttribute("loginUser");
        if(loginUser == null) {
            return "redirect:/auth/login";
        }try{
            accountService.changePassword(loginUser.getAccountId(), currentPassword, newPassword);
            return "redirect:/mypage";
        } catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            return "mypage/password";
        }
    }

}
