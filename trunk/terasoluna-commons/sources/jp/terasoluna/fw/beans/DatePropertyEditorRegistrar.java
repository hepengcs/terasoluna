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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;

/**
 * Date�^�̃v���p�e�B�G�f�B�^�𐶐�����N���X�B
 *
 */
public class DatePropertyEditorRegistrar 
    implements PropertyEditorRegistrar {
    
    /**
     * ���̃v���p�e�B�G�f�B�^���g�p������t�t�H�[�}�b�g�B
     */
    private DateFormat dateFormat = DEFAULT_DATE_FORMAT;
    
    /**
     * �f�t�H���g�̓��t�t�H�[�}�b�g�BYYYY/MM/DD�`���B
     */
    private static final DateFormat DEFAULT_DATE_FORMAT 
        = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * ���t�t�H�[�}�b�g��ݒ肷��B
     * @param dateFormat ���t�t�H�[�}�b�g
     */
    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    /**
     * �J�X�^���v���p�e�B�G�f�B�^�𐶐�����B
     * @param registry �v���p�e�B�G�f�B�^��ێ�����I�u�W�F�N�g
     */
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Date.class, 
                new CustomDateEditor(dateFormat, false));
    }
}