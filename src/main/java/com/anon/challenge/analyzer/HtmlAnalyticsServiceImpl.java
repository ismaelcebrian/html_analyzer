package com.anon.challenge.analyzer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anon.challenge.analyzer.inspector.HtmlInspector;
import com.anon.challenge.analyzer.inspector.LinksInspector;
import com.anon.challenge.analyzer.response.AnalyticsResult;

@Service
public class HtmlAnalyticsServiceImpl implements HtmlAnalyticsService {
	
	@Autowired
	private HtmlInspector htmlInspector;

	@Autowired
	private LinksInspector linksInspector;

	@Override
	public AnalyticsResult analyze(String url) {
		
		AnalyticsResult result = new AnalyticsResult();

		try {
			result.setUrl(URLEncoder.encode(url, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//If UTF-8 is not supported...
			e.printStackTrace();
		}
		
		try {
			Document doc = Jsoup.connect(url).get();
			
			result.setHtmlVersion(htmlInspector.findVersion(doc));
			Optional<String> title = htmlInspector.findTitle(doc);
			if (title.isPresent()) {
				result.setTitle(title.get());
			}
			
			result.setHeaders(htmlInspector.countHeaders(doc));
			
			result.setLinkCount(linksInspector.countLinks(doc, url));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
