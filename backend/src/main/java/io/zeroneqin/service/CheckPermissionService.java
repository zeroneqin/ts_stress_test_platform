package io.zeroneqin.service;

import io.zeroneqin.base.domain.Project;
import io.zeroneqin.base.domain.UserRole;
import io.zeroneqin.base.mapper.ProjectMapper;
import io.zeroneqin.base.mapper.ext.*;
import io.zeroneqin.commons.utils.SessionUtils;
import io.zeroneqin.i18n.Translator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CheckPermissionService {
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ExtApiTestMapper extApiTestMapper;
    @Resource
    private ExtLoadTestMapper extLoadTestMapper;
    @Resource
    private ExtTestCaseMapper extTestCaseMapper;
    @Resource
    private ExtTestPlanMapper extTestPlanMapper;
    @Resource
    private ExtTestCaseReviewMapper extTestCaseReviewMapper;




    public void checkProjectOwner(String projectId) {
        Set<String> organizationIds = getUserRelatedOrganizationIds();
        Project project = projectMapper.selectByPrimaryKey(projectId);
        if (project == null) {
            return;
        }
        if (CollectionUtils.isEmpty(organizationIds)) {
            return;
        }
        if (!organizationIds.contains(project.getOrganizationId())) {
            throw new RuntimeException(Translator.get("check_owner_project"));
        }
    }

    private Set<String> getUserRelatedOrganizationIds() {
        return Objects.requireNonNull(SessionUtils.getUser()).getUserRoles().stream()
                .filter(ur ->
                        StringUtils.equalsAny(ur.getRoleId()))
                .map(UserRole::getSourceId)
                .collect(Collectors.toSet());
    }

    public void checkApiTestOwner(String testId) {
        // 关联为其他时
        if (StringUtils.equals("other", testId)) {
            return;
        }
        Set<String> organizationIds = getUserRelatedOrganizationIds();
        if (CollectionUtils.isEmpty(organizationIds)) {
            return;
        }

        int result = extApiTestMapper.checkApiTestOwner(testId, organizationIds);

        if (result == 0) {
            throw new RuntimeException(Translator.get("check_owner_test"));
        }
    }

    public void checkPerformanceTestOwner(String testId) {
        // 关联为其他时
        if (StringUtils.equals("other", testId)) {
            return;
        }
        Set<String> organizationIds = getUserRelatedOrganizationIds();
        if (CollectionUtils.isEmpty(organizationIds)) {
            return;
        }
        int result = extLoadTestMapper.checkLoadTestOwner(testId, organizationIds);

        if (result == 0) {
            throw new RuntimeException(Translator.get("check_owner_test"));
        }
    }

    public void checkTestCaseOwner(String caseId) {
        Set<String> organizationIds = getUserRelatedOrganizationIds();
        if (CollectionUtils.isEmpty(organizationIds)) {
            return;
        }

        int result = extTestCaseMapper.checkIsHave(caseId, organizationIds);
        if (result == 0) {
            throw new RuntimeException(Translator.get("check_owner_case"));
        }
    }

    public void checkTestPlanOwner(String planId) {
        Set<String> organizationIds = getUserRelatedOrganizationIds();
        if (CollectionUtils.isEmpty(organizationIds)) {
            return;
        }
        int result = extTestPlanMapper.checkIsHave(planId, organizationIds);
        if (result == 0) {
            throw new RuntimeException(Translator.get("check_owner_plan"));
        }
    }

    public void checkTestReviewOwner(String reviewId) {
        Set<String> organizationIds = getUserRelatedOrganizationIds();
        if (CollectionUtils.isEmpty(organizationIds)) {
            return;
        }
        int result = extTestCaseReviewMapper.checkIsHave(reviewId, organizationIds);
        if (result == 0) {
            throw new RuntimeException(Translator.get("check_owner_review"));
        }
    }
}
