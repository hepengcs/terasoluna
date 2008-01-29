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

package jp.terasoluna.fw.validation.springmodules;

import java.util.Locale;

import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorResources;

/**
 * ValidatorResources�̃X�^�u�N���X�B
 *
 */
public class CommonsValidatorEx_ValidatorResourcesStub01 extends
        ValidatorResources {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = 9152907604153954344L;
    
    /**
     * �t�H�[��
     */
    private Form form;

    /**
     * �t�H�[�����擾����
     * @param locale ���[�J��
     * @param formKey �t�H�[���L�[
     * @return �t�H�[��
     */
    @Override
    public Form getForm(Locale locale, String formKey) {
        return form;
    }

    /**
     * �t�H�[����ݒ肷��
     * @param form �t�H�[��
     */
    public void setForm(Form form){
        this.form = form;
    }
}
