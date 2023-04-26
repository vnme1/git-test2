package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName="builder")
public class MusicFileDomain {
	
	private Integer bdmSeq;
	private String mbId;
	
	private String upOriginalmFileName;
	private String upNewmFileName; //동일 이름 업로드 될 경우
	private String upmFilePath;
	private Integer upmFileSize;

}
