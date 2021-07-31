package ar.com.pa.collections.country;

import ar.com.pa.utils.GenerateUUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


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

    @Field(name = "totalCoverage")
    private Integer totalCoverage;

    @Field(name = "coverageMarketIndex")
    private Integer coverageMarketIndex;

    @Field(name = "coverageShares")
    private Integer coverageShares;

    @Field(name = "isCovered")
    private Boolean isCovered = false;

    @Field(name = "lastScrapedAt")
    @NonNull
    private Date lastScrapedAt;


    private CoverageCountry(@NonNull Integer marketIndexes, @NonNull Integer shares) {
        this.id = GenerateUUID.generateUniqueId();
        this.totalMarketIndex = marketIndexes;
        this.totalShares = shares;
        this.isCoverageBase = isCoverageBase;
        this.lastScrapedAt = new Date();
    }

    public static CoverageCountry buildCoverageBaseToCompare(Integer totalMarketIndex, Integer totalShares) {
        return new CoverageCountry(totalMarketIndex, totalShares, true);
    }

    public static CoverageCountry createNewCoverage(){
            return new CoverageCountry(0,0, )
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

        if(coverageCountryBase.getTotalShares().equals(this.totalShares))
            this.coverageShares = 100;

        if(coverageCountryBase.getTotalShares() > this.totalShares) {
            this.coverageShares = this.totalShares * 100 / coverageCountryBase.totalShares;
            this.totalShares = coverageCountryBase.totalShares;
        }
        if(coverageCountryBase.getTotalShares() < this.totalShares)
            this.coverageShares = coverageCountryBase.totalShares  * 100 / this.totalShares;

    }

    public void generateMarketIndexCoverage(CoverageCountry coverageCountryBase) throws Exception {

        if(this.isCoverageBase)
            throw new Exception("You can't generate a Coverage from base");

        if(coverageCountryBase.totalMarketIndex.equals(this.totalMarketIndex))
            this.coverageMarketIndex = 100;

        if(coverageCountryBase.totalMarketIndex > this.totalMarketIndex) {
            this.coverageMarketIndex = this.totalMarketIndex * 100 / coverageCountryBase.coverageMarketIndex;
            this.totalMarketIndex = coverageCountryBase.totalMarketIndex;
        }

        if(coverageCountryBase.totalMarketIndex < this.totalMarketIndex)
            this.coverageMarketIndex = coverageCountryBase.totalMarketIndex  * 100 / this.totalMarketIndex;

    }

    public void setTotalCoverage(){
        this.totalCoverage = this.coverageShares + this.coverageMarketIndex / 2;
        if(this.totalCoverage >= 90)
            this.isCovered = true;
    }



}

