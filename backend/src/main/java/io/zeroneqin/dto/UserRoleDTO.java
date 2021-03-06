package io.zeroneqin.dto;

import io.zeroneqin.base.domain.Role;
import io.zeroneqin.base.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRoleDTO {

    private String userId;
    private List<Role> roles;
    private List<UserRole> userRoles;
    private static final long serialVersionUID = 1L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
