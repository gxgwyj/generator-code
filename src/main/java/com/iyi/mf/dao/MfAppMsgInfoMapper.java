package com.iyi.mf.dao;

import com.iyi.mf.pojo.MfAppMsgInfo;
import java.util.List;

public interface MfAppMsgInfoMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(MfAppMsgInfo record);

    MfAppMsgInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MfAppMsgInfo record);

    List<MfAppMsgInfo> selectByWhere(MfAppMsgInfo record);
}