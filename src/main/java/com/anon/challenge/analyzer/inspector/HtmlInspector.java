package com.anon.challenge.analyzer.inspector;

import java.util.Optional;

import org.jsoup.nodes.Document;

import com.anon.challenge.analyzer.response.HeaderCount;

public interface HtmlInspector {
	
	Optional<String> findTitle(Document doc);
	
	HeaderCount countHeaders(Document doc);
	
	boolean hasLogin(Document doc);

}
