package com.example.beerarchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.beerarchive.dto.AccountDTO;
import com.example.beerarchive.dto.BeerDTO;
import com.example.beerarchive.repository.AccountRepository;
import com.example.beerarchive.service.BeerService;
import com.example.beerarchive.service.BeerTastingReviewService;
import com.example.beerarchive.service.BreweryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    
    private final AccountRepository accountRepository;
    private final BeerService beerService;
    private final BreweryService breweryService;
    private final BeerTastingReviewService reviewService;

    // 관리자 권한 체크
    private boolean isAdmin(HttpSession session){
        AccountDTO loginUser = (AccountDTO) session.getAttribute("loginUser");
        return loginUser != null && "ROLE_ADMIN".equals(loginUser.getRole().name());
    }

    // 대시보드
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        model.addAttribute("accountCount", accountRepository.count());
        model.addAttribute("beerCount", beerService.getBeerList(null, null).size());
        model.addAttribute("breweryCount", breweryService.getAllBreweries().size());
        return "admin/dashboard";
    }

    // 사용자 목록
    @GetMapping("/accounts")
    public String accounts(@RequestParam(required = false) String keyword, HttpSession session, Model model){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        if(keyword != null && !keyword.isEmpty()){
            model.addAttribute("accountList", accountRepository.findByUsernameContainingOrNicknameContaining(keyword, keyword));
        } else {
            model.addAttribute("accountList", accountRepository.findAll());
        }
        model.addAttribute("keyword", keyword);
        return "admin/accounts";
    }

    // 사용자 삭제
    @PostMapping("/accounts/delete/{accountId}")
    public String deleteAccounts(@PathVariable Long accountId, HttpSession session){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        accountRepository.deleteById(accountId);
        return "redirect:/admin/accounts";
    }

    // 맥주 목록
    @GetMapping("/beers")
    public String beers(@RequestParam(required = false) String keyword, HttpSession session, Model model){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }

        model.addAttribute("beerList", beerService.getBeerList(keyword, null));
        model.addAttribute("keyword", keyword);
        return "admin/beers";
    }

    // 맥주 등록 폼
    @GetMapping("/beers/register")
    public String beerRegisterForm(HttpSession session, Model model){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        model.addAttribute("breweryList", breweryService.getAllBreweries());
        return "admin/beer-register";
    }

    // 맥주 등록 처리
    @PostMapping("/beers/register")
    public String beerRegister(@RequestParam String beerName,
                                @RequestParam String beerStyle,
                                @RequestParam double beerAbv,
                                @RequestParam int beerIbu,
                                @RequestParam Long breweryId,
                                HttpSession session,
                                Model model){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        try {
            AccountDTO loginUser = (AccountDTO) session.getAttribute("loginUser");
            BeerDTO dto = new BeerDTO();
            dto.setBeerName(beerName);
            dto.setBeerStyle(beerStyle);
            dto.setBeerAbv(beerAbv);
            dto.setBeerIbu(beerIbu);
            dto.setBreweryId(breweryId);
            beerService.register(dto, loginUser.getAccountId());
            return "redirect:/admin/beers";
        } catch(IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("breweryList", breweryService.getAllBreweries());
            return "admin/beer-register";
        }

    }

    // 맥주 수정 폼
    @GetMapping("/beers/edit/{beerId}")
    public String beerEditForm(@PathVariable Long beerId,
                                HttpSession session, Model model){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        model.addAttribute("beer", beerService.getBeer(beerId));
        model.addAttribute("breweryList", breweryService.getAllBreweries());
        return "admin/beer-edit";
    }

    // 맥주 수정 처리
    @PostMapping("/beers/edit/{beerId}")
    public String beerEdit(@PathVariable Long beerId,
                            @RequestParam String beerName,
                            @RequestParam String beerStyle,
                            @RequestParam double beerAbv,
                            @RequestParam int beerIbu,
                            HttpSession session){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        BeerDTO dto = new BeerDTO();
        dto.setBeerName(beerName);
        dto.setBeerStyle(beerStyle);
        dto.setBeerAbv(beerAbv);
        dto.setBeerIbu(beerIbu);
        beerService.update(beerId, dto);
        return "redirect:/admin/beers";
    }

    // 맥주 삭제
    @PostMapping("/beers/delete/{beerId}")
    public String beerDelete(@PathVariable Long beerId, HttpSession session){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        beerService.delete(beerId);
        return "redirect:/admin/beers";
    }

    // 양조장 목록
    @GetMapping("/breweries")
    public String breweries(@RequestParam(required = false) String keyword, HttpSession session, Model model){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        if(keyword != null && !keyword.isEmpty()){
            model.addAttribute("breweryList", breweryService.searchBreweries(keyword));
        } else {
            model.addAttribute("breweryList", breweryService.getAllBreweries());
        }
        model.addAttribute("keyword", keyword);
        return "admin/breweries";
    }

    // 양조장 등록 폼
    @GetMapping("/breweries/register")
    public String breweryRegister(HttpSession session){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        return "admin/brewery-register";
    }

    // 양조장 등록 처리
    @PostMapping("/breweries/register")
    public String breweryRegister(@RequestParam String breweryName,
                                    HttpSession session, Model model){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        try{
            breweryService.register(breweryName);
            return "redirect:/admin/breweries";
        } catch(IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            return "admin/brewery-register";
        }
    }

    // 양조장 수정 폼
    @GetMapping("/breweries/edit/{breweryId}")
    public String breweryEditForm(@PathVariable Long breweryId,
                                    HttpSession session, Model model){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        model.addAttribute("brewery", breweryService.getBrewery(breweryId));
        return "admin/brewery-edit";
    }

    // 양조장 수정 처리
    @PostMapping("/breweries/edit/{breweryId}")
    public String breweryEdit(@PathVariable Long breweryId,
                                @RequestParam String breweryName,
                                HttpSession session){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        breweryService.updateBrewery(breweryId, breweryName);
        return "redirect:/admin/breweries";
    }

    // 양조장 삭제
    @PostMapping("/breweries/delete/{breweryId}")
    public String breweryDelete(@PathVariable Long breweryId, HttpSession session){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        breweryService.delete(breweryId);
        return "redirect:/admin/breweries";
    }

    // 리뷰 목록
    @GetMapping("/reviews")
    public String reviews(HttpSession session, Model model) {
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        model.addAttribute("reviewList", reviewService.getAllReviews());
        return "admin/reviews";
    }

    // 리뷰 삭제
    @PostMapping("/reviews/delete/{reviewId}")
    public String reviewDelete(@PathVariable Long reviewId, HttpSession session){
        if(!isAdmin(session)){
            return "redirect:/auth/login";
        }
        reviewService.adminDelete(reviewId);
        return "redirect:/admin/reviews";
    }

}