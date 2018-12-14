package cn.AssassinG.ScsyERP.User.core.dao.MyBatisHandler;

import cn.AssassinG.ScsyERP.User.facade.enums.AdminDeptType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDeptTypeHandler extends BaseTypeHandler<AdminDeptType> {

//    private Class<AdminDeptType> type;
//    private AdminDeptType[] enums;
//
//    public AdminDeptTypeHandler(Class<AdminDeptType> type) {
//        if(type == null)
//            throw new IllegalArgumentException("type参数不能为空");
//        this.type = type;
//        this.enums = type.getEnumConstants();
//        if(this.enums == null){
//            throw new IllegalArgumentException(type.getSimpleName() + "不是一个枚举类型");
//        }
//    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, AdminDeptType adminDeptType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, adminDeptType.getName());
    }

    @Override
    public AdminDeptType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String str = resultSet.getString(s);
        if(resultSet.wasNull())
            return null;
        else{
            return AdminDeptType.getEnum(str);
        }
    }

    @Override
    public AdminDeptType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String str = resultSet.getString(i);
        if(resultSet.wasNull())
            return null;
        else{
            return AdminDeptType.getEnum(str);
        }
    }

    @Override
    public AdminDeptType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String str = callableStatement.getString(i);
        if(callableStatement.wasNull())
            return null;
        else{
            return AdminDeptType.getEnum(str);
        }
    }
}
