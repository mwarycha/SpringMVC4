package com.twitter.TwitterEduApp.controllers;

import com.twitter.TwitterEduApp.model.ProfileForm;
import com.twitter.TwitterEduApp.profile.UserProfileSession;
import com.twitter.TwitterEduApp.utils.USLocalDateFormatter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ProfileController {

    @Autowired
    public ProfileController(UserProfileSession userProfileSession) {
        this.userProfileSession = userProfileSession;
    }

    private UserProfileSession userProfileSession;

    @RequestMapping(value = "/profile")
    public String displayProfile(ProfileForm profileForm) {
        return "profile/profilePage";
    }

    @RequestMapping(value = "/profile", method=RequestMethod.POST)
    public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "profile/profilePage";
        }

        // save profile in session
        userProfileSession.saveForm(profileForm);

        return "redirect:/search/mixed;keywords=" + String.join(",", profileForm.getTastes());
    }

    @ModelAttribute("dateFormat")
    public String localeFormat(Locale locale) {
        return USLocalDateFormatter.getPattern(locale);
    }

    @ModelAttribute
    public ProfileForm getProfileForm() {
        return userProfileSession.toForm();
    }

    @RequestMapping(value = "/profile", params = {"addTaste"})
    public String addRow(ProfileForm profileForm) {
        profileForm.getTastes().add(null);
        return "profile/profilePage";
    }

    @RequestMapping(value = "/profile",  params = {"removeTaste"})
    public String removeRow(ProfileForm profileForm, HttpServletRequest req) {
        Integer rowId = Integer.valueOf(req.getParameter("removeTaste"));
        profileForm.getTastes().remove(rowId.intValue());
        return "profile/profilePage";
    }
}

