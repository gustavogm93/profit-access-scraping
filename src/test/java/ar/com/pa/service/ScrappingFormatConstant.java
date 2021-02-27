package ar.com.pa.service;

public class ScrappingFormatConstant {

	
	public final static String[] mockPeriods = {"Dec 31, 2020","Sep 30, 2020","5 july of 2020","dicy 2020 20",
			"20/20/2020" ,"Jun 30, 2020", "Mar 31, 2020","320","2131","date wrong",   
			"20/20/2020" ,"2020-20-17", "00/08/9999","50/58/5860","2131","date wrong"};
	
	public final static String[] mockElements =  {
			//Invalid Format
			"Invalid Class","dds646", "---", "$%$%", "Cash From Financing Invalidities",
			"Cash From Financing Invalidities2", "Gross ProFIL", "Griss Profish", "Total Noquitry", "---222222", "mocki",
			"From Invalid Cash","542352dal", "dolar","margin failed","github", "pepe", "d020" , "Total Money", "T$#%#"};
	
	public final static String httpUrl = "https://www.investing.com/instruments/Financials/changesummaryreporttypeajax?action=change_report_type&pid=243&financial_id=243&ratios_id=243&period_type=Interim";


}
