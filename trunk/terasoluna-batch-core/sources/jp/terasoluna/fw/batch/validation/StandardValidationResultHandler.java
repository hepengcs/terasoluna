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

package jp.terasoluna.fw.batch.validation;

import jp.terasoluna.fw.batch.messages.MessageAccessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

/**
 * <code>ValidationResultHandler</code> �C���^�t�F�[�X�̕W�������N���X�B
 * ���̓`�F�b�N���ʂ��珈�����f�A�����p���̔�����s���B<BR>
 *
 */
public class StandardValidationResultHandler 
    implements ValidationResultHandler {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = 
        LogFactory.getLog(StandardValidationResultHandler.class);

    /**
     * �G���[�p���t���O�B
     */
    private boolean errorContinueFlg = false;
    
    /**
     * ���b�Z�[�W�擾�N���X�̃C���X�^���X�B
     */
    private MessageAccessor messageAccessor = null;
    
    /**
     * ���̓`�F�b�N���ʂ��珈�����f�A�����p���̔�����s���B
     * <code>errorContinueFlg<code>��<code>true<code>�̏ꍇ��
     * ���̓`�F�b�N���ʂ��G���[�̏ꍇ�A<code>false<code>��Ԃ��B
     * <code>errorContinueFlg<code>��<code>false<code>�̏ꍇ��
     * ���̓`�F�b�N���ʂ��G���[�̏ꍇ�A��O�𔭐����������𒆒f����B
     *
     * @param bindException �L���[�����v���Z�b�T
     * @param value �L���[
     * @return ���茋�ʁA�G���[�̏ꍇ��<code>false<code>
     */
    public boolean handle(BindException bindException, Object value) {
        if (bindException.getErrorCount() > 0) {
            if (errorContinueFlg) {
                   StringBuilder errorString = new StringBuilder();
                   for (Object objectErrors : bindException.getAllErrors()) {
                       FieldError fieldError = (FieldError) objectErrors;
                    errorString.append(
                            messageAccessor.getMessage(
                                    fieldError.getDefaultMessage(), 
                                    fieldError.getArguments()));
                       
                   }
                log.warn(errorString.toString());
                return false;
            } else {
                throw new ValidateException(bindException);
            }
        }
        return true;
    }

    /**
     * �G���[�p���t���O��ݒ肷��B
     * 
     * @param errorContinueFlg �G���[�p���t���O
     */
    public void setErrorContinueFlg(boolean errorContinueFlg) {
        this.errorContinueFlg = errorContinueFlg;
    }

    /**
     * ���b�Z�[�W�擾�N���X�̃C���X�^���X��ݒ肷��B
     * 
     * @param messageAccessor ���b�Z�[�W�擾�N���X�̃C���X�^���X
     */
    public void setMessageAccessor(MessageAccessor messageAccessor) {
        this.messageAccessor = messageAccessor;
    }
}
