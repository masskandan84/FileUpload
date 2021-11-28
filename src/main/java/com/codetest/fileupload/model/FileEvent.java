package com.codetest.fileupload.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FileEvent {

  @JsonProperty("Id")
  private String id;
  private String state;
  private String type;
  private String host;
  private Long timestamp;
}
