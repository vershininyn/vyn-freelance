package dev.projects.sspSoft.javaCore.spring;

import dev.projects.sspSoft.javaCore.spring.service.FileUploadService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FileUploadControllerTests {
    @MockBean
    private FileUploadService uploadService;

    @Autowired
    private MockMvc mockMvc;

    private static final String fileName = "/home/dev/projects/java/ssp-soft/file.dat";

    private MockMultipartFile file = new MockMultipartFile("file",
            fileName,
            MediaType.APPLICATION_JSON_VALUE,
            new byte[]{0, 0, 0});

    @Test
    public void fileUploadControllerPostTest() throws Exception {
        Mockito.when(uploadService.uploadFile(file)).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/uploadFile")
                .file(file)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(Charset.forName("UTF-8"));

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
