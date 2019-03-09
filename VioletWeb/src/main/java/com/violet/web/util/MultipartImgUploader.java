package com.violet.web.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

public class MultipartImgUploader {

	private static Logger log = LoggerFactory.getLogger(MultipartImgUploader.class);
	
	/**
	 * Application.properties Value Getter
	 */
	@Autowired
	Environment environment;
	
	/**
	 * Image Upload util
	 * @param imageUpload
	 * @return destinationFileName
	 */
	public String imgUploader(MultipartFile imageUpload) {
		
		String orgFileName = imageUpload.getOriginalFilename();
		String orgFileNameExt = FilenameUtils.getExtension(orgFileName).toLowerCase();
		
		File destinationFile = null;
		String destinationFileName = "";
		// Application.properties imgfileupload.path
		String fileUploadPath = environment.getProperty("imgfileupload.path")+"profile"+File.separator;
		
		try {
			if (!orgFileName.isEmpty()) {
				destinationFileName = RandomStringUtils.randomAlphanumeric(12) + "." + orgFileNameExt; 
	            destinationFile = new File(fileUploadPath + destinationFileName);
	            
	            destinationFile.getParentFile().mkdirs(); 
				imageUpload.transferTo(destinationFile);
			}
			
		} catch(IOException ioe) {
			log.error("Image Upload Error : " + ioe);
			ioe.printStackTrace();
			return "Upload-Error";
		
		} finally {
			orgFileName = null;
			orgFileNameExt = null;
			destinationFile = null;
			destinationFileName = null;
			
		}
		
		return fileUploadPath + destinationFileName;
	}
}
