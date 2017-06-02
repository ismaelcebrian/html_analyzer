package com.anon.challenge.analyzer.inspector;

import org.jsoup.nodes.Document;

import com.anon.challenge.analyzer.response.HtmlVersion;

/**
 * Component that finds the HTML version of a parsed HTML document
 */
public interface HtmlVersionInspector {
	
	HtmlVersion findVersion(Document doc);

}
