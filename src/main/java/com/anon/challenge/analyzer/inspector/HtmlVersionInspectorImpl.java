package com.anon.challenge.analyzer.inspector;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Component;

import com.anon.challenge.analyzer.response.HtmlVersion;

@Component
public class HtmlVersionInspectorImpl implements HtmlVersionInspector {

	private static final String NO_VERSION = "No Version";
	private static final String UNKNOWN_VERSION = "Unknown Version";
	private static final String HTML5 = "HTML5";
	
	Map<String,String> versionByPublicId = new HashMap<>();
	
	@PostConstruct
	public void initializeMap() {
		versionByPublicId.put("-//W3C//DTD HTML 4.01//EN", "HTML 4.01 Strict");
		versionByPublicId.put("-//W3C//DTD HTML 4.01 Transitional//EN", "HTML 4.01 Transitional");
		versionByPublicId.put("-//W3C//DTD HTML 4.01 Frameset//EN", "HTML 4.01 Frameset");
	}

	@Override
	public HtmlVersion findVersion(Document doc) {
		HtmlVersion version = new HtmlVersion();
		Optional<Node> docTypeNode = doc.childNodes().stream().filter(node -> node instanceof DocumentType).findFirst();
		// TODO constants, and return names for other values
		if (!docTypeNode.isPresent()) {
			version.setName(NO_VERSION);
			return version;
		}
		DocumentType docType = (DocumentType) docTypeNode.get();
		
		String publicId = docType.attr("publicid");
		if ("".equals(publicId)) {
			version.setName(HTML5);
			return version;
		}
		
		version.setPublicId(publicId);
		version.setName(versionByPublicId.getOrDefault(publicId, UNKNOWN_VERSION));

		return version;
	}

}
