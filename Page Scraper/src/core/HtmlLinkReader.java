package core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlLinkReader {
	public String HTMLinfo;
	public String websiteUrl;
	private String HTML_A_TAG_PATTERN = "(?i)<a([^>]+)>(.+?)</a>";
	private String HTML_HREF_ATTR_PATTERN = "\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";

	public HtmlLinkReader(String HTMLinfo, String websiteUrl) {
		this.HTMLinfo = HTMLinfo;
		this.websiteUrl = websiteUrl;
	}

	public List<String> getLinks() {
		String resultForATag = getATagsFromHtmlFile(HTMLinfo);
		Set<String> result = HREFAttributeFromHtmlFile(resultForATag);
		List<String> list = new ArrayList<>(result);
		return list;
	}

	private Set<String> HREFAttributeFromHtmlFile(String resultForATag) {
		Pattern Hrefpattern = Pattern.compile(HTML_HREF_ATTR_PATTERN);
		Matcher HrefMatcher = Hrefpattern.matcher(resultForATag);
		Set<String> resultSet = new HashSet<>();
		String l = "";
		while (HrefMatcher.find()) {
			l = HrefMatcher.group().replaceAll("href=", "").replaceAll("\"", "").trim();
			if (!(l.startsWith("http"))) {
				if (l.startsWith("/")) {
					l = websiteUrl + l;
				} else {
					l = websiteUrl + "/" + l;
				}
			}

			resultSet.add(l);
		}

		return resultSet;
	}

	private String getATagsFromHtmlFile(String HTMLPage) {
		Pattern pattern = Pattern.compile(HTML_A_TAG_PATTERN);
		Matcher pageMatcher = pattern.matcher(HTMLPage);
		StringBuilder b = new StringBuilder();
		String link = "";
		while (pageMatcher.find()) {
			link = pageMatcher.group();
			b.append(link);
			b.append("\n");
		}

		String res = b.toString();

		return res;
	}
}
