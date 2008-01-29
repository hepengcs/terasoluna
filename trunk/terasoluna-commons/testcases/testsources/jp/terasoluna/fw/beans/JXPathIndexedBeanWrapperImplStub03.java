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

package jp.terasoluna.fw.beans;

/**
 * JXPathIndexedBeanWrapperImplTest�Ŏg�p����X�^�u�N���X�B
 *
 */
public class JXPathIndexedBeanWrapperImplStub03 extends
        JXPathIndexedBeanWrapperImpl {
 
    /**
     * isMapProperty�X�^�u���\�b�h�̓��͊m�F�p�����o�B
     */
    public String isMapPropertyParam1 = null;
    
    /**
     * isMapProperty�X�^�u���\�b�h�̖߂�l�ݒ�p�����o�B
     */
    public boolean isMapPropertyResult = false;
    
    /**
     * extractIncrementIndex�X�^�u���\�b�h�̓��͊m�F�p�B
     */
    public String extractIncrementIndexParam1 = null;
    
    /**
     * extractIncrementIndex�X�^�u���\�b�h�̖߂�l�ݒ�p�B
     */
    public String extractIncrementIndexResult = null;
    
    
    /**
     * �R���X�g���N�^�B
     * @param obj ���b�v����Bean
     */
    public JXPathIndexedBeanWrapperImplStub03(Object obj) {
        super(obj);
    }

    /**
     * Map�^�������ǂ������f����X�^�u���\�b�h�B
     * @param property Java�v���p�e�B���B
     * @return boolean true��Ԃ��B 
     */
    @Override
    protected boolean isMapProperty(String property) {
        // ���͊m�F�p�����o�Ɋi�[�B
        this.isMapPropertyParam1 = property;
        
        // �߂�l�ݒ�p�����o�̒l��Ԃ��B
        return this.isMapPropertyResult;
    }
    

    /**
     * Map�v���p�e�B��XPath�`���ɃG�X�P�[�v����X�^�u���\�b�h�B
     * @param property Java�v���p�e�B���B
     * @return String XPath�B 
     */
    @Override
    protected String escapeMapProperty(String property) {
        // ���I�ɖ߂�l���쐬�B
        return property+"[@name='key']";
    }
    
    /**
     * �����������o���X�^�u���\�b�h�B
     * @param property XPath�̃m�[�h�B
     * @return �������B
     */
    @Override
    protected String extractAttributeName(String property) {
        // ���I�ɖ߂�l���쐬�B
        return property+"Attribute";
    }
    
    /**
     * �C���N�������g���ꂽ�Y���������o���X�^�u���\�b�h�B
     * @param property Java�v���p�e�B���B
     * @return String XPath�`���̓Y�����B 
     */
    @Override
    protected String extractIncrementIndex(String property) {
        // ���͊m�F�p�����o�Ɋi�[�B
        this.extractIncrementIndexParam1 = property;
        
        // �߂�l�ݒ�p�����o�̒l��Ԃ��B
        return extractIncrementIndexResult;
    }

    

    

}
