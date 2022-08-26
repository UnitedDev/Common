package fr.uniteduhc.common.api;

import fr.uniteduhc.common.cache.data.Report;

import java.util.Queue;

public interface ReportAPI {
    void addReport(Report report);

    void updateReport(Report report);

    Queue<Report> getReports();
}
