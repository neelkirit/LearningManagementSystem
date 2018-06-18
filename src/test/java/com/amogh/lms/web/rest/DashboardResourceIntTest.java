package com.amogh.lms.web.rest;

import com.amogh.lms.AmoghServerApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the Dashboard REST controller.
 *
 * @see DashboardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmoghServerApp.class)
public class DashboardResourceIntTest {

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        DashboardResource dashboardResource = new DashboardResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(dashboardResource)
            .build();
    }

    /**
    * Test getUserRank
    */
    @Test
    public void testGetUserRank() throws Exception {
        restMockMvc.perform(get("/api/dashboard/get-user-rank"))
            .andExpect(status().isOk());
    }
    /**
    * Test getNumberOfUserQuestions
    */
    @Test
    public void testGetNumberOfUserQuestions() throws Exception {
        restMockMvc.perform(get("/api/dashboard/get-number-of-user-questions"))
            .andExpect(status().isOk());
    }
    /**
    * Test getNumberOfUserErrors
    */
    @Test
    public void testGetNumberOfUserErrors() throws Exception {
        restMockMvc.perform(get("/api/dashboard/get-number-of-user-errors"))
            .andExpect(status().isOk());
    }
    /**
    * Test getUserProgress
    */
    @Test
    public void testGetUserProgress() throws Exception {
        restMockMvc.perform(get("/api/dashboard/get-user-progress"))
            .andExpect(status().isOk());
    }
    /**
    * Test getAllUserDashboardStats
    */
    @Test
    public void testGetAllUserDashboardStats() throws Exception {
        restMockMvc.perform(get("/api/dashboard/get-all-user-dashboard-stats"))
            .andExpect(status().isOk());
    }
    /**
    * Test getNumberOfQuestionsAttemptedPerCourse
    */
    @Test
    public void testGetNumberOfQuestionsAttemptedPerCourse() throws Exception {
        restMockMvc.perform(get("/api/dashboard/get-number-of-questions-attempted-per-course"))
            .andExpect(status().isOk());
    }
    /**
    * Test getProgressByCourse
    */
    @Test
    public void testGetProgressByCourse() throws Exception {
        restMockMvc.perform(get("/api/dashboard/get-progress-by-course"))
            .andExpect(status().isOk());
    }

}
