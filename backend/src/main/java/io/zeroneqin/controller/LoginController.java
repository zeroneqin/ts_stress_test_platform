package io.zeroneqin.controller;

import io.zeroneqin.commons.constants.SsoMode;
import io.zeroneqin.commons.constants.UserSource;
import io.zeroneqin.commons.user.SessionUser;
import io.zeroneqin.commons.utils.SessionUtils;
import io.zeroneqin.controller.request.LoginRequest;
import io.zeroneqin.service.BaseDisplayService;
import io.zeroneqin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping
public class LoginController {

    @Resource
    private UserService userService;
    @Resource
    private Environment env;
    @Resource
    private BaseDisplayService baseDisplayService;

    @GetMapping(value = "/isLogin")
    public ResultHolder isLogin() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            SessionUser user = SessionUtils.getUser();
            if (StringUtils.isBlank(user.getLanguage())) {
                user.setLanguage(LocaleContextHolder.getLocale().toString());
            }
            return ResultHolder.success(user);
        }
        String ssoMode = env.getProperty("sso.mode");
        if (ssoMode != null && StringUtils.equalsIgnoreCase(SsoMode.CAS.name(), ssoMode)) {
            return ResultHolder.error("sso");
        }
        return ResultHolder.error("");
    }

    @PostMapping(value = "/signin")
    public ResultHolder login(@RequestBody LoginRequest request) {
        SecurityUtils.getSubject().getSession().setAttribute("authenticate", UserSource.LOCAL.name());
        return userService.login(request);
    }

    @GetMapping(value = "/currentUser")
    public ResultHolder currentUser() {
        return ResultHolder.success(SecurityUtils.getSubject().getSession().getAttribute("user"));
    }

    @GetMapping(value = "/signout")
    public ResultHolder logout() {
        String ssoMode = env.getProperty("sso.mode");
        if (ssoMode != null && StringUtils.equalsIgnoreCase(SsoMode.CAS.name(), ssoMode)) {
            return ResultHolder.error("sso");
        } else {
            SecurityUtils.getSubject().logout();
        }
        return ResultHolder.success("");
    }

    /*Get default language*/
    @GetMapping(value = "/language")
    public String getDefaultLanguage() {
        return userService.getDefaultLanguage();
    }

    @GetMapping("display/file/{imageName}")
    public ResponseEntity<byte[]> image(@PathVariable("imageName") String imageName) throws IOException {
        return baseDisplayService.getImage(imageName);
    }
}
