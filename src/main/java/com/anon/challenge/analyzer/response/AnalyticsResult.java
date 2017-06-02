package com.anon.challenge.analyzer.response;

/**
 * Container for the results of all the Analytics performed in an URL
 */
public class AnalyticsResult {

	private String url;

	private HtmlVersion htmlVersion;

	private String title;

	private HeaderCount headers;

	private LinkCount linkCount;

	private boolean hasLogin;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HtmlVersion getHtmlVersion() {
		return htmlVersion;
	}

	public void setHtmlVersion(HtmlVersion htmlVersion) {
		this.htmlVersion = htmlVersion;
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

	public LinkCount getLinkCount() {
		return linkCount;
	}

	public void setLinkCount(LinkCount linkCount) {
		this.linkCount = linkCount;
	}

	public boolean isHasLogin() {
		return hasLogin;
	}

	public void setHasLogin(boolean hasLogin) {
		this.hasLogin = hasLogin;
	}

}
