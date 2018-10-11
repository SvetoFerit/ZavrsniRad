package com.example.sveto.zavrsnirad.models;

public class FitbitParameters {
    public static int STEPS=0;
    public static int CALORIES=0;

    private Summary summary;

    public FitbitParameters(Summary summary) {
        this.summary = summary;
    }

    public Summary getSummary() {
        return summary;
    }
}
