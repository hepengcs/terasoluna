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

package jp.terasoluna.fw.message;

import java.io.Serializable;

/**
 * ���b�Z�[�W���\�[�X��ێ�����N���X�B<br>
 * ���b�Z�[�W���\�[�X�ɂ̓��b�Z�[�W�R�[�h�A����R�[�h�A���R�[�h�A
 * �o���A���g�R�[�h�A���b�Z�[�W�{�̂��i�[�����B
 * 
 * @see jp.terasoluna.fw.message.DataSourceMessageSource
 * @see jp.terasoluna.fw.message.DBMessageQuery
 * @see jp.terasoluna.fw.message.DBMessageResourceDAO
 * @see jp.terasoluna.fw.message.DBMessageResourceDAOImpl
 * 
 */
public class DBMessage implements Serializable {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = 299442236623116335L;

    /**
     * ���b�Z�[�W�R�[�h�B
     */
    protected String code = null;

    /**
     * ���b�Z�[�W�̌���R�[�h�B
     */
    protected String language = null;

    /**
     * ���b�Z�[�W�̍��R�[�h�B
     */
    protected String country = null;

    /**
     * ���b�Z�[�W�̃o���A���g�R�[�h�B
     */
    protected String variant = null;

    /**
     * ���b�Z�[�W�{�́B
     */
    protected String message = null;

    /**
     * DBMessage�Ƀ��b�Z�[�W�R�[�h�A����R�[�h�A���R�[�h�A�o���A���g�R�[�h�A
     * ���b�Z�[�W�{�̂��i�[����B
     * 
     * @param code ���b�Z�[�W�R�[�h�B
     * @param language ���b�Z�[�W�̌���R�[�h�B
     * @param country ���b�Z�[�W�̍��R�[�h�B
     * @param variant ���b�Z�[�W�̃o���A���g�R�[�h�B
     * @param message ���b�Z�[�W�{�́B
     */
    public DBMessage(String code, String language, String country,
            String variant, String message) {
        this.code = code;
        this.language = language;
        this.country = country;
        this.variant = variant;
        this.message = message;
    }

    /**
     * DBMessage�I�u�W�F�N�g���烁�b�Z�[�W�R�[�h���擾����B
     * 
     * @return ���b�Z�[�W�R�[�h�B
     */
    public String getCode() {
        return code;
    }

    /**
     * DBMessage�I�u�W�F�N�g���烁�b�Z�[�W�̌���R�[�h���擾����B
     * 
     * @return ���b�Z�[�W�̌���R�[�h�B
     */
    public String getLanguage() {
        return language;
    }

    /**
     * DBMessage�I�u�W�F�N�g���烁�b�Z�[�W�̍��R�[�h���擾����B
     * 
     * @return ���b�Z�[�W�̍��R�[�h�B
     */
    public String getCountry() {
        return country;
    }

    /**
     * DBMessage�I�u�W�F�N�g���烁�b�Z�[�W�̃o���A���g�R�[�h���擾����B
     * 
     * @return ���b�Z�[�W�̃o���A���g�R�[�h�B
     */
    public String getVariant() {
        return variant;
    }
    
    /**
     * DBMessage�I�u�W�F�N�g���烁�b�Z�[�W�{�̂��擾����B
     * 
     * @return ���b�Z�[�W�{�́B
     */
    public String getMessage() {
        return message;
    }
}
