package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName="builder")
public class MusicListDomain {

	private String bdmSeq;
	private String mbId;
	private String bdmTitle;
	private String bdmContent;
	private String bdmCreateAt;
	private String bdmUpdateAt;
}
