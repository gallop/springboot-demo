package com.gallop.mybatis.cache2.mybatis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * author gallop
 * date 2020-04-19 19:55
 * Description: 用于mybatis java实体类和数据库字段的类型转换！
 * Modified By:
 * <columnOverride column="ids" javaType="java.lang.Integer[]" typeHandler="JsonIntegerArrayTypeHandler"/>
 */
@Slf4j
public class JsonIntegerArrayTypeHandler extends BaseTypeHandler<Integer[]> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Integer[] integers, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, toJson(integers));
    }

    @Override
    public Integer[] getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return this.toObject(resultSet.getString(columnName));
    }

    @Override
    public Integer[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex));
    }

    @Override
    public Integer[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex));
    }

    private String toJson(Integer[] params) {
        try {
            return mapper.writeValueAsString(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[]";
    }

    private Integer[] toObject(String content) {
        if (content != null && !content.isEmpty()) {
            try {
                return (Integer[]) mapper.readValue(content, Integer[].class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
}
