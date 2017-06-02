package com.anon.challenge.analyzer;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.anon.challenge.analyzer.response.AnalyticsResult;

public class HtmlAnalyticsControllerTest {
	
	private static final String WRONG_URL = "wrongUrl";
	private static final String VALID_URL = "http://testing.com";
	HtmlAnalyticsController controller = new HtmlAnalyticsController();
	
	@Before
	public void setup() {
		HtmlAnalyticsService serviceMock = mock(HtmlAnalyticsService.class);
		try {
			when(serviceMock.analyze(VALID_URL)).thenReturn(new AnalyticsResult());
			when(serviceMock.analyze(WRONG_URL)).thenThrow(new IOException());
		} catch (IOException e) {
			// never happens
			e.printStackTrace();
		}
		ReflectionTestUtils.setField(controller, "htmlAnalyticsService", serviceMock);

	}
	
	@Test
	public void testValidUrl() throws IOException {
		AnalyticsResult result = controller.analyze(VALID_URL);
		assertNotNull(result);
		
	}

	@Test(expected = IOException.class)
	public void testWrongUrl() throws IOException {
		controller.analyze(WRONG_URL);
		
	}

}
