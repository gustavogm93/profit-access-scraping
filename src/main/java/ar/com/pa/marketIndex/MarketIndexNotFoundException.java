package ar.com.pa.marketIndex;

public class MarketIndexNotFoundException extends RuntimeException{

    public MarketIndexNotFoundException(String msg) {
        super(msg);
    }
}