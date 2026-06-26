package com.example.beerarchive.controller;

import com.example.beerarchive.service.BeerLikeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.beerarchive.dto.AccountDTO;
import com.example.beerarchive.dto.BeerDTO;
import com.example.beerarchive.service.BeerService;
import com.example.beerarchive.service.BreweryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/beer")
public class BeerController {

    private final BeerLikeService beerLikeService;
    private final BeerService beerService;
    private final BreweryService breweryService;

    // BeerController(BeerLikeService beerLikeService) {
    //     this.beerLikeService = beerLikeService;
    // }

    // 맥주 목록 검색
    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword,
            @RequestParam(required = false) String beerStyle,
            Model model) {
        model.addAttribute("beerList", beerService.getBeerList(keyword, beerStyle));
        model.addAttribute("keyword", keyword);
        model.addAttribute("style", beerStyle);
        return "beer/list";
    }

    // 맥주 상세
    @GetMapping("/detail/{beerId}")
    public String detail(@PathVariable Long beerId, HttpSession session, Model model) {
        AccountDTO loginUser = (AccountDTO) session.getAttribute("loginUser");
        Long accountId = loginUser != null ? loginUser.getAccountId() : null;

        model.addAttribute("beer", beerService.getBeer(beerId));
        model.addAttribute("likeInfo", beerLikeService.getLikeInfo(beerId, accountId));
        return "beer/detail";
    }

    // 맥주 등록 폼
    @GetMapping("/register")
    public String registerForm(HttpSession session, Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("breweryList", breweryService.getAllBerweries());
        return "beer/register";
    }

    // 맥주 등록 처리
    @PostMapping("/register")
    public String register(@RequestParam String beerName,
            @RequestParam String beerStyle,
            @RequestParam double beerAbv,
            @RequestParam int beerIbu,
            @RequestParam Long breweryId,
            Model model,
            HttpSession session) {
        if (session.getAttribute("beerName") == null) {
            return "redirect:/auth/login";
        }
        try {
            BeerDTO dto = new BeerDTO();
            dto.setBeerName(beerName);
            dto.setBeerStyle(beerStyle);
            dto.setBeerAbv(beerAbv);
            dto.setBeerIbu(beerIbu);
            dto.setBreweryId(breweryId);
            beerService.register(dto);
            return "redirect:/beer/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("breweryList", breweryService.getAllBerweries());
            return "beer/register";
        }
    }

    // 맥주 수정 폼
    @GetMapping("/edit/{beerId}")
    public String editForm(@PathVariable Long beerId,
            Model model,
            HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("beer", beerService.getBeer(beerId));
        return "beer/edit";
    }

    // 맥주 수정 처리
    @PostMapping("/edit/{beerId}")
    public String edit(@PathVariable Long beerId,
            @RequestParam String beerName,
            @RequestParam String beerStyle,
            @RequestParam double beerAbv,
            @RequestParam int beerIbu,
            HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/auth/login";
        }
        BeerDTO dto = new BeerDTO();
        dto.setBeerName(beerName);
        dto.setBeerStyle(beerStyle);
        dto.setBeerAbv(beerAbv);
        dto.setBeerIbu(beerIbu);
        beerService.update(beerId, dto);
        return "redirect:/beer/detail/" + beerId;
    }

    // 맥주 삭제
    @PostMapping("/delete/{beerId}")
    public String delete(@PathVariable Long beerId, HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/auth/login";
        }
        beerService.delete(beerId);
        return "redirect:/beer/list";
    }

}
