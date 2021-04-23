package ar.com.pa.generics;


import com.google.common.base.Function;

import ar.com.pa.collections.marketIndex.MarketIndexDTO;
import ar.com.pa.collections.marketIndex.MarketIndexProp;
import ar.com.pa.collections.region.RegionDTO;
import ar.com.pa.collections.share.ShareDTO;
import ar.com.pa.collections.share.ShareProp;
import ar.com.pa.failed.FailedRegionDTO;

public class Mapper {

	/*
	public static Function<FailedRegionDTO, RegionDTO> failedToRegion = new Function<FailedRegionDTO, RegionDTO>() {

		@Override
		public RegionDTO apply(FailedRegionDTO failedRegion) {
			return new RegionDTO(failedRegion.getId(), failedRegion.getRegion(), failedRegion.getCountries());

		}

	};
	*/
	public static Function<ShareProp, ShareDTO> sharePropToShareDTO = new Function<ShareProp, ShareDTO>() {

		@Override
		public ShareDTO apply(ShareProp share) {
			return new ShareDTO(share.getCode(), share);
		}

	};
	
	public static Function<MarketIndexDTO, MarketIndexProp> MarketIndexDtoToMarketIndexProp = new Function<MarketIndexDTO, MarketIndexProp>() {

		@Override
		public MarketIndexProp apply(MarketIndexDTO marketIndexDTO) {
			return marketIndexDTO.getPropierties();
		}

	};

}
