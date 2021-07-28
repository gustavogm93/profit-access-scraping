package ar.com.pa.collections.country;

import ar.com.pa.collections.marketIndex.MarketIndexProp;
import ar.com.pa.collections.share.ShareProp;
import ar.com.pa.utils.GenerateUUID;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Set;


@Data
public class CoverageCountry {

    @Id
    private String id;

    @Field(name = "totalMarketIndex")
    @NonNull
    private Integer totalMarketIndex;

    @Field(name = "totalShares")
    @NonNull
    private Integer totalShares;

    @Field(name = "coverage")
    private Integer totalCoverage;

    @Field(name = "coverageMarketIndex")
    private Integer coverageMarketIndex;

    @Field(name = "coverageShares")
    private Integer coverageShares;

    @Field(name = "isCovered")
    private Boolean isCovered = false;

    @Field(name = "isCoverageBase")
    private Boolean isCoverageBase = false;

    @Field(name = "firstScraping")
    @NonNull
    private Boolean firstScraping;

    @Field(name = "scrapedAt")
    @NonNull
    private Date scrapedAt;


    private CoverageCountry(@NonNull Integer marketIndexes, @NonNull Integer shares) {
        this.id = GenerateUUID.generateUniqueId();
        this.totalMarketIndex = marketIndexes;
        this.totalShares = shares;
        this.scrapedAt = new Date();
    }

    public static CoverageCountry buildCoverageBaseToCompare(Integer totalMarketIndex, Integer totalShares) {
     CoverageCountry coverageCountryBase = new CoverageCountry(totalMarketIndex, totalShares);
     coverageCountryBase.setIsCoverageBase(true);
     return coverageCountryBase;
    }

    public static CoverageCountry buildCoverage(Set<MarketIndexProp> marketIndexes, Set<ShareProp> shares) {
        CoverageCountry coverage = new CoverageCountry(marketIndexes.size(), shares.size());
        return coverage;
    }

    public void compareAndFill(CoverageCountry coverageBase) throws Exception {
        if(!coverageBase.getIsCoverageBase())
            throw new Exception("You can't Compare to not Coverage base");

        if(this.getIsCoverageBase())
            throw new Exception("You can't compare a Coverage base to a Coverage Base");

        this.generateMarketIndexCoverage(coverageBase);
        this.generateShareCoverage(coverageBase);
    }

    public void generateShareCoverage(CoverageCountry coverageCountryBase) throws Exception {
        if(this.isCoverageBase)
            throw new Exception("You can't generate a Coverage from base");

        this.coverageShares = this.totalShares * 100 / coverageCountryBase.totalShares;
    }

    public void generateMarketIndexCoverage(CoverageCountry coverageCountryBase) throws Exception {
        if(this.isCoverageBase)
            throw new Exception("You can't generate a Coverage from base");

        this.coverageShares = this.totalMarketIndex * 100 / coverageCountryBase.totalMarketIndex;
    }

    public void setTotalCoverage(){
        this.totalCoverage = this.coverageShares + this.coverageMarketIndex / 2;
        if(this.totalCoverage >= 90)
            this.isCovered = true;
    }

}

