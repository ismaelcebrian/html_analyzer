package com.anon.challenge.analyzer.inspector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
		URI parsedUrl;
		try {
			parsedUrl = new URI(url);
		} catch (URISyntaxException e) {
			// this should never happen, since the URL has been validated by
			// Spring
			throw new ApplicationException(
					"The target URL passed validation, but could not be converted to an URL object", e);
		}
		String domain = extractDomain(parsedUrl);
		log.debug("querying links");
		Elements links = doc.select("a[href]");
		log.debug(String.format("Number of links found: %d", links.size()));
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
				URI linkUrl = new URI(href);
				if (domain.equals(extractDomain(linkUrl))) {
					internal++;
				} else {
					external++;
				}
			} catch (URISyntaxException e) {
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
			new URI(url);
		} catch (URISyntaxException e) {
			return false;
		}
		return true;

	}

	private String extractDomain(URI uri) {
		String host = uri.getHost();
		if (host.startsWith("www.")) {
			host = host.substring(4,host.length());
		}
		List<String> labels = Arrays.asList(host.split("\\."));
		if (labels.size() <= 2) return host;
		String last = labels.get(labels.size()-1);
		
		//Domains of the type google.com
		if(last.length() == 3) {
			List<String> domainLabels = labels.subList(labels.size() -2, labels.size());
			Optional<String> domain = domainLabels.stream().reduce((s1, s2) -> s1 + "." + s2);
			return domain.get();
			
		} 
		
		//Domains like google.co.uk
		if(last.length() == 2) {
			List<String> domainLabels = labels.subList(labels.size() -3, labels.size());
			Optional<String> domain = domainLabels.stream().reduce((s1, s2) -> s1 + "." + s2);
			return domain.get();
			
		} 
		return host;
	}

}
