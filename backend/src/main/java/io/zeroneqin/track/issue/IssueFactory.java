package io.zeroneqin.track.issue;

import io.zeroneqin.commons.constants.IssuesManagePlatform;
import io.zeroneqin.track.request.testcase.IssuesRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class IssueFactory {
    public static AbstractIssuePlatform createPlatform(String platform, IssuesRequest addIssueRequest) {
        if (StringUtils.equals(IssuesManagePlatform.Tapd.toString(), platform)) {
            return new TapdPlatform(addIssueRequest);
        } else if (StringUtils.equals(IssuesManagePlatform.Jira.toString(), platform)) {
            return new JiraPlatform(addIssueRequest);
        } else if (StringUtils.equals(IssuesManagePlatform.Zentao.toString(), platform)) {
            return new ZentaoPlatform(addIssueRequest);
        } else if (StringUtils.equals("LOCAL", platform)) {
            return new LocalPlatform(addIssueRequest);
        }
        return null;
    }

    public static List<AbstractIssuePlatform> createPlatforms(List<String> types, IssuesRequest addIssueRequest) {
        List<AbstractIssuePlatform> platforms = new ArrayList<>();
        types.forEach(type -> {
            AbstractIssuePlatform abstractIssuePlatform = createPlatform(type, addIssueRequest);
            if (abstractIssuePlatform != null) {
                platforms.add(abstractIssuePlatform);
            }
        });
        return platforms;
    }
}
