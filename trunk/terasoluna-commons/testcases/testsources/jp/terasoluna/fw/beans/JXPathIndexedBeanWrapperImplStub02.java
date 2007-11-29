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
 * 
 * testExtractIncrementIndexStringXX()���\�b�h�Ŏg�p����B
 *
 */
public class JXPathIndexedBeanWrapperImplStub02 extends
        JXPathIndexedBeanWrapperImpl {
 
    
    /**
     * �R���X�g���N�^�B
     * @param obj ���b�v����Bean
     */
    public JXPathIndexedBeanWrapperImplStub02(Object obj) {
        super(obj);
    }

    /**
     * �C���N�������g���ꂽ�Y���������o���X�^�u���\�b�h�B
     * @param property Java�v���p�e�B���B
     * @return String XPath�`���̓Y�����B 
     */
    @Override
    protected String extractIncrementIndex(String property,int increment) {
        // ���I�ɖ߂�l���쐬�B
        return property + ":" + increment;
    }
    

}
