package com.codetest.fileupload.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="log_event")
public class LogEvent {

  @Id
  @Column(name = "event_id")
  private String id;
  @Column(name = "type")
  private String type;
  @Column(name = "host")
  private String host;
  @Column(name = "event_duration")
  private Long eventDuration;
  @Column(name = "alert")
  private Boolean alert;

}
