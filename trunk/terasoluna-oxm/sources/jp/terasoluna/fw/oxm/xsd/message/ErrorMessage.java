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

package jp.terasoluna.fw.oxm.xsd.message;

/**
 * �G���[���b�Z�[�W��ێ�����N���X�B
 * 
 * <p>
 * �ȉ��̏���ێ�����B
 * <li>�G���[�R�[�h</li>
 * <li>�u��������</li>
 * <li>�G���[�����������t�B�[���h���</li>
 * </p>
 * 
 * <p>
 * �ʏ�A���̃N���X�̓��b�Z�[�W�o���h���Ɏg�p�����B
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.SchemaValidator
 * @see jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 * 
 */
public class ErrorMessage {

    /**
     * �G���[�R�[�h�B
     */ 
    private String key = null;

    /**
     * �u��������B
     */
    private String[] replaceValues = null;

    /**
     * �G���[�����������t�B�[���h�B
     */
    private String field = null;

    /**
     * ErrorMessage�𐶐�����B
     * 
     * @param key �G���[�R�[�h
     * @param field �G���[�����������t�B�[���h
     * @param values �u��������
     */
    public ErrorMessage(String key, String field, String... values) {
        this.key = key;
        this.field = field;
        this.replaceValues = values;
    }

    /**
     * �G���[�R�[�h��ԋp����B
     * 
     * @return �G���[�R�[�h
     */
    public String getKey() {
        return key;
    }

    /**
     * �G���[�R�[�h��ݒ肷��B
     * 
     * @param key �G���[�R�[�h
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * �u���������ԋp����B
     * 
     * @return �u��������
     */
    public String[] getReplaceValues() {
        return replaceValues;
    }

    /**
     * �u���������ԋp����B
     * 
     * @param replaceValues �u��������
     */
    public void setReplaceValues(String[] replaceValues) {
        this.replaceValues = replaceValues;
    }

    /**
     * �G���[�����������t�B�[���h��ԋp����B
     * 
     * @return �G���[�����������t�B�[���h
     */
    public String getField() {
        return field;
    }

    /**
     * �G���[�����������t�B�[���h��ݒ肷��B
     * 
     * @param field �G���[�����������t�B�[���h
     */
    public void setField(String field) {
        this.field = field;
    }

}
