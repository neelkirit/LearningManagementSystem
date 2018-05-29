package com.amogh.lms.web.rest;

import com.amogh.lms.service.DashboardService;
import com.amogh.lms.service.dto.UserStatsDTO;
import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Anvitha G K Bhat on 13/5/18.
 */

@RestController
@RequestMapping("/api/users/dashboard")
public class DashboardController
{
    DashboardService dashboardService;

    @Autowired
    DashboardController(DashboardService dashboardService)
    {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @Timed
    public UserStatsDTO getUserStats()
    {
        return dashboardService.getUserStats();
    }
}
