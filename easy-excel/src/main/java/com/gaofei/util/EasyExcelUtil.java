package com.gaofei.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

import ink.aos.module.storage.impl.AosStorage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * 高性能处理Excel工具类
 *
 */
@Slf4j
@Component
public class EasyExcelUtil {
    private static final int MAXROWS = 1000000;
    public static final String EMPTY = "";

    public EasyExcelUtil(AosStorage aosStorage) {
        this.aosStorage = aosStorage;
    }


    /**
     * 使用 模型 来读取Excel
     *
     * @param inputStream Excel的输入流
     * @param clazz       模型的类
     * @return 返回 模型 的列表
     */
    public static <T> List<T> readExcel(InputStream inputStream, Class<T> clazz) {
        ModelExcelListener<T> listener = new ModelExcelListener<T>();
        EasyExcel.read(inputStream, clazz, listener).sheet().doRead();
        return listener.getDatas();
    }

    /**
     * 使用 模型 来导出到WEB
     *
     * @param response      web的响应
     * @param data         要写入的以 模型 为单位的数据
     * @param fileName     配置Excel的表名
     * @param sheetName    配置Excel的页签名
     * @param clazz        模型的类
     * @throws IOException
     */
    public static <T> void writeExcel(HttpServletResponse response, List<T> data, Class<T> clazz, String fileName, String sheetName) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), clazz).sheet(sheetName).doWrite(data);
    }

    /**
     * 使用 模型 来写入Excel
     * <br/>注意，如果是web输出流，需要设置头
     *
     * @param outputStream Excel的输出流
     * @param data         要写入的以 模型 为单位的数据
     * @param sheetName    配置Excel的表名字
     * @param clazz        模型的类
     */
    public static <T> void writeExcel(OutputStream outputStream, List<T> data, Class<T> clazz, String sheetName) {
        EasyExcel.write(outputStream, clazz).sheet(sheetName).doWrite(data);
    }

    /**
     * 模型 解析监听器
     */
    private static class ModelExcelListener<T> extends AnalysisEventListener<T> {
        private List<T> datas = new ArrayList<>();

        @Override
        public void invoke(T object, AnalysisContext context) {
            datas.add(object);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
        }

        public List<T> getDatas() {
            return datas;
        }
    }


    /**
     * @author QiuYu
     * @createDate 2020-11-16
     * @param response
     * @param data  查询结果
     * @param fileName 导出文件名称
     * @param clazz 映射实体class类
     * @param <T>  查询结果类型
     * @throws Exception
     */
    public static<T> void writeExcelMax(HttpServletResponse response, List<T> data, String fileName, Class clazz) throws Exception {
        long exportStartTime = System.currentTimeMillis();
        log.info("报表导出Size: "+data.size()+"条。");

        List<List<T>> lists = ListUtil.split(data,MAXROWS); // 分割的集合

        OutputStream out = getOutputStream(fileName, response);
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(out, clazz).excelType(ExcelTypeEnum.XLSX).registerWriteHandler(getDefaultHorizontalCellStyleStrategy());
        ExcelWriter excelWriter = excelWriterBuilder.build();
        ExcelWriterSheetBuilder excelWriterSheetBuilder;
        WriteSheet writeSheet;
        for (int i =1;i<=lists.size();i++){
            excelWriterSheetBuilder = new ExcelWriterSheetBuilder(excelWriter);
            excelWriterSheetBuilder.sheetNo(i);
            excelWriterSheetBuilder.sheetName("sheet"+i);
            writeSheet = excelWriterSheetBuilder.build();
            excelWriter.write(lists.get(i-1),writeSheet);
        }
        out.flush();
        excelWriter.finish();
        out.close();
        System.out.println("报表导出结束时间:"+ new Date()+";写入耗时: "+(System.currentTimeMillis()-exportStartTime)+"ms" );
    }


    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
        fileName = URLEncoder.encode(fileName, "UTF-8");
        //  response.setContentType("application/vnd.ms-excel"); // .xls
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // .xlsx
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        return response.getOutputStream();
    }


    /**
     * 获取默认表头内容的样式
     * @return
     */
    private static HorizontalCellStyleStrategy getDefaultHorizontalCellStyleStrategy(){
        /** 表头样式 **/
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景色（浅灰色）
        // 可以参考：https://www.cnblogs.com/vofill/p/11230387.html
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // 字体大小
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        /** 内容样式 **/
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 内容字体样式（名称、大小）
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontName("宋体");
        contentWriteFont.setFontHeightInPoints((short) 10);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //设置内容垂直居中对齐
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置内容水平居中对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //设置边框样式
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        // 头样式与内容样式合并
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final AosStorage aosStorage;


    @SneakyThrows
    public String toExcel( String sql) {
        log.info("获取数据开始");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(baos).registerConverter(new TimestampConverter()).build();
        try {
            jdbcTemplate.setFetchSize(1000);
            jdbcTemplate.query(sql, rs -> {
                int sheetIndex = 0;//当前第几个sheet
                int pageSize = 1000000;//每个sheet数量
                List<List<String>> headList = new ArrayList<>();
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    List<String> head0 = new ArrayList<>();
                    head0.add(metaData.getColumnName(i));
                    headList.add(head0);
                }
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetIndex, "sheet" + sheetIndex).head(headList).build();//writerBuilder.head(headList).sheet("sheet" + sheetIndex).build();
                List<List<Object>> re = new ArrayList<>();
                int c = 0;
                while (rs.next()) {
                    re.add(getResultObjectList(null, rs, metaData, null));
                    if (c++ == 1000) {
                        excelWriter.write(re, writeSheet);
                        re.clear();
                        c = 0;
                    }
                    if (pageSize == 0) {
                        excelWriter.write(re, writeSheet);
                        re.clear();
                        c = 0;
                        sheetIndex++;
                        writeSheet = EasyExcel.writerSheet(sheetIndex, "sheet" + sheetIndex).head(headList).build();
                        pageSize = 1000000;
                    } else {
                        pageSize--;
                    }
                }
                if (re.size() > 0) {
                    excelWriter.write(re, writeSheet);
                }
            });
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new Exception("导出失败：" + e.getMessage());
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
        log.info("获取数据成功，准备导出excel");

        return aosStorage.save(baos.toByteArray(), DateFormatUtils.format(new Date(), "yyyy/MM") + "/", UUID.randomUUID().toString() + ".xlsx");
    }


    private List<Object> getResultObjectList(Set<String> excludeColumns, ResultSet rs, ResultSetMetaData metaData, Set<String> queryFromsAndJoins) throws SQLException {
        List<Object> list = new ArrayList<>();

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String key = metaData.getColumnLabel(i);
            String label = getColumnLabel(queryFromsAndJoins, key);
            if (!CollectionUtils.isEmpty(excludeColumns) && excludeColumns.contains(label)) {
                continue;
            }
            Object value = rs.getObject(key);
            list.add(value instanceof byte[] ? new String((byte[]) value) : value);
        }
        return list;
    }

    public static String getColumnLabel(Set<String> columnPrefixs, String columnLable) {
        if (!CollectionUtils.isEmpty(columnPrefixs)) {
            for (String prefix : columnPrefixs) {
                if (columnLable.startsWith(prefix)) {
                    return columnLable.replaceFirst(prefix, EMPTY);
                }
                if (columnLable.startsWith(prefix.toLowerCase())) {
                    return columnLable.replaceFirst(prefix.toLowerCase(), EMPTY);
                }
                if (columnLable.startsWith(prefix.toUpperCase())) {
                    return columnLable.replaceFirst(prefix.toUpperCase(), EMPTY);
                }
            }
        }
        return columnLable;
    }


}


