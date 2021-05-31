package io.zeroneqin.notice.controller.request;

import io.zeroneqin.notice.domain.MessageDetail;
import lombok.Data;

import java.util.List;

@Data
public class MessageRequest {
    private List<MessageDetail> messageDetail;
}
