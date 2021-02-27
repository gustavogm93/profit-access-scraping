package ar.com.pa.service;


import java.io.IOException;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import ar.com.pa.services.ScrappingFormat;
import ar.com.pa.utils.MapperUtils;
import ar.com.pa.utils.PatternResource;
import ar.com.pa.utils.ValidateUtils;


public class ScrappingFormatTest {
	
	@Mock
	public static Elements elements;
	
	@Mock
	public static Elements quarterPeriodDates;

	private ScrappingFormat scrappingMock = new ScrappingFormat(); 
	

	@Test
	@BeforeAll
	public static void getConnection() {
		
		Document doc;
		try {
			doc = Jsoup.connect(ScrappingFormatConstant.httpUrl).get();
			setElements(doc.getElementsByTag("td"));
			setQuarterPeriodDates(doc.getElementsByTag("th"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	@Test
	void getValidElementsTest() {
		
		List<String> validElements = scrappingMock.getValidElements(getElements());
		
		List<String> invalidElements = Lists.newArrayList(ScrappingFormatConstant.mockElements);
					
		List<String> mockList = Stream.of(validElements, invalidElements)
				                .flatMap(Collection::stream)
				                .filter(ValidateUtils::isSummaryModelValue)
				                .collect(Collectors.toList());
				
		 Assertions.assertEquals( mockList, validElements); 		
	}
	
	
	
	@Test
	void getQuarterPeriodListTest() {
			
			List<String> mockedList = Lists.newArrayList(ScrappingFormatConstant.mockPeriods);  
			
			ImmutableList<Date> mockQuarterPeriodList = ImmutableList.copyOf(mockedList.stream()
																						 .distinct()
																						 .filter(PatternResource::dateStringPattern)
																						 .map(MapperUtils::toDate)
																						 .collect(Collectors.toList())); 


			 List<Date> quarterPeriodDates = scrappingMock.getQuarterPeriodDate(getQuarterPeriodDates());
		
			 Assertions.assertEquals( quarterPeriodDates, mockQuarterPeriodList); 
	}


	public static void setElements(Elements elements) {
		ScrappingFormatTest.elements = elements;
	}


	public static void setQuarterPeriodDates(Elements quarterPeriodDates) {
		ScrappingFormatTest.quarterPeriodDates = quarterPeriodDates;
	}


	public static Elements getElements() {
		return elements;
	}
	public static Elements getQuarterPeriodDates() {
		return quarterPeriodDates;
	}


	
}
