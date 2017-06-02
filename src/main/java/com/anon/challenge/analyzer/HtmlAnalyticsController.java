package com.anon.challenge.analyzer;


import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anon.challenge.analyzer.response.AnalyticsResult;

@RestController
public class HtmlAnalyticsController {
	
	//private Logger log = Logger.getLogger(HtmlAnalyticsController.class.getCanonicalName());
	private Logger log = Logger.getLogger(HtmlAnalyticsController.class.getName());
	
	@Autowired
	private HtmlAnalyticsService htmlAnalyticsService;

	
	@RequestMapping(value = "/api/analyze", method = RequestMethod.GET)
	//TODO remove request param if not needed or value
	public AnalyticsResult analyze(@RequestParam(value = "url") String url) throws IOException {
		
		//URL already validated and decoded by Spring
		log.debug("URL received: " + url);
		
		return htmlAnalyticsService.analyze(url);

	}

}
