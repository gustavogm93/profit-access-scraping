package ar.com.pa.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import ar.com.pa.scraping.ScrapingFetchImpl;
import ar.com.pa.service.mock.ScrappingFormatConstant;
import ar.com.pa.service.mock.constantMock;

public class ScrapingFetchImplTest {

	@Mock
	public static Elements elements;

	@Mock
	public static Elements quarterPeriodDates;

	public static Document doc;

	private ScrapingFetchImpl scrappingMock = new ScrapingFetchImpl();

	@Test
	@BeforeAll
	public static void getConnection() {

		try {
			doc = Jsoup.connect(ScrappingFormatConstant.httpUrl).get();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	void getPeriods() {

		Elements periodElements = scrappingMock.getElementsByTag("Th", doc);

		List<LocalDate> periodDate = scrappingMock.getPeriods(periodElements);
		List<LocalDate> listDate = constantMock.MAX_DATE;

		Assertions.assertEquals(periodDate, listDate);
	}

}
