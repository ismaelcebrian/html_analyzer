package com.anon.challenge.analyzer.inspector;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.anon.challenge.analyzer.response.HeaderCount;

@Component
public class HtmlInspectorImpl implements HtmlInspector {
	
	private Logger log = Logger.getLogger(HtmlInspectorImpl.class.getName());

	@Override
	public Optional<String> findTitle(Document doc) {
		String titleText = doc.title();
		if (titleText.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(titleText);
	}

	@Override
	public HeaderCount countHeaders(Document doc) {
		HeaderCount result = new HeaderCount();
		result.setH1(doc.getElementsByTag("h1").size());
		result.setH2(doc.getElementsByTag("h2").size());
		result.setH3(doc.getElementsByTag("h3").size());
		result.setH4(doc.getElementsByTag("h4").size());
		result.setH5(doc.getElementsByTag("h5").size());
		result.setH6(doc.getElementsByTag("h6").size());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.anon.challenge.analyzer.inspector.HtmlInspector#hasLogin(org.jsoup.nodes.Document)
	 * Checks if there is a login form. The criterion is simple: just looks for a form with exactly one 
	 * one password field, and either exactly one text input field or one email field (or both)
	 */
	@Override
	public boolean hasLogin(Document doc) {
		log.debug("Looking for login form");
		
		Elements forms = doc.select("form");
		log.debug(String.format("found %d forms", forms.size()));
		for (int i = 0; i< forms.size(); i++) {
			Element form = forms.get(i);
			Elements passwords = form.select("input[type='password']");
			Elements textInputs = form.select("input[type='text']");
			Elements emailInputs = form.select("input[type='email']");
			if(passwords.size() == 1 && (textInputs.size() == 1 || emailInputs.size() == 1)) {
				log.debug("login form found");
				return true; 
			}
			
		}
		log.debug("login form not found");
		return false;
	}

}
