package com.anon.challenge.analyzer.inspector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import com.anon.challenge.analyzer.response.HtmlVersion;

public class HtmlVersionInspectorImplTest {

	private HtmlVersionInspectorImpl inspector = new HtmlVersionInspectorImpl();
	
	//Let's make html sandwiches
	private static final String TOP = "<html><head>";
	private static final String MEDIUM = "</head><body>";
	private static final String BOTTOM = "</body></html>";

	private static final String FULL = TOP + MEDIUM + BOTTOM;
	
	@Before
	public void init() {
		inspector.initializeMap();
	}

	//HTML version:
	@Test
	public void testVersionHtml5() {
		String html = "<!DOCTYPE html>";
		html += FULL;
		Document doc = Jsoup.parse(html);
		HtmlVersion version = inspector.findVersion(doc);
		assertEquals("HTML5", version.getName());
		assertNull(version.getPublicId());
	}
	

	@Test
	public void testVersionMissing() {
		String html = FULL;
		Document doc = Jsoup.parse(html);
		HtmlVersion version = inspector.findVersion(doc);
		assertEquals("No Version", version.getName());
		assertNull(version.getPublicId());
	}
	
	@Test
	public void testVersion401Strict() {
		String html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">";
		html += FULL;
		Document doc = Jsoup.parse(html);
		HtmlVersion version = inspector.findVersion(doc);
		assertEquals("HTML 4.01 Strict", version.getName());
		assertEquals("-//W3C//DTD HTML 4.01//EN", version.getPublicId());
		
	}

	@Test
	public void testVersion401Trans() {
		String html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">";
		html += FULL;
		Document doc = Jsoup.parse(html);
		HtmlVersion version = inspector.findVersion(doc);
		assertEquals("HTML 4.01 Transitional", version.getName());
		assertEquals("-//W3C//DTD HTML 4.01 Transitional//EN", version.getPublicId());
		
	}
	
	@Test
	public void testVersion401Frameset() {
		String html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\" \"http://www.w3.org/TR/html4/frameset.dtd\">";
		html += FULL;
		Document doc = Jsoup.parse(html);
		HtmlVersion version = inspector.findVersion(doc);
		assertEquals("HTML 4.01 Frameset", version.getName());
		assertEquals("-//W3C//DTD HTML 4.01 Frameset//EN", version.getPublicId());
		
	}
	
	

}
