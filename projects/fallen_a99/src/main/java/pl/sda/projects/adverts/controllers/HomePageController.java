package pl.sda.projects.adverts.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import pl.sda.projects.adverts.model.domain.Advert;
import pl.sda.projects.adverts.model.domain.User;
import pl.sda.projects.adverts.model.repository.AdvertRepository;
import pl.sda.projects.adverts.model.repository.UserRepository;

import java.time.LocalDateTime;


@Controller @Slf4j @RequiredArgsConstructor
@RequestMapping("/")
public class HomePageController {

    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;

    @GetMapping
    public String prepareHomePage(Model model) {
        model.addAttribute("advertToAdd", new Advert());
        model.addAttribute("adverts", advertRepository.findAllByOrderByPostedDesc());
        return "home";
    }

    @PostMapping("/add-adverts")
    public String processAddAdvert(@RequestParam String title,
                                   @RequestParam String description) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.getByUsername(username);
        Advert advertToSave = Advert.builder()
                .title(title)
                .description(description)
                .posted(LocalDateTime.now())
                .user(currentUser)
                .build();
        log.debug("Advert to save: {}", advertToSave);
        advertRepository.save(advertToSave);
        log.debug("Saved advert: {}", advertToSave);
        return "redirect:/";
    }
}
