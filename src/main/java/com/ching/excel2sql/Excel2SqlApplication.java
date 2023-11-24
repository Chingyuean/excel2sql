package com.ching.excel2sql;

import com.alibaba.excel.EasyExcel;
import com.ching.excel2sql.listener.I18nListener;
import com.ching.excel2sql.model.I18nData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;

@SpringBootApplication
public class Excel2SqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(Excel2SqlApplication.class, args);
        System.out.println("\n================================================ 读取文件 ================================================\n");
        String fileName = "国际化数据维护申请表.xlsx";
        InputStream in = Excel2SqlApplication.class.getClassLoader().getResourceAsStream(fileName);
        EasyExcel.read(in, I18nData.class, new I18nListener()).sheet(1).headRowNumber(1).doRead();
    }

}
