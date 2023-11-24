package com.ching.excel2sql.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Classname ExcelItem
 * @Description TODO
 * @Author ching
 * @Date 11/23/2023
 */
@Data
public class I18nData {

    @ExcelProperty(value = "id", index = 0)
    private String id;

    @ExcelProperty(value = "code", index = 1)
    private String code;

    @ExcelProperty(value = "国际化数据类型", index = 2)
    private String i18nType;

    @ExcelProperty(value = "中文文案", index = 3)
    private String valueZh;

    @ExcelProperty(value = "英文文案", index = 4)
    private String valueEn;

    @ExcelProperty(value = "前端/后端", index = 5)
    private String client;

    @ExcelProperty(value = "申请人", index = 6)
    private String creator;

    @ExcelProperty(value = "变更类型", index = 7)
    private String changeType;

}
