package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.repositories.ReportRepository;
import net.croz.pancakes_unlimited.services.ReportService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReportServiceImpl implements ReportService
{
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository)
    {
        this.reportRepository = reportRepository;
    }

}
