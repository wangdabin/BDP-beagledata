package com.joe.user.service;

import com.joe.core.service.EntityService;
import com.joe.user.vo.User;
/**
 * @ClassName: UserService
 * @Description: 用户管理
 * @author WDB
 * @date 2015-4-16 上午11:37:05
 *
 */
public interface UserService extends EntityService<User>{
    public User getUserByAccount(String userAccount);
}
