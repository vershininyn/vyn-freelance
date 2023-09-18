package dev.projects.profsouz.opcuaclient.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"test"})
public class IndexControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getIndexPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/")
                        .contentType(MediaType.TEXT_HTML_VALUE)
                        .accept(MediaType.TEXT_HTML_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("index"))
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }
}
