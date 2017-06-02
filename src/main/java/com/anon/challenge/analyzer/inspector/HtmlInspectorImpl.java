package com.anon.challenge.analyzer.inspector;

import java.util.Optional;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Component;

import com.anon.challenge.analyzer.response.HeaderCount;

@Component
public class HtmlInspectorImpl implements HtmlInspector {

	@Override
	public String findVersion(Document doc) {
		Optional<Node> docTypeNode = doc.childNodes().stream().filter(node -> node instanceof DocumentType).findFirst();
		// TODO constants, and return names for other values
		if (!docTypeNode.isPresent()) {
			return "No Version";
		}
		DocumentType docType = (DocumentType) docTypeNode.get();

		String publicId = docType.attr("publicid");
		if ("".equals(publicId)) {
			return "HTML5";
		}
		return publicId;
	}

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

	@Override
	public boolean hasLogin(Document doc) {
		// TODO Auto-generated method stub
		return false;
	}

}
