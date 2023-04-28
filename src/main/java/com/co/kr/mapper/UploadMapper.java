package com.co.kr.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.BoardContentDomain;
import com.co.kr.domain.BoardFileDomain;
import com.co.kr.domain.BoardListDomain;
import com.co.kr.domain.MusicContentDomain;
import com.co.kr.domain.MusicFileDomain;
import com.co.kr.domain.MusicListDomain;

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
	
	//추가 중간과제
	public List<MusicListDomain> musicList();
	
	public void mcontentUpload(MusicContentDomain musicContentDomain);
	//file insert
	public void mfileUpload(MusicFileDomain musicFileDomain);
	//content update
	public void mbdContentUpdate(MusicContentDomain musicContentDomain);
	//file updata
	public void mbdFileUpdate(MusicFileDomain musicFileDomain);

  //content delete 
	public void mbdContentRemove(HashMap<String, Object> map);
	//file delete 
	public void mbdFileRemove(MusicFileDomain musicFileDomain);
	
	public MusicListDomain musicSelectOne(HashMap<String, Object> map);

	//select one file
	public List<MusicFileDomain> musicSelectOneFile(HashMap<String, Object> map);
}