package com.anon.challenge.analyzer.inspector;

import static org.junit.Assert.assertEquals;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.anon.challenge.analyzer.response.LinkCount;

public class LinksInspectorImplTest {
	
	//TODO
	private LinksInspector inspector = new LinksInspectorImpl();
	
	//Let's make html sandwiches
	private static final String TOP = "<html><head>";
	private static final String MEDIUM = "</head><body>";
	private static final String BOTTOM = "</body></html>";

	private static final String FULL = TOP + MEDIUM + BOTTOM;
	
	@Test
	public void testLinkCount() {
		String html = "<!DOCTYPE html>";
		html += TOP + MEDIUM;
		html += "<a href='http://jsoup.org/'>jsoup</a>";
		html += "<a href='http://localhost:8080/mock/cases/headers1.html'>jsoup</a>";
		html += "<a href='/mock/cases/headers2.html'>jsoup</a>";
		html += BOTTOM;
		
		Document doc = Jsoup.parse(html, "http://localhost:8080");
		LinkCount countLinks = inspector.countLinks(doc, "http://localhost:8080");
		assertEquals(2, countLinks.getInternal());
		assertEquals(1, countLinks.getExternal());
	}
	
	@Test
	public void testLinkCountEmpty() {
		String html = "<!DOCTYPE html>";
		html += FULL;
		
		Document doc = Jsoup.parse(html, "http://localhost:8080");
		LinkCount countLinks = inspector.countLinks(doc, "http://localhost:8080");
		assertEquals(0, countLinks.getInternal());
		assertEquals(0, countLinks.getExternal());
	}
	
	@Test
	public void testSubdomains1() {
		String html = "<!DOCTYPE html>";
		html += TOP + MEDIUM;
		html += "<a href='http://google.com/'>google</a>";
		html += "<a href='http://www.google.com/'>google</a>";
		html += "<a href='http://mail.google.com/'>google</a>";
		html += "<a href='http://jsoup.org/'>jsoup</a>";
		html += BOTTOM;
		
		Document doc = Jsoup.parse(html, "http://google.com");
		LinkCount countLinks = inspector.countLinks(doc, "http://google.com");
		assertEquals(3, countLinks.getInternal());
		assertEquals(1, countLinks.getExternal());
	}
	
	@Test
	public void testSubdomains2() {
		String html = "<!DOCTYPE html>";
		html += TOP + MEDIUM;
		html += "<a href='http://google.co.uk/'>google</a>";
		html += "<a href='http://www.google.co.uk/'>google</a>";
		html += "<a href='http://mail.google.co.uk/'>google</a>";
		html += "<a href='http://jsoup.co.uk/'>jsoup</a>";
		html += "<a href='http://jsoup.org/'>jsoup</a>";
		html += BOTTOM;
		
		Document doc = Jsoup.parse(html, "http://google.co.uk");
		LinkCount countLinks = inspector.countLinks(doc, "http://google.co.uk");
		assertEquals(3, countLinks.getInternal());
		assertEquals(2, countLinks.getExternal());
	}

}
