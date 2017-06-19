package com.anon.challenge.analyzer.response;

import java.util.List;

public class LinkInfo {
	
	private LinkCount linkCount;
	
	private List<LinkDetails> linkList;

	public LinkCount getLinkCount() {
		return linkCount;
	}

	public void setLinkCount(LinkCount linkCount) {
		this.linkCount = linkCount;
	}

	public List<LinkDetails> getLinkList() {
		return linkList;
	}

	public void setLinkList(List<LinkDetails> linkList) {
		this.linkList = linkList;
	}

}
