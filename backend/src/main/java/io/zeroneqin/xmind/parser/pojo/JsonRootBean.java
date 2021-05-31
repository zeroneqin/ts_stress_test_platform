package io.zeroneqin.xmind.parser.pojo;

import lombok.Data;

@Data
public class JsonRootBean {

	private String id;
	private String title;
	private RootTopic rootTopic;

}