package com.aiden.board.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aiden.board.dto.MenuImage.MenuImageDto;

import com.aiden.board.exception.CustomException;
import com.aiden.board.exception.ErrorCode;
import com.aiden.board.service.MenuImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuImageController {

	private final MenuImageService menuImageService;
	private static final String UPLOAD_DIR = "menuImages/";

	@PostMapping("/menuImage/{fileName}")
	public ResponseEntity<Void> saveMenuImageFile(MultipartFile imageFile, @PathVariable("fileName") String fileName) {

		if (imageFile != null && !imageFile.isEmpty() && fileName != null && !fileName.isEmpty()) {
			try {
				// 업로드할 디렉토리 생성
				File uploadDir = new File(UPLOAD_DIR);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}

				// 파일 확장
				String fileExtension = '.' + imageFile.getOriginalFilename().replaceAll("^.*\\.(.*)$", "$1");

				// 파일 저장 경로 설정
				String filePath = uploadDir.getAbsolutePath() + "/" + fileName + fileExtension;

				// 이미지 파일 저장
				imageFile.transferTo(new File(filePath));

				MenuImageDto imageDto = MenuImageDto.builder()
						.originName(imageFile.getOriginalFilename())
						.storedName(fileName)
						.imagePath(filePath)
						.build();

				menuImageService.saveMenuImageDto(imageDto);
			} catch (IOException e) {
				e.printStackTrace();
				throw new CustomException(ErrorCode.UNKNOWN_ERROR);
			}
		}

		return ResponseEntity.ok().build();
	}

	@GetMapping("menuImage/{fileName}")
    public ResponseEntity<String> getMenuImage(@PathVariable("fileName") String fileName) {
                
        MenuImageDto imageDto = menuImageService.selectMenuImageDto(fileName);
        
        if (imageDto == null) {
        	return ResponseEntity.notFound().build();
        }
        

        Path imagePath = Paths.get(imageDto.getImagePath()).normalize();
                
        try {
           
        	File imageFile = new File(imagePath.toUri());
        	
        	byte[] fileContent = FileCopyUtils.copyToByteArray(imageFile);
        	String encodedString = Base64.getEncoder().encodeToString(fileContent);
        	
        	        	
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION)
                    .body(encodedString);
            
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
		}
    }
}
