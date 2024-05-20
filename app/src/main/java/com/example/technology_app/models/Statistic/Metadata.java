package com.example.technology_app.models.Statistic;

import java.util.List;

public class Metadata {
    List<Statistic> statistics;

    public List<Statistic> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Statistic> statistics) {
        this.statistics = statistics;
    }

    public Metadata(List<Statistic> statistics) {
        this.statistics = statistics;
    }
}
