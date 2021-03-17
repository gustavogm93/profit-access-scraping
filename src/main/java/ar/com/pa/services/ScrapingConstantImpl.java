package ar.com.pa.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import io.vavr.control.Try;

@Service
public class ScrapingConstantImpl {

	private static final Logger logger = LoggerFactory.getLogger(ScrapingConstantImpl.class);

	public ScrapingConstantImpl() {
	};

	public void getCountryConstant() {

		logger.info("Getting Countries Enum");

		ImmutableMap<String, String> immutableMap = ImmutableMap.of("1", "Americas", "2", "Europe", "3", "Asia-Pacific",
				"4", "Middle-East", "5", "Africa");

		Try<Document> doc = Try.of(() -> Jsoup.connect("https://www.investing.com/equities/").get());

		doc.onSuccess(data -> {

			for (Entry<String, String> region : immutableMap.entrySet()) {
				saveCountriesByRegion(data, region);
			}

		});

		doc.onFailure(ex -> {
			logger.error(ex.toString());
		});

	}

	public void saveCountriesByRegion(Document data, Entry<String, String> region) {

		String idElement = String.format("#cdregion%s", region.getKey());
		StringBuilder countryAndCode = new StringBuilder();

		Element regionElements = data.select(idElement).first();
		Elements elements = regionElements.select("a");

		for (Element element : elements) {
			getCountryEnumFormat(element,countryAndCode);
		}

		System.out.println(countryAndCode.toString());
	}

	public void getCountryEnumFormat(Element element, StringBuilder countryAndCode) {

		String urlCountry = element.attr("href");
		String titleCountry = element.text();
		if (Objects.nonNull(urlCountry) && urlCountry.contains("/equities/")) {
			
			String[] matcherUrl = Arrays.copyOfRange(urlCountry.split("/"), 2, 3);

			String enumFormat = String.format("%s(\"%s\"),", titleCountry.replaceAll("\\s", ""), matcherUrl[0]);

			countryAndCode.append(enumFormat);
		}

	}
	
}
