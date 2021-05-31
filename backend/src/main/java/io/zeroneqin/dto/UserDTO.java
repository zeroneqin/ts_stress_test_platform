package io.zeroneqin.dto;

import io.zeroneqin.base.domain.Role;
import io.zeroneqin.base.domain.User;
import io.zeroneqin.base.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDTO extends User {

    private List<Role> roles = new ArrayList<>();

    private List<UserRole> userRoles = new ArrayList<>();

    private static final long serialVersionUID = 1L;

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
