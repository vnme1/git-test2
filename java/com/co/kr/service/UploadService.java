package com.co.kr.service;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.co.kr.domain.BoardFileDomain;
import com.co.kr.domain.BoardListDomain;
import com.co.kr.vo.FileListVO;

public interface UploadService {
	
	// 전체 리스트 조회
	public List<BoardListDomain> boardList();
	
	public int fileProcess(FileListVO fileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq);
	
	public void bdContentRemove(HashMap<String, Object> map);
	
	public void bdFileRemove(BoardFileDomain boardFileDomain);
	
	//리스트 하나 조회
	public BoardListDomain boardSelectOne(HashMap<String, Object> map);
	
	//파일 리스트 하ㄴ조회
	public List<BoardFileDomain> boardSelectOneFile(HashMap<String, Object> map);
}