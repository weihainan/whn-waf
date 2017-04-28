package com.whn.waf.common.config.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于NamedParameterJdbcTemplate实现
 */
public class SqlExecutor {


    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 定义异常处理机制
     */
    /*
     * private void setExceptionTranslator(){ JdbcTemplate jdbcTemplate =
     * (JdbcTemplate) getJdbcOperations(); DefaultSQLExceptionTranslator dst =
     * new DefaultSQLExceptionTranslator();
     * jdbcTemplate.setExceptionTranslator(dst); }
     */

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static SqlExecutor of(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new SqlExecutor(namedParameterJdbcTemplate);
    }

    /**
     * 构造方法。参数为数据源名称。<br/>
     *
     * @param namedParameterJdbcTemplate 数据源名称
     */
    public SqlExecutor(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        // setExceptionTranslator();
    }


    /**
     * 获得Connection对象<br/>
     * ps:不建议直接获得Connection对象进行JDBC操作
     *
     * @return Connection对象
     */
    public Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得DataSource对象<br/>
     * ps:不建议直接获得DataSource对象进行JDBC操作
     *
     * @return DataSource对象
     */
    public DataSource getDataSource() {
        JdbcTemplate jdbcTemplate = (JdbcTemplate) getJdbcOperations();
        return jdbcTemplate.getDataSource();
    }

    private String getDBDialect() {

        String dialect = "MYSQL";
        try {
            Connection conn = getDataSource().getConnection();
            String driverName = conn.getMetaData().getDriverName();
            if (driverName.toUpperCase().contains("MYSQL")) {
                dialect = "MYSQL";
            } else if (driverName.toUpperCase().contains("ORACLE")) {
                dialect = "ORACLE";
            } else if (driverName.toUpperCase().contains("POSTGRESQL")) {
                dialect = "POSTGRESQL";
            } else if (driverName.toUpperCase().contains("SQLSERVER")) {
                dialect = "SQLSERVER";
            } else if (driverName.toUpperCase().contains("H2")) {
                dialect = "H2";
            } else if (driverName.toUpperCase().contains("HSQL")) {
                dialect = "HSQL";
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return dialect;
    }

    /**
     * 获得NamedParameterJdbcTemplate实例
     *
     * @return NamedParameterJdbcTemplate对象
     */
    public NamedParameterJdbcTemplate getNPJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    /**
     * 返回JdbcOperations的实现，即JdbcTemplate
     *
     * @return JdbcOperations对象
     */
    public JdbcOperations getJdbcOperations() {
        return namedParameterJdbcTemplate.getJdbcOperations();
    }

    /**
     * 执行不带参数的查询并返回指定类型的对象集
     *
     * @param sql          查询语句
     * @param requiredType 指定的类型
     * @return 查询的结果对象集
     */
    public <T> List<T> queryForList(String sql, Class<T> requiredType) {
        return queryForList(sql, new HashMap<String, Object>(), requiredType);
    }

    /**
     * 执行带参数的查询并返回指定类型的对象集
     *
     * @param sql          查询语句
     * @param paramMap     命名参数
     * @param requiredType 指定的类型
     * @return 查询的结果对象集
     */
    public <T> List<T> queryForList(String sql, Map<String, ?> paramMap, Class<T> requiredType) {

        Package classPackage = requiredType.getPackage();
        String packName = classPackage.getName();
        List<T> resultList = new ArrayList<>();
        // 如果requiredType的类属于java.lang或者java.util包，那么不需要进行转换
        if (packName.contains("java.lang") || packName.contains("java.util")) {
            resultList = namedParameterJdbcTemplate.queryForList(sql, paramMap, requiredType);
        } else {
            RowMapper<T> rm = new BeanPropertyRowMapper<>(requiredType);
            resultList = namedParameterJdbcTemplate.query(sql, paramMap, rm);
        }

        return resultList;
    }

    /**
     * 执行不带参数的查询并返回List<br/>
     * list中包含map，每个map对应一条记录。map中的key为列名，value为值
     *
     * @param sql 查询语句
     * @return 每一行对应一个Map的List
     */
    public List<Map<String, Object>> queryForList(String sql) {
        return queryForList(sql, new HashMap<String, Object>());
    }

    /**
     * 执行带命名参数的查询并返回List<br/>
     * list中包含map，每个map对应一条记录。map中的key为列名，value为值
     *
     * @param sql      查询语句
     * @param paramMap 命名参数
     * @return 每一行对应一个Map的List
     */
    public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) {
        final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        namedParameterJdbcTemplate.query(sql, paramMap, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {

                Map<String, Object> map = new HashMap<String, Object>();
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String columnLabel = rsmd.getColumnLabel(i);
                    Object obj = rs.getObject(i);
                    map.put(columnLabel, obj);
                }
                resultList.add(map);
            }
        });

        return resultList;
    }

    /**
     * 执行不带参数的查询并返回单个类型对象<br/>
     * 该方法的查询只能返回一条记录<br/>
     * ps:如果查询的结果中有多条记录，那么返回的是第一条记录
     *
     * @param sql          查询SQL语句
     * @param requiredType 指定的类型
     * @return 查询到的类型对象
     */
    public <T> T queryForObject(String sql, Class<T> requiredType) {
        return queryForObject(sql, new HashMap<String, Object>(), requiredType);
    }

    /**
     * 执行带命名参数的查询并返回指定类型的对象<br/>
     * 该方法的查询只能返回一条记录<br/>
     * ps:如果查询的结果中有多条记录，那么返回的是第一条记录
     *
     * @param sql          查询SQL语句
     * @param paramMap     命名参数
     * @param requiredType 指定的类型
     * @return 查询到的类型对象
     */
    public <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> requiredType) {

        Package classPackage = requiredType.getPackage();
        String packName = classPackage.getName();
        T obj;
        try {
            // 如果requiredType的类属于java.lang或者java.util包，那么不需要进行转换
            if (packName.contains("java.lang") || packName.contains("java.util")) {
                obj = namedParameterJdbcTemplate.queryForObject(sql, paramMap, requiredType);
            } else {
                RowMapper<T> rm = new BeanPropertyRowMapper<T>(requiredType);
                obj = namedParameterJdbcTemplate.queryForObject(sql, paramMap, rm);
            }
        } catch (EmptyResultDataAccessException e) {
//            logger.error(e.getMessage());
            return null;
        } catch (IncorrectResultSizeDataAccessException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            List<T> list = queryForList(sql, paramMap, requiredType);
            return list.get(0);
        }
        return obj;
    }

    /**
     * 执行不带参数的查询并返回Map<br/>
     * 该方法的查询只能返回一条记录<br/>
     * ps:如果查询的结果中有多条记录，那么返回的是第一条记录
     *
     * @param sql 查询语句
     * @return 每一行对应一个Map
     */
    public Map<String, Object> queryForMap(String sql) {
        Map<String, ?> paramMap = new HashMap<String, Object>();
        return queryForMap(sql, paramMap);
    }

    /**
     * 执行带命名参数的查询并返回Map<br/>
     * 该方法的查询只能返回一条记录<br/>
     * ps:如果查询的结果中有多条记录，那么返回的是第一条记录
     *
     * @param sql      查询语句
     * @param paramMap 命名参数
     * @return 每一行对应的一个Map
     */
    public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) {
        try {
            return namedParameterJdbcTemplate.queryForMap(sql, paramMap);
        } catch (EmptyResultDataAccessException e) {
//            logger.error(e.getMessage());
            return null;
        } catch (IncorrectResultSizeDataAccessException e) {
//            logger.error(e.getMessage());
            List<Map<String, Object>> list = queryForList(sql, paramMap);
            return list.get(0);
        }
    }


    /**
     * 执行不带参数的批量操作
     *
     * @param sql 更新SQL语句集
     * @return
     */
    public int[] batchUpdate(String[] sql) {
        return getJdbcOperations().batchUpdate(sql);
    }

    /**
     * 执行带参数的批量操作<br/>
     * 这里的SQL不支持命名参数，只能用?代替
     *
     * @param sql       更新SQL语句
     * @param batchArgs 参数列表
     * @return 受影响的条数数组
     */
    public int[] batchUpdate(String sql, List<Object[]> batchArgs) {
        return getJdbcOperations().batchUpdate(sql, batchArgs);
    }

    /**
     * 执行带命名参数的批量操作<br/>
     * 这里的SQL支持命名参数
     *
     * @param sql         更新SQL语句
     * @param batchValues 参数列表
     * @return 受影响的条数数组
     */
    public int[] batchUpdate(String sql, Map<String, ?>[] batchValues) {
        return namedParameterJdbcTemplate.batchUpdate(sql, batchValues);
    }

    /**
     * 执行不带参数的更新语句
     *
     * @param sql 更新SQL语句
     * @return 受影响的条数
     */
    public int update(String sql) {
        return getJdbcOperations().update(sql);
    }

    /**
     * 执行带命名参数的更新语句
     *
     * @param sql      更新SQL语句
     * @param paramMap 命名参数
     * @return 受影响的行数
     */
    public int update(String sql, Map<String, ?> paramMap) {
        return namedParameterJdbcTemplate.update(sql, paramMap);
    }

    /**
     * 插入数据并返回主键
     *
     * @param sql      SQL语句
     * @param paramMap 参数
     * @param keys     主键
     * @return 主键值的Map
     */
    public Map<String, Object> insertReturnGeneratedKey(String sql, Map<String, ?> paramMap, String[] keys) {

        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, paramSource, generatedKeyHolder, keys);
        Map<String, Object> values = generatedKeyHolder.getKeys();

        return values;
    }

    // TODO 调用存储过程


}
