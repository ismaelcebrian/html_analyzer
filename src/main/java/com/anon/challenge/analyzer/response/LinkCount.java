package com.anon.challenge.analyzer.response;

public class LinkCount {
	
	/**
	 * Number of links in the page that point to pages in the same domain as the original URL (including subdomains)
	 */
	private int internal;
	
	/**
	 * Number of links in the page that point to pages in other domains
	 */
	private int external;

	public int getInternal() {
		return internal;
	}

	public void setInternal(int internal) {
		this.internal = internal;
	}

	public int getExternal() {
		return external;
	}

	public void setExternal(int external) {
		this.external = external;
	}

}
