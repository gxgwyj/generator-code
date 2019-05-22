package com.xyz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * 类: XyzFileReader <br>
 * 描述: 文件读取工具<br>
 * 作者:  gaoxugang<br>
 * 时间: 2019年05月21日 10:32
 */
public class XyzFileReader {
    private static final String INSERT_ALL = "insert all\n";
    private static final String SQL_TMP = "into microfinance.mf_app_terminal_info(MER_EQUIPMENT_IP) values(''{0}'')\n";
    private static final StringBuilder SQL_TMP_BUILD =  new StringBuilder(INSERT_ALL);
    private static final String[] files = {
            "C:\\Users\\Administrator.USER-20170406WP\\Desktop\\IP地址\\common-biz.log",
            "C:\\Users\\Administrator.USER-20170406WP\\Desktop\\IP地址\\common-biz.log.2019-01-07"
    };



    private static final String sqlFile = "C:\\Users\\Administrator.USER-20170406WP\\Desktop\\ip.sql";

    public static void readFile() {

        Set<String> ips = new HashSet<>();

        for (int i = 0; i < files.length; i++) {
            // 读数据
            try {
                FileReader reader = new FileReader(files[i]);
                BufferedReader br = new BufferedReader(reader);
                String line;
                while ((line = br.readLine()) != null) {
                    int  index1 = line.indexOf('[');
                    int  index2 = line.indexOf('/');
                    if (index1 != -1 && index2 != -1) {
                        // 一次读入一行数据
                        String ip = line.substring(index1+1,index2);
                        ips.add(ip);
                    }
                }
                br.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // 写数据

        System.out.println("ip地址个数："+ips.size());

        try {
            // 拼装sql，写入文件
            FileWriter writer = new FileWriter(sqlFile);
            BufferedWriter bw = new BufferedWriter(writer);

            int allSize = 0;
            int batchSize = 10;
            int currSize = 0;
            StringBuilder stringBuilder = new StringBuilder(INSERT_ALL);
            for (String newIp : ips) {
                if (allSize>300) {
                    break;
                }
                allSize++;
                String sql = MessageFormat.format(SQL_TMP, newIp);
                if ((++currSize) <= batchSize) {
                    stringBuilder.append(sql);
                    if (allSize == ips.size()){
                        stringBuilder.append("SELECT 1 FROM DUAL;\n");
                        bw.write(stringBuilder.toString());
                        bw.flush();
                    }
                } else {
                    stringBuilder.append("SELECT 1 FROM DUAL;\n");
                    bw.write(stringBuilder.toString());
                    bw.flush();

                    stringBuilder = new StringBuilder(INSERT_ALL);
                    currSize = 1;
                    stringBuilder.append(sql);
                }

            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        readFile();
    }

}
