package com.mike.CoronavirusTracker.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VirusData {
    private String state;
    private String country;
    private int numOfInfected;
    private int diffFromPrevDay;
}
