package com.anon.challenge.analyzer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anon.challenge.analyzer.inspector.HtmlInspector;
import com.anon.challenge.analyzer.inspector.LinksInspector;
import com.anon.challenge.analyzer.response.AnalyticsResult;

@Service
public class HtmlAnalyticsServiceImpl implements HtmlAnalyticsService {

	private Logger log = Logger.getLogger(HtmlAnalyticsServiceImpl.class.getName());

	@Autowired
	private HtmlInspector htmlInspector;

	@Autowired
	private LinksInspector linksInspector;

	@Override
	public AnalyticsResult analyze(String url) throws IOException {

		AnalyticsResult result = new AnalyticsResult();

		try {
			result.setUrl(URLEncoder.encode(url, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}

		log.info("Connecting to URL and parsing HTML");
		Document doc = Jsoup.connect(url).get();
		log.info("HTML parsed");

		result.setHtmlVersion(htmlInspector.findVersion(doc));
		Optional<String> title = htmlInspector.findTitle(doc);
		if (title.isPresent()) {
			result.setTitle(title.get());
		}

		result.setHeaders(htmlInspector.countHeaders(doc));

		result.setLinkCount(linksInspector.countLinks(doc, url));

		result.setHasLogin(htmlInspector.hasLogin(doc));

		return result;
	}

}
