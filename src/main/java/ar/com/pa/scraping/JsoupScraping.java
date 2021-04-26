package ar.com.pa.scraping;

import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.google.common.collect.ImmutableList;

import ar.com.pa.collections.summary.ScrapedData;

public interface JsoupScraping {

	public void executor() throws IOException;
	public Document getDocument(String urlEquities) throws IOException;
	public ImmutableList<? extends Object> getConstantsToFetch();
	public List<? extends ScrapedData> elementsToCountryScrapedCoverageData(Elements elements);
}
