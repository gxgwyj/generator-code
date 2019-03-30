package com.xyz;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * 类: FreemarkerUtil <br>
 * 描述: Freemarker工具类<br>
 * 作者:  gaoxugang<br>
 * 时间: 2019年03月27日 11:51
 */
public class FreemarkerUtil {

    public Template getTemplate(String name) {
        try {
            Configuration cfg = new Configuration();
            cfg.setClassForTemplateLoading(this.getClass(), "/ftl");
            // 在模板文件目录中找到名称为name的文件
            Template temp = cfg.getTemplate(name);
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(String name, Map<String, Object> root) {
        FileWriter out = null;
        try {
            out = new FileWriter(new File("src/main/resources/target.txt"));
            Template temp = this.getTemplate(name);
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
