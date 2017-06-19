package com.anon.challenge.analyzer.inspector;

import org.jsoup.nodes.Document;

import com.anon.challenge.analyzer.response.LinkInfo;

/**
 * Component that count the number of links in a parsed HTML document
 */
public interface LinksInspector {
	
	LinkInfo countLinks(Document doc, String url);

}
