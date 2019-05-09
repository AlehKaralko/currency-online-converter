package com.korolko.converter.controller;

import com.korolko.converter.domain.Currency;
import com.korolko.converter.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CurrencyRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRestController.class);

    private CurrencyService currencyService;

    @Autowired
    public CurrencyRestController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        List<Currency> currencies = currencyService.getAll();

        if (currencies == null) {
            LOGGER.info("Currencies are not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("Get all currencies.");
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping(value = "/convert", params = {"current", "target", "amount"},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BigDecimal> convert(@RequestParam("current") String currentAbbr,
                                              @RequestParam("target") String targetAbbr,
                                              @RequestParam("amount") double amount) {
        BigDecimal convertValue = currencyService.convert(currentAbbr, targetAbbr, amount);

        return new ResponseEntity<>(convertValue, HttpStatus.OK);
    }
}
