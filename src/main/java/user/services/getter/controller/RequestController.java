package user.services.getter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import user.services.getter.model.Request;
import user.services.getter.services.RequestService;

@Controller
@RequestMapping(value = {"/", "/request"})
public class RequestController {

    @Autowired(required = true)
    @Qualifier(value = "requestService")
    private RequestService requestService;

    @RequestMapping(value = {"/list", ""}, method = RequestMethod.GET)
    public String listRequests(Model model){
        model.addAttribute("request", new Request());
        model.addAttribute("listRequests", this.requestService.getAllRequests());
        return "requests_page";
    }
}
