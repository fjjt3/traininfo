package com.traininfo.interfaces.rest;

import com.traininfo.domain.port.DatabaseHealthPort;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class BaselineHealthControllerTest {

    private final DatabaseHealthPort databaseHealthPort = mock(DatabaseHealthPort.class);

    @Test
    void returnsUpWhenDatabaseIsReachable() throws Exception {
        when(databaseHealthPort.isUp()).thenReturn(true);

        mockMvc().perform(get("/api/v1/health")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.database").value("UP"));
    }

    @Test
    void returnsDownWhenDatabaseIsUnreachable() throws Exception {
        when(databaseHealthPort.isUp()).thenReturn(false);

        mockMvc().perform(get("/api/v1/health")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DOWN"))
                .andExpect(jsonPath("$.database").value("DOWN"));
    }

    private MockMvc mockMvc() {
        return standaloneSetup(new BaselineHealthController(databaseHealthPort)).build();
    }
}