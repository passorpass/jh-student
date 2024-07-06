package com.jinghai.framework.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import sun.reflect.misc.FieldUtil;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * mybatis sql 日志拦截器, 用于打印SQL
 *
 * @author ly-chn
 */
@Slf4j
@Profile("local")
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
public class MybatisLogSqlInterceptor implements Interceptor {
    private static final Set<String> NEED_BRACKETS =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    "String", "Date", "Time", "LocalDate", "LocalTime", "LocalDateTime", "BigDecimal", "Timestamp")));
    private Configuration configuration = null;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        long startTime = System.currentTimeMillis();
        int lines = 1;
        String status = "failed";
        try {
            // 如需打印结果, 返回值即结果集
            Object proceed = invocation.proceed();
            if (proceed instanceof Collection<?>) {
                lines = ((List<?>) proceed).size();
            }
            status = "succeeded";
            return proceed;
        } finally {
            long sqlCost = System.currentTimeMillis() - startTime;
            String sql = this.getSql(target);
//            System.out.printf("SQL执行状态：%s，耗时：%d毫秒，影响行数：%d，SQL语句：%s%n", status, sqlCost, lines, sql);
            log.info("SQL执行状态：{}，耗时：{}毫秒，影响行数：{}，SQL语句：{}", status, sqlCost, lines, sql);
        }
    }

    /**
     * 获取sql FieldUtils
     */
    private String getSql(Object target) {
        try {
            StatementHandler statementHandler = (StatementHandler) target;
            BoundSql boundSql = statementHandler.getBoundSql();
            if (configuration == null) {
                final ParameterHandler parameterHandler = statementHandler.getParameterHandler();
                this.configuration = (Configuration) ReflectUtil.getFieldValue(parameterHandler, "configuration");
            }
            // 替换参数格式化Sql语句，去除换行符
            return formatSql(boundSql, configuration);
        } catch (Exception e) {
            log.warn("获取SQL错误：目标对象:{}，异常信息：{}", target, e);
            return "failed to parse sql";
        }
    }

    /**
     * 获取完整的sql实体的信息
     */
    private String formatSql(BoundSql boundSql, Configuration configuration) {
        String sql = boundSql.getSql();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        // 输入sql字符串空判断
        if (ObjectUtil.isEmpty(sql) || Objects.isNull(configuration)) {
            return "";
        }

        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

        // 替换空格容易造成本身存在空格的查询条件被替换
        sql = sql.replaceAll("[\n\r ]+", " ");

        if (parameterMappings == null) {
            return sql;
        }

        parameterMappings = parameterMappings.stream().filter(it -> it.getMode() != ParameterMode.OUT).collect(Collectors.toList());

        final StringBuilder result = new StringBuilder(sql);

        // 解析问号并填充
        for (int i = result.length(); i > 0; i--) {
            if (result.charAt(i - 1) != '?') {
                continue;
            }
            ParameterMapping parameterMapping = parameterMappings.get(parameterMappings.size() - 1);
            Object value;
            String propertyName = parameterMapping.getProperty();
            if (boundSql.hasAdditionalParameter(propertyName)) {
                value = boundSql.getAdditionalParameter(propertyName);
            } else if (parameterObject == null) {
                value = null;
            } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                value = parameterObject;
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                value = metaObject.getValue(propertyName);
            }
            if (value != null) {
                String type = value.getClass().getSimpleName();
                if (NEED_BRACKETS.contains(type)) {
                    result.replace(i - 1, i, "'" + value + "'");
                } else {
                    result.replace(i - 1, i, value.toString());
                }
            } else {
                result.replace(i - 1, i, "null");
            }
            parameterMappings.remove(parameterMappings.size() - 1);
        }
        return result.toString();
    }
}
