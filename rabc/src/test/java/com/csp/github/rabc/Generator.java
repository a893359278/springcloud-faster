package com.csp.github.rabc;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 陈少平
 * @date 2019-11-10 08:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Generator {

    @Value("${spring.datasource.url:jdbc:mysql://localhost:3306/rabc}")
    String URL;

    @Value("${spring.datasource.username:root}")
    String USERNAME;

    @Value("${spring.datasource.password:root}")
    String PASSWORD;

    @Value("${spring.datasource.driverClassName:com.mysql.cj.jdbc.Driver}")
    String DRIVER_CLASS;


    static final String AUTHOR = "陈少平";

    // 模块名称，若单个项目里面，没有多个子模块，请置为null
    static final String MODULE_NAME = null;
    // 包名
    static final String PACKAGE_NAME = "com.csp.github.rabc";
    // 生成的 java 文件存放位置
    static final String CLASS_LOCATION = "/src/main/java";
    // 生成的 mapper 文件存放位置
    static final String MAPPER_LOCATION = "/src/main/resources/mapper";
    // 表名
    static final String [] TABLES = {
        "admin_user", "menu", "resource", "role_resource_relation",
            "role", "user_resource_relation", "user_role_relation"
    };

    @Test
    public void generator() {

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + CLASS_LOCATION)
                .setAuthor(AUTHOR)
                .setOpen(false)
                .setBaseResultMap(true)
                .setEnableCache(false);


        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL)
                .setUrl(URL)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setDriverName(DRIVER_CLASS);

        // 包配置
        PackageConfig pc = new PackageConfig();
        if (!StringUtils.isEmpty(MODULE_NAME)) {
            pc.setModuleName(MODULE_NAME);
        }
        pc.setParent(PACKAGE_NAME);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setInclude(TABLES)
                .setControllerMappingHyphenStyle(false)
                .setTablePrefix(pc.getModuleName() + "_");

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        String templatePath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return String.join("", projectPath, MAPPER_LOCATION, "/", tableInfo.getEntityName(), "Mapper", StringPool.DOT_XML);
            }
        });
        cfg.setFileOutConfigList(focList);
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        // 不生成 controller， service
//        templateConfig.setController(null);
//        templateConfig.setService(null);
//        templateConfig.setServiceImpl(null);

        // 代码生成器
        mpg.setGlobalConfig(gc)
                .setDataSource(dsc)
                .setPackageInfo(pc)
                .setCfg(cfg)
                .setTemplate(templateConfig)
                .setStrategy(strategy)
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    public static void main(String[] args) {

    }
}
