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

package jp.terasoluna.fw.web.struts.actions;

import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ControllerConfig;
import org.apache.struts.config.DataSourceConfig;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.MessageResourcesConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.PlugInConfig;

/**
 * ModuleConfigŽÀ‘•ƒNƒ‰ƒX
 * 
 */
public class ModuleConfigImpl01 implements ModuleConfig {

    /**
     * prefix:"prefix"
     */
    public String getPrefix() {
        return "prefix";
    }
    
    public boolean getConfigured() {
        return false;
    }

    public ControllerConfig getControllerConfig() {
        return null;
    }

    public void setControllerConfig(ControllerConfig cc) {
    }

    public void setPrefix(String prefix) {
    }

    public String getActionFormBeanClass() {
        return null;
    }

    public void setActionFormBeanClass(String actionFormBeanClass) {
    }

    public String getActionMappingClass() {
        return null;
    }

    public void setActionMappingClass(String actionMappingClass) {
    }

    public void addActionConfig(ActionConfig config) {
    }

    public void addDataSourceConfig(DataSourceConfig config) {
    }

    public void addExceptionConfig(ExceptionConfig config) {
    }

    public void addFormBeanConfig(FormBeanConfig config) {
    }

    public String getActionForwardClass() {
        return null;
    }

    public void setActionForwardClass(String actionForwardClass) {
    }

    public void addForwardConfig(ForwardConfig config) {
    }

    public void addMessageResourcesConfig(MessageResourcesConfig config) {
    }

    public void addPlugInConfig(PlugInConfig plugInConfig) {
    }

    public ActionConfig findActionConfig(String path) {
        return null;
    }

    public ActionConfig[] findActionConfigs() {
        return null;
    }

    public DataSourceConfig findDataSourceConfig(String key) {
        return null;
    }

    public DataSourceConfig[] findDataSourceConfigs() {
        return null;
    }

    public ExceptionConfig findExceptionConfig(String type) {
        return null;
    }

    public ExceptionConfig[] findExceptionConfigs() {
        return null;
    }

    public FormBeanConfig findFormBeanConfig(String name) {
        return null;
    }

    public FormBeanConfig[] findFormBeanConfigs() {
        return null;
    }

    public ForwardConfig findForwardConfig(String name) {
        return null;
    }

    public ForwardConfig[] findForwardConfigs() {
        return null;
    }

    public MessageResourcesConfig findMessageResourcesConfig(String key) {
        return null;
    }

    public MessageResourcesConfig[] findMessageResourcesConfigs() {
        return null;
    }

    public PlugInConfig[] findPlugInConfigs() {
        return null;
    }

    public void freeze() {
    }

    public void removeActionConfig(ActionConfig config) {
    }

    public void removeExceptionConfig(ExceptionConfig config) {
    }

    public void removeDataSourceConfig(DataSourceConfig config) {
    }

    public void removeFormBeanConfig(FormBeanConfig config) {
    }

    public void removeForwardConfig(ForwardConfig config) {
    }

    public void removeMessageResourcesConfig(MessageResourcesConfig config) {
    }
}
