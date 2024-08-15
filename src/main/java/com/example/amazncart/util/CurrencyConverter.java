package com.example.amazncart.util;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyConverter {

	private static final String API_URL = "https://api.exchangeratesapi.io/latest?access_key=45779b791652a3ad55e09bbd6812e8a4";

	public Double convertToINR(Double amount, String currency) {
	    RestTemplate restTemplate = new RestTemplate();
	    String response = restTemplate.getForObject(API_URL, String.class);

	    JSONObject jsonObject = new JSONObject(response);
	    JSONObject rates = jsonObject.getJSONObject("rates");

	    if (rates.has(currency)) {
	        Double rateInCurrency = rates.getDouble(currency);
	        return amount * (rates.getDouble("INR") / rateInCurrency);
	    } else {
	        throw new RuntimeException("Currency not supported: " + currency);
	    }
	}

}
