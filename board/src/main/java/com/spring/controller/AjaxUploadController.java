package com.spring.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.domain.AttachFileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@Slf4j
@Controller
public class AjaxUploadController {
	
	@GetMapping("/uploadAjax")
	public void uploadAjaxGet() {
		log.info("uploadAjax ��û");
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/uploadAjax",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody	
	public ResponseEntity<List<AttachFileVO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		log.info("upload Ajax ��û");
		String uploadFolder = "d:\\upload";
		String uploadFileName = "";
		
		String uploadFolderPath = getFolder();
		File uploadPath = new File(uploadFolder,uploadFolderPath);
		
		if(!uploadPath.exists()) {
			uploadPath.mkdirs(); //��/��/�� ������ ���� ����
		}
		
		List<AttachFileVO> attachList = new ArrayList<AttachFileVO>();
		for(MultipartFile f:uploadFile) {
			log.info("-------------------");
			log.info("upload File Name : "+f.getOriginalFilename());
			log.info("upload File Size : "+f.getSize());
			
			//������ �ߺ��� �����ϱ� ���� ������ ����
			UUID uuid = UUID.randomUUID();			
			uploadFileName = uuid.toString()+"_"+f.getOriginalFilename();
			
			AttachFileVO attach = new AttachFileVO();
			attach.setFileName(f.getOriginalFilename());
			attach.setUploadPath(uploadFolderPath);
			attach.setUuid(uuid.toString());
			
			try {
				Path saveFile = Paths.get(uploadPath.getPath(), uploadFileName);
				
				//�̹��� ���� ���� Ȯ��
				if(checkImageType(saveFile.toFile())) {
					attach.setFileType(true);
					
					//����� �۾�
					FileOutputStream thumbnail = 
							new FileOutputStream(new File(uploadPath,"s_"+uploadFileName));
					Thumbnailator.createThumbnail(f.getInputStream(), thumbnail, 100,100);
					thumbnail.close();
				}
				f.transferTo(saveFile);
				attachList.add(attach);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}				
		}
		//OK�����ڵ�� + uuid�� ���� ���ϸ� ����
		return new ResponseEntity<List<AttachFileVO>>(attachList,HttpStatus.OK);
	}
	
	//����� �̹����� �����ϴ� ��Ʈ�ѷ�
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("����� ��û "+fileName);
		File f = new File("d:\\upload\\"+fileName);
		
		ResponseEntity<byte[]> result = null;
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			headers.add("Content-Type", Files.probeContentType(f.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f),
					headers,HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) {
		log.info("÷������ ���� fileName : "+fileName+" type : "+type);
		try {
			File file = new File("d:\\upload\\"+URLDecoder.decode(fileName,"utf-8"));
			
			//����� Ȥ�� �Ϲ� ���� ����
			file.delete();
			
			if(type.equals("image")) {
				String oriPath = file.getAbsolutePath().replace("s_","");
				file = new File(oriPath);
				//�̹��� ���� ���� ����
				file.delete();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("deleted",HttpStatus.OK);
		
	}
	//�ٿ�ε� ��Ʈ�ѷ�
		@GetMapping(value="/download",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
		@ResponseBody
		public ResponseEntity<Resource> downloadFile(String fileName) {
			log.info("�ٿ�ε� ���� : "+fileName);
			
			Resource resource = new FileSystemResource("d:\\upload\\"+fileName);
			
			String resourceName = resource.getFilename();
			
			//������ ����� �ٿ� ������
			HttpHeaders headers = new HttpHeaders();
			try {
				//uuid���� �پ �ٿ�ε尡 �Ǵ� ��Ȳ			
				/*
				 * headers.add("Content-Disposition", "attachment;fileName=" +new
				 * String(resourceName.getBytes("utf-8"),"ISO-8859-1"));
				 */		
				String resouceUidName = resource.getFilename();			
				resourceName = resouceUidName.substring(resouceUidName.indexOf("_")+1);	
				headers.add("Content-Disposition", "attachment;fileName="
						+new String(resourceName.getBytes("utf-8"),"ISO-8859-1"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
		}
	
	private boolean checkImageType(File file) {
		String contentType;
		try {
			contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	//��¥�� ���� ����
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}

}
