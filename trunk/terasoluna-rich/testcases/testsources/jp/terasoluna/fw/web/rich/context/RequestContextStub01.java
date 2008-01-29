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

import jp.terasoluna.fw.web.rich.context.RequestContext;

/**
 * ���N�G�X�g���E�Ɩ��v���p�e�B��ێ�����X�^�u�N���X�B
 *
 */
public class RequestContextStub01 extends RequestContext {

    /**
     * �v���p�e�B�L�[�m�F�p�B
     */
    public String key = null;

    /**
     * �v���p�e�B�l�m�F�p�B
     */
    public Object value = null;

    /**
     * �Ɩ��v���p�e�B���擾����B
     * @param key �v���p�e�B�L�[
     * @return value �v���p�e�B�l
     */
    @Override
    public Object getProperty(String key) {
    	this.key = key;
        return this.value;
    }
    
    /**
     * �Ɩ��v���p�e�B��ݒ肷��B
     * @param key �v���p�e�B�L�[
     * @param value �v���p�e�B�l
     */
    @Override
    public void setProperty(String key, Object value) {
    	this.key = key;
    	this.value = value;
    }
    
    

}
