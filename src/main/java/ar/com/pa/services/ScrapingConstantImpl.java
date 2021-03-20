package ar.com.pa.services;
//TODO Restructurar los nombres de Scrapping de todos

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.ImmutableList;
import ar.com.pa.enums.RegionConstant;
import ar.com.pa.model.Country;
import ar.com.pa.model.RegionDTO;
import ar.com.pa.repository.RegionRepository;
import ar.com.pa.utils.SerializeImpl;
import io.vavr.control.Try;

@Service
public class ScrapingConstantImpl {

	private static final Logger logger = LoggerFactory.getLogger(ScrapingConstantImpl.class);

	SerializeImpl serializeImpl;

	RegionRepository regionRepository;

	@Autowired
	public ScrapingConstantImpl(SerializeImpl serializeImpl, RegionRepository regionRepository) {
		this.serializeImpl = serializeImpl;
		this.regionRepository = regionRepository;
	};

	public void getCountryConstant() {
		logger.info("Getting countries by regions");

		ImmutableList<RegionConstant> regionsMap = ImmutableList.copyOf(Arrays.asList(RegionConstant.values()));

		Try<Document> doc = Try.of(() -> Jsoup.connect("https://www.investing.com/equities/").get());

		doc.onSuccess(data -> {

			regionsMap.stream().forEach((region) -> saveCountriesByRegion(data, region));

		});

		doc.onFailure(ex -> {
			logger.error(ex.toString());
		});

	}

	public void saveCountriesByRegion(Document data, RegionConstant region) {

		RegionDTO regionDTO = new RegionDTO();
		List<Country> countries = getCountryList(data, region);
		
		regionDTO.setId(region.getCode());
		regionDTO.setTitle(region.getTitle());		
		regionDTO.setCountries(countries);
		
		regionRepository.save(regionDTO);
	}

	public List<Country> getCountryList(Document data, RegionConstant region) {

		String idElement = String.format("#cdregion%s", region.getCode());

		Elements regionElements = data.select(idElement).first().select("a");

		return regionElements.stream().map(this::findCountriesByElement).distinct().filter(Objects::nonNull)
				.collect(Collectors.toList());

	}

	private Country findCountriesByElement(Element element) {

		String urlCountry = element.attr("href");
		String titleCountry = element.text();

		Country country = new Country();

		if (Objects.nonNull(urlCountry) && urlCountry.contains("/equities/")
				&& checkisMarketOverview(titleCountry) == false) {

			String[] matcherUrl = Arrays.copyOfRange(urlCountry.split("/"), 2, 3);

			country.setCode(matcherUrl[0]);
			country.setTitle(titleCountry.replaceAll(" ", ""));

			return country;
		} else {
			return null;
		}
	}

	public void saveMarketIndex(RegionDTO region) {
		// TODO GUARDAR EN MARKET TODOS LOS INDEX
		Try<Document> doc = Try.of(() -> Jsoup.connect("https://www.investing.com/equities/argentina").get());

		doc.onSuccess(data -> {

			Elements regionElements = data.select("#stocksFilter");

		});

	}

	public boolean checkisMarketOverview(String s) {
		return s.contains("Market Overview");
	}

	public void getMarketIndex(Document data) {

	}

	public void getStockIndex(Document data) {

	}

}
