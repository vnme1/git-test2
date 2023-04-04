package com.co.kr.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.co.kr.code.Code;
import com.co.kr.domain.BoardContentDomain;
import com.co.kr.domain.BoardFileDomain;
import com.co.kr.domain.BoardListDomain;
import com.co.kr.exception.RequestException;
import com.co.kr.mapper.UploadMapper;
import com.co.kr.util.CommonUtils;
import com.co.kr.vo.FileListVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UploadServiceImple implements UploadService {

	@Autowired
	private UploadMapper uploadMapper;
	
	@Override
	public BoardListDomain boardSelectOne(HashMap<String, Object> map) {
		return uploadMapper.boardSelectOne(map);
	}

	@Override
	public List<BoardFileDomain> boardSelectOneFile(HashMap<String, Object> map) {
		return uploadMapper.boardSelectOneFile(map);
	}
	
	@Override
	public List<BoardListDomain> boardList() {
		// TODO Auto-generated method stub
		return uploadMapper.boardList();
	}

	@Override
	public int fileProcess(FileListVO fileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq) {
		// TODO Auto-generated method stub
		HttpSession session = httpReq.getSession();
		
		BoardContentDomain boardContentDomain = BoardContentDomain.builder()
				.mbId(session.getAttribute("id").toString())
				.bdTitle(fileListVO.getTitle())
				.bdContent(fileListVO.getContent())
				.build();
		
		if(fileListVO.getIsEdit() != null) {
			boardContentDomain.setBdSeq(Integer.parseInt(fileListVO.getSeq()));
			System.out.println("수정업데이트");
			
			uploadMapper.bdContentUpdate(boardContentDomain);
		}else {	
			
			uploadMapper.contentUpload(boardContentDomain);
			System.out.println(" db 인서트");

		}
		
		int bdSeq = boardContentDomain.getBdSeq();
		String mbId = boardContentDomain.getMbId();
		
		List<MultipartFile> multipartFiles = request.getFiles("files");
		
		if(fileListVO.getIsEdit() != null) {
			List<BoardFileDomain> fileList = null;
			
			for (MultipartFile multipartFile : multipartFiles) {
				if(!multipartFile.isEmpty()) { 
					if(session.getAttribute("files") != null) {
						fileList = (List<BoardFileDomain>) session.getAttribute("files");
						for (BoardFileDomain list : fileList) {
							list.getUpFilePath();
							Path filePath = Paths.get(list.getUpFilePath());
							
							try {
					        	
					            
					            Files.deleteIfExists(filePath);
					            
											bdFileRemove(list); 
								
					        } catch (DirectoryNotEmptyException e) {
												throw RequestException.fire(Code.E404, "디렉토리가 존재하지 않습니다", HttpStatus.NOT_FOUND);
					        } catch (IOException e) {
					            e.printStackTrace();
					        }
						}
					}
				}
			}
		}
		
		Path rootPath = Paths.get(new File("C://").toString(),"upload", File.separator).toAbsolutePath().normalize();			
		File pathCheck = new File(rootPath.toString());
		
		if(!pathCheck.exists()) pathCheck.mkdirs();
		
		for (MultipartFile multipartFile : multipartFiles) {
			if(!multipartFile.isEmpty()) { 
				String originalFileExtension;
				String contentType = multipartFile.getContentType();
				String origFilename = multipartFile.getOriginalFilename();
				
				//확장자 존재x일 경우
				if(ObjectUtils.isEmpty(contentType)){
					break;
				}else {
					if(contentType.contains("image/jpeg")) {
						originalFileExtension = ".jpg";
					}else if(contentType.contains("image/png")) {
						originalFileExtension = ".png";
					}else {
						break;
					}
				}
				
				String uuid = UUID.randomUUID().toString();
				String current = CommonUtils.currentTime();
				String newFileName = uuid + current + originalFileExtension;
				
				Path targetPath = rootPath.resolve(newFileName);
				File file = new File(targetPath.toString());
				
				
				try {
					multipartFile.transferTo(file);
					// 파일 권한 설정함(쓰기,읽기)
					file.setWritable(true);
					file.setReadable(true);
					
					
					//파일 domain 생성 
					BoardFileDomain boardFileDomain = BoardFileDomain.builder()
							.bdSeq(bdSeq)
							.mbId(mbId)
							.upOriginalFileName(origFilename)
							.upNewFileName("resources/upload/"+newFileName) // WebConfig에 동적 이미지 폴더 생성 했기때문
							.upFilePath(targetPath.toString())
							.upFileSize((int)multipartFile.getSize())
							.build();
					
						
						uploadMapper.fileUpload(boardFileDomain);
						System.out.println("upload done");
					
				} catch (IOException e) {
					throw RequestException.fire(Code.E404, "잘못된 업로드 파일", HttpStatus.NOT_FOUND);
				}
			}
		}
		return bdSeq;
		//저장한 게시판 번호임 기억하고 잇ㅅㄱ
	}

	@Override
	public void bdContentRemove(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		uploadMapper.bdContentRemove(map);
		
	}

	@Override
	public void bdFileRemove(BoardFileDomain boardFileDomain) {
		// TODO Auto-generated method stub
		uploadMapper.bdFileRemove(boardFileDomain);
		
	}
	
	

}