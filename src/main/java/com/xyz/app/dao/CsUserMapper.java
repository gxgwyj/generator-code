package com.xyz.app.dao;

import com.xyz.app.entity.CsUser;
import java.util.List;

public interface CsUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insertSelective(CsUser record);

    CsUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(CsUser record);

    List<CsUser> selectByWhere(CsUser record);
}