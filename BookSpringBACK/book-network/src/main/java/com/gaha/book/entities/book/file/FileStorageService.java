package com.gaha.book.entities.book.file;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.io.IOException;
import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {
	@Value("${application.file.uploads.photos-output-path}")
	private String fileUploadPath;

	public String saveFile(@NonNull MultipartFile sourceFile, @NonNull Integer userId) {
		final String fileUploadSubPath = "users" + File.separator + userId;
		return uploadFile(sourceFile, fileUploadSubPath);
	}

	private String uploadFile(@NonNull MultipartFile sourceFile, @NonNull String fileUploadSubPath) {
		final String finalUploadPath = fileUploadPath + File.separator + fileUploadSubPath;
		File targetFolder = new File(finalUploadPath);
		if (!targetFolder.exists()) {
			boolean folderCreated = targetFolder.mkdirs();
			if (!folderCreated) {
				log.warn("Failed to create the target folder.");
				return null;
			}
		}
		final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
		// .uploads/users/1/6345665468465.jpg
		String targetFilePath = finalUploadPath + File.separator + System.currentTimeMillis() + "." + fileExtension;
		Path targetPath = java.nio.file.Paths.get(targetFilePath);
		try {
			Files.write(targetPath, sourceFile.getBytes());
			log.info("File saved to the target location.");
			return targetFilePath;
		} catch (IOException e) {
			log.error("File was not saved!", e);
			return null;
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String getFileExtension(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			return "";
		}
		int lastDoIndex = fileName.lastIndexOf(".");
		if (lastDoIndex == -1) {
			return "";
		}
		return fileName.substring(lastDoIndex + 1).toLowerCase();
	}

}
