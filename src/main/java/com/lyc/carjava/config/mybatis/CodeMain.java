package com.lyc.carjava.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


public class CodeMain {

    private static String outputDir = "D:\\java工程\\car-java\\src\\main\\java";
    private static String userName = "root";
    private static String password = "123456";
    private static String datasource = "java_car";
    private static String[] includeTables = {"car_use_record"};
    private static String parentPackage = "com.lyc.carjava.moudle.base";
    private static String author="lyc";


    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
        //使用freemarker
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());

        GlobalConfig globalConfig = new GlobalConfig();
        //生成路径
        globalConfig.setOutputDir(outputDir);
        //是否覆盖同名文件夹
        globalConfig.setFileOverride(true);
        //作者
        globalConfig.setAuthor(author);
        //service 名字
        globalConfig.setServiceName("%sService");
        //impl 名字
        globalConfig.setServiceImplName("%sServiceImpl");

        //模板设置
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setMapper(null);
        templateConfig.setService(null);
        templateConfig.setXml(null);


        //数据库设置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername(userName);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setUrl("jdbc:mysql://127.0.0.1:3306/"+datasource+"?useJDBCCompliantTimezoneShift=true&serverTimezone=GMT%2B8");

        //策略配置
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true) // 全局大写命名
                .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                .setRestControllerStyle(true);//将controller设置为RestController
        stConfig.setInclude(includeTables);//设置需要生成的表

        //包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(parentPackage);
        packageConfig.setMapper("mapper");
        packageConfig.setXml("mapper.xml");

        //整合配置
        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setStrategy(stConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.setTemplate(templateConfig);
        //配置生效
        autoGenerator.execute();
    }
}
