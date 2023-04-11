package com.co.kr.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.BoardContentDomain;
import com.co.kr.domain.BoardFileDomain;
import com.co.kr.domain.BoardListDomain;

@Mapper
public interface UploadMapper {

	//list
	public List<BoardListDomain> boardList();
	
	public void contentUpload(BoardContentDomain boardContentDomain);
	
	public void fileUpload(BoardFileDomain boardFileDomain);
	
	public void bdContentUpdate(BoardContentDomain boardContentDomain);
	
	public void bdFileUpdate(BoardFileDomain boardFileDomain);

	public void bdContentRemove(HashMap<String, Object> map);

	public void bdFileRemove(BoardFileDomain boardFileDomain);
	
	public BoardListDomain boardSelectOne(HashMap<String, Object> map);
	
	public List<BoardFileDomain> boardSelectOneFile(HashMap<String, Object> map);
}