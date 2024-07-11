package com.myfund.configs;

import com.myfund.models.BankName;
import com.myfund.services.BudgetService;
import com.myfund.services.csv.CsvParser;
import com.myfund.services.csv.MIlleniumCsvParser;
import com.myfund.services.csv.SantanderCsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CsvParserConfig {
    private final BudgetService budgetService;

    @Autowired
    public CsvParserConfig(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Bean
    public Map<BankName, CsvParser> parserMap() {
        return Map.of(BankName.SANTANDER, new SantanderCsvParser(budgetService),
                BankName.MILLENIUM, new MIlleniumCsvParser(budgetService));
    }
}

