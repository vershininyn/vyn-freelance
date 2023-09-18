package dev.projects.profsouz.opcuaclient.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.projects.profsouz.opcuaclient.domain.request.XmlFilepathRequestDTO;
import dev.projects.profsouz.opcuaclient.service.OpcUaFileSystemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringJoiner;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"test"})
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class OpcUaFileSystemControllerUnitTests {
    private final static String templateFilename = "template.xml", temporalXmlFileDirectory = System.getProperty("user.home");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OpcUaFileSystemService fsService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws IOException {
        Files.deleteIfExists(Path.of(joinTemporalDirectoryAndXmlFilename(templateFilename)));
    }

    @ParameterizedTest
    @ValueSource(strings = templateFilename)
    @SqlGroup({
            @Sql(value = "classpath:empty-h2.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void createXmlFileTemplate_andUseCorrectData_andCheckItIsSuccessfulHandled(String xmlTemplateFilename) throws Exception {
        String xmlFilepath = joinTemporalDirectoryAndXmlFilename(xmlTemplateFilename);

        XmlFilepathRequestDTO requestDTO = new XmlFilepathRequestDTO(xmlFilepath);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/opc-ua-fs-api/createXmlFile")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO));

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$", aMapWithSize(4)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.xmlFilename", is(xmlTemplateFilename)))
                .andExpect(jsonPath("$.xmlFilepath", is(xmlFilepath)))
                .andExpect(jsonPath("$.isExists", is(true)));
    }

    @ParameterizedTest
    @ValueSource(strings = "  ")
    @SqlGroup({
            @Sql(value = "classpath:empty-h2.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void createXmlFileTemplate_andUseIncorrectData_andCheckItIsSuccessfulHandled(String xmlFilename) throws Exception {
        String xmlFilepath = joinTemporalDirectoryAndXmlFilename(xmlFilename);

        XmlFilepathRequestDTO requestDTO = new XmlFilepathRequestDTO(xmlFilepath);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put ("/opc-ua-fs-api/createXmlFile")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO));

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(jsonPath("$.message", isA(String.class)))
                .andExpect(jsonPath("$.timestamp", isA(Long.class)));
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    @SqlGroup({
            @Sql(value = "classpath:empty-h2.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:data-h2.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void deleteXmlFileTemplate_andUseCorrectData_andCheckItIsSuccessfulHandled(Long existingId) throws Exception {
        String xmlFilepath = joinTemporalDirectoryAndXmlFilename(templateFilename);

        Files.createFile(Path.of(xmlFilepath));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/opc-ua-fs-api/deleteXmlFile/" + existingId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$", aMapWithSize(4)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.xmlFilename", is(templateFilename)))
                .andExpect(jsonPath("$.xmlFilepath", is(xmlFilepath)))
                .andExpect(jsonPath("$.isExists", is(false)));
    }

    @ParameterizedTest
    @ValueSource(longs = 2L)
    @SqlGroup({
            @Sql(value = "classpath:empty-h2.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:data-h2.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void deleteXmlFileTemplate_andUseIncorrectData_andCheckItIsSuccessfulHandled(Long notExistingId) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/opc-ua-fs-api/deleteXmlFile/" + notExistingId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.statusCode", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(jsonPath("$.message", isA(String.class)))
                .andExpect(jsonPath("$.timestamp", isA(Long.class)));
    }

    private static String joinTemporalDirectoryAndXmlFilename(String templateXmlFilename) {
        String fileSeparator = System.getProperty("file.separator");

        return (new StringJoiner(fileSeparator, "", ""))
                .add(temporalXmlFileDirectory)
                .add(templateXmlFilename)
                .toString();
    }
}
