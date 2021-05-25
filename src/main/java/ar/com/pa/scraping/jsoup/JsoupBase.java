package ar.com.pa.scraping.jsoup;

import java.io.IOException;
import java.util.TreeSet;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.google.common.collect.ImmutableList;

public interface JsoupBase {

	public void executor() throws IOException;
	public Document getDocument(String urlEquities) throws IOException;
}
