package com.anon.challenge.analyzer.inspector;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.anon.challenge.analyzer.error.ApplicationException;
import com.anon.challenge.analyzer.response.LinkCount;

@Component
public class LinksInspectorImpl implements LinksInspector {

	private Logger log = Logger.getLogger(LinksInspectorImpl.class.getName());

	@Override
	public LinkCount countLinks(Document doc, String url) {
		LinkCount result = new LinkCount();
		URL parsedUrl;
		try {
			parsedUrl = new URL(url);
		} catch (MalformedURLException e) {
			// this should never happen, since the URL has been validated by Spring
			throw new ApplicationException(
					"The target URL passed validation, but could not be converted to an URL object", e);
		}
		log.info("querying links");
		Elements links = doc.select("a[href]");
		log.info(String.format("Number of links found: %d", links.size()));
		int internal = 0, external = 0, malformed = 0;
		for (int i = 0; i < links.size(); i++) {
			String href = links.get(i).attr("abs:href");
			if (!isValidUrl(href)) {
				log.info("This is not a well-formed url: " + href);
				log.info("It comes from link " + links.get(i).outerHtml());
				
			} else {
				log.info("This is a well-formed url: " + href);
			}
			try {
				URL linkUrl = new URL(href);
				if (parsedUrl.getHost().equals(linkUrl.getHost())) {
					internal++;
				} else {
					external++;
				}
			} catch (MalformedURLException e) {
				// TODO change this?
				// Just ignore links with malformed URLs
				// TODO debug
				malformed++;
				log.error("malformed URL in link", e);
			}
		}
		result.setInternal(internal);
		result.setExternal(external);
		// TODO debug
		log.info("Number of malformed links: " + malformed);

		return result;
	}

	private boolean isValidUrl(String url) {
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			return false;
		}
		return true;

	}

}
