package ar.com.pa.scraping;

import java.io.IOException;
import java.util.TreeSet;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.google.common.collect.ImmutableList;

public interface JsoupScraping {

	public void executor() throws IOException;
	public Document getDocument(String urlEquities) throws IOException;
	public ImmutableList<? extends Object> getConstantsToFetch();
	public TreeSet<? extends Object> elementsToTreeSet(Elements elements);
}
