package com.mike.CoronavirusTracker.controllers;

import com.mike.CoronavirusTracker.models.VirusData;
import com.mike.CoronavirusTracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String getHome(Model model) {
        List<VirusData> allStats = coronaVirusDataService.getData();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getNumOfInfected()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "home";
    }
}
