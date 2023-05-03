package com.fintech.loanservice;

import com.fintech.loanservice.controller.TariffController;
import com.fintech.loanservice.service.TariffService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TariffController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TariffControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    TariffService tariffService;

    @WithMockUser(value = "user")
    @Test
    public void getTariffsTest() throws Exception {
        ResultActions response = mockMvc.perform(get("/loan-service/getTariffs"));

        response.andDo(print())
                .andExpect(status().isOk());
    }

}
