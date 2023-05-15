package com.expect.spring;

import com.expect.spring.config.ExpectConfig;
import com.expect.spring.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassUtils;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Arrays;

@Slf4j
public class BeanDefinitionTest {
    @Test
    void genericBeanDefinitionTest() {
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClassName("com.expect.spring.entity.User");
        genericBeanDefinition.setScope(GenericBeanDefinition.SCOPE_SINGLETON);
        genericBeanDefinition.setInitMethodNames("init1", "init2");
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue("name", "33");
        propertyValues.addPropertyValue("age", 16);
        genericBeanDefinition.setPropertyValues(propertyValues);
    }

    @Test
    void rootBeanDefinitionTest() {
        //定义简单注册表
        SimpleBeanDefinitionRegistry simpleBeanDefinitionRegistry = new SimpleBeanDefinitionRegistry();

        //添加父类RootBeanDefinition
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClassName("com.expect.spring.entity.Pet");
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue("name", "咪咪");
        propertyValues.addPropertyValue("age", 2);
        rootBeanDefinition.setPropertyValues(propertyValues);
        simpleBeanDefinitionRegistry.registerBeanDefinition("pet", rootBeanDefinition);


        //添加子类ChildBeanDefinition
        ChildBeanDefinition childBeanDefinition = new ChildBeanDefinition("pet");
        childBeanDefinition.setBeanClassName("com.expect.spring.entity.Cat");
        MutablePropertyValues cValues = new MutablePropertyValues();
        cValues.addPropertyValue("color", "黑色");
        childBeanDefinition.setPropertyValues(cValues);

        simpleBeanDefinitionRegistry.registerBeanDefinition("cat", childBeanDefinition);
    }

    @Test
    void xmlBeanDefinitionReaderTest() {
        BeanDefinitionRegistry beanDefinitionRegistry = new SimpleBeanDefinitionRegistry();

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanDefinitionRegistry);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:bean.xml");
        log.info("User:{}", beanDefinitionRegistry.getBeanDefinition("user").getBeanClassName());
        log.info("User:{}", beanDefinitionRegistry.getBeanDefinition("pet").getBeanClassName());
        log.info("User:{}", beanDefinitionRegistry.getBeanDefinition("cat").getBeanClassName());
    }

    @Test
    void annotatedBeanDefinitionReaderTest() {
        BeanDefinitionRegistry beanDefinitionRegistry = new SimpleBeanDefinitionRegistry();
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(beanDefinitionRegistry);
        annotatedBeanDefinitionReader.registerBean(ExpectConfig.class);
        String[] beanDefinitionNames = beanDefinitionRegistry.getBeanDefinitionNames();
        log.info(Arrays.toString(beanDefinitionNames));
    }

    @Test
    void annotatedBeanDefinitionReader2Test() {
        BeanDefinitionRegistry beanDefinitionRegistry = new SimpleBeanDefinitionRegistry();
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(beanDefinitionRegistry);
        annotatedBeanDefinitionReader.registerBean(User.class);
        String[] beanDefinitionNames = beanDefinitionRegistry.getBeanDefinitionNames();
        log.info(Arrays.toString(beanDefinitionNames));
    }


    @Test
    void classPathBeanDefinitionScannerTest() {
        BeanDefinitionRegistry beanDefinitionRegistry = new SimpleBeanDefinitionRegistry();
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        classPathBeanDefinitionScanner.scan("com.expect.spring.config");
        String[] beanDefinitionNames = beanDefinitionRegistry.getBeanDefinitionNames();
        log.info(Arrays.toString(beanDefinitionNames));
    }

    @Test
    void UrlResourceTest() throws IOException {
        Resource urlResource = new UrlResource("https://images.liqucn.com/img/h22/h83/img_localize_f2a2a76aa032509dbe143b7faedbc14b_500x375.png");
        InputStream in = urlResource.getInputStream();

        File file = new File("F:\\temp\\b.png");
        OutputStream out = new FileOutputStream(file);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }

        in.close();
        out.close();
    }

    @Test
    void classPathResourceTest() throws IOException {
        Resource urlResource = new ClassPathResource("bean.xml");
        InputStream in = urlResource.getInputStream();

        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            System.out.print(new String(buffer, 0, length));
        }

        in.close();
    }

    @Test
    void pathMatchingResourcePatternResolverTest() throws IOException {
        PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
        Resource httpResource = resourceLoader.getResource("https://images.liqucn.com/img/h22/h83/img_localize_f2a2a76aa032509dbe143b7faedbc14b_500x375.png");
        Resource classpathResource = resourceLoader.getResource("classpath:bean.xml");
        Resource fileResource = resourceLoader.getResource("file://D:/b.png");
        Resource[] resources = resourceLoader.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
            System.out.println(resource.getURI());
        }
    }

    @Test
    void environmentTest() {
        ApplicationContext applicationContext = new GenericApplicationContext();
        Environment environment = applicationContext.getEnvironment();
        boolean javaHome = environment.containsProperty("JAVA_HOME");
        if (javaHome) {
            log.info("JAVA_HOME:{}", environment.getProperty("JAVA_HOME"));
        } else {
            log.info("没有");
        }

    }


    @Test
    void environment1Test() {
        StandardEnvironment standardEnvironment = new StandardEnvironment();

    }

    public class  MyApplicationListener implements ApplicationListener<MyApplicationEvent>{
        @Override
        public void onApplicationEvent(MyApplicationEvent event) {
            System.out.println(event.getSource()+"   MyApplicationListener");
        }
    }

    public class  YouApplicationListener implements ApplicationListener<MyApplicationEvent>{
        @Override
        public void onApplicationEvent(MyApplicationEvent event) {
            System.out.println(event.getSource()+"   YouApplicationListener");
        }
    }

    public class  MyApplicationEvent extends ApplicationEvent{
        public MyApplicationEvent(Object source) {
            super(source);
        }
    }
    @Test
    void safas(){
        SimpleApplicationEventMulticaster eventMulticaster=new SimpleApplicationEventMulticaster();
        eventMulticaster.addApplicationListener(new MyApplicationListener());
        eventMulticaster.addApplicationListener(new YouApplicationListener());
        eventMulticaster.addApplicationListener(new MyApplicationListener());
        eventMulticaster.multicastEvent(new MyApplicationEvent(this));
    }


}
