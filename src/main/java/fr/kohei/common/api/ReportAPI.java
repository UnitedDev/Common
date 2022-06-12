package fr.kohei.common.api;

import fr.kohei.common.cache.Report;

import java.util.List;

public interface ReportAPI {

    void addReport(Report report);

    void updateReport(Report report);

    List<Report> getReports();

}
