package com.anon.challenge.analyzer.response;

public class AnalyticsResult {
	
	private String url;
	
	private String title;

	private HeaderCount headers;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public HeaderCount getHeaders() {
		return headers;
	}

	public void setHeaders(HeaderCount headers) {
		this.headers = headers;
	}

}
