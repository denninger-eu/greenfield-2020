package eu.k5.greenfield.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.InvocationTargetException;

/*@Configuration
public class RepositoryConfigurationBean implements BeanFactoryPostProcessor {
    @Autowired
    private GenericApplicationContext context;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        try {
            // Replace X1.class with the one from bytebuddy
            // The name should be unique
            configurableListableBeanFactory.registerSingleton("abc", X1.class.getConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("abc");
    }*/
/*
    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory)
            throws BeansException {

        beanFactory.register
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(X1.class);
        bd.getPropertyValues().add("strProp", "my string property");
bd.
        ((DefaultListableBeanFactory) beanFactory)
                .registerBeanDefinition("myBeanName", bd);
    }*/
//}