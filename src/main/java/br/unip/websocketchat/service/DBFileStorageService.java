package br.unip.websocketchat.service;

import br.unip.websocketchat.exception.FileStorageException;
import br.unip.websocketchat.exception.MyFileNotFoundException;
import br.unip.websocketchat.model.DBFile;
import br.unip.websocketchat.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

@Service
public class DBFileStorageService {

  @Autowired
  private DBFileRepository dbFileRepository;

  public DBFile storeFile(MultipartFile file) {
    var fileName = StringUtils.cleanPath(requireNonNull(file.getOriginalFilename()));

    try {
      if (fileName.contains("..")) {
        throw new FileStorageException("Desculpe! O nome do arquivo contém uma sequência de caminho inválida " + fileName);
      }

      var dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

      return dbFileRepository.save(dbFile);
    } catch (IOException ex) {
      throw new FileStorageException("Não foi possível armazenar o arquivo " + fileName + ". Por favor, tente novamente!", ex);
    }
  }

  public DBFile getFile(String fileId) {
    return dbFileRepository.findById(fileId)
        .orElseThrow(() -> new MyFileNotFoundException("Arquivo não encontrado com id " + fileId));
  }
}
