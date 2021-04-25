package ar.com.pa.scraping.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import com.google.common.collect.ImmutableList;
import ar.com.pa.collections.country.CountryDTO;
import ar.com.pa.collections.country.CountryProp;
import ar.com.pa.collections.country.CountryService;
import ar.com.pa.collections.marketIndex.MarketIndexDTO;
import ar.com.pa.collections.marketIndex.MarketIndexProp;
import ar.com.pa.collections.marketIndex.MarketIndexService;
import ar.com.pa.collections.region.RegionDTO;
import ar.com.pa.collections.region.RegionProp;
import ar.com.pa.collections.region.RegionService;
import ar.com.pa.collections.share.ShareProp;
import ar.com.pa.enums.utils.Url;
import ar.com.pa.generics.Property;
import ar.com.pa.utils.Msg;
import static ar.com.pa.generics.Mapper.*;

@Component
public class InvestingFetchCountry extends InvestingEquityPage {
	// InvestmentBuildSummaryCountry

	private RegionService regionService;

	private CountryService countryService;

	private MarketIndexService marketIndexService;

	private String countryId;
	
	private static final Logger log = LoggerFactory.getLogger(InvestingFetchCountry.class);

	private InvestingFetchCountry(RegionService regionService, MarketIndexService marketIndexService,
			CountryService countryService) {
		this.regionService = regionService;
		this.marketIndexService = marketIndexService;
		this.countryService = countryService;
	}

	public void executor(@Nullable String regionTitle) {

		startSeleniumDriver();
		setUpSeleniumDriver();

		ImmutableList<RegionDTO> regions = getConstantsToFetch(regionTitle);
		regions.stream().forEach(region -> {

			region.getCountries().stream().limit(1).forEach(country -> {

				try {
					
					fetchProcess(country, region.getProperties(), driver);
					
				} catch (Exception e) {
					log.error(Msg.error(country, e.getMessage()));
					
					driver.quit();
					
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

		log.info(String.format("Getting %s", regionTitle));
		return ImmutableList.copyOf(regionService.findByTitle(regionTitle));

	}

	private void fetchProcess(CountryProp country, RegionProp region) throws Exception {

		getPage(String.format("%s%s", Url.equities, country.getCode()));

		List<MarketIndexDTO> MarketIndexList = fetchMarketIndexes(driver);

		buildAndSaveCountry(country, region, MarketIndexList);

		saveMarketIndexList(MarketIndexList);

	}

	private void buildAndSaveCountry(CountryProp country, RegionProp region, List<MarketIndexDTO> MarketIndexList)
			throws Exception {
		
		CountryProp countryToAdd = getCountryToSave(country);

		TreeSet<MarketIndexProp> marketIndexes = convertMarketIndexDTOListToProp(MarketIndexList);

		Set<ShareProp> shares = getSharesByCountry(MarketIndexList);

		CountryDTO newCountryDTO = new CountryDTO(countryId, countryToAdd, region, shares, marketIndexes);

		countryService.add(newCountryDTO);

	}

	private void saveMarketIndexList(List<MarketIndexDTO> MarketIndexList) {

		marketIndexService.addAll(MarketIndexList);

	}

	private List<MarketIndexDTO> fetchMarketIndexes(WebDriver driver) {
		log.info(Msg.fetchingMarketIndex);

		List<MarketIndexDTO> MarketIndexDTOList = new ArrayList<>();

		countryId = getCountryId();

		List<WebElement> listOfoptionsMarketIndex = getMarketIndexOptions();

		for (WebElement optionMarketIndex : listOfoptionsMarketIndex) {
			log.info(Msg.fetchingMarketIndex);

			var idMarketIndex = getIdFromMarketIndexOption(optionMarketIndex);
			var titleMarketIndex = getTitleFromMarketIndexOption(optionMarketIndex);

			clickOnOption(optionMarketIndex);

			TreeSet<ShareProp> shares = getSharesByElement(driver);

			MarketIndexProp marketIndex = new MarketIndexProp(idMarketIndex, titleMarketIndex);
			MarketIndexDTO marketIndexDTO = new MarketIndexDTO(idMarketIndex, countryId, marketIndex, shares);

			MarketIndexDTOList.add(marketIndexDTO);

		}
		return MarketIndexDTOList;
	}

	private TreeSet<ShareProp> getSharesByElement(WebDriver driver) {
		log.info(Msg.fetchingShares);
		TreeSet<ShareProp> shareList = new TreeSet<ShareProp>(Property.byTitle);
		List<WebElement> shareElements = getSharesInTable();

		for (WebElement shareElement : shareElements) {

			String shareTitle = getShareTitle(shareElement);
			String shareId = getShareId(shareElement);

			ShareProp share = new ShareProp(shareId, shareTitle);
			shareList.add(share);

		}
		log.info(Msg.saveShares);
		return shareList;
	}

	private CountryProp getCountryToSave(CountryProp country) {
		var countryID = getCountryId();
		return new CountryProp(countryID, country.getTitle());
	}

	private Set<ShareProp> getSharesByCountry(List<MarketIndexDTO> marketIndexListDTO) throws Exception {

		Optional<MarketIndexDTO> marketIndexOfAllSharesByCountry = marketIndexListDTO.stream()
				.filter(isOverallMarketIndex).findFirst();

		if (marketIndexOfAllSharesByCountry.isPresent()) {
			MarketIndexDTO marketIndex = marketIndexOfAllSharesByCountry.get();
			Set<ShareProp> sharesByCountry = marketIndex.getShares();
			return sharesByCountry;
		}

		throw new Exception();
	}

	public static Predicate<MarketIndexDTO> isOverallMarketIndex = (marketIndex) -> marketIndex.getPropierties()
			.getCode().equalsIgnoreCase("all");

}
