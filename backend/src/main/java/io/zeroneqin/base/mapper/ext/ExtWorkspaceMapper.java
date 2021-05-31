package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.controller.request.WorkspaceRequest;
import io.zeroneqin.dto.WorkspaceDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtWorkspaceMapper {

    List<WorkspaceDTO> getWorkspaceWithOrg(@Param("request") WorkspaceRequest request);
    List<String> getWorkspaceIdsByOrgId(@Param("orgId") String orgId);
}
