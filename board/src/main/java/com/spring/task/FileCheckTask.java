package com.spring.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.spring.domain.AttachFileVO;
import com.spring.mapper.AttachMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileCheckTask {
	
	@Autowired
	private AttachMapper attach;
	
	private String getYesterDayFolder() {
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator); // 2020/07/21 ����
	}
	
	@Scheduled(cron="* * 2 * * *")
	public void checkFiles() {
		log.warn("���� üũ �����층 ����....");
		
		//db���� ���� ��¥�� ���� ��� ��������
		List<AttachFileVO> oldList = attach.getYesterdayFiles();
		//Stream : �ڹ� 8���� �߰���(�÷��� ��Ҹ� �ϳ��� �����ؼ� ���ٽ����� ó���ϰ� ����)
		Stream<AttachFileVO> stream = oldList.stream();
		Stream<Path> filePath = stream.map(vo -> 
			Paths.get("d:\\upload",vo.getUploadPath(),vo.getUuid()+"_"+vo.getFileName()));
		
		List<Path> fileListPaths = filePath.collect(Collectors.toList());
		
		//����� �̹��� �۾��ϱ�
		oldList.stream().filter(vo -> vo.isFileType() == true)
						.map(vo -> Paths.get("d:\\upload",vo.getUploadPath(),"s_"+vo.getUuid()+"_"+vo.getFileName()))
						.forEach(p -> fileListPaths.add(p));
		
		//���� ��¥�� ������ �����ؼ� db ���� ����̶� �ٸ� �����
		File targetDir = Paths.get("d:\\upload",getYesterDayFolder()).toFile();
		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath())==false);
		//�����ϱ�
		for(File f : removeFiles) {
			log.warn("�������� : "+f.getAbsolutePath());
			f.delete();
		}
	}

}
