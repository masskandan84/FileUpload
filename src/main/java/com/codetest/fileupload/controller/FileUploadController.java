package com.codetest.fileupload.controller;

import com.codetest.fileupload.model.LogEvent;
import com.codetest.fileupload.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;
import java.util.List;


@RestController
@RequestMapping(value = "/api/rest/v1/file")
@RequiredArgsConstructor
@Slf4j
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping(value = "upload", produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile uploadfile) throws Exception {

        if (uploadfile.isEmpty()) {
            throw new FileNotFoundException("File is empty");
        }
        log.info("file name {}", uploadfile.getOriginalFilename());
        fileUploadService.save(uploadfile);
        return "File uploaded successfully";
    }

    @GetMapping()
    public List<LogEvent> getLogEvent() {
        List<LogEvent> eventList = fileUploadService.getLogEvent();
        if (eventList.size() <= 0) {
            log.info("No data fount");
        }
        return eventList;
    }
}
