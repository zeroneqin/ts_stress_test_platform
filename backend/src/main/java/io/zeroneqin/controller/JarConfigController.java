package io.zeroneqin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.zeroneqin.base.domain.JarConfig;
import io.zeroneqin.commons.constants.RoleConstants;
import io.zeroneqin.commons.utils.PageUtils;
import io.zeroneqin.commons.utils.Pager;
import io.zeroneqin.service.JarConfigService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/jar")

public class JarConfigController {

    @Resource
    JarConfigService JarConfigService;

    @PostMapping("list/{goPage}/{pageSize}")
    @RequiresRoles(RoleConstants.ORG_ADMIN)
    public Pager<List<JarConfig>> list(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody JarConfig request) {
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, JarConfigService.list(request));
    }

    @GetMapping("list/all")
    public List<JarConfig> listAll() {
        return JarConfigService.list();
    }

    @GetMapping("/get/{id}")
    public JarConfig get(@PathVariable String id) {
        return JarConfigService.get(id);
    }

    @PostMapping(value = "/add", consumes = {"multipart/form-data"})

    public String add(@RequestPart("request") JarConfig request, @RequestPart(value = "file") MultipartFile file) {
        return JarConfigService.add(request, file);
    }

    @PostMapping(value = "/update", consumes = {"multipart/form-data"})

    public void update(@RequestPart("request") JarConfig request, @RequestPart(value = "file", required = false) MultipartFile file) {
        JarConfigService.update(request, file);
    }

    @GetMapping("/delete/{id}")

    public void delete(@PathVariable String id) {
        JarConfigService.delete(id);
    }

}
