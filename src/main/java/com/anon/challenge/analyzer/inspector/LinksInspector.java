package com.anon.challenge.analyzer.inspector;

import org.jsoup.nodes.Document;

import com.anon.challenge.analyzer.response.LinkCount;

public interface LinksInspector {
	
	LinkCount countLinks(Document doc, String url);

}
