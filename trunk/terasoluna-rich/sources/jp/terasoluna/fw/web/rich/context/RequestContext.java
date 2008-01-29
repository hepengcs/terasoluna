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

package jp.terasoluna.fw.web.rich.context;

import java.util.HashMap;
import java.util.Map;

/**
 * ���N�G�X�g���E�Ɩ��v���p�e�B��ێ����邽�߂̃N���X�B
 *
 * <p>
 * �Ɩ��������s����ŕK�v�ƂȂ鐧�����ێ�����B
 * ������Ƃ͈ȉ�������킷�B
 * </p>
 * 
 * <ol>
 * <li>���N�G�X�g���B</li>
 * <li>�Ɩ��v���p�e�B�B</li>
 * </ol>
 * 
 * <p>
 * ���N�G�X�g���Ƃ́A���N�G�X�g�����ʂ��邽�߂̕�����ł���A�t���[�����[�N���痘�p�����B
 * </p>
 * 
 * <p>
 * �Ɩ��v���p�e�B�́A���N�G�X�g�Ԃŋ��ʓI�Ɉ�������ێ�������̂ł���A
 * �f�t�H���g�ł̓t���[�����[�N���痘�p����Ȃ��B
 * �Ɩ��̗v���ɂ��킹�ĕK�v�ɉ����ė��p���邱�ƁB
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.context.support.AbstractRequestContextSupport
 * 
 */
public class RequestContext  {
    /**
     * ���N�G�X�g���B
     */
    private String requestName = null;
    
    /**
     * �Ɩ��v���p�e�B�B
     */
    private Map<String, Object> propertyMap = new HashMap<String, Object>();

    /**
     * ���N�G�X�g�����擾����B
     * @return ���N�G�X�g��
     */
    public String getRequestName() {
        return requestName;
    }

    /**
     * ���N�G�X�g����ݒ肷��B
     * @param requestName ���N�G�X�g��
     */
    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }
    
    /**
     * �Ɩ��v���p�e�B���擾����B
     * @param key �v���p�e�B�L�[
     * @return �v���p�e�B�l
     */
    public Object getProperty(String key) {
        return propertyMap.get(key);
    }
    
    /**
     * �Ɩ��v���p�e�B��ݒ肷��B
     * @param key �v���p�e�B�L�[
     * @param value �v���p�e�B�l
     */
    public void setProperty(String key, Object value) {
    	propertyMap.put(key, value);
    }
    
    /**
     * �Ɩ��v���p�e�B���擾����B
     * �v���p�e�B�l��String�^�łȂ��ꍇ��null��Ԃ��B
     * @param key �v���p�e�B�L�[
     * @return �v���p�e�B�l
     */
    public String getPropertyString(String key) {
    	Object object = getProperty(key);
    	
    	if (object instanceof String) {
    		return (String) object;
        }
    	
        return null;
    }
    
    /**
     * �Ɩ��v���p�e�B��ݒ肷��B
     * @param key �v���p�e�B�L�[
     * @param value �v���p�e�B�l
     */
    public void setPropertyString(String key, String value) {
    	setProperty(key, value);
    }
    
    /**
     * ���C���X�^���X�̕�����\����Ԃ��B
     * @return ���C���X�^���X�̕�����\���B
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // ���N�G�X�g���̕�����
        sb.append("requestName:");
        sb.append(requestName);
        sb.append(",");
        
        // �Ɩ��v���p�e�B�̕�����
        sb.append("properties:");
        sb.append(propertyMap.toString());
        
        return sb.toString();
    }
}
