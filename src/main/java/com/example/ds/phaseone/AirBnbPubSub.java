package com.example.ds.phaseone;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AirBnbPubSub {
	
	private static RestTemplate restTemplate;
	
	public AirBnbPubSub(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(AirBnbPubSub.class, args);
		saveSates();
	}


	
	public static void saveSates() {
		Map<String, String> country = new HashMap<>();
		country.put("country", "United States");
		LinkedHashMap<String, LinkedHashMap> resultFromAPI = null;

		try {
			resultFromAPI = restTemplate.postForObject(new URI("https://countriesnow.space/api/v0.1/countries/states"), country, LinkedHashMap.class);
		} catch (RestClientException e) {
			System.out.println("getStates rest error- " + e.getMessage());
		} catch (URISyntaxException e) {
			System.out.println("getStates url error- " + e.getMessage());
		}

		System.out.println("completed getStates");
		List<Map<String, String>> states = (List<Map<String, String>>)resultFromAPI.get("data").get("states");
		for (Map<String, String> m : states) {
			System.out.println(m.get("name") + ",  " + m.get("state_code"));
		}
	}

}
