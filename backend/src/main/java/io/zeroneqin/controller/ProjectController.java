package io.zeroneqin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.zeroneqin.base.domain.Project;
import io.zeroneqin.commons.utils.PageUtils;
import io.zeroneqin.commons.utils.Pager;
import io.zeroneqin.commons.utils.SessionUtils;
import io.zeroneqin.controller.request.ProjectRequest;
import io.zeroneqin.dto.ProjectDTO;
import io.zeroneqin.service.CheckPermissionService;
import io.zeroneqin.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {
    @Resource
    private ProjectService projectService;
    @Resource
    private CheckPermissionService checkPermissionService;

    @GetMapping("/listAll")
    public List<ProjectDTO> listAll() {
        String currentOrganizationId = SessionUtils.getCurrentOrganizationId();
        ProjectRequest request = new ProjectRequest();
        if (!SessionUtils.getUserId().equalsIgnoreCase("admin")) {
            request.setOrganizationId(currentOrganizationId);
        }
        return projectService.getProjectList(request);
    }

    /*jenkins项目列表*/
    @GetMapping("/listAll/{workspaceId}")
    public List<ProjectDTO> jlistAll(@PathVariable String organizationId) {
        ProjectRequest request = new ProjectRequest();
        request.setOrganizationId(organizationId);
        return projectService.getProjectList(request);
    }

    @GetMapping("/recent/{count}")

    public List<Project> recentProjects(@PathVariable int count) {
        String currentOrganizationId = SessionUtils.getCurrentOrganizationId();
        ProjectRequest request = new ProjectRequest();
        if (!SessionUtils.getUserId().equalsIgnoreCase("admin")) {
            request.setOrganizationId(currentOrganizationId);
        }
        // 最近 `count` 个项目
        PageHelper.startPage(1, count);
        return projectService.getRecentProjectList(request);
    }

    @GetMapping("/get/{id}")
    public Project getProject(@PathVariable String id) {
        return projectService.getProjectById(id);
    }

    @PostMapping("/add")
    public Project addProject(@RequestBody Project project) {
        return projectService.addProject(project);
    }

    @PostMapping("/list/{goPage}/{pageSize}")
    public Pager<List<ProjectDTO>> getProjectList(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody ProjectRequest request) {
        if (!SessionUtils.getUserId().equalsIgnoreCase("admin")) {
            request.setOrganizationId(SessionUtils.getCurrentOrganizationId());
        }
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, projectService.getProjectList(request));
    }

    @GetMapping("/delete/{projectId}")

    public void deleteProject(@PathVariable(value = "projectId") String projectId) {
        checkPermissionService.checkProjectOwner(projectId);
        projectService.deleteProject(projectId);
    }

    @PostMapping("/update")

    public void updateProject(@RequestBody Project Project) {
        projectService.updateProject(Project);
    }

}
