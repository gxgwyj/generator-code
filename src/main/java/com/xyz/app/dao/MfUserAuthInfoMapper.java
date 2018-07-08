package com.xyz.app.dao;

import com.xyz.app.entity.MfUserAuthInfo;
import java.util.List;

public interface MfUserAuthInfoMapper {
    int deleteByPrimaryKey(String userId);

    int insertSelective(MfUserAuthInfo record);

    MfUserAuthInfo selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(MfUserAuthInfo record);

    List<MfUserAuthInfo> selectByWhere(MfUserAuthInfo record);
}