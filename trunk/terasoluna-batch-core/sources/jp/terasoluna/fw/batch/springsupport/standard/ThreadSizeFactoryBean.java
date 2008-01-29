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

package jp.terasoluna.fw.batch.springsupport.standard;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * �X���b�h�v�[���̃T�C�Y�𐶐�����N���X�B
 * �����W���u�̑��d�x�~�Q���X���b�h�v�[���T�C�Y�Ƃ��ĕԂ��B
 * 
 */
public class ThreadSizeFactoryBean implements FactoryBean, InitializingBean {

    /**
     * ���d�x
     */
    private int multiplicity = 0;

    /**
     * ��������X���b�h���B
     */
    private Integer threadSize = null;

    /**
     * <code>FactoryBean</code>�̎����B
     * ��������X���b�h���̃C���X�^���X��Ԃ��B
     * 
     * @return ��������X���b�h��
     */
    public Object getObject() {
        return this.threadSize;
    }

    /**
     * <code>FactoryBean</code>�̎����B
     * <code>FactoryBean</code>���琶�������C���X�^���X�̃^�C�v�B
     * 
     * @return Integer.class
     */
    public Class getObjectType() {
        return Integer.class;
    }

    /**
     * <code>FactoryBean</code>�̎����B
     * <code>Singleton</code>�ł��邩��]������B
     * 
     * @return <code>true</code>
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * ���d�x��ݒ肷��B
     * 
     * @param multiplicity ���d�x
     */
    public void setMultiplicity(int multiplicity) {
        this.multiplicity = multiplicity;
    }

    /**
     * <code>InitializingBean</code>�̎����B
     * �C���X�^���X�쐬���̏������s�B
     * ���d�x�~�Q��<code>threadSize</code>�ɐݒ肷��B
     * 
     */
    public void afterPropertiesSet() {
//        this.threadSize = new Integer(multiplicity * 2);
        this.threadSize = Integer.valueOf(multiplicity * 2);
    }

}
