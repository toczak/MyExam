package pl.potoczak.myexam.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.potoczak.myexam.configuration.CustomSimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(Model model){
        model.addAttribute("pageTitle","Login page | MyExam");
        return "login";
    }

    @GetMapping("/redirect")
    public String showUserMainPage(){
        String url = new CustomSimpleUrlAuthenticationSuccessHandler()
                .determineTargetUrl(SecurityContextHolder.getContext().getAuthentication());
        return "redirect:" + url;
    }
}
