package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.base.domain.User;
import io.zeroneqin.controller.request.UserRequest;
import io.zeroneqin.notice.domain.UserDetail;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExtUserMapper {

    List<User> getUserList(@Param("userRequest") UserRequest request);

    int updatePassword(User record);

    String getDefaultLanguage(String paramKey);

    List<User> searchUser(String condition);

    List<UserDetail> queryTypeByIds(List<String> userIds);

    @MapKey("id")
    Map<String, User> queryNameByIds(List<String> userIds);
}
