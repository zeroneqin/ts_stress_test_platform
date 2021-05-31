package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.api.dto.APITestResult;
import io.zeroneqin.api.dto.QueryAPITestRequest;
import io.zeroneqin.base.domain.ApiTest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ExtApiTestMapper {
    List<APITestResult> list(@Param("request") QueryAPITestRequest request);

    List<ApiTest> getApiTestByProjectId(String projectId);

    List<ApiTest> listByIds(@Param("ids") List<String> ids);

    int checkApiTestOwner(@Param("testId") String testId, @Param("organizationIds") Set<String> organizationIds);

}
