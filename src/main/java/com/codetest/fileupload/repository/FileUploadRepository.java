package com.codetest.fileupload.repository;

import com.codetest.fileupload.model.LogEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends JpaRepository<LogEvent, Long> {

}
