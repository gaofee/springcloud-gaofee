package com.gaofei.util;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class TimestampConverter implements Converter<Timestamp>{

    @Override
    public Class<Timestamp> supportJavaTypeKey() {
        return Timestamp.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<String> convertToExcelData(Timestamp timestamp, ExcelContentProperty contentProperty,
                                                    GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<String>(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp));
    }

}