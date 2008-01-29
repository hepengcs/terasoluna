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

package jp.terasoluna.fw.oxm.xsd;

import java.io.InputStream;

import org.w3c.dom.Document;

import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;

/**
 * XML�f�[�^�̌`���`�F�b�N���s���N���X���������ׂ��C���^�t�F�[�X�B
 * <p>
 * �ʏ�͎����N���X�Ƃ��āA{@link jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl}�𗘗p����΂悢�B
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 * 
 */
public interface SchemaValidator {

    /**
     * XML�f�[�^�̌`���`�F�b�N���s�����\�b�h�B
     * 
     * @param in XML�f�[�^�̓��̓X�g���[��
     * @param object ��`�t�@�C���̃p�X���������邽�߂̃I�u�W�F�N�g
     * @param errorMessages �G���[���b�Z�[�W���i�[�����C���X�^���X
     * 
     * @return DOM�c���[
     */
    Document validate(InputStream in, Object object,
            ErrorMessages errorMessages);
}
