package com.mike.CoronavirusTracker.services;

import com.mike.CoronavirusTracker.models.VirusData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {
    public static final String COVID_DATA = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<VirusData> data = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "0 11 * * * *")
    public void getVirusData() throws IOException {
        StringReader result = new StringReader(makeRequest());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(result);
        List<VirusData> localData = new ArrayList<>();
        for (CSVRecord record : records) {
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            localData.add(new VirusData().builder()
                    .state(record.get("Province/State"))
                    .country(record.get("Country/Region"))
                    .numOfInfected(Integer.parseInt(record.get(record.size() - 1)))
                    .diffFromPrevDay(latestCases - prevDayCases)
                    .build());
        }
        this.data = localData;
    }

    private String makeRequest() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(COVID_DATA, String.class);
    }

    public List<VirusData> getData() {
        return this.data;
    }
}
