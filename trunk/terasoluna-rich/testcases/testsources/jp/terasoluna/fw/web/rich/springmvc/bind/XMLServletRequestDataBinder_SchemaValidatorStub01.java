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

package jp.terasoluna.fw.web.rich.springmvc.bind;

import java.io.InputStream;

import org.w3c.dom.Document;

import jp.terasoluna.fw.oxm.xsd.SchemaValidator;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;

/**
 * SchemaValidator�C���^�t�F�[�X�����N���X�̃X�^�u�B
 * 
 * <p>
 * �������n���ꂽ���Ƃ��m�F����B
 * </p>
 * 
 */
public class XMLServletRequestDataBinder_SchemaValidatorStub01 implements
        SchemaValidator {

    protected InputStream in = null;

    protected Object object = null;

    protected ErrorMessages errorMessages = null;

    protected Document doc = null;

    public Document validate(InputStream in, Object object,
            ErrorMessages errorMessages) {
        this.in = in;
        this.object = object;
        this.errorMessages = errorMessages;

        return doc;
    }

}
