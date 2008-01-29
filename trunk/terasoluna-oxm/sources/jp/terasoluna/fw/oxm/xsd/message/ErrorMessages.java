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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �G���[���b�Z�[�W�̃��X�g��ێ�����N���X�B
 * 
 * @see jp.terasoluna.fw.oxm.xsd.SchemaValidator
 * @see jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 * 
 */
public class ErrorMessages {
    
    /**
     * ���O�B
     */
    private static Log log = LogFactory.getLog(ErrorMessages.class);

    /**
     * �G���[���b�Z�[�W�̃��X�g�B
     */
    private List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();

    /**
     * �G���[���b�Z�[�W��ǉ�����B
     * Null��ǉ����悤�Ƃ����ꍇ�A��O����������B
     * @param errorMessage �G���[���b�Z�[�W
     */
    public void add(ErrorMessage errorMessage) {
        if (errorMessage == null) {
            log.error("ErrorMessages instance cannot add null object.");
            throw new IllegalArgumentException(
                    "ErrorMessages instance cannot add null object.");
        }
        this.errorMessages.add(errorMessage);
    }

    /**
     * �G���[���b�Z�[�W�̃��X�g���擾����B
     * �G���[���b�Z�[�W��Null�͑��݂��Ȃ��B
     * 
     * @return �G���[���b�Z�[�W�̃��X�g
     */
    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

    /**
     * �G���[���b�Z�[�W��ێ����Ă���ꍇ�Atrue��ԋp����B
     * 
     * @return ���b�Z�[�W�������Ă���ꍇ�Atrue��Ԃ��B����ȊO��false��Ԃ��B
     */
    public boolean hasErrorMessage() {
        return !this.errorMessages.isEmpty();
    }
}
