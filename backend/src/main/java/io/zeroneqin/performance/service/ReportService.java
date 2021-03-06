package io.zeroneqin.performance.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.zeroneqin.base.domain.*;
import io.zeroneqin.base.mapper.*;
import io.zeroneqin.base.mapper.ext.ExtLoadTestReportMapper;
import io.zeroneqin.commons.constants.PerformanceTestStatus;
import io.zeroneqin.commons.constants.ReportKeys;
import io.zeroneqin.commons.exception.MSException;
import io.zeroneqin.commons.utils.LogUtil;
import io.zeroneqin.commons.utils.ServiceUtils;
import io.zeroneqin.commons.utils.SessionUtils;
import io.zeroneqin.controller.request.OrderRequest;
import io.zeroneqin.dto.LogDetailDTO;
import io.zeroneqin.dto.ReportDTO;
import io.zeroneqin.i18n.Translator;
import io.zeroneqin.performance.base.*;
import io.zeroneqin.performance.controller.request.DeleteReportRequest;
import io.zeroneqin.performance.controller.request.ReportRequest;
import io.zeroneqin.performance.engine.Engine;
import io.zeroneqin.performance.engine.EngineFactory;
import io.zeroneqin.service.FileService;
import io.zeroneqin.service.TestResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReportService {

    @Resource
    private LoadTestReportMapper loadTestReportMapper;
    @Resource
    private ExtLoadTestReportMapper extLoadTestReportMapper;
    @Resource
    private LoadTestMapper loadTestMapper;
    @Resource
    private LoadTestReportResultMapper loadTestReportResultMapper;
    @Resource
    private LoadTestReportLogMapper loadTestReportLogMapper;
    @Resource
    private TestResourceService testResourceService;
    @Resource
    private LoadTestReportDetailMapper loadTestReportDetailMapper;
    @Resource
    private FileService fileService;

    public List<ReportDTO> getRecentReportList(ReportRequest request) {
        List<OrderRequest> orders = new ArrayList<>();
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setName("update_time");
        orderRequest.setType("desc");
        orders.add(orderRequest);
        request.setOrders(orders);
        request.setProjectId(SessionUtils.getCurrentProjectId());
        return extLoadTestReportMapper.getReportList(request);
    }

    public List<ReportDTO> getReportList(ReportRequest request) {
        request.setOrders(ServiceUtils.getDefaultOrder(request.getOrders()));
        request.setProjectId(SessionUtils.getCurrentProjectId());
        return extLoadTestReportMapper.getReportList(request);
    }

    public void deleteReport(String reportId) {
        if (StringUtils.isBlank(reportId)) {
            MSException.throwException("report id cannot be null");
        }

        LoadTestReport loadTestReport = loadTestReportMapper.selectByPrimaryKey(reportId);
        LoadTestWithBLOBs loadTest = loadTestMapper.selectByPrimaryKey(loadTestReport.getTestId());

        LogUtil.info("Delete report started, report ID: %s" + reportId);

        try {
            final Engine engine = EngineFactory.createEngine(loadTest);
            if (engine == null) {
                MSException.throwException(String.format("Delete report fail. create engine fail???report ID???%s", reportId));
            }

            String reportStatus = loadTestReport.getStatus();
            boolean isRunning = StringUtils.equals(reportStatus, PerformanceTestStatus.Running.name());
            boolean isStarting = StringUtils.equals(reportStatus, PerformanceTestStatus.Starting.name());
            boolean isError = StringUtils.equals(reportStatus, PerformanceTestStatus.Error.name());
            if (isRunning || isStarting || isError) {
                LogUtil.info("Start stop engine, report status: %s" + reportStatus);
                stopEngine(loadTest, engine);
            }
        } catch (Exception e) {
            LogUtil.error(e.getMessage(), e);
        }

        // delete load_test_report_result
        LoadTestReportResultExample loadTestReportResultExample = new LoadTestReportResultExample();
        loadTestReportResultExample.createCriteria().andReportIdEqualTo(reportId);
        loadTestReportResultMapper.deleteByExample(loadTestReportResultExample);

        // delete load_test_report_detail
        LoadTestReportDetailExample example = new LoadTestReportDetailExample();
        example.createCriteria().andReportIdEqualTo(reportId);
        loadTestReportDetailMapper.deleteByExample(example);

        // delete jtl file
        fileService.deleteFileById(loadTestReport.getFileId());

        loadTestReportMapper.deleteByPrimaryKey(reportId);
    }

    public void stopEngine(LoadTestWithBLOBs loadTest, Engine engine) {
        engine.stop();
        loadTest.setStatus(PerformanceTestStatus.Saved.name());
        loadTestMapper.updateByPrimaryKeySelective(loadTest);

    }

    public ReportDTO getReportTestAndProInfo(String reportId) {
        return extLoadTestReportMapper.getReportTestAndProInfo(reportId);
    }

    private String getContent(String id, ReportKeys reportKey) {
        LoadTestReportResultExample example = new LoadTestReportResultExample();
        example.createCriteria().andReportIdEqualTo(id).andReportKeyEqualTo(reportKey.name());
        List<LoadTestReportResult> loadTestReportResults = loadTestReportResultMapper.selectByExampleWithBLOBs(example);
        if (loadTestReportResults.size() == 0) {
            MSException.throwException("get report result error.");
        }
        return loadTestReportResults.get(0).getReportValue();
    }

    public List<Statistics> getReportStatistics(String id) {
        checkReportStatus(id);
        String reportValue = getContent(id, ReportKeys.RequestStatistics);
        return JSON.parseArray(reportValue, Statistics.class);
    }

    public List<Errors> getReportErrors(String id) {
        checkReportStatus(id);
        String content = getContent(id, ReportKeys.Errors);
        return JSON.parseArray(content, Errors.class);
    }

    public List<ErrorsTop5> getReportErrorsTOP5(String id) {
        checkReportStatus(id);
        String content = getContent(id, ReportKeys.ErrorsTop5);
        return JSON.parseArray(content, ErrorsTop5.class);
    }

    public TestOverview getTestOverview(String id) {
        checkReportStatus(id);
        String content = getContent(id, ReportKeys.Overview);
        return JSON.parseObject(content, TestOverview.class);
    }

    public ReportTimeInfo getReportTimeInfo(String id) {
        checkReportStatus(id);
        String content = getContent(id, ReportKeys.TimeInfo);
        return JSON.parseObject(content, ReportTimeInfo.class);
    }

    public List<ChartsData> getLoadChartData(String id) {
        checkReportStatus(id);
        String content = getContent(id, ReportKeys.LoadChart);
        return JSON.parseArray(content, ChartsData.class);
    }

    public List<ChartsData> getResponseTimeChartData(String id) {
        checkReportStatus(id);
        String content = getContent(id, ReportKeys.ResponseTimeChart);
        return JSON.parseArray(content, ChartsData.class);
    }

    public void checkReportStatus(String reportId) {
        LoadTestReport loadTestReport = loadTestReportMapper.selectByPrimaryKey(reportId);
        String reportStatus = "";
        if (loadTestReport != null) {
            reportStatus = loadTestReport.getStatus();
        }
        if (StringUtils.equals(PerformanceTestStatus.Error.name(), reportStatus)) {
            MSException.throwException("Report generation error!");
        }
    }

    public LoadTestReportWithBLOBs getLoadTestReport(String id) {
        return loadTestReportMapper.selectByPrimaryKey(id);
    }

    public List<LogDetailDTO> getReportLogResource(String reportId) {
        List<LogDetailDTO> result = new ArrayList<>();
        List<String> resourceIdAndIndexes = extLoadTestReportMapper.selectResourceId(reportId);
        resourceIdAndIndexes.forEach(resourceIdAndIndex -> {
            LogDetailDTO detailDTO = new LogDetailDTO();
            String[] split = StringUtils.split(resourceIdAndIndex, "_");
            String resourceId = split[0];
            TestResource testResource = testResourceService.getTestResource(resourceId);
            detailDTO.setResourceId(resourceIdAndIndex);
            if (testResource == null) {
                detailDTO.setResourceName(resourceId);
                result.add(detailDTO);
                return;
            }
            String configuration = testResource.getConfiguration();
            if (StringUtils.isBlank(configuration)) {
                detailDTO.setResourceName(resourceId);
                result.add(detailDTO);
                return;
            }
            JSONObject object = JSON.parseObject(configuration);
            if (StringUtils.isNotBlank(object.getString("masterUrl"))) {
                detailDTO.setResourceName(object.getString("masterUrl"));
                result.add(detailDTO);
                return;
            }
            if (StringUtils.isNotBlank(object.getString("ip"))) {
                detailDTO.setResourceName(object.getString("ip"));
                result.add(detailDTO);
            }
        });
        return result;
    }

    public List<LoadTestReportLog> getReportLogs(String reportId, String resourceId) {
        LoadTestReportLogExample example = new LoadTestReportLogExample();
        example.createCriteria().andReportIdEqualTo(reportId).andResourceIdEqualTo(resourceId);
        example.setOrderByClause("part");
        return loadTestReportLogMapper.selectByExampleWithBLOBs(example);
    }

    public void downloadLog(HttpServletResponse response, String reportId, String resourceId) throws Exception {
        LoadTestReportLogExample example = new LoadTestReportLogExample();
        LoadTestReportLogExample.Criteria criteria = example.createCriteria();
        criteria.andReportIdEqualTo(reportId).andResourceIdEqualTo(resourceId);
        example.setOrderByClause("part");

        long count = loadTestReportLogMapper.countByExample(example);

        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=jmeter.log");
            for (long i = 1; i <= count; i++) {
                example.clear();
                LoadTestReportLogExample.Criteria innerCriteria = example.createCriteria();
                innerCriteria.andReportIdEqualTo(reportId).andResourceIdEqualTo(resourceId).andPartEqualTo(i);

                List<LoadTestReportLog> loadTestReportLogs = loadTestReportLogMapper.selectByExampleWithBLOBs(example);
                LoadTestReportLog content = loadTestReportLogs.get(0);
                outputStream.write(content.getContent().getBytes());
                outputStream.flush();
            }
        }
    }

    public LoadTestReportWithBLOBs getReport(String reportId) {
        return loadTestReportMapper.selectByPrimaryKey(reportId);
    }

    public void updateStatus(String reportId, String status) {
        LoadTestReportWithBLOBs report = new LoadTestReportWithBLOBs();
        report.setId(reportId);
        report.setStatus(status);
        loadTestReportMapper.updateByPrimaryKeySelective(report);
    }

    public void deleteReportBatch(DeleteReportRequest reportRequest) {
        List<String> ids = reportRequest.getIds();
        ids.forEach(this::deleteReport);
    }

    public List<ChartsData> getErrorChartData(String id) {
        checkReportStatus(id);
        String content = getContent(id, ReportKeys.ErrorsChart);
        return JSON.parseArray(content, ChartsData.class);
    }

    public List<ChartsData> getResponseCodeChartData(String id) {
        checkReportStatus(id);
        String content = getContent(id, ReportKeys.ResponseCodeChart);
        return JSON.parseArray(content, ChartsData.class);
    }

    public byte[] downloadJtl(String reportId) {
        LoadTestReportWithBLOBs report = getReport(reportId);
        if (StringUtils.isBlank(report.getFileId())) {
            throw new RuntimeException(Translator.get("load_test_report_file_not_exist"));
        }
        return fileService.loadFileAsBytes(report.getFileId());
    }
}
