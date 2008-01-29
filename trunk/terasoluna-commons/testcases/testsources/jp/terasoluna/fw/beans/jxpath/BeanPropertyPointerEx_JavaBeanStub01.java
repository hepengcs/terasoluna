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

package jp.terasoluna.fw.beans.jxpath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaBean�̃X�^�u�B
 */
public class BeanPropertyPointerEx_JavaBeanStub01 implements Serializable {
    
    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 3092857648272149366L;
    
    private String property = "";
    private List listProperty = new ArrayList();

    /**
     * property���擾����B
     * @return property
     */
    public String getProperty() {
        return property;
    }

    /**
     * property��ݒ肷��B
     * @param property
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * listProperty���擾����B
     * @return listProperty
     */
    public List getListProperty() {
        return listProperty;
    }

    /**
     * listProperty��ݒ肷��B
     * @param listProperty
     */
    public void setListProperty(List listProperty) {
        this.listProperty = listProperty;
    }
}
