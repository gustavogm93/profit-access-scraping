package ar.com.pa.service.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class constantMock {

	public static final List<LocalDate> MAX_DATE = new ArrayList<>(
													List.of(LocalDate.parse("2020-12-31"),
															LocalDate.parse("2019-12-31"),
															LocalDate.parse("2018-12-31"),
															LocalDate.parse("2017-12-31")));

}
