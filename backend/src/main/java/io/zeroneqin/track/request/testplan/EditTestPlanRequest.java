package io.zeroneqin.track.request.testplan;

import io.zeroneqin.base.domain.FileMetadata;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EditTestPlanRequest extends TestPlanRequest {
    private List<FileMetadata> updatedFileList;

    public List<FileMetadata> getUpdatedFileList() {
        return updatedFileList;
    }

    public void setUpdatedFileList(List<FileMetadata> updatedFileList) {
        this.updatedFileList = updatedFileList;
    }
}
