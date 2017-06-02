package com.anon.challenge.analyzer.response;

public class HtmlVersion {
	
	/**
	 * name of the HTML Version. It can also have the values "No Version" and "Unknown Version"
	 */
	private String name;
	
	/**
	 * publicId of the HTML version as defined by the standard. Null if the version is HTML5, or if no version was found
	 */
	private String publicId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

}
