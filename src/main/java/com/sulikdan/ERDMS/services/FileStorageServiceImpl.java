package com.sulikdan.ERDMS.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * Created by Daniel Šulik on 02-Jul-20
 * @see FileStorageService
 */
@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

  private static final String STORAGE_FOLDER_NAME = "fileStorage";
  private static final String PDF_SUBFOLDER_NAME = "pdf";
  private static final Path BASE_PATH = Paths.get(STORAGE_FOLDER_NAME);
  private static final Path PDF_BASE_PATH =
      Paths.get(STORAGE_FOLDER_NAME + "/" + PDF_SUBFOLDER_NAME);

  @Override
  public void init() {
    try {
      Files.createDirectory(BASE_PATH);
      Files.createDirectory(PDF_BASE_PATH);
    } catch (IOException e) {
      throw new RuntimeException(
          MessageFormat.format(
              "Wasn't able to initialize folder-{} or {} for file uploading!",
              BASE_PATH,
              PDF_BASE_PATH));
    }
  }

  @Override
  public Path saveFile(MultipartFile file, String filePrefixName) {
    try {
      Files.copy(
          file.getInputStream(),
          com.sulikdan.ERDMS.services.FileStorageServiceImpl.BASE_PATH.resolve(
              filePrefixName + "_" + Objects.requireNonNull(file.getOriginalFilename())));
      return com.sulikdan.ERDMS.services.FileStorageServiceImpl.BASE_PATH.resolve(
          filePrefixName + "_" + Objects.requireNonNull(file.getOriginalFilename()));
    } catch (Exception e) {
      throw new RuntimeException(
          MessageFormat.format(
              "Wasn't able to store the file.\nReceived error: {}", e.getMessage()));
    }
  }

  @Override
  public Path saveFile(MultipartFile file, String filePrefixName, String subFolderName) {
    Path extendedBasePath =
        Paths.get(
            com.sulikdan.ERDMS.services.FileStorageServiceImpl.BASE_PATH.toString()
                + "/"
                + subFolderName);
    try {
      Files.copy(
          file.getInputStream(),
          extendedBasePath.resolve(
              filePrefixName + "_" + Objects.requireNonNull(file.getOriginalFilename())));
      return extendedBasePath.resolve(
          filePrefixName + "_" + Objects.requireNonNull(file.getOriginalFilename()));
    } catch (Exception e) {
      throw new RuntimeException(
          MessageFormat.format(
              "Wasn't able to store the file.\nReceived error: {}", e.getMessage()));
    }
  }

  @Override
  public Path saveTmpFile(BufferedImage bufferedImage, int pageNum, String filePrefixName) {

    try {
      File temp = File.createTempFile(filePrefixName + pageNum, ".png");
      ImageIO.write(bufferedImage, "png", temp);
      return temp.toPath();
    } catch (IOException e) {
      // TODO IOException
      e.printStackTrace();
      throw new RuntimeException("Error with saving bufferedImage to tempFile!");
    }
  }

  @Override
  public void deleteFile(Path file) {
    try {
      Files.deleteIfExists(file);
    } catch (IOException e) {
      file.toFile().deleteOnExit();
      System.err.println("Problem with deleting file: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public boolean deleteTmpFile(Path file) {
    return file.toFile().delete();
  }

  @Override
  public Resource loadFile(String filename) {
    try {
      Path file = BASE_PATH.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException(
            MessageFormat.format("Wasn't able to read the file with name {}!", filename));
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAllFiles() {
    FileSystemUtils.deleteRecursively(BASE_PATH.toFile());
  }

  //  @Override
  //  public Stream<Path> loadAllFiles() {
  //    try {
  //      return Files.walk(com.sulikdan.ocrApi.services.FileStorageServiceImpl.BASE_PATH, 1)
  //          .filter(path ->
  // !path.equals(com.sulikdan.ocrApi.services.FileStorageServiceImpl.BASE_PATH))
  //          .map(com.sulikdan.ocrApi.services.FileStorageServiceImpl.BASE_PATH::relativize);
  //    } catch (IOException e) {
  //      throw new RuntimeException("Could not load all files!");
  //    }
  //  }
}
