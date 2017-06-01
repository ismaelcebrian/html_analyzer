package com.anon.challenge.analyzer;

import static org.junit.Assert.*;

import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.anon.challenge.analyzer.response.HeaderCount;

public class HtmlInspectorImplTest {

	private HtmlInspector inspector = new HtmlInspectorImpl();
	
	//Let's make html sandwiches
	private static final String TOP = "<html><head>";
	private static final String MEDIUM = "</head><body>";
	private static final String BOTTOM = "</body></html>";

	
	//title:
	
	@Test
	public void testTitlePresent() {
		String html = TOP;
		html += "<title>This is the title</title>";
		html += MEDIUM + BOTTOM;
		Document doc = Jsoup.parse(html);
		Optional<String> title = inspector.findTitle(doc);
		assertTrue(title.isPresent());
		assertEquals("This is the title", title.get());
	}

	@Test
	public void testTitleEmpty() {
		String html = TOP;
		html += "<title></title>";
		html += MEDIUM + BOTTOM;
		Document doc = Jsoup.parse(html);
		Optional<String> title = inspector.findTitle(doc);
		assertTrue(!title.isPresent());
	}
	
	@Test
	public void testTitleMissing() {
		String html = TOP + MEDIUM + BOTTOM;
		Document doc = Jsoup.parse(html);
		Optional<String> title = inspector.findTitle(doc);
		assertTrue(!title.isPresent());
	}
	
	//headers:
	
	@Test
	public void testHeadersMissing() {
		String html = TOP + MEDIUM + BOTTOM;
		Document doc = Jsoup.parse(html);
		HeaderCount headers = inspector.countHeaders(doc);
		assertEquals(0,headers.getH1());
		assertEquals(0,headers.getH2());
		assertEquals(0,headers.getH3());
		assertEquals(0,headers.getH4());
		assertEquals(0,headers.getH5());
		assertEquals(0,headers.getH6());
		
	}
	
	@Test
	public void testHeadersAll() {
		String html = TOP + MEDIUM;
		html += "<h1></h1>";
		html += "<h2></h2>";
		html += "<h3></h3>";
		html += "<h4></h4>";
		html += "<h5></h5>";
		html += "<h6></h6>";
		html += BOTTOM;
		Document doc = Jsoup.parse(html);
		HeaderCount headers = inspector.countHeaders(doc);
		assertEquals(1,headers.getH1());
		assertEquals(1,headers.getH2());
		assertEquals(1,headers.getH3());
		assertEquals(1,headers.getH4());
		assertEquals(1,headers.getH5());
		assertEquals(1,headers.getH6());
	}
	
	@Test
	public void testHeadersMultiple() {
		String html = TOP + MEDIUM;
		html += "<h2></h2>";
		html += "<h1></h1>";
		html += "<h2></h2>";
		html += "<h6></h6>";
		html += "<h3></h3>";
		html += "<h3></h3>";
		html += "<h2></h2>";
		html += BOTTOM;
		Document doc = Jsoup.parse(html);
		HeaderCount headers = inspector.countHeaders(doc);
		assertEquals(1,headers.getH1());
		assertEquals(3,headers.getH2());
		assertEquals(2,headers.getH3());
		assertEquals(0,headers.getH4());
		assertEquals(0,headers.getH5());
		assertEquals(1,headers.getH6());
	}
	
}
