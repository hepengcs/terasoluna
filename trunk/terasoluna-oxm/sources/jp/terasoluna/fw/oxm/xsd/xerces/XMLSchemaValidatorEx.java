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

import jp.terasoluna.fw.oxm.xsd.exception.ErrorReporterNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;

/**
 * 解析中のXMLの要素情報をXMLErrorReporterExのスタックに格納するために、XMLSchemaValidatorの拡張を行ったクラス。
 * 
 * <p>
 * 要素の開始時に、要素名にインデックスを付加した文字列を、XMLErrorReporterExのスタックにプッシュする。<br>
 * 要素の終了時に、スタックからポップする。<br>
 * 要素の情報が入ったスタックを利用することで、エラーが発生した箇所を特定することができる。
 * </p>
 * <p>
 * XMLの要素情報がどのようにスタックに格納されるかを以下に記す。
 * </p>
 * <p>
 * 【XMLデータのサンプル】<br>
 * <code><pre>
 * &lt;sample-dto&gt;
 *   &lt;param-a&gt;
 *     &lt;param-b&gt;
 *       &lt;param-c&gt;...&lt;/param-c&gt;
 *       &lt;param-d&gt;...&lt;/param-d&gt;
 *     &lt;/param-b&gt;
 *   &lt;/param-a&gt;
 *   &lt;param-e&gt;
 *     &lt;param-f&gt;...&lt;/param-f&gt;
 *   &lt;/param-e&gt;
 *   &lt;param-e&gt;
 *     &lt;param-f&gt;...&lt;/param-f&gt;
 *   &lt;/param-e&gt;
 * &lt;/sample-dto&gt;
 * </pre></code>
 * </p>
 * <p>
 * 【上記のXMLデータが入力された場合の、スタックの状態の変化】
 * <code><pre>
 * <ol>
 * <li>（空）</li>
 * <li>{sample-dto[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}{param-b[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}{param-b[0]}{param-c[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}{param-b[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}{param-b[0]}{param-d[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}{param-b[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}</li>
 * <li>{sample-dto[0]}</li>
 * <li>{sample-dto[0]}{param-e[0]}</li>
 * <li>{sample-dto[0]}{param-e[0]}{param-f[0]}</li>
 * <li>{sample-dto[0]}{param-e[0]}</li>
 * <li>{sample-dto[0]}</li>
 * <li>{sample-dto[0]}{param-e[1]}</li>
 * <li>{sample-dto[0]}{param-e[1]}{param-f[0]}</li>
 * <li>{sample-dto[0]}{param-e[1]}</li>
 * <li>{sample-dto[0]}</li>
 * <li>（空）</li>
 * </ol>
 * </pre></code>
 * </p>
 * 
 * <p>
 * 例えば、上記サンプルでparam-c要素に不正な値が入っていた場合、エラー発生時のスタックの状態は以下の通りである。<br>
 * スタックの状態：<code><b>{sample-dto[0]}{param-a[0]}{param-b[0]}{param-c[0]}</b></code>
 * </p>
 * 
 * <p>
 * XMLErrorReporterExは以下のようにスタックの値をドット（"."）で連結して、エラーのフィールド情報を生成する。<br>
 * フィールド情報：<code><b>sample-dto[0].param-a[0].param-b[0].param-c[0]</b></code>
 * </p>
 * 
 * <p>
 * 解析中のXMLの属性の情報は特定することができない。<br>
 * エラーが発生した属性名はエラー情報の置換文字列から取得することができる。<br>
 * 詳細は、{@link jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx}を参照すること。
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx
 * 
 */
public class XMLSchemaValidatorEx extends XMLSchemaValidator {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(XMLSchemaValidatorEx.class);
    
    /**
     * XMLErrorReporterExインスタンス
     */
    private XMLErrorReporterEx reporter = null;

    /**
     * コンストラクタ
     * 
     * @param reporter XMLErrorReporterExインスタンス
     */
    public XMLSchemaValidatorEx(XMLErrorReporterEx reporter) {
        this.reporter = reporter;
    }

    /**
     * 要素の開始時に、解析中のフィールド情報をXMLErrorReporterExのスタックにプッシュする。
     * 
     * @param element XML要素の情報を持つインスタンス
     * @param attributes XML属性の情報を持つインスタンス
     * @param augs 妥当性検証の結果が格納されるインスタンス
     * @throws XNIException パーサ内で発生する実行時例外
     */
    @Override
    public void startElement(QName element, XMLAttributes attributes,
            Augmentations augs) throws XNIException {

        if (reporter == null) {
            log.error("ErrorReporterEx is not found.");
            throw new ErrorReporterNotFoundException();
        }
        // 要素名にインデックスを付加した文字列を返却する
        String pushStr = reporter.indexResolve(element.rawname);
        // XMLErrorReporterExのスタックに解析中のフィールド情報をプッシュする
        reporter.getTagStack().push(pushStr);
        super.startElement(element, attributes, augs);
    }
    
    /**
     * 要素の終了時に、XMLErrorReporterExのスタックからポップする。
     * 
     * @param element XML要素の情報を持つインスタンス
     * @param augs 妥当性検証の結果が格納されるインスタンス
     * @throws XNIException パーサ内で発生する実行時例外
     */
    @Override
    public void endElement(QName element, Augmentations augs)
            throws XNIException {

        super.endElement(element, augs);
        if (reporter == null) {
            log.error("ErrorReporterEx is not found.");
            throw new ErrorReporterNotFoundException();
        }
        // 要素の終わりで、XMLErrorReporterExのスタックからポップする
        reporter.getTagStack().pop();
    }

}
