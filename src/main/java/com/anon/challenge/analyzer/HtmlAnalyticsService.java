package com.anon.challenge.analyzer;

import java.io.IOException;

import com.anon.challenge.analyzer.response.AnalyticsResult;

public interface HtmlAnalyticsService {
	
	AnalyticsResult analyze(String url) throws IOException;

}
