package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.base.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtRoleMapper {

    List<Role> getRoleList(@Param("sign") String sign);
}
