package com.ching.excel2sql.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.ching.excel2sql.model.I18nData;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Classname I18nListener
 * @Description TODO
 * @Author ching
 * @Date 11/23/2023
 */
public class I18nListener implements ReadListener<I18nData> {
    private final String SQL_HEAD_INSERT = "insert into \"system\".\"global_i18n\" (\"id\", \"code\", \"i18n_type\", \"remark\", \"value_zh\", \"value_en\", \"client\", \"create_time\", \"update_time\", \"creator\", \"updater\", \"status\") values";
    private final String SQL_HEAD_UPDATE = "update \"system\".\"global_i18n\" set \"code\" = '%s', \"remark\" = '%s', \"value_zh\" = '%s', \"value_en\" = '%s', \"update_time\" = %s where \"id\" = '%s';";
    private List<I18nData> list = new ArrayList<>();

    private int count = 0;

    @Override
    public void invoke(I18nData i18nData, AnalysisContext analysisContext) {
        list.add(i18nData);
        count++;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        StringBuilder sb = new StringBuilder();
        Iterator<I18nData> iterator = list.iterator();
        while (iterator.hasNext()) {
            I18nData i18nData = iterator.next();
            if (!StringUtils.hasText(i18nData.getId())){
                continue;
            }
            if (i18nData.getChangeType().trim().equalsIgnoreCase("新增")) {
                sb.append(SQL_HEAD_INSERT)
                        .append("(")
                        .append(dealProperty(i18nData.getId())).append(", ")
                        .append(dealProperty(i18nData.getCode())).append(", ")
                        .append(dealProperty(i18nData.getI18nType())).append(", ")
                        .append(dealProperty(i18nData.getValueZh())).append(", ")  //remark
                        .append(dealProperty(i18nData.getValueZh())) .append(", ")
                        .append(dealProperty(i18nData.getValueEn())).append(", ")
                        .append(dealProperty(i18nData.getClient())).append(", ")
                        .append(dealProperty(now)).append(", ")  //create time
                        .append(dealProperty(now)).append(", ")  //update time
                        .append(dealProperty(i18nData.getCreator())).append(", ")
                        .append("null").append(", ")   //updator
                        .append("true").append(");"); //status
            }
            if (i18nData.getChangeType().trim().equalsIgnoreCase("修改")) {
                String update = String.format(SQL_HEAD_UPDATE,i18nData.getCode(), i18nData.getValueZh(), i18nData.getValueZh(), i18nData.getValueEn(), dealProperty(now), i18nData.getId());
                sb.append(update);
            }
            System.out.println(sb);
            sb.setLength(0);
        }

        System.out.println("\n================================================ Excel文件解析完毕，共解析 " + count + "条数据 ================================================\n");
    }

    private String dealProperty(String value) {
        return "'" + value + "'";
    }
}
