package user.services.getter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import user.services.getter.model.Request;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    CsrfTokenRepository csrfTokenRepository;

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String listRequests(Model model){
        model.addAttribute("request", new Request());
        return "requests_page";
    }

    @RequestMapping(value = {"", "/login"}, method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

}
