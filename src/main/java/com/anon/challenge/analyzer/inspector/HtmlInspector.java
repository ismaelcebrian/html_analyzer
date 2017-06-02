package com.anon.challenge.analyzer.inspector;

import java.util.Optional;

import org.jsoup.nodes.Document;

import com.anon.challenge.analyzer.response.HeaderCount;

/**
 * Component that carries miscellaneous analysis in a parsed html file.
 */
public interface HtmlInspector {
	
	/**
	 * Finds the title of the given document. If the title is not present or is empty, returns an empty Optional
	 * @param doc
	 * @return
	 */
	Optional<String> findTitle(Document doc);
	
	/**
	 * Counts the number of header elements in the given document, by header level (h1, h2, ... , h6)
	 * @param doc
	 * @return
	 */
	HeaderCount countHeaders(Document doc);
	
	/**
	 * Checks if the document includes a login form
	 * @param doc
	 * @return
	 */
	boolean hasLogin(Document doc);

}
