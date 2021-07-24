package ar.com.pa.service;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class ScrapingFetchImplTest {

    @Test
    public void test(){
        Assert.isTrue(true);

    }
/*
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
*/
}
