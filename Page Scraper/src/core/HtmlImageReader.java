package core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlImageReader {
	public String HTMLinfo;
	public String websiteUrl;

	private String HTML_IMG_TAG_PATTERN = "(?i)<img([^>]+)>(.+?)";
	private String HTML_SRC_ATTR_PATTERN = "\\s*(?i)src\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";

	public HtmlImageReader(String HTMLinfo, String websiteUrl) {
		this.HTMLinfo = HTMLinfo;
		this.websiteUrl = websiteUrl;
	}

	public List<String> getImages() {
		String resultForATag = getIMGTagsFromHtmlFile(HTMLinfo);
		Set<String> result = SRCAttributeFromHtmlFile(resultForATag);
		List<String> list = new ArrayList<>(result);
		return list;
	}

	private Set<String> SRCAttributeFromHtmlFile(String resultForATag) {
		Pattern Hrefpattern = Pattern.compile(HTML_SRC_ATTR_PATTERN);
		Matcher HrefMatcher = Hrefpattern.matcher(resultForATag);
		Set<String> resultSet = new HashSet<>();
		String l = "";
		while (HrefMatcher.find()) {
			l = HrefMatcher.group().replaceAll("src=", "").replaceAll("\"", "").trim();
			resultSet.add(l);
		}

		return resultSet;
	}

	private String getIMGTagsFromHtmlFile(String HTMLPage) {
		Pattern pattern = Pattern.compile(HTML_IMG_TAG_PATTERN);
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
