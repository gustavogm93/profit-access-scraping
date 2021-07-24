package ar.com.pa.collections.coverage;

import ar.com.pa.collections.country.CountryProp;
import com.google.common.base.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor
@Data
public class CoverageCountry {

    @Id
    @NonNull
    private final String id;

    @Field(name = "totalMarketIndex")
    @NonNull
    private final Integer totalMarketIndex;

    @Field(name = "totalShares")
    @NonNull
    private final Integer totalShares;

    @Field(name = "coverageMarketIndex")
    private final Integer coverageMarketIndex;

    @Field(name = "coverageShares")
    private final Integer coverageShares;

    @Field(name = "coverage")
    @NonNull
    private Integer coverage;

    @Field(name = "scrapedAt")
    @NonNull
    private Date scrapedAt;


    public static Predicate<CoverageCountry> withoutCoverage = (coverageCountry) -> {
        return coverageCountry.getCoverage() <= 95;
    };

    public boolean isCoveraged() {
        return this.coverage <= 90;
    }

    //TODO: Coverage get return
}

