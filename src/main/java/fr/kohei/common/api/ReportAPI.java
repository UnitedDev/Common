package fr.kohei.common.api;

import fr.kohei.common.cache.data.Report;

import java.util.List;
import java.util.Queue;

public interface ReportAPI {
    void addReport(Report report);

    void updateReport(Report report);

    Queue<Report> getReports();
}
