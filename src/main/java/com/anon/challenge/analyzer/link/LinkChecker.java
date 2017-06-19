package com.anon.challenge.analyzer.link;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.anon.challenge.analyzer.HtmlAnalyticsServiceImpl;
import com.anon.challenge.analyzer.response.LinkDetails;

public class LinkChecker implements Callable<LinkDetails> {
	
	private Logger log = Logger.getLogger(LinkChecker.class.getName());

	private URI uri;

	public LinkChecker(URI uri) {
		super();
		this.uri = uri;
	}

	@Override
	public LinkDetails call() throws Exception {
		URI targetUri = new URI("https", uri.getSchemeSpecificPart(), uri.getFragment());
		URL url = targetUri.toURL();

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setConnectTimeout(15000);
		connection.setReadTimeout(15000);
		
		connection.connect();
		
		int responseCode = connection.getResponseCode();
		
		LinkDetails result = new LinkDetails();
		
		result.setHttpStatus(responseCode);
		try {
			result.setUrl(URLEncoder.encode(url.toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			//Only happens if UTF-8 is not supported
			log.error(e.getMessage(), e);
		}
		
 		return result;
	}

}
