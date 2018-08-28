package user.services.getter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import user.services.getter.model.Report;
import user.services.getter.model.Request;
import user.services.getter.model.RequestStatus;
import user.services.getter.services.AbonentInfoService;
import user.services.getter.services.ReportService;
import user.services.getter.services.RequestService;

import java.util.*;

@Controller
@RequestMapping(value = {"/request", "/"})

public class RequestController {

    @Autowired(required = true)
    @Qualifier(value = "requestService")
    private RequestService requestService;

    @Autowired(required = true)
    private ReportService reportService;

    @RequestMapping(value = {"/list", ""}, method = RequestMethod.GET)
    public String listRequests(Model model){
        model.addAttribute("request", new Request());

        ArrayList<Request> rs = new ArrayList<Request>(requestService.getAllRequests());
        Collections.sort(rs);
        model.addAttribute("listRequests", rs);
        return "requests_page";
    }

    @RequestMapping(value = "/report/{id}", method = RequestMethod.GET)
    public String report(@PathVariable("id") Integer id, Model model){
        Collection<Report> reports = reportService.getReports(id);
        model.addAttribute("listReports", reports);
        return "reports_page";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editReqest(@ModelAttribute("request") Request request) {
        request.setStatus(RequestStatus.PLANNED);
        requestService.save(request);
        return "redirect:/request";
    }
}
