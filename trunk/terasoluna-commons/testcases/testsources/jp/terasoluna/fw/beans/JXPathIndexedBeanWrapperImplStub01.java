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
 * <br><br>
 * 
 * testEscapeMapPropertyXX()���\�b�h�Ŏg�p����B<br>
 * testEextractIncremantIndexStringIntXX()���\�b�h�Ŏg�p����B<br>
 *
 */
public class JXPathIndexedBeanWrapperImplStub01 extends
        JXPathIndexedBeanWrapperImpl {
    
    /**
     * extractIndex�X�^�u���\�b�h�̓��͊m�F�p�B
     */
    public String extractIndexParam1 = null;
    
    /**
     * extractIndex�X�^�u���\�b�h�̖߂�l�ݒ�p�B
     */
    public String extractIndexResult = null;
    
    /**
     * �R���X�g���N�^�B
     * @param obj ���b�v����Bean
     */
    public JXPathIndexedBeanWrapperImplStub01(Object obj) {
        super(obj);
    }
    
    /**
     * Map�^�����̃v���p�e�B�������o���X�^�u���\�b�h�B
     * @param property Java�v���p�e�B���B
     * @return String XPath�B 
     */
    @Override
    protected String extractMapPropertyName(String property) {
        // ���I�ɖ߂�l���쐬�B
        return property + "Name";
    }
    
    /**
     * Map�^�����̃L�[�������o���X�^�u���\�b�h�B
     * @param property Java�v���p�e�B���B
     * @return String XPath�B 
     */
    @Override
    protected String extractMapPropertyKey(String property) {
        // ���I�ɖ߂�l���쐬�B
        return property + "Key";
    }
    
    
    /**
     * �z��C���f�b�N�X���擾����X�^�u���\�b�h�B
     * @param property �v���p�e�B���B
     * @return �z��C���f�b�N�X�B
     */
    @Override
    protected String extractIndex(String property) {
        // ���͊m�F�p�����o�Ɋi�[�B
        this.extractIndexParam1 = property;
        
        // �߂�l�ݒ�p�����o�̒l��Ԃ��B
        return this.extractIndexResult;
    }

    
}
