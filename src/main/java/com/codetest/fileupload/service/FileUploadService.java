package com.codetest.fileupload.service;

import com.codetest.fileupload.model.FileEvent;
import com.codetest.fileupload.model.LogEvent;
import com.codetest.fileupload.repository.FileUploadRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadService {

    public static final String STARTED = "STARTED";
    public static final String FINISHED = "FINISHED";

    private final FileUploadRepository fileUploadRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    public void save(MultipartFile multipartFile) throws Exception {

        try (InputStream inputStream = multipartFile.getInputStream()) {
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .parallel()
                    .map(this::handleLine)
                    .collect(Collectors.groupingBy(FileEvent::getId))
                    .values()
                    .stream()
                    .map(value -> {
                        FileEvent startState = value
                                .stream()
                                .filter(fileObject -> STARTED.equals(fileObject.getState()))
                                .findFirst().orElse(null);
                        FileEvent endState = value
                                .stream()
                                .filter(fileObject -> FINISHED.equals(fileObject.getState()))
                                .findFirst().orElse(null);
                        if (!ObjectUtils.isEmpty(startState) && !ObjectUtils.isEmpty(endState)) {
                            long eventDuration = endState.getTimestamp() - startState.getTimestamp();
                            LogEvent logEvent = new LogEvent();
                            logEvent.setId(startState.getId());
                            logEvent.setHost(startState.getHost());
                            logEvent.setType(startState.getType());
                            logEvent.setEventDuration(eventDuration);
                            logEvent.setAlert(eventDuration >= 4);
                            return logEvent;
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList()).forEach(fileUploadRepository::save);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private FileEvent handleLine(String textFile) {
        FileEvent fileObject;
        try {
            fileObject = mapper.readValue(textFile, FileEvent.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e);
        }
        return fileObject;
    }

    public List<LogEvent> getLogEvent() {
        return fileUploadRepository.findAll();
    }

}
