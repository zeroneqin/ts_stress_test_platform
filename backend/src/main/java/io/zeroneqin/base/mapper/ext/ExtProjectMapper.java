package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.controller.request.ProjectRequest;
import io.zeroneqin.dto.ProjectDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtProjectMapper {

    List<ProjectDTO> getProjectWithWorkspace(@Param("proRequest") ProjectRequest request);

    List<String> getProjectIdByWorkspaceId(String workspaceId);

    int removeIssuePlatform(@Param("platform") String platform, @Param("orgId") String orgId);
}
