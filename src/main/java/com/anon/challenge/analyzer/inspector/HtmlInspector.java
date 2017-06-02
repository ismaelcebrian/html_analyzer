package com.anon.challenge.analyzer;

import java.util.Optional;

import org.jsoup.nodes.Document;

import com.anon.challenge.analyzer.response.HeaderCount;
import com.anon.challenge.analyzer.response.LinkCount;

public interface HtmlInspector {
	
	String findVersion(Document doc);

	Optional<String> findTitle(Document doc);
	
	HeaderCount countHeaders(Document doc);
	
	LinkCount countLinks(Document doc);
	
	boolean hasLogin(Document doc);

}
