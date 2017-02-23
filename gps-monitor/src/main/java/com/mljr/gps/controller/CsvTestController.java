package com.mljr.gps.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by songchi on 17/1/19.
 */
@RestController
public class CsvTestController {
  @RequestMapping(value = "/media", method = RequestMethod.GET)
  public ResponseEntity<InputStreamResource> downloadFile() throws IOException {
    FileSystemResource file = new FileSystemResource("/Users/songchi/get_page.js");
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");

    return ResponseEntity.ok().headers(headers).contentLength(file.contentLength()).contentType(MediaType.parseMediaType("application/octet-stream"))
        .body(new InputStreamResource(file.getInputStream()));
  }
}
