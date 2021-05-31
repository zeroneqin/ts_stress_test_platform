package io.zeroneqin.controller;

import io.zeroneqin.base.domain.SystemParameter;
import io.zeroneqin.commons.constants.ParamConstants;
import io.zeroneqin.commons.constants.RoleConstants;
import io.zeroneqin.dto.BaseSystemConfigDTO;
import io.zeroneqin.ldap.domain.LdapInfo;
import io.zeroneqin.service.SystemParameterService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/system")
public class SystemParameterController {
    @Resource
    private SystemParameterService SystemParameterService;

    @PostMapping("/edit/email")
    @RequiresRoles(value = {RoleConstants.ADMIN})
    public void editMail(@RequestBody List<SystemParameter> systemParameter) {
        SystemParameterService.editMail(systemParameter);
    }

    @PostMapping("/testConnection")
    @RequiresRoles(value = {RoleConstants.ADMIN})
    public void testConnection(@RequestBody HashMap<String, String> hashMap) {
        SystemParameterService.testConnection(hashMap);
    }

    @GetMapping("/version")
    public String getVersion() {
        return SystemParameterService.getVersion();
    }

    @GetMapping("/mail/info")
    @RequiresRoles(value = {RoleConstants.ADMIN})
    public Object mailInfo() {
        return SystemParameterService.mailInfo(ParamConstants.Classify.MAIL.getValue());
    }

    @GetMapping("/base/info")
    @RequiresRoles(value = {RoleConstants.ADMIN})
    public BaseSystemConfigDTO getBaseInfo () {
        return SystemParameterService.getBaseInfo();
    }

    @PostMapping("/save/base")
    @RequiresRoles(value = {RoleConstants.ADMIN})
    public void saveBaseInfo (@RequestBody List<SystemParameter> systemParameter) {
        SystemParameterService.saveBaseInfo(systemParameter);
    }

    @PostMapping("/save/ldap")
    @RequiresRoles(value = {RoleConstants.ADMIN})
    public void saveLdap(@RequestBody List<SystemParameter> systemParameter) {
        SystemParameterService.saveLdap(systemParameter);
    }

    @GetMapping("/ldap/info")
    @RequiresRoles(value = {RoleConstants.ADMIN})
    public LdapInfo getLdapInfo() {
        return SystemParameterService.getLdapInfo(ParamConstants.Classify.LDAP.getValue());
    }

}
