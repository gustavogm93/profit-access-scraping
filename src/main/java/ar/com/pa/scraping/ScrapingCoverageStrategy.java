package ar.com.pa.scraping;

import java.util.*;
import java.util.function.Predicate;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import com.google.common.collect.ImmutableList;
import ar.com.pa.collections.country.*;
import ar.com.pa.collections.coverage.*;
import ar.com.pa.collections.region.*;
import ar.com.pa.scraping.selenium.InvestmentEquityPage;
import ar.com.pa.utils.Msg;

@Component
public class ScrapingCoverageStrategy extends InvestmentEquityPage {

	private CoverageCountryService coverageCountryService;

	private RegionService regionService;

	private static final Logger log = LoggerFactory.getLogger(ScrapingCoverageStrategy.class);

	private ScrapingCoverageStrategy(RegionService regionService, CoverageCountryService coverageCountryService) {
		this.regionService = regionService;
		this.coverageCountryService = coverageCountryService;
	}

	public void executor(@Nullable String regionTitle) {

		startSeleniumDriver();
		setUpSeleniumDriver();

		ImmutableList<RegionDTO> regions = getConstantsToFetch(regionTitle);

		regions.stream().forEach(region -> {

			region.getCountries().stream().limit(1).forEach(country -> {

				try {
					fetchProcess(country, region.getProperties());
				} catch (Exception e) {
					log.error(Msg.error(country, e.getMessage()));
					driver.close();
					
					startSeleniumDriver();
					setUpSeleniumDriver();
				}

			});
			driver.close();
		});

	}

	public ImmutableList<RegionDTO> getConstantsToFetch(@Nullable String regionTitle) {

		if (Objects.isNull(regionTitle) || regionTitle.isEmpty()) {
			log.info("Getting list of all regions..");
			return ImmutableList.copyOf(regionService.getAll());
		}

		log.info("Getting countries in Region: {}", regionTitle);
		return ImmutableList.copyOf(regionService.findByTitle(regionTitle));

	}

	private void fetchProcess(CountryProp country, RegionProp region) throws Exception {
		
		log.info("Starting fetching process of country: {} ", country.getTitle());
		
		goToCountryPage(country.getCode());

		List<CoverageMarketIndex> coverageMarketIndexList = fetchMarketIndexes();

		buildAndSaveCoverageCountry(country, region, coverageMarketIndexList);

	}

	private List<CoverageMarketIndex> fetchMarketIndexes() {
		log.info("Starting fetching market Index");
		
		List<CoverageMarketIndex> coverageMarketIndexList = new ArrayList<>();
		
		for (WebElement optionMarketIndex : getOptionsOfMarketIndex()) {

			var idMarketIndex = getIdMarketIndex(optionMarketIndex);
			var titleMarketIndex = getTitleMarketIndex(optionMarketIndex);
			log.info("Getting market Index of {}", titleMarketIndex);
			
			optionMarketIndex.click();
			wait.until(checkForTableShares());
			
			log.info("Getting shares of market Index {}", titleMarketIndex);
			int shareQuantities = getSharesByElement();

			CoverageMarketIndex coverageMarketIndex = new CoverageMarketIndex(idMarketIndex, titleMarketIndex,
					shareQuantities);

			coverageMarketIndexList.add(coverageMarketIndex);

		}
		return coverageMarketIndexList;
	}

	private int getSharesByElement() {
		int shareQuantities = 0;
		for (WebElement shareElement : getShareElements()) {

			String shareTitle = getShareTitle(shareElement);
			String shareId = getShareId(shareElement);

			if (shareId.length() > 0 && shareTitle.length() > 0)
				shareQuantities++;

		}
		return shareQuantities;
	}

	private int getSharesByCountry(List<CoverageMarketIndex> coverageMarketIndexList) throws Exception {

		Optional<CoverageMarketIndex> optionalCoverageMarketIndexOfGeneralCountry = coverageMarketIndexList.stream()
				.filter(isOverallMarketIndex).findFirst();

		if (optionalCoverageMarketIndexOfGeneralCountry.isPresent()) {
			CoverageMarketIndex coverageMarketIndexOfGeneralCountry = optionalCoverageMarketIndexOfGeneralCountry.get();
			int sharesQuantityOfCountry = coverageMarketIndexOfGeneralCountry.getSharesQuantity();
			return sharesQuantityOfCountry;
		}

		throw new Exception("Error getting general Country market index when get Shares by Country");
	}

	public static Predicate<CoverageMarketIndex> isOverallMarketIndex = (marketIndex) -> {
		return marketIndex.getId().equalsIgnoreCase("all");
	};

	private void buildAndSaveCoverageCountry(CountryProp country, RegionProp region,
			List<CoverageMarketIndex> coverageMarketIndexList) throws Exception {

		int sharesQuantityByCountry = getSharesByCountry(coverageMarketIndexList);

		CoverageCountry coverageCountry = new CoverageCountry(country.getCode(), country.getTitle(),
				coverageMarketIndexList, sharesQuantityByCountry);

		coverageCountryService.add(coverageCountry);

	}

}
