package com.nn.moneyflow.service;

import com.nn.moneyflow.utils.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
public class NbpExchangeRateService {

    private static final Integer SCALE = 4;

    @Value("${api.nbp.exchange-rate-url}")
    private String nbpExchangeRateUrl;

    private final RestTemplate restTemplate = new RestTemplate();


    public BigDecimal getExchangeRate(String srcCurrency, String dstCurrency) {

        String currencyCode = srcCurrency;
        if (currencyCode.equals(Currency.PLN.name())) {
            currencyCode = dstCurrency;
        }
        String url = nbpExchangeRateUrl.replace("{currencyCode}", currencyCode);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> rates = (List<Map<String, Object>>) response.get("rates");
        BigDecimal rate = BigDecimal.valueOf((Double) rates.getFirst().get("mid"));
        if (!srcCurrency.equals(Currency.PLN.name())) {
            return BigDecimal.ONE.divide(rate, SCALE, RoundingMode.HALF_UP);
        }
        return rate;
    }

}
