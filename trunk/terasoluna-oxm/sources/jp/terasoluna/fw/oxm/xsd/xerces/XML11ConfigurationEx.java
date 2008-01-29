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
 * XMLSchemaValidatorExインスタンスをパーサに設定するために、XML11Configurationを拡張したクラス。
 * <p>
 * XMLSchemaValidatorExの詳細は、{@link jp.terasoluna.fw.oxm.xsd.xerces.XMLSchemaValidatorEx}を参照すること。
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLSchemaValidatorEx
 * @see jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl
 * 
 */
public class XML11ConfigurationEx extends XML11Configuration {

    /**
     * コンストラクタ。
     */
    public XML11ConfigurationEx() {
        super();
    }

    /**
     * コンストラクタ。
     * 
     * @param grammarPool 名前空間の文法プール
     */
    public XML11ConfigurationEx(XMLGrammarPool grammarPool) {
        super(null, grammarPool);
    }

    /**
     * XMLSchemaValidatorExインスタンスをパーサにセットする。
     * <p>
     * スーパークラスのconfigurePipelineメソッドをオーバーライドして、XMLSchemaValidatorExインスタンスを設定する。
     * </p>
     */
    @Override
    protected void configurePipeline() {

        // スーパークラスのconfigurePipelineメソッドを呼び出す前に、XMLSchemaValidatorExインスタンスをパーサに設定する。
        // スーパークラスのconfigurePipelineメソッドでXMLSchemaValidatorを設定するコードをそのまま使用する。
        if (fFeatures.get(XMLSCHEMA_VALIDATION) == Boolean.TRUE) {
            // XMLSchemaValidatorExを生成する
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

        // XMLSchemaValidatorExインスタンスを設定した後で、スーパークラスのメソッドを呼び出す。
        super.configurePipeline();

    }

}
