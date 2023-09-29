package test.security.security_test.handler;


import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import test.security.security_test.dto.authorization.enums.AuthoritiesEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleEnumHandler <E extends Enum<E>> implements TypeHandler<AuthoritiesEnum> {

    private Class<E> type;

    public RoleEnumHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, AuthoritiesEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i , parameter.getCode());
    }

    @Override
    public AuthoritiesEnum getResult(ResultSet rs, String columnName) throws SQLException {
        return getCode(rs.getString(columnName));
    }

    @Override
    public AuthoritiesEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
        return getCode(rs.getString(columnIndex));
    }

    @Override
    public AuthoritiesEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getCode(cs.getString(columnIndex));
    }


    private AuthoritiesEnum getCode(String code) {
        try {
            AuthoritiesEnum[] enumConstants = (AuthoritiesEnum[]) type.getEnumConstants();
            for (AuthoritiesEnum enumElement : enumConstants) {
                if (enumElement.getCode().equals(code)) {
                    return enumElement;
                }
            }
            return null;
        } catch (Exception exception) {
            throw new TypeException("Can't make enum object '" + type + "'", exception);
        }
    }
}
