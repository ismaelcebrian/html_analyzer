package com.anon.challenge.analyzer.inspector;

import org.jsoup.nodes.Document;

import com.anon.challenge.analyzer.response.HtmlVersion;

public interface HtmlVersionInspector {
	
	HtmlVersion findVersion(Document doc);

}
