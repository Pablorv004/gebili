package com.main.libridex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.main.libridex.entity.User;
import com.main.libridex.model.UserDTO;
import com.main.libridex.service.impl.UserServiceImpl;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/profile")
public class ProfileController {

    private static final String PROFILE_VIEW = "profile";
    private static final String PROFILE_EDIT_VIEW = "profile_edit";

    @Autowired
    @Qualifier("userService")
    private UserServiceImpl userService;

    @GetMapping("")
    public String getProfile(Model model, User user) {
        model.addAttribute("user", userService.findByEmail(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
        return PROFILE_VIEW;
    }

    @GetMapping("/edit")
    public String editProfile(Model model, User user) {
        model.addAttribute("user", userService.findByEmail(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
        return PROFILE_EDIT_VIEW;
    }
    
    @PostMapping("/apply")
    public String applyChanges(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, RedirectAttributes flash){

        if(!userService.isEditValid(userDTO, bindingResult)){
            return PROFILE_EDIT_VIEW;
        } else {
            userService.edit(userDTO);
            flash.addFlashAttribute("success", "Profile edited successfully!");
            return "redirect:/profile";
        }
    }
}
