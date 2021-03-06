package io.zeroneqin.job.sechedule;

import io.zeroneqin.api.dto.SaveAPITestRequest;
import io.zeroneqin.api.service.APITestService;
import io.zeroneqin.commons.constants.ReportTriggerMode;
import io.zeroneqin.commons.constants.ScheduleGroup;
import io.zeroneqin.commons.utils.CommonBeanFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

public class ApiTestJob extends MsScheduleJob {

    private APITestService apiTestService;
    public ApiTestJob() {
        apiTestService = (APITestService) CommonBeanFactory.getBean(APITestService.class);
    }

    @Override
    void businessExecute(JobExecutionContext context) {
        SaveAPITestRequest request = new SaveAPITestRequest();
        request.setId(resourceId);
        request.setUserId(userId);
        request.setTriggerMode(ReportTriggerMode.SCHEDULE.name());
        apiTestService.run(request);
    }

    public static JobKey getJobKey(String testId) {
        return new JobKey(testId, ScheduleGroup.API_TEST.name());
    }

    public static TriggerKey getTriggerKey(String testId) {
        return new TriggerKey(testId, ScheduleGroup.API_TEST.name());
    }
}

