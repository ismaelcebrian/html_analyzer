package com.anon.challenge.analyzer;

import com.anon.challenge.analyzer.response.AnalyticsResult;

public interface HtmlAnalyticsService {
	
	AnalyticsResult analyze(String url);

}
