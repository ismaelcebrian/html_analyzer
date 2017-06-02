package com.anon.challenge.analyzer.inspector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
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
	
	
	//login:
	
	@Test
	public void loginSpiegelTest() throws IOException {
		
		File input = new File( getClass().getClassLoader().getResource("mock/spiegelLogin.html").getFile());
		Document doc = Jsoup.parse(input, "UTF-8", "https://www.spiegel.de/meinspiegel/login.html");
		boolean hasLogin = inspector.hasLogin(doc);
		assertTrue(hasLogin);
	}

	@Test
	public void loginGithubTest() throws IOException {
		
		File input = new File( getClass().getClassLoader().getResource("mock/githubLogin.html").getFile());
		Document doc = Jsoup.parse(input, "UTF-8");
		boolean hasLogin = inspector.hasLogin(doc);
		assertTrue(hasLogin);
	}
	
	@Test
	public void registerGithubTest() throws IOException {
		
		File input = new File( getClass().getClassLoader().getResource("mock/githubRegister.html").getFile());
		Document doc = Jsoup.parse(input, "UTF-8");
		boolean hasLogin = inspector.hasLogin(doc);
		assertTrue(!hasLogin);
	}

	@Test
	public void registerSpiegelTest() throws IOException {
		
		File input = new File( getClass().getClassLoader().getResource("mock/spiegelRegister.html").getFile());
		Document doc = Jsoup.parse(input, "UTF-8");
		boolean hasLogin = inspector.hasLogin(doc);
		assertTrue(!hasLogin);
	}
	
	@Test
	public void loginEmtpyTest() throws IOException {
		
		String html = FULL;
		Document doc = Jsoup.parse(html);
		boolean hasLogin = inspector.hasLogin(doc);
		assertTrue(!hasLogin);
	}
	
	@Test
	public void loginMissingTest() throws IOException {
		
		File input = new File( getClass().getClassLoader().getResource("mock/headers1.html").getFile());
		Document doc = Jsoup.parse(input, "UTF-8", "https://localhost:8080/headers1.html");
		boolean hasLogin = inspector.hasLogin(doc);
		assertTrue(!hasLogin);
	}

}
