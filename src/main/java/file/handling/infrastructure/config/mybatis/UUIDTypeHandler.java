package file.handling.infrastructure.config.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;

@MappedTypes(UUID.class)
public class UUIDTypeHandler implements TypeHandler<UUID> {

    private static final Logger LOG = LoggerFactory.getLogger(UUIDTypeHandler.class);
    @Override
    public void setParameter(PreparedStatement preparedStatement, int index, UUID parameter, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(index, parameter, Types.OTHER);
    }

    @Override
    public UUID getResult(ResultSet resultSet, String columnName) throws SQLException {
        return toUUID(resultSet.getString(columnName));
    }

    @Override
    public UUID getResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return toUUID(resultSet.getString(columnIndex));
    }

    @Override
    public UUID getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return toUUID(callableStatement.getString(columnIndex));
    }

    private static UUID toUUID(String value) {
        return UUID.fromString(value);
    }

}