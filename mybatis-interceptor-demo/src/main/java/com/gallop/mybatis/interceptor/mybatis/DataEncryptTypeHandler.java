package com.gallop.mybatis.interceptor.mybatis;

import com.gallop.mybatis.interceptor.utils.SM4Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * author gallop
 * date 2022-07-18 14:28
 * Description:
 * Modified By:
 */
@Slf4j
public class DataEncryptTypeHandler extends BaseTypeHandler<String> {
    /**
     * 非空字段加密 - 入库
     * @param preparedStatement
     * @param i
     * @param parameter
     * @param jdbcType
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String parameter, JdbcType jdbcType) {
        //不处理空字符串
        if(StringUtils.isBlank(parameter)){
            return;
        }
        try {
            SM4Utils sm4 = new SM4Utils();
            String encrypt = sm4.encryptData_ECB(parameter);
            log.info("数据：{},加密{}",parameter,encrypt);
            preparedStatement.setString(i, encrypt);
        } catch (Exception e) {
            log.error("typeHandler加密异常：" + e);
        }
    }
    /**
     * 非空字段解密 - 出库
     * @param resultSet
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public String getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String col = resultSet.getString(columnName);
        //不处理空字符串
        if(StringUtils.isBlank(col)){
            return col;
        }
        try {
            //16位key
            SM4Utils sm4 = new SM4Utils();
            String plain = sm4.decryptData_ECB(col);
            log.info("数据：{},解密{}",col,plain);
            return plain;
        } catch (Exception e) {
            log.error("数据非sms加密");
        }
        return col;
    }

    /**
     * 可空字段加密
     * @param resultSet
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public String getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getString(columnIndex);
    }

    /**
     * 可空字段解密
     * @param callableStatement
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public String getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return callableStatement.getString(columnIndex);
    }


}
