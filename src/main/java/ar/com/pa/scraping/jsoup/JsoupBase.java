package ar.com.pa.scraping.jsoup;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface JsoupBase {

	public void executor() throws IOException, Exception;
	public Document getDocument(String urlEquities) throws IOException;
}
