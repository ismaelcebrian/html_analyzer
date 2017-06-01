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

	private static final String FULL = TOP + MEDIUM + BOTTOM;

	//HTML version:
	@Test
	public void testVersionHtml5() {
		String html = "<!DOCTYPE html>";
		html += FULL;
		Document doc = Jsoup.parse(html);
		String version = inspector.findVersion(doc);
		assertEquals("HTML5", version);
	}
	

	@Test
	public void testVersionMissing() {
		String html = FULL;
		Document doc = Jsoup.parse(html);
		String version = inspector.findVersion(doc);
		assertEquals("No Version", version);
	}
	
	@Test
	public void testVersion401Strict() {
		String html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">";
		html += FULL;
		Document doc = Jsoup.parse(html);
		String version = inspector.findVersion(doc);
		assertEquals("-//W3C//DTD HTML 4.01//EN", version);
	}

	@Test
	public void testVersion32() {
		String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">";
		html += FULL;
		Document doc = Jsoup.parse(html);
		String version = inspector.findVersion(doc);
		assertEquals("-//W3C//DTD HTML 3.2 Final//EN", version);
	}
	
	
	
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
		String html = FULL;
		Document doc = Jsoup.parse(html);
		Optional<String> title = inspector.findTitle(doc);
		assertTrue(!title.isPresent());
	}
	
	//headers:
	
	@Test
	public void testHeadersMissing() {
		String html = FULL;
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
