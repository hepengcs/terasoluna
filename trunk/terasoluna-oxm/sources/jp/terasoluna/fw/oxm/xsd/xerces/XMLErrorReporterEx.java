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

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Stack;

import jp.terasoluna.fw.oxm.xsd.exception.IllegalSchemaDefinitionException;
import jp.terasoluna.fw.oxm.xsd.exception.UnknownXMLDataException;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessage;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XNIException;

/**
 * XMLデータのパース時の詳細なエラー情報として、フィールド情報を扱うため、XMLErrorReporterの拡張を行ったクラス。
 * <p>
 * フィールド情報とは、形式チェックのエラーが発生した箇所を特定するための情報である。<br>
 * スタックに格納されている要素名にインデックスを付加した文字列をドット（"."）で連結し、 フィールド情報を生成する。<br>
 * 要素の数が１つの場合でも、必ずインデックスを付加する。 属性にはインデックスを付加しない。<br>
 * エラーが発生した場合に生成されるフィールド情報のサンプルを以下に記す。
 * </p>
 * <p>
 * 【XMLデータのサンプル】 <br>
 * <code><pre>
 *   &lt;sample-dto param-d=&quot;...&quot;&gt;
 *     &lt;param-a&gt;
 *       &lt;param-b&gt;
 *         &lt;param-c&gt;...&lt;/param-c&gt;
 *       &lt;/param-b&gt;
 *     &lt;/param-a&gt;
 *   &lt;/sample-dto&gt;
 * </pre></code>
 * </p>
 * <p>
 * 上記のXMLデータのparam-c要素に不正な値が入力された場合に生成されるフィールド情報を以下に記す。<br>
 * フィールド情報：<code><b>sample-dto[0].param-a[0].param-b[0].param-c[0]</code></b><br>
 * ※要素の数が１つでもインデックスが付加されている。
 * </p>
 * <p>
 * 上記のXMLデータのparam-d属性に不正な値が入力された場合に生成されるフィールド情報を以下に記す。<br>
 * フィールド情報：<code><b>sample-dto[0].param-a[0].param-b[0].param-d</code></b><br>
 * ※属性にはインデックスが付加されていない。
 * </p>
 * <p>
 * <b>形式チェックで生成されるエラーコード</b>
 * <p>
 * 形式チェックで発生するエラーコードの一覧を以下に記す。<br>
 * <table border="1" CELLPADDING="8">
 * <tr>
 * <th>エラーコード</th>
 * <th>置換文字列</th>
 * <th>発生する状況</th>
 * </tr>
 * <tr>
 * <td>typeMismatch.number</td>
 * <td>{入力された値, データ型}</td>
 * <td>不正な数値が入力された場合</td>
 * </tr>
 * <tr>
 * <td>typeMismatch.boolean</td>
 * <td>{入力された値, データ型}</td>
 * <td>不正なboolean値が入力された場合</td>
 * </tr>
 * <tr>
 * <td>typeMismatch.date</td>
 * <td>{入力された値, データ型}</td>
 * <td>不正な日付が入力された場合</td>
 * </tr>
 * <tr>
 * <td>typeMismatch.numberMaxRange</td>
 * <td>{入力された値, データ型の最大値, データ型}</td>
 * <td>定義された型の最大値より大きい数値が入力された場合</td>
 * </tr>
 * <tr>
 * <td>typeMismatch.numberMinRange</td>
 * <td>{入力された値, データ型の最小値, データ型}</td>
 * <td>定義された型の最小値より小さい数値が入力された場合</td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * <b>形式チェックサンプル</b>
 * </p>
 * <p>
 * スキーマ定義ファイルでXMLデータの形式を定義する
 * </p>
 * <p>
 * 【スキーマ定義ファイル設定例】<br>
 * <code><pre>
 *    &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 *    &lt;xs:schema xmlns:xs=&quot;http://www.w3.org/2001/XMLSchema&quot;&gt;
 *      &lt;xs:element name=&quot;int-param&quot; type=&quot;int-param-type&quot;/&gt;
 *      &lt;xs:complexType name=&quot;int-param-type&quot;&gt;
 *        &lt;xs:sequence&gt;
 *          &lt;xs:element name=&quot;param1&quot; type=&quot;xs:int&quot; /&gt;
 *          &lt;xs:element name=&quot;param2&quot; type=&quot;xs:int&quot; /&gt;
 *        &lt;/xs:sequence&gt;
 *      &lt;xs:attribute name=&quot;param3&quot; type=&quot;xs:int&quot;/&gt;
 *      &lt;/xs:complexType&gt;
 *    &lt;/xs:schema&gt;
 * </pre></code>
 * </p>
 * <p>
 * 要素(param2)と属性(param3)に不正な値が格納されたXMLデータを入力する
 * </p>
 * <p>
 * 【入力されるXMLデータ例】<br>
 * <code><pre>
 *   &lt;int-param param3=&quot;30b&quot;&gt;
 *   &lt;param1&gt;100&lt;/param1&gt; 
 *   &lt;param2&gt;20a&lt;/param2&gt;
 *   &lt;/int-param&gt;
 * </pre></code>
 * </p>
 * <p>
 * 形式チェックでエラーが発生し、エラーメッセージが格納されたインスタンスが生成される。<br>
 * エラーメッセージの適切なハンドリングを行うこと。
 * </p>
 * <p>
 * 【param2のエラーメッセージが格納されたインスタンス】<br>
 * <code><pre>
 *    フィールド情報：int-param[0].param2[0]
 *    エラーコード：typeMismatch.number
 *    置換文字列：{20a, integer}
 * </pre></code>
 * </p>
 * <p>
 * 【param3のエラーメッセージが格納されたインスタンス】<br>
 * <code><pre>
 *    フィールド情報：int-param[0].param3
 *    エラーコード：typeMismatch.number
 *    置換文字列：{30b, integer}
 * </pre></code>
 * </p>
 * <p>
 * <b>null値（空文字）の許容</b>
 * </p>
 * <p>
 * XMLスキーマの仕様では、数値型の要素の値にnull値を許容することはできない。<br>
 * 数値型の要素の値にnull値を許容する場合は、独自のデータ型を宣言すること。<br>
 * 独自のデータ型を宣言する場合は、末尾にAllowEmptyを付けること。（エラーのハンドリングを行うために必要）<br>
 * ※null値とは、「<code>&lt;param&gt;&lt;/param&gt;</code>」のように要素の値として空文字が設定されることを指す。
 * </p>
 * <p>
 * 【数値型の要素「param」にnull値を許容する場合のスキーマ定義ファイル設定例】<br>
 * <code><pre>
 *     &lt;xs:simpleType name=&quot;integerAllowEmpty&quot;&gt;
 *       &lt;xs:union&gt;
 *         &lt;xs:simpleType&gt;
 *           &lt;xs:restriction base=&quot;xs:string&quot;&gt;
 *             &lt;xs:enumeration value=&quot;&quot; /&gt;
 *           &lt;/xs:restriction&gt;
 *         &lt;/xs:simpleType&gt;
 *         &lt;xs:simpleType&gt;
 *           &lt;xs:restriction base=&quot;xs:integer&quot; /&gt;
 *         &lt;/xs:simpleType&gt;
 *       &lt;/xs:union&gt;
 *     &lt;/xs:simpleType&gt;
 *     &lt;xs:element name=&quot;param&quot; type=&quot;integerAllowEmpty&quot; /&gt;
 * </pre></code>
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessages
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessage
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLSchemaValidatorEx
 * 
 */
public class XMLErrorReporterEx extends XMLErrorReporter {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(XMLErrorReporterEx.class);

    /**
     * XMLの要素ごとに最終インデックスをMap形式で持つインスタンス。
     * <p>
     * インデックス無しの要素名でインデックス値を保持する。<br>
     * 指定する要素は上位タグ名も含み、最下位タグに対応する要素のインデックスを取得できる。
     * </p>
     */
    private Map<String, Integer> tagIndex = new HashMap<String, Integer>();

    /**
     * 解析中の要素情報を保持するスタック。
     * <p>
     * フィールド情報生成の際に使用される。
     * </p>
     */
    private Stack<String> tagStack = new Stack<String>();

    /**
     * 形式チェックのエラーメッセージを格納するインスタンス。
     */
    private ErrorMessages errorMessages = null;

    /**
     * リソースバンドルを行うファイルの接頭辞
     */
    protected static final String XERCES_RESOURCE_BUNDLE_PREFIX = "org/apache/xerces/impl/msg/XMLSchemaMessages";

    /**
     * nullを許容する場合に定義する独自のデータ型の末尾に付ける文字列
     */
    protected static final String ALLOW_EMPTY_SUFFIX = "AllowEmpty";

    /**
     * メッセージバンドルを行なうときに使用するエラーコードのプレフィックス
     */
    protected static final String ERROR_CODE_PREFIX = "typeMismatch";

    /**
     * 形式チェックで属性に不正なデータ型の値が入力された場合に、出力されるエラーコード。
     */
    protected static final String ATTRIBUTE_ERROR_CODE = "cvc-attribute.3";

    /**
     * 形式チェックで要素に不正なデータ型の値が入力された場合に、出力されるエラーコード。
     */
    protected static final String ELEMENT_ERROR_CODE = "cvc-type.3.1.3";

    /**
     * 形式チェックで不正なデータ型の値が入力された場合に、出力されるエラーコード
     */
    protected static final String DATATYPE_ERROR_CODE = "cvc-datatype-valid.1.2.1";

    /**
     * 形式チェックでunion定義のエラーが発生した場合に、出力されるエラーコード
     * <p>
     * 数値型にnullを許容する独自のデータ型の宣言にはunion定義を用いるので、 不正な値が入力された場合、このエラーが発生する
     * </p>
     */
    protected static final String UNION_ERROR_CODE = "cvc-datatype-valid.1.2.3";

    /**
     * 形式チェックで数値型の最大値より大きい値が入力された場合に出力されるエラーコード
     */
    protected static final String MAXINCLUSIVE_ERROR_CODE = "cvc-maxInclusive-valid";

    /**
     * 形式チェックで数値型の最小値より小さい値が入力された場合に出力されるエラーコード
     */
    protected static final String MININCLUSIVE_ERROR_CODE = "cvc-minInclusive-valid";

    /**
     * 形式チェックで、XMLデータの形式に問題がある場合に出力されるエラーコードのプレフィックス
     */
    protected static final String XML_DATA_ERROR_CODE_PREFIX = "cvc-";

    /**
     * 数値型のフィールドに不正な値が入力された場合のエラーコード
     */
    protected static final String NUMBER_ERROR_CODE = "number";

    /**
     * boolean型のフィールドに不正な値が入力された場合のエラーコード
     */
    protected static final String BOOLEAN_ERROR_CODE = "boolean";

    /**
     * date型のフィールドに不正な値が入力された場合のエラーコード
     */
    protected static final String DATE_ERROR_CODE = "date";

    /**
     * 数値型のフィールドに、定義された数値型の最大値より大きい値が入力された場合のエラーコード
     */
    protected static final String NUMBERMINRANGE_ERROR_CODE = "numberMinRange";

    /**
     * 数値型のフィールドに、定義された数値型の最小値より小さい値が入力された場合のエラーコード
     */
    protected static final String NUMBERMAXRANGE_ERROR_CODE = "numberMaxRange";

    /**
     * フィールドのセパレータ
     */
    protected static final String FIELD_SEPARATOR = ".";

    /**
     * エラーコードのセパレータ
     */
    protected static final String ERROR_CODE_SEPARATOR = ".";

    /**
     * boolean型としてハンドリングするXMLスキーマのデータ型
     */
    protected static final String DATATYPE_BOOLEAN = "boolean";

    /**
     * 日付型としてハンドリングするXMLスキーマのデータ型
     */
    protected static final List DATATYPE_DATE = Arrays.asList(new String[] {
            "date", "time", "dateTime" });

    /**
     * データ型のエラーで生成されるエラーメッセージのフィールド情報に、属性名を追加するため、インスタンスの参照を保存する。
     */
    private ErrorMessage tmpErrorMessage = null;

    /**
     * コンストラクタ
     * 
     * @param errorMessages
     *            エラーメッセージのリスト
     */
    public XMLErrorReporterEx(ErrorMessages errorMessages) {
        super();
        this.errorMessages = errorMessages;
    }

    /**
     * エラーメッセージのリストを返却する
     * 
     * @return エラーメッセージのリスト
     */
    public ErrorMessages getErrorMessages() {
        return errorMessages;
    }

    /**
     * 形式チェックで発生したエラー情報を利用して、エラーメッセージを生成する。
     * 
     * @param location
     *            XMLの位置情報を取得するオブジェクト
     * @param domain
     *            エラードメイン
     * @param key
     *            形式チェックのエラーで発生したエラーコード
     * @param arguments
     *            形式チェックのエラーで発生した置換文字列
     * @param severity
     *            エラーレベル
     * @throws XNIException
     *             パーサ内で発生する実行時例外
     */
    @Override
    public void reportError(XMLLocator location, String domain, String key,
            Object[] arguments, short severity) throws XNIException {
        super.reportError(location, domain, key, arguments, severity);

        String[] options = null;

        if (arguments == null) {
            options = new String[] {};
        } else {
            // 形式チェックのエラーで発生した置換文字列を取得する
            options = new String[arguments.length];

            // エラーメッセージの置換文字列を生成する
            for (int i = 0; i < arguments.length; i++) {
                if (arguments[i] == null) {
                    options[i] = null;
                } else {
                    options[i] = arguments[i].toString();
                }
            }
        }

        // 形式チェックで発生したエラーの情報をログに出力する
        errorLog(key, options);

        // エラーメッセージを生成する
        addErrorMessage(key, options);

    }

    /**
     * 入力された要素名にインデックスを付加して返却する。
     * <p>
     * 要素名には、配列の有無に関わらず[ ]を付加する。
     * </p>
     * 
     * @param element
     *            要素名
     * @return インデックスの値が付加された要素名
     */
    protected String indexResolve(String element) {
        // インデックスは0から始まる
        Integer index = 0;
        // インデックスのキーを生成する
        StringBuilder tagIndexKey = new StringBuilder(getField());
        tagIndexKey.append(element);
        // インデックスのキーに対応する値を取得する
        Integer val = this.tagIndex.get(tagIndexKey.toString());
        // 値が登録されている場合は、インデックスの値をインクリメントする
        if (val != null) {
            index = val + 1;
        }
        // インデックスのキーと値を登録する
        this.tagIndex.put(tagIndexKey.toString(), index);

        StringBuilder retStr = new StringBuilder();
        retStr.append(element);
        retStr.append("[");
        retStr.append(index);
        retStr.append("]");
        return retStr.toString();
    }

    /**
     * エラーが発生したフィールド情報を返却する
     * 
     * @return エラーが発生したフィールド情報
     */
    private String getField() {
        StringBuilder key = new StringBuilder();
        for (String tagstr : tagStack) {
            // スタックに格納されている値をドットで連結する
            key.append(tagstr);
            key.append(FIELD_SEPARATOR);
        }
        return key.toString();
    }

    /**
     * 形式チェックで発生したエラーの情報を変換し、独自のエラーメッセージインスタンスを生成する。
     * <p>
     * Xercesで発生したデータ型のエラーに関してのみ、独自のエラーメッセージを生成する。<br>
     * それ以外のエラーが発生した場合は、例外をスローする。
     * <p>
     * <p>
     * Xercesでデータ型のエラーが発生した場合、要素・属性に関するエラーも発生する。<br>
     * 例えば、ある要素で最大値エラーが発生した場合、データ型のエラーと要素エラーが発生する。<br>
     * ある属性でデータ型のエラーが発生した場合、データ型のエラーと属性のエラーが発生する。
     * </p>
     * <p>
     * 要素・属性のエラーは、データ型のエラーとほぼ同じ内容のエラー情報を持つので、エラーメッセージは生成しない。
     * </p>
     * データ型のエラーの一覧を以下に記す。<br>
     * <table border="1" CELLPADDING="8">
     * <tr>
     * <th>Xercesのエラーコード</th>
     * <th>独自のエラーコード</th>
     * <th>独自の置換文字列</th>
     * <th>発生する状況</th>
     * </tr>
     * <tr>
     * <td>cvc-datatype-valid.1.2.1</td>
     * <td>typeMismatch.number<br>
     * typeMismatch.boolean<br>
     * typeMismatch.date</td>
     * <td>{不正な値, データ型}</td>
     * <td>不正な値が入力された場合</td>
     * </tr>
     * <tr>
     * <td>cvc-maxInclusive-valid</td>
     * <td>typeMismatch.numberMaxRange</td>
     * <td>{不正な値, データ型の最大値, データ型}</td>
     * <td>数値型の最大値より大きい値が入力された場合</td>
     * </tr>
     * <tr>
     * <td>cvc-minInclusive-valid</td>
     * <td>typeMismatch.numberMinRange</td>
     * <td>{不正な値, データ型の最小値, データ型}</td>
     * <td>数値型の最小値より小さい値が入力された場合</td>
     * </tr>
     * <tr>
     * <td>cvc-datatype-valid.1.2.3</td>
     * <td>typeMismatch.numberMinRange</td>
     * <td>{不正な値, データ型}</td>
     * <td>null値を許容したフィールドに、不正な値が入力された場合</td>
     * </tr>
     * </table>
     * </p>
     * <p>
     * 要素・属性のエラーの一覧を下記に示す。<br>
     * <table border="1" CELLPADDING="8">
     * <tr>
     * <th>Xercesで発生したエラーコード</th>
     * <th>発生する状況</th>
     * </tr>
     * <tr>
     * <td>cvc-type.3.1.3</td>
     * <td>要素に不正な値が入力された場合</td>
     * </tr>
     * <tr>
     * <td>cvc-attribute.3</td>
     * <td>属性に不正な値が入力された場合</td>
     * </tr>
     * </table>
     * </p>
     * <p>
     * 属性のエラーが発生した場合、置換文字列として格納されている属性名を、エラーメッセージのフィールド情報に付加する。<br>
     * </p>
     * 
     * @param key
     *            エラーコード
     * @param options
     *            置換文字列
     */
    protected void addErrorMessage(String key, String[] options) {

        String messageID = null;

        // Xercesで発生したエラーメッセージのハンドリングを行う
        if (ELEMENT_ERROR_CODE.equals(key)) {
            // 要素のエラーはハンドリングしない
            return;
        } else if (ATTRIBUTE_ERROR_CODE.equals(key)) {
            if (tmpErrorMessage != null) {
                // フィールド情報に属性名を付加する
                StringBuilder fieldStr = new StringBuilder();
                fieldStr.append(tmpErrorMessage.getField());
                fieldStr.append(FIELD_SEPARATOR);
                fieldStr.append(options[1]);
                tmpErrorMessage.setField(fieldStr.toString());
            }
            // 属性のエラーはハンドリングしない
            return;
        } else if (DATATYPE_ERROR_CODE.equals(key)) {
            // フィールドに定義されているデータ型でない値が入力された場合に、このエラーが発生する
            if (DATATYPE_BOOLEAN.equals(options[1])) {
                messageID = ERROR_CODE_PREFIX + ERROR_CODE_SEPARATOR
                        + BOOLEAN_ERROR_CODE;
            } else if (DATATYPE_DATE.contains(options[1])) {
                messageID = ERROR_CODE_PREFIX + ERROR_CODE_SEPARATOR
                        + DATE_ERROR_CODE;
            } else {
                messageID = ERROR_CODE_PREFIX + ERROR_CODE_SEPARATOR
                        + NUMBER_ERROR_CODE;
            }
        } else if (UNION_ERROR_CODE.equals(key)
                && options[1].endsWith(ALLOW_EMPTY_SUFFIX)) {
            // 数値型にnull値を許容するフィールドに、不正な値が入力された場合に、このエラーが発生する
            // データ型の文字列から"AllowEmpty"を削除して、置換文字列に格納する
            options[1] = (options[1]).substring(0, (options[1])
                    .indexOf(ALLOW_EMPTY_SUFFIX));
            messageID = ERROR_CODE_PREFIX + ERROR_CODE_SEPARATOR
                    + NUMBER_ERROR_CODE;
            // 不要な置換文字列を削除する
            String[] tmpOptions = new String[2];
            System.arraycopy(options, 0, tmpOptions, 0, 2);
            options = tmpOptions;
        } else if (MAXINCLUSIVE_ERROR_CODE.equals(key)) {
            // 型の最大値より大きい値が入力された場合に、このエラーが発生する
            messageID = ERROR_CODE_PREFIX + ERROR_CODE_SEPARATOR
                    + NUMBERMAXRANGE_ERROR_CODE;
        } else if (MININCLUSIVE_ERROR_CODE.equals(key)) {
            // 型の最小値より小さい値が入力された場合に、このエラーが発生する
            messageID = ERROR_CODE_PREFIX + ERROR_CODE_SEPARATOR
                    + NUMBERMINRANGE_ERROR_CODE;
        } else if (key.startsWith(XML_DATA_ERROR_CODE_PREFIX)) {
            // XMLデータの構造にエラーがあると考えられる場合、例外をスローする
            log.error("xml data is invalid.");
            throw new UnknownXMLDataException();
        } else {
            // スキーマ自体に問題があると考えられる場合、例外をスローする
            log.error("schema is invalid.");
            throw new IllegalSchemaDefinitionException();
        }

        ErrorMessage errorMessage = new ErrorMessage(messageID, "", options);

        // エラーメッセージにフィールド情報を設定する
        errorMessage.setField(getField().substring(0, getField().length() - 1));
        // エラーメッセージのリストに追加する
        errorMessages.add(errorMessage);
        // エラーメッセージの参照を保存する
        tmpErrorMessage = errorMessage;

    }

    /**
     * 形式チェックで発生したエラーの情報をログに出力する
     * 
     * @param key
     *            エラーコード
     * @param options
     *            置換文字列
     */
    protected void errorLog(String key, Object[] options) {

        StringBuilder buf = new StringBuilder();

        // 改行コードの取得
        String lineSeparator = System.getProperty("line.separator");

        buf.append("Schema error[]------------------------");
        buf.append(lineSeparator);
        // フィールド情報
        if (getField().length() > 0) {
            buf.append("xpath="
                    + getField().substring(0, getField().length() - 1));
        } else {
            buf.append("xpath=" + getField());
        }
        buf.append(lineSeparator);
        buf.append("getMessage=");
        // Xercesのエラーメッセージ
        buf.append(getMessage(key, options));
        buf.append(lineSeparator);
        buf.append("key=");
        buf.append(key);
        // Xercesのエラーコード
        buf.append(lineSeparator);
        StringBuilder argNo = new StringBuilder();
        for (int i = 0; i < options.length; i++) {
            argNo.setLength(0);
            argNo.append("arg[");
            argNo.append(i);
            argNo.append("]=");
            buf.append(argNo.toString());
            // Xercesの置換文字列
            buf.append(options[i]);
            buf.append(lineSeparator);
        }
        buf.append("-----------------------------------------");
        buf.append(lineSeparator);

        log.error(buf.toString());
    }

    /**
     * Xercesのリソースバンドルを用いて生成したメッセージを返却する。
     * 
     * @param key
     *            エラーコード
     * @param options
     *            置換文字列
     * @return エラーメッセージ
     */
    private String getMessage(String key, Object[] options) {

        String message = null;
        try {
            // リソースバンドルを取得する
            ResourceBundle bundle = ResourceBundle
                    .getBundle(XERCES_RESOURCE_BUNDLE_PREFIX);
            // エラーコードに対応するメッセージを取得する
            message = bundle.getString(key);
        } catch (MissingResourceException e) {
            return "[[" + e.getMessage() + "]]";
        }
        // プレースフォルダが置換されたメッセージを生成する
        return MessageFormat.format(message, options);

    }

    /**
     * 解析中のフィールド情報を保持するスタックを返却する
     * 
     * @return 解析中のフィールド情報を保持するスタック
     */
    public Stack<String> getTagStack() {
        return tagStack;
    }

}
