package com.anon.challenge.analyzer.inspector;

import org.jsoup.nodes.Document;

import com.anon.challenge.analyzer.response.LinkCount;

/**
 * Component that count the number of links in a parsed HTML document
 */
public interface LinksInspector {
	
	LinkCount countLinks(Document doc, String url);

}
