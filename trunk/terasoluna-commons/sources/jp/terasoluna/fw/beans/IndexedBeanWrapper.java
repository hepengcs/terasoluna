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

import java.util.Map;

/**
 * JavaBean�̔z��E�R���N�V�����^�����ւ̃A�N�Z�T�����C���^�t�F�[�X�B
 * 
 * <p>{@link #getIndexedPropertyValues(String)}���\�b�h�ŁA
 * �z��^�����ɃA�N�Z�X����B
 * String�^�̈����ɃA�N�Z�X����v���p�e�B�����w�肷��ƁA
 * �v���p�e�B���Ɉ�v���鑮����S�Ď擾����B
 * �߂�l��Map�i�L�[���v���p�e�B���A�l�������l�j���Ԃ����B
 * {@link #getIndexedPropertyValues(String)}���\�b�h�́A�z��^�ȊO�ł�
 * �g�p���\�ł���B</p>
 * 
 * <h5>�z��^�����ɃA�N�Z�X�����</h5>
 * <p>
 * <pre>
 * public class TestBean {
 *     private String[] stringArray;
 *     
 *     ����i�ȉ��Agetter/setter�͗��j
 * </pre>
 * <pre>
 * IndexedBeanWrapperImpl bw = new JXPathIndexedBeanWrapperImpl(bean);
 * Map map = bw.getIndexedPropertyValues("stringArray");
 * </pre>
 * </p>
 * 
 * <p>{@link #getIndexedPropertyValues(String)}���\�b�h��stringArray������
 * �A�N�Z�X����ƁAstringArray[0]�AstringArray[1]���stringArray[n]�܂ł�
 * �v���p�e�B���Ƒ����l��Map�^�ɂ��ĕԂ��B
 * �����ɂ́hstringArray[0]�h�̂悤�ɁA���ڗv�f���w�肷��K�v�͂Ȃ��B</p>
 * 
 *
 */
public interface IndexedBeanWrapper {
    /**
     * �w�肵���v���p�e�B���Ɉ�v���鑮���l��Ԃ��B
     * �擾�����v���p�e�B���̓C���f�b�N�X���L�[�ɏ����Ƀ\�[�g����Ă���B
     * @param propertyName �v���p�e�B��
     * @return �v���p�e�B���Ɉ�v���鑮���l���i�[����Map
     * �i�v���p�e�B���A�����l�j
     */
    Map<String, Object> getIndexedPropertyValues(String propertyName);
}
