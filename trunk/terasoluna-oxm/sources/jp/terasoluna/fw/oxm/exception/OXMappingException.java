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

package jp.terasoluna.fw.oxm.exception;

/**
 *  �`���`�F�b�N����уo�C���h�������Ŕ���������O�����b�v����N���X�B
 *  
 * <p>
 *  ���ۂɔ���������O�ɂ��Ă�{@link java.lang.Throwable#getCause}���\�b�h����擾����B
 * </p>
 * 
 */
public class OXMappingException extends RuntimeException {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = -5964737290418496214L;

    /**
     * �R���X�g���N�^
     * 
     */
    public OXMappingException() {
        super();
    }

    /**
     * �R���X�g���N�^
     * 
     * @param cause �X�L�[�}�ɂ��`���`�F�b�N�A�����Castor�ɂ��o�C���h�����Ŕ���������O
     */
    public OXMappingException(Throwable cause) {
        super(cause);
    }
}
