package com.codetest.fileupload;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class FileUploadControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void test_handleFileUpload() throws Exception{

        MockMultipartFile file1 = new MockMultipartFile("file", "sample.txt", MediaType.MULTIPART_FORM_DATA_VALUE,
                this.getClass().getResourceAsStream("/sample.txt"));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/rest/v1/file/upload").file(file1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_handleFileEmpty() throws Exception{

        MockMultipartFile file1 = new MockMultipartFile("file", "empty.txt", MediaType.MULTIPART_FORM_DATA_VALUE,
                this.getClass().getResourceAsStream("/empty.txt"));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/rest/v1/file/upload").file(file1))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void test_handleGetLogEvent() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rest/v1/file"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_handleJsonProcessingException() throws Exception{

        MockMultipartFile file1 = new MockMultipartFile("file", "invalid-file.txt", MediaType.MULTIPART_FORM_DATA_VALUE,
                this.getClass().getResourceAsStream("/invalid-file.txt"));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/rest/v1/file/upload").file(file1))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}
