package com.anon.challenge.analyzer;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anon.challenge.analyzer.response.AnalyticsResult;

/**
 * Rest controller that exposes the endpoint to analyze a given URL
 */
@RestController
public class HtmlAnalyticsController {

	private Logger log = Logger.getLogger(HtmlAnalyticsController.class.getName());

	@Autowired
	private HtmlAnalyticsService htmlAnalyticsService;

	/**
	 * @param url
	 *            must be a full URL (including protocol), encoded
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/analyze", method = RequestMethod.GET)
	public AnalyticsResult analyze(@RequestParam(value = "url") String url) throws IOException {

		// URL already validated and decoded by Spring
		log.debug("URL received: " + url);

		return htmlAnalyticsService.analyze(url);

	}

}
