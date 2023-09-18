package dev.projects.sspSoft.javaCore.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.projects.sspSoft.javaCore.spring.model.numberTransformation.NumberTransformRequestDTO;
import dev.projects.sspSoft.javaCore.spring.model.numberTransformation.NumberTransformResponseDTO;
import dev.projects.sspSoft.javaCore.spring.service.NumberTransformationService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class NumberTransformationControllerTests {
    @MockBean
    private NumberTransformationService numberTransformationService;

    private static final String TEST_NUMBER = "333";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void numberTransformationGetTest() throws Exception {
        Mockito.when(numberTransformationService.transformNumber(TEST_NUMBER)).thenReturn("триста тридцать три");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/numberTransform/" + TEST_NUMBER);

        mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void numberTransformationPostTest() throws Exception {
        Mockito.when(numberTransformationService.transformNumber(TEST_NUMBER)).thenReturn("триста тридцать три");

        NumberTransformRequestDTO requestDto = new NumberTransformRequestDTO(TEST_NUMBER);

        // Тут Object - DTO класс на request
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/numberTransform")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        // Где Object - DTO класс на response
        final NumberTransformResponseDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), NumberTransformResponseDTO.class);

        Assertions.assertNotNull(dto);
    }
}
