package com.jinghai.framework.config;


import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.jinghai.framework.interceptor.MybatisLogSqlInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }

    /**
     * sql 日志
     *
     * @return SqlLogInterceptor
     */
    @Bean
    @ConditionalOnProperty(value = "blade.mybatis-plus.sql-log", matchIfMissing = true)
    public MybatisLogSqlInterceptor sqlLogInterceptor() {
        return new MybatisLogSqlInterceptor();
    }


}

