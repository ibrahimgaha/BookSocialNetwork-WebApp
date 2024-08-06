package com.gaha.book.entities.book.file;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import io.jsonwebtoken.io.IOException;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

	public static byte[] readFileFromLocation(String fileUrl) {

		if (StringUtils.isBlank(fileUrl)) {
			return null;
		}
		try {
			Path filePath = new File(fileUrl).toPath();
			return Files.readAllBytes(filePath);
		} catch (IOException e) {
			log.warn("No file found in the path {}", fileUrl);
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new byte[0];
	}
}
