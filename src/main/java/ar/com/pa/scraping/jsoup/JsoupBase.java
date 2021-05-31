package ar.com.pa.scraping.jsoup;

import java.io.IOException;
import org.jsoup.nodes.Document;

public interface JsoupBase {

	public void executor() throws IOException;
	public Document getDocument(String urlEquities) throws IOException;
}
