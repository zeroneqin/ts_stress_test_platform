package io.zeroneqin.config;

import com.github.pagehelper.PageInterceptor;
import io.zeroneqin.base.domain.ApiTestReportDetail;
import io.zeroneqin.base.domain.FileContent;
import io.zeroneqin.base.domain.TestResource;
import io.zeroneqin.commons.utils.CompressUtils;
import io.zeroneqin.commons.utils.MybatisInterceptorConfig;
import io.zeroneqin.interceptor.MybatisInterceptor;
import io.zeroneqin.interceptor.UserDesensitizationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = {"io.zeroneqin.base.mapper", "io.zeroneqin.xpack.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
public class MybatisConfig {

    @Bean
    @ConditionalOnMissingBean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("pageSizeZero", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public MybatisInterceptor dbInterceptor() {
        MybatisInterceptor interceptor = new MybatisInterceptor();
        List<MybatisInterceptorConfig> configList = new ArrayList<>();
        configList.add(new MybatisInterceptorConfig(FileContent.class, "file", CompressUtils.class, "zip", "unzip"));
        configList.add(new MybatisInterceptorConfig(ApiTestReportDetail.class, "content", CompressUtils.class, "compress", "decompress"));
        configList.add(new MybatisInterceptorConfig(TestResource.class, "configuration"));
        interceptor.setInterceptorConfigList(configList);
        return interceptor;
    }

    @Bean
    public UserDesensitizationInterceptor userDesensitizationInterceptor() {
        return new UserDesensitizationInterceptor();
    }
}