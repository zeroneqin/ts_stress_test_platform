package io.zeroneqin.track.issue;

import io.zeroneqin.base.domain.ServiceIntegration;
import io.zeroneqin.base.mapper.IssuesMapper;
import io.zeroneqin.base.mapper.TestCaseIssuesMapper;
import io.zeroneqin.base.mapper.ext.ExtIssuesMapper;
import io.zeroneqin.commons.exception.MSException;
import io.zeroneqin.commons.user.SessionUser;
import io.zeroneqin.commons.utils.CommonBeanFactory;
import io.zeroneqin.commons.utils.EncryptUtils;
import io.zeroneqin.commons.utils.SessionUtils;
import io.zeroneqin.controller.request.IntegrationRequest;
import io.zeroneqin.service.IntegrationService;
import io.zeroneqin.service.ProjectService;
import io.zeroneqin.track.request.testcase.IssuesRequest;
import io.zeroneqin.track.service.TestCaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

public abstract class AbstractIssuePlatform implements IssuesPlatform {

    protected IntegrationService integrationService;
    protected TestCaseIssuesMapper testCaseIssuesMapper;
    protected ProjectService projectService;
    protected TestCaseService testCaseService;
    protected IssuesMapper issuesMapper;
    protected ExtIssuesMapper extIssuesMapper;

    protected String testCaseId;

    public AbstractIssuePlatform(IssuesRequest issuesRequest) {
        this.integrationService = CommonBeanFactory.getBean(IntegrationService.class);
        this.testCaseIssuesMapper = CommonBeanFactory.getBean(TestCaseIssuesMapper.class);
        this.projectService = CommonBeanFactory.getBean(ProjectService.class);
        this.testCaseService = CommonBeanFactory.getBean(TestCaseService.class);
        this.issuesMapper = CommonBeanFactory.getBean(IssuesMapper.class);
        this.extIssuesMapper = CommonBeanFactory.getBean(ExtIssuesMapper.class);
        this.testCaseId = issuesRequest.getTestCaseId();
    }

    protected String getPlatformConfig(String platform) {
        SessionUser user = SessionUtils.getUser();
        String orgId = user.getLastOrganizationId();

        IntegrationRequest request = new IntegrationRequest();
        if (StringUtils.isBlank(orgId)) {
            MSException.throwException("organization id is null");
        }
        request.setOrgId(orgId);
        request.setPlatform(platform);

        ServiceIntegration integration = integrationService.get(request);
        return integration.getConfiguration();
    }

    protected HttpHeaders auth(String apiUser, String password) {
        String authKey = EncryptUtils.base64Encoding(apiUser + ":" + password);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + authKey);
        return headers;
    }

    /**
     * 获取平台与项目相关的属性
     * @return 其他平台和本地项目绑定的属性值
     */
    abstract String getProjectId();

    protected boolean isIntegratedPlatform(String orgId, String platform) {
        IntegrationRequest request = new IntegrationRequest();
        request.setPlatform(platform);
        request.setOrgId(orgId);
        ServiceIntegration integration = integrationService.get(request);
        return StringUtils.isNotBlank(integration.getId());
    }

}
