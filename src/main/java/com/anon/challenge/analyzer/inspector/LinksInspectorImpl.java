package com.anon.challenge.analyzer.inspector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.anon.challenge.analyzer.error.ApplicationException;
import com.anon.challenge.analyzer.link.LinkChecker;
import com.anon.challenge.analyzer.response.LinkCount;
import com.anon.challenge.analyzer.response.LinkDetails;
import com.anon.challenge.analyzer.response.LinkInfo;

@Component
public class LinksInspectorImpl implements LinksInspector {

	private Logger log = Logger.getLogger(LinksInspectorImpl.class.getName());

	@Override
	public LinkInfo countLinks(Document doc, String url) {
		LinkInfo result = new LinkInfo();
		LinkCount linkCount = new LinkCount();
		result.setLinkCount(linkCount);
		result.setLinkList(new ArrayList<>());
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
		Elements linkElements = doc.select("a[href]");
		log.debug(String.format("Number of links found: %d", linkElements.size()));
		List<LinkChecker> linkTasks = new ArrayList<>();
		//iterate the links found, and compare the domain to count as external or internal
		int internal = 0, external = 0;
		for (int i = 0; i < linkElements.size(); i++) {
			String href = linkElements.get(i).attr("abs:href");
			try {
				URI linkUri = new URI(href);
				if (!"http".equals(linkUri.getScheme()) && !"https".equals(linkUri.getScheme())) {
					//ignore other types of links
					continue;
				}
				linkTasks.add(new LinkChecker(linkUri));
				if (domain.equals(extractDomain(linkUri))) {
					internal++;
				} else {
					external++;
				}
			} catch (URISyntaxException e) {
				// Just ignore links with malformed URLs
			}
		}
		
		linkCount.setInternal(internal);
		linkCount.setExternal(external);
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		try {
			List<Future<LinkDetails>> linkTest = executorService.invokeAll(linkTasks);
			for (Future<LinkDetails> future : linkTest) {
				try {
					LinkDetails linkDetails = future.get();
					result.getLinkList().add(linkDetails);
					
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}


	/**
	 * extracts the domain from and URI
	 * It splits the host of the URI in period-separated labels.
	 * If there 2 or less labels (localhost... ) the whole host is taken as domain
	 * For domains of the type google.com, the last two labels are used as domains
	 * For domains of the type google.co.uk, the last three labels are used
	 * @param uri
	 * @return
	 */
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
			return String.join(".", domainLabels);
			
		} 
		
		//Domains like google.co.uk
		if(last.length() == 2) {
			List<String> domainLabels = labels.subList(labels.size() -3, labels.size());
			return String.join(".", domainLabels);
			
		} 
		return host;
	}

}
