package ar.com.pa.generics;

import com.google.common.base.Function;

import ar.com.pa.failed.FailedRegionDTO;
import ar.com.pa.model.dto.RegionDTO;
import ar.com.pa.share.Share;
import ar.com.pa.share.ShareDTO;

public class Mapper {

	public static Function<FailedRegionDTO, RegionDTO> failedToRegion = new Function<FailedRegionDTO, RegionDTO>() {

		@Override
		public RegionDTO apply(FailedRegionDTO failedRegion) {
			return new RegionDTO(failedRegion.getId(), failedRegion.getRegion(), failedRegion.getCountries());

		}

	};
	public static Function<Share, ShareDTO> shareToShareDTO = new Function<Share, ShareDTO>() {

		@Override
		public ShareDTO apply(Share share) {
			return new ShareDTO(share.getCode(), share);
		}

	};
}
