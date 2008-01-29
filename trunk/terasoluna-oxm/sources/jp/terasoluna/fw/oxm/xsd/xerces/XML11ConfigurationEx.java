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

package jp.terasoluna.fw.oxm.xsd.xerces;


import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.parsers.XML11Configuration;
import org.apache.xerces.xni.grammars.XMLGrammarPool;

/**
 * XMLSchemaValidatorEx�C���X�^���X���p�[�T�ɐݒ肷�邽�߂ɁAXML11Configuration���g�������N���X�B
 * <p>
 * XMLSchemaValidatorEx�̏ڍׂ́A{@link jp.terasoluna.fw.oxm.xsd.xerces.XMLSchemaValidatorEx}���Q�Ƃ��邱�ƁB
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLSchemaValidatorEx
 * @see jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl
 * 
 */
public class XML11ConfigurationEx extends XML11Configuration {

    /**
     * �R���X�g���N�^�B
     */
    public XML11ConfigurationEx() {
        super();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param grammarPool ���O��Ԃ̕��@�v�[��
     */
    public XML11ConfigurationEx(XMLGrammarPool grammarPool) {
        super(null, grammarPool);
    }

    /**
     * XMLSchemaValidatorEx�C���X�^���X���p�[�T�ɃZ�b�g����B
     * <p>
     * �X�[�p�[�N���X��configurePipeline���\�b�h���I�[�o�[���C�h���āAXMLSchemaValidatorEx�C���X�^���X��ݒ肷��B
     * </p>
     */
    @Override
    protected void configurePipeline() {

        // �X�[�p�[�N���X��configurePipeline���\�b�h���Ăяo���O�ɁAXMLSchemaValidatorEx�C���X�^���X���p�[�T�ɐݒ肷��B
        // �X�[�p�[�N���X��configurePipeline���\�b�h��XMLSchemaValidator��ݒ肷��R�[�h�����̂܂܎g�p����B
        if (fFeatures.get(XMLSCHEMA_VALIDATION) == Boolean.TRUE) {
            // XMLSchemaValidatorEx�𐶐�����
            fSchemaValidator = new XMLSchemaValidatorEx(
                    (XMLErrorReporterEx) getProperty(
                            Constants.XERCES_PROPERTY_PREFIX
                            + Constants.ERROR_REPORTER_PROPERTY));
            setProperty(SCHEMA_VALIDATOR, fSchemaValidator);
            addCommonComponent(fSchemaValidator);
            fSchemaValidator.reset(this);
            if (fErrorReporter.getMessageFormatter(
                    XSMessageFormatter.SCHEMA_DOMAIN) == null) {
                XSMessageFormatter xmft = new XSMessageFormatter();
                fErrorReporter.putMessageFormatter(
                        XSMessageFormatter.SCHEMA_DOMAIN, xmft);
            }
        }

        // XMLSchemaValidatorEx�C���X�^���X��ݒ肵����ŁA�X�[�p�[�N���X�̃��\�b�h���Ăяo���B
        super.configurePipeline();

    }

}
