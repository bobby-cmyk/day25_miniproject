package vttp.batchb.paf.day25.mini.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vttp.batchb.paf.day25.mini.project.services.MainService;

@Controller
@RequestMapping
public class AskController {

    @Autowired
    private MainService mainSvc;
    
    @GetMapping
    public ModelAndView getAskPage() {

        ModelAndView mav = new ModelAndView();

        
        mav.setViewName("ask-page");
        return mav;
    } 

    @PostMapping("/ask")
    public ModelAndView sendQuery(
        @RequestParam("query") String query
    ) {

        ModelAndView mav = new ModelAndView();

        String response = mainSvc.getResultsStr(query);

        mav.addObject("response", response);
        mav.setViewName("response");

        return mav;
    }
}
