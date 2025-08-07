package com.in28minutes.microservices.currency_exchange_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.env.Environment;

import java.util.logging.Logger;

@RestController
public class CurrencyExchangeController {
    private Logger logger = Logger.getLogger(CurrencyExchangeController.class.getName());

    private final CurrencyExchangeRepository repository;
    private final Environment environment;

    public  CurrencyExchangeController(CurrencyExchangeRepository currencyExchangeRepository, Environment environment) {
        this.repository = currencyExchangeRepository;
        this.environment = environment;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to){
        logger.info("Retrieving exchange value called with " + from + " to " + to);
        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);

        if(currencyExchange == null){
            throw new RuntimeException("Unable to find data for " + from + " to " + to);
        }

        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);

        return currencyExchange;
    }
}

