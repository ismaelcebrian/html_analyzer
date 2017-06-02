package com.anon.challenge.analyzer;

import java.io.IOException;

import com.anon.challenge.analyzer.response.AnalyticsResult;

/**
 * Service that connects to the given URL, parses the HTML, and calls the inspectors to perform analysis in the HTML
 */
public interface HtmlAnalyticsService {
	
	AnalyticsResult analyze(String url) throws IOException;

}
