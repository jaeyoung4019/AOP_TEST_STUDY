package test.security.security_test.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.security.security_test.dto.authorization.enums.AuthoritiesEnum;
import test.security.security_test.handler.RoleEnumHandler;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "test.security.security_test.mapper" , sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceConfig {

    @Value("${mybatis.mapper-locations}")
    private String PATH;

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory SqlSessionFactory(@Qualifier("dataSource") DataSource DataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(DataSource);
        // MyBatis Alias , xml Source Mapping
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(PATH));  // resources 에서의 위치
        sqlSessionFactoryBean.setTypeAliasesPackage("test.security.security_test.dto");
        sqlSessionFactoryBean.setTypeHandlers(new AuthoritiesEnum.TypeHandler());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "SessionTemplate")
    public SqlSessionTemplate SqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory firstSqlSessionFactory) {
        return new SqlSessionTemplate(firstSqlSessionFactory);
    }
}
