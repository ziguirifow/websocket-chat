package br.unip.websocketchat.controller;

import br.unip.websocketchat.model.UploadFileResponse;
import br.unip.websocketchat.service.DBFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {

  @Autowired
  private DBFileStorageService dbFileStorageService;

  @PostMapping("/uploadFile")
  public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
    var dbFile = dbFileStorageService.storeFile(file);

    var fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(dbFile.getId()).toUriString();

    return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
  }

  @PostMapping("/uploadMultipleFiles")
  public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
    return Arrays.stream(files).map(this::uploadFile).collect(Collectors.toList());
  }

  @GetMapping("/downloadFile/{fileId}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
    var dbFile = dbFileStorageService.getFile(fileId);

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(dbFile.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
        .body(new ByteArrayResource(dbFile.getData()));
  }

}