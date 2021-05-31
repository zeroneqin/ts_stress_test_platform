package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.base.domain.Role;
import io.zeroneqin.base.domain.User;
import io.zeroneqin.controller.request.member.QueryMemberRequest;
import io.zeroneqin.controller.request.organization.QueryOrgMemberRequest;
import io.zeroneqin.dto.OrganizationMemberDTO;
import io.zeroneqin.dto.UserRoleHelpDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtUserRoleMapper {

    List<UserRoleHelpDTO> getUserRoleHelpList(@Param("userId") String userId);

    List<User> getMemberList(@Param("member") QueryMemberRequest request);

    List<User> getOrgMemberList(@Param("orgMember") QueryOrgMemberRequest request);

    List<OrganizationMemberDTO> getOrganizationMemberDTO(@Param("orgMember") QueryOrgMemberRequest request);

    List<Role> getOrganizationMemberRoles(@Param("orgId") String orgId, @Param("userId") String userId);

    List<Role> getWorkspaceMemberRoles(@Param("workspaceId") String workspaceId, @Param("userId") String userId);

    List<User> getBesideOrgMemberList(@Param("orgId") String orgId);


    List<User> getTestManagerAndTestUserList(@Param("request") QueryMemberRequest request);
}
