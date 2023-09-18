package dev.projects.sspsoft.springsecurity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.projects.sspSoft.springSecurity.Main;
import dev.projects.sspSoft.springSecurity.domain.JwtRequest;
import dev.projects.sspSoft.springSecurity.domain.TokenPair;
import dev.projects.sspSoft.springSecurity.service.AccountDetailsService;
import dev.projects.sspSoft.springSecurity.util.JwtTokenUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class JwtServiceTests {
    @MockBean
    private AccountDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final JwtRequest request = new JwtRequest("javainuse","password0");

    private String JWT_AUTH_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJ0eXAiOiJCZWFyZXIiLCJzdWIiOiJqYXZhaW51c2UiLCJpYXQiOjE2NTcwMTA4OTcsImV4cCI6MTY4ODU0Njg5N30.tRjqkjNV7KqE1kynAoLli8K9LUsd2HYjRQJGT6iKjU8vAiqkFgsY3asGpHOC5XJyhKjJ6k17DhXayKtYFX49VQ";

    private String JWT_REFRESH_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJ0eXAiOiJSZWZyZXNoIiwic3ViIjoiamF2YWludXNlIiwiaWF0IjoxNjU3MDEwODk3LCJleHAiOjE2ODg1NDY4OTd9.F4GnkFRgQRcchAVWReSFkER8OI1obJZEGKEffju63DDaaWOAH9gtgqtEwR0MN1xKWrU8aJHdokGMPJ0XGo6xSg";

    @Test
    public void authenticationPostTest() throws Exception {
        TokenPair dto = new TokenPair(Map.of(JWT_AUTH_TOKEN, jwtTokenUtil.getAllClaimsFromToken(JWT_AUTH_TOKEN)),
                Map.of(JWT_REFRESH_TOKEN, jwtTokenUtil.getAllClaimsFromToken(JWT_REFRESH_TOKEN)));

        Mockito.when(userDetailsService.generateTokenPair(request.getUsername())).thenReturn(dto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/authenticate")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
