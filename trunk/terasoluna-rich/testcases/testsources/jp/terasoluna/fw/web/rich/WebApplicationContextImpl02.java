/*
 * Copyright (c) 2007 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.web.rich;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.io.Resource;
import org.springframework.ui.context.Theme;
import org.springframework.web.context.WebApplicationContext;

/**
 * WebApplicationContext実装クラス
 * getBeanがBeanNotOfRequiredTypeExceptionを起こす。
 * 
 */
public class WebApplicationContextImpl02 implements WebApplicationContext {

    /**
     * 引数確認用
     */
    public String name = null;
    
    /**
     * 引数確認用
     */
    public Class requiredType = null;
    
    /**
     * BeanId "forbiddenURIChecker"に対して
     * ForbiddenURIChecker以外を返し、BeanNotOfRequiredTypeException発生
     */
    public Object getBean(String name, Class requiredType)
    throws BeansException {
        this.name = name;
        this.requiredType = requiredType;
        
        throw new BeanNotOfRequiredTypeException(name, requiredType, Object.class);
    }
    
    
    public ServletContext getServletContext() {
        return null;
    }

    public ApplicationContext getParent() {
        return null;
    }

    public String getDisplayName() {
        return null;
    }

    public long getStartupDate() {
        return 0;
    }

    public void publishEvent(ApplicationEvent event) {
    }

    public boolean containsBeanDefinition(String beanName) {
        return false;
    }

    public int getBeanDefinitionCount() {
        return 0;
    }

    public String[] getBeanDefinitionNames() {
        return null;
    }

    @SuppressWarnings("deprecation")
    @Deprecated
    public String[] getBeanDefinitionNames(Class type) {
        return null;
    }

    public String[] getBeanNamesForType(Class type) {
        return null;
    }

    public String[] getBeanNamesForType(Class type, boolean includePrototypes,
            boolean includeFactoryBeans) {
        return null;
    }

    public Map getBeansOfType(Class type) throws BeansException {
        return null;
    }

    public Map getBeansOfType(Class type, boolean includePrototypes,
            boolean includeFactoryBeans) throws BeansException {
        return null;
    }

    public Object getBean(String name) throws BeansException {
        return null;
    }

    public boolean containsBean(String name) {
        return false;
    }

    public boolean isSingleton(String name)
            throws NoSuchBeanDefinitionException {
        return false;
    }

    public Class getType(String name) throws NoSuchBeanDefinitionException {
        return null;
    }

    public String[] getAliases(String name)
            throws NoSuchBeanDefinitionException {
        return null;
    }

    public BeanFactory getParentBeanFactory() {
        return null;
    }

    public String getMessage(String code, Object[] args, String defaultMessage,
            Locale locale) {
        return null;
    }

    public String getMessage(String code, Object[] args, Locale locale)
            throws NoSuchMessageException {
        return null;
    }

    public String getMessage(MessageSourceResolvable resolvable, Locale locale)
            throws NoSuchMessageException {
        return null;
    }

    public Resource[] getResources(String locationPattern) throws IOException {
        return null;
    }

    public Resource getResource(String location) {
        return null;
    }

    public Theme getTheme(String themeName) {
        return null;
    }


    public boolean containsLocalBean(String arg0) {
        return false;
    }
    
    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {

        return null;
    }

    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {

        return false;
    }

    public boolean isTypeMatch(String name, Class targetType) throws NoSuchBeanDefinitionException {

        return false;
    }

    public ClassLoader getClassLoader() {

        return null;
    }


}