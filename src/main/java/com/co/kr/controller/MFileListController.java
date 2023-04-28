package com.co.kr.controller;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.domain.MusicFileDomain;
import com.co.kr.domain.MusicListDomain;
import com.co.kr.service.UploadService;
import com.co.kr.vo.MFileListVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MFileListController {

	@Autowired
	private UploadService uploadService;

	
	@PostMapping(value = "mupload")
	public ModelAndView bdmUpload(MFileListVO mfileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq) throws IOException, ParseException {
		
		ModelAndView mav = new ModelAndView();
		int bdmSeq = uploadService.mfileProcess(mfileListVO, request, httpReq);
		mfileListVO.setMcontent(""); //초기화
		mfileListVO.setMtitle(""); //초기화
		
		mav = mbdSelectOneCall(mfileListVO, String.valueOf(bdmSeq),request);
		mav.setViewName("music/musicList.html");
		return mav;
		
	}
	public ModelAndView mbdSelectOneCall(@ModelAttribute("mfileListVO") MFileListVO mfileListVO, String bdmSeq, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		
		map.put("bdmSeq", Integer.parseInt(bdmSeq));
		MusicListDomain musicListDomain =uploadService.musicSelectOne(map);
		System.out.println("musicListDomain"+musicListDomain);
		List<MusicFileDomain> mfileList =  uploadService.musicSelectOneFile(map);
		
		for (MusicFileDomain list : mfileList) {
			String path = list.getUpmFilePath().replaceAll("\\\\", "/");
			list.setUpmFilePath(path);
		}
		mav.addObject("detail", musicListDomain);
		mav.addObject("files", mfileList);

		//삭제시 사용할 용도
		session.setAttribute("files", mfileList);

		return mav;
	}
	
}