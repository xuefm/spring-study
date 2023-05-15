package com.expect.spring;

import com.expect.spring.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class ToolTest {
    @Test
    public void introspectorTest() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(User.class, Object.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            log.info("name:[{}]", propertyDescriptor.getName());
            log.info("type:[{}]", propertyDescriptor.getPropertyType());
            log.info("writeMethod:[{}]", propertyDescriptor.getWriteMethod());
            log.info("readMethod:[{}]", propertyDescriptor.getReadMethod());
        }
    }

    @Test
    public void introspector1Test() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        User user = new User();
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor("age", User.class);
        Method writeMethod = propertyDescriptor.getWriteMethod();
        writeMethod.invoke(user, "13");
        log.info("user:[{}]", user);
        Method readMethod = propertyDescriptor.getReadMethod();
        Object age = readMethod.invoke(user);
        log.info("age:[{}]", age);
    }

    @Test
    void beanWrapperTest() throws ClassNotFoundException {
        //获取BeanDefinition
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClassName("com.expect.spring.entity.User");
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue("name", "33");
        propertyValues.addPropertyValue("age", 16);
        genericBeanDefinition.setPropertyValues(propertyValues);

        //实例化
        Class<?> aClass = Class.forName(genericBeanDefinition.getBeanClassName());
        BeanWrapper beanWrapper = new BeanWrapperImpl(aClass);
        beanWrapper.setPropertyValues(genericBeanDefinition.getPropertyValues());
        Object wrappedInstance = beanWrapper.getWrappedInstance();
        log.info("obj:[{}]", wrappedInstance);
    }

    @Test
    void beanWrapperBatchTest() throws ClassNotFoundException {
        BeanDefinitionRegistry beanDefinitionRegistry = new SimpleBeanDefinitionRegistry();
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        classPathBeanDefinitionScanner.scan("com.expect.spring");
        for (String beanDefinitionName : beanDefinitionRegistry.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanDefinitionName);
            //实例化
            Class<?> aClass = Class.forName(beanDefinition.getBeanClassName());
            BeanWrapper beanWrapper = new BeanWrapperImpl(aClass);
            beanWrapper.setPropertyValues(beanDefinition.getPropertyValues());
            Object wrappedInstance = beanWrapper.getWrappedInstance();
            log.info("obj:[{}]", wrappedInstance);
        }

    }

    @Test
    void beanWrapperBatch2Test() throws ClassNotFoundException {
        BeanDefinitionRegistry beanDefinitionRegistry = new SimpleBeanDefinitionRegistry();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanDefinitionRegistry);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:bean.xml");
        for (String beanDefinitionName : beanDefinitionRegistry.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanDefinitionName);
            //实例化
            Class<?> aClass = Class.forName(beanDefinition.getBeanClassName());
            BeanWrapper beanWrapper = new BeanWrapperImpl(aClass);
            beanWrapper.setPropertyValues(beanDefinition.getPropertyValues());
            Object wrappedInstance = beanWrapper.getWrappedInstance();
            log.info("obj:[{}]", wrappedInstance);
        }
    }

    private Map<Integer, List<String>> map = new HashMap<>();

    @Test
    public void resolvableTypeTest() throws NoSuchFieldException {
        ResolvableType resolvableType = ResolvableType.forField(getClass().getDeclaredField("map"));
        log.info("{}", resolvableType.getSuperType());
        log.info("{}", resolvableType.getType());
        log.info("{}", resolvableType.getGeneric(0));
        log.info("{}", resolvableType.getGeneric(1));
        log.info("{}", resolvableType.getGeneric(1).getGeneric(0));
    }


    @Test
    void beanWrapperBatch3Test() throws ClassNotFoundException {
        BeanDefinitionRegistry beanDefinitionRegistry = new SimpleBeanDefinitionRegistry();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanDefinitionRegistry);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:bean.xml");
        for (String beanDefinitionName : beanDefinitionRegistry.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanDefinitionName);
            //实例化
            Class<?> aClass = Class.forName(beanDefinition.getBeanClassName());
            BeanWrapper beanWrapper = new BeanWrapperImpl(aClass);
            beanWrapper.setConversionService(new DefaultConversionService());
            beanWrapper.setPropertyValues(beanDefinition.getPropertyValues());
            Object wrappedInstance = beanWrapper.getWrappedInstance();
            log.info("obj:[{}]", wrappedInstance);
        }
    }

    @Test
    public void testStandardEnvironment() {
        //定义一个标准环境
        StandardEnvironment env = new StandardEnvironment();
        //定义一个Properties
        Properties properties = new Properties();
        properties.setProperty("age", "12");
        PropertySource source = new PropertiesPropertySource("my-propertySource ", properties);
        MutablePropertySources propertySources = env.getPropertySources();
        propertySources.addLast(source);
        System.out.println(env.getProperty("age"));
    }

    @Test
    public void i18nTest() {
        ResourceBundleMessageSource source=new ResourceBundleMessageSource();
        source.setBasename("i18n/message");
        source.setDefaultEncoding("UTF-8");
        source.setDefaultLocale(Locale.ENGLISH);
        System.out.println(source.getMessage("hello", new Object[]{"tom"}, Locale.US));
        System.out.println(source.getMessage("hello", new Object[]{"tom"}, Locale.CHINA));
        System.out.println(source.getMessage("hello", new Object[]{"tom"}, Locale.getDefault()));
    }
    @Test
    public void expressionParserTest() {
        ExpressionParser expressionParser=new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression("'hello'.length");
        System.out.println(expression.getValue());
    }


}
