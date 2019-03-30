package com.xyz;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类: ApiDataObjGenerator <br>
 * 描述: 根据文档生成实体数据的工具类<br>
 * 作者:  gaoxugang<br>
 * 时间: 2019年03月27日 11:02
 */
public class ApiDataObjGenerator {

    private static final String fileName = "src/main/resources/data.xlsx";

    public static void main(String[] args) throws IOException {

        InputStream inputStream = new FileInputStream(fileName);
        List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 0));
        List<FieldInfo> fieldInfos = buildFieldInfo(data);
        FreemarkerUtil freemarkerUtil = new FreemarkerUtil();
        Map<String, Object> dataModel = new HashMap<String, Object>();
        dataModel.put("dataModel",fieldInfos);
        freemarkerUtil.write("data.ftl",dataModel);
        inputStream.close();
    }

    public static List<FieldInfo>  buildFieldInfo(List<Object> data) {
        List<FieldInfo> list = new ArrayList<FieldInfo>();
        for (Object obj : data) {
            List<String> ele = (List<String>)obj;
            FieldInfo fieldInfo = new FieldInfo(ele.get(0),ele.get(2),ele.get(1));
            list.add(fieldInfo);
        }
        return list;
    }

}
