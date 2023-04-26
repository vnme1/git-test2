package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName="builder")
public class MusicContentDomain {
	
	private Integer bdmSeq;
	private String mbId;

	private String bdmTitle;
	private String bdmContent;

}
