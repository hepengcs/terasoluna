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

package jp.terasoluna.fw.web.struts.form;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.beans.IndexedBeanWrapper;
import jp.terasoluna.fw.beans.JXPathIndexedBeanWrapperImpl;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.ClassUtil;
import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.validator.FieldChecks;
import org.apache.struts.validator.Resources;
import org.apache.struts.validator.validwhen.ValidWhen;

/**
 * <code>Validator</code> 追加ルールクラス。
 *
 * <p>
 * <code>Struts</code>が提供する {@link FieldChecks}
 * を拡張した入力チェッククラス。<br>
 * {@link FieldChecksEx} の拡張ルールとしては、以下のものがある。
 * </p>
 * <table border="1">
 * <tr>
 *  <td><center><b>検証名</b></center></td>
 *  <td><center><b>クライアントチェック</b></center></td>
 *  <td><center><b>メソッド名</b></center></td>
 *  <td><center><b>(validation-rules.xmlに記述する)
 *   ルール名</b></center></td>
 *  <td><center><b>概要</b></center></td>
 * </tr>
 * <tr>
 *  <td>英数字チェック</td>
 *  <td><center>○</center></td>
 *  <td>{@link #validateAlphaNumericString(Object,
 *                                         ValidatorAction,
 *                                         Field,
 *                                         ActionMessages,
 *                                         Validator,
 *                                         HttpServletRequest)
 *       validateAlphaNumericString()}</td>
 *  <td><code>alphaNumericString</code></td>
 *  <td>フィールド値が半角英数字のみで構成されているかどうかをチェック。</td>
 * </tr>
 * <tr>
 *  <td>大文字英数字チェック</td>
 *  <td><center>○</center></td>
 *  <td>{@link #validateCapAlphaNumericString(Object,
 *                                            ValidatorAction,
 *                                            Field,
 *                                            ActionMessages,
 *                                            Validator,
 *                                            HttpServletRequest)
 *      validateCapAlphaNumericString()}</td>
 *  <td><code>capAlphaNumericString</code></td>
 *  <td>値が大文字の半角英数字のみで構成されているかどうかをチェック。
 *  </td>
 * </tr>
 * <tr>
 *  <td>フィールド数値チェック</td>
 *  <td><center>○</center></td>
 *  <td>{@link #validateNumber(Object,
 *                             ValidatorAction,
 *                             Field,
 *                             ActionMessages,
 *                             Validator,
 *                             HttpServletRequest)
 *      validateNumber()}</td>
 *  <td><code>number</code></td>
 *  <td>フィールド値が数値であることをチェック。</td>
 * </tr>
 * <tr>
 *  <td>半角カナ文字チェック</td>
 *  <td><center>○</center></td>
 *  <td>{@link #validateHankakuKanaString(Object,
 *                                        ValidatorAction,
 *                                        Field,
 *                                        ActionMessages,
 *                                        Validator,
 *                                        HttpServletRequest)
 *      validateHankakuKanaString()}</td>
 *  <td><code>hankakuKanaString</code></td>
 *  <td>フィールド値が半角カナのみで構成されていることをチェック。</td>
 * </tr>
 * <tr>
 *  <td>半角文字チェック</td>
 *  <td><center>○</center></td>
 *  <td>{@link #validateHankakuString(Object,
 *                                    ValidatorAction,
 *                                    Field,
 *                                    ActionMessages,
 *                                    Validator,
 *                                    HttpServletRequest)
 *      validateHankakuString()}</td>
 *  <td><code>hankakuString</code></td>
 *  <td>フィールド値が半角の文字のみで構成されていることをチェック。</td>
 * </tr>
 * <tr>
 *  <td>全角文字チェック</td>
 *  <td><center>○</center></td>
 *  <td>{@link #validateZenkakuString(Object,
 *                                    ValidatorAction,
 *                                    Field,
 *                                    ActionMessages,
 *                                    Validator,
 *                                    HttpServletRequest)
 *      validateZenkakuString()}</td>
 *  <td><code>zenkakuString</code></td>
 *  <td>フィールド値が全角の文字のみで構成されていることをチェック。</td>
 * </tr>
 * <tr>
 *  <td>全角カナ文字チェック</td>
 *  <td><center>○</center></td>
 *  <td>{@link #validateZenkakuKanaString(Object,
 *                                        ValidatorAction,
 *                                        Field,
 *                                        ActionMessages,
 *                                        Validator,
 *                                        HttpServletRequest)
 *      validateZenkakuKanaString()}</td>
 *  <td><code>zenkakuKanaString</code></td>
 *  <td>フィールド値が全角カナ文字のみで構成されていることをチェック。</td>
 * </tr>
 * <tr>
 *  <td>入力禁止文字チェック</td>
 *  <td><center>×</center></td>
 *  <td>{@link #validateProhibited(Object,
 *                                 ValidatorAction,
 *                                 Field,
 *                                 ActionMessages,
 *                                 Validator,
 *                                 HttpServletRequest)
 *      validateProhibited()}</td>
 *  <td><code>prohibited</code></td>
 *  <td>フィールド値に入力禁止文字が含まれていないことをチェック。</td>
 * </tr>
 * <tr>
 *  <td>文字列長一致チェック</td>
 *  <td><center>○</center></td>
 *  <td>{@link #validateStringLength(Object,
 *                                   ValidatorAction,
 *                                   Field,
 *                                   ActionMessages,
 *                                   Validator,
 *                                   HttpServletRequest)
 *      validateStringLength()}</td>
 *  <td><code>stringLength</code></td>
 *  <td>フィールド値が指定した文字列長と一致することをチェック。</td>
 * </tr>
 * <tr>
 *  <td>数字文字列チェック</td>
 *  <td><center>○</center></td>
 *  <td>{@link #validateNumericString(Object,
 *                                    ValidatorAction,
 *                                    Field,
 *                                    ActionMessages,
 *                                    Validator,
 *                                    HttpServletRequest)
 *      validateNumericString()}</td>
 *  <td><code>numericString</code></td>
 *  <td>フィールド値が数字のみで構成されていることをチェック。</td>
 * </tr>
 * <tr>
 *  <td>バイト列長チェック</td>
 *  <td><center>×</center></td>
 *  <td>{@link #validateByteLength(Object,
 *                                 ValidatorAction,
 *                                 Field,
 *                                 ActionMessages,
 *                                 Validator,
 *                                 HttpServletRequest)
 *      validateByteLength()}</td>
 *  <td><code>byteLength</code></td>
 *  <td>フィールド値のバイト列長が指定した長さと一致することをチェック。</td>
 * </tr>
 * <tr>
 *  <td>バイト列範囲チェック</td>
 *  <td><center>×</center></td>
 *  <td>{@link #validateByteRange(Object,
 *                                ValidatorAction,
 *                                Field,
 *                                ActionMessages,
 *                                Validator,
 *                                HttpServletRequest)
 *      validateByteRange()}</td>
 *  <td><code>byteRange</code></td>
 *  <td>フィールド値のバイト列長が指定した範囲以内であることをチェック。</td>
 * </tr>
 * <tr>
 *  <td>日付範囲チェック</td>
 *  <td><center>○</center></td>
 *  <td>{@link #validateDateRange(Object,
 *                                ValidatorAction,
 *                                Field,
 *                                ActionMessages,
 *                                Validator,
 *                                HttpServletRequest)
 *      validateDateRange()}</td>
 *  <td><code>dateRange</code></td>
 *  <td>フィールド値の日付が指定した範囲以内であることをチェック。</td>
 * </tr>
 * <tr>
 *  <td>複数フィールドチェック</td>
 *  <td><center>×</center></td>
 *  <td>{@link #validateMultiField(Object,
 *                                 ValidatorAction,
 *                                 Field,
 *                                 ActionMessages,
 *                                 Validator,
 *                                 HttpServletRequest)
 *      validateMultiField()}</td>
 *  <td><code>multiField</code></td>
 *  <td>複数フィールド間の相関チェック。</td>
 * </tr>
 * <tr>
 *  <td>配列・コレクション型フィールド全要素チェック</td>
 *  <td><center>×</center></td>
 *  <td>{@link #validateArraysIndex(Object,
 *                                  ValidatorAction,
 *                                  Field,
 *                                  ActionMessages,
 *                                  Validator,
 *                                  HttpServletRequest)
 *      validateArraysIndex()}</td>
 *  <td>(<code>validation.xml</code>の
 *  <code>&lt;depend&gt;</code>要素で決定)</td>
 *  <td>depends属性で指定された入力チェックを配列・コレクション型のフィールド値
 *  の全ての値に対してチェックを行う。</td>
 * </tr>
 * </ul>
 * </table>
 * <p>
 * フィールドチェックの詳細については、各メソッドの説明を参照。<br>
 * 拡張したValidatorを利用するためには、アクション毎に
 * 検証内容を記述した定義ファイル( <code>validation.xml</code> )
 * を作成する必要がある。
 * </p>
 * <h5>単体のフィールド検証</h5>
 * <p>
 * <code>&lt;formset&gt;</code>タグ内部の <code>&lt;form&gt;</code>
 * 要素の <code>name</code> 属性に、
 * <code>struts-config.xml</code>で定義されている
 * アクションのパス名を記述する。<br>
 * 記述がない場合は、該当するアクションパスの検証は一切行われない。
 * <code>&lt;field&gt;</code>要素の <code>property</code> 属性に、
 * 対象のフィールド名、<code>depends</code>属性に
 * <code>validation-rules.xml</code>で定義されている
 * 検証ルール名を記述する。
 * ひとつのフォームに複数の検証を行う場合（必須入力、桁数など）は、
 * カンマ区切りで記述していく。<br>
 * ただし、出力されるエラーメッセージは１フィールドに対し、
 * １つのみである。
 * （必須入力、桁数チェックの両方に違反している場合、どれか１つの
 * エラーしか出力されない）
 * </p>
 * <h5>validation.xmlの記述例（単体フィールド検証）</h5>
 * <code><pre>
 *  &lt;formset&gt;
 *    ・・・
 *    &lt;!-- 単体のフィールド検証 --&gt;
 *    &lt;form name="/logon"&gt;
 *      &lt;field property="companyId"
 *          depends="required,alphaNumericString,maxlength"&gt;
 *        &lt;arg0 key="logon.companyId"/&gt;
 *        &lt;arg1 key="${var:maxlength}" resource="false"/&gt;
 *        &lt;var&gt;
 *          &lt;var-name&gt;maxlength&lt;/var-name&gt;
 *          &lt;var-value&gt;10&lt;/var-value&gt;
 *        &lt;/var&gt;
 *      &lt;/field&gt;
 *    &lt;/form&gt;
 *    ・・・
 *  &lt;/formset&gt;
 * </pre></code>
 * <h5>配列・コレクション型の一覧検証</h5>
 * <p>
 * 配列・コレクション型の一覧検証は、下記の点を拡張している。
 * <ul>
 *  <li>対象フィールドの配列・コレクション要素に対し、途中でエラーが
 *     返却されても最後の要素まで検証を行う</li>
 *  <li>各要素のインデックス番号をエラーメッセージに反映できる</li>
 * </ul>
 * 基本的には単体フィールド検証と同様<code>validation.xml</code>に
 * <code>&lt;form&gt;</code> 要素を作成するが、
 * 配列・コレクション型の一覧検証を行うためには、下記の設定が必要である。
 * <ol>
 *  <li>配列・コレクション型の一覧表示には、<b>必ず
 *   <code>property</code>
 *   属性でフォーム内のフィールド名を指定する</b>。またプロパティ名には
 *   JXPathIndexedBeanWrapperImplの仕様に従い、ネストしたプロパティ名を
 *   指定することが可能である。</li>
 *  <li><code>&lt;depends&gt;</code>で指定するルールは、
 *   <code>validation-rules.xml</code>の
 *   末尾が <code>Array</code> であるルール名を用いる。</li>
 *  <li>検証されるフォームがFormExインタフェースを
 *   実装している必要がある。</li>
 * </li>
 * </ol>
 * 配列・コレクション型の一覧検証に対応するルールは以下の通りである。
 * <ul>
 *   <li><code>requiredArray</code></li>
 *   <li><code>minLengthArray</code></li>
 *   <li><code>maxLengthArray</code></li>
 *   <li><code>maskArray</code></li>
 *   <li><code>byteArray</code></li>
 *   <li><code>shortArray</code></li>
 *   <li><code>integerArray</code></li>
 *   <li><code>longArray</code></li>
 *   <li><code>floatArray</code></li>
 *   <li><code>doubleArray</code></li>
 *   <li><code>dateArray</code></li>
 *   <li><code>intRangeArray</code></li>
 *   <li><code>doubleRangeArray</code></li>
 *   <li><code>floatRangeArray</code></li>
 *   <li><code>creditCardArray</code></li>
 *   <li><code>emailArray</code></li>
 *   <li><code>urlArray</code></li>
 *   <li><code>alphaNumericStringArray</code></li>
 *   <li><code>hankakuKanaStringArray</code></li>
 *   <li><code>hankakuStringArray</code></li>
 *   <li><code>zenkakuStringArray</code></li>
 *   <li><code>zenkakuKanaStringArray</code></li>
 *   <li><code>capAlphaNumericStringArray</code></li>
 *   <li><code>numberArray</code></li>
 *   <li><code>numericStringArray</code></li>
 *   <li><code>prohibitedArray</code></li>
 *   <li><code>stringLengthArray</code></li>
 *   <li><code>dateRangeArray</code></li>
 *   <li><code>byteLengthArray</code></li>
 *   <li><code>byteRangeArray</code></li>
 * </ul>
 * </p>
 * <h5>validation.xmlの記述例（配列・コレクション型一覧表示の検証）</h5>
 * <code><pre>
 *  &lt;formset&gt;
 *    ・・・
 *    &lt;!-- 配列・コレクション型一覧表示の検証確認 --&gt;
 *    &lt;form name="/isValid"&gt;
 *      &lt;field property="codeArray"
 *          depends="requiredArray,alphaNumericStringArray"&gt;
 *        &lt;arg0 <b>key="##INDEX" resource="false"</b>/&gt;
 *        &lt;arg1 key="sampleValidate.codeArray"/&gt;
 *      &lt;/field&gt;
 *    &lt;/form&gt;
 *    ・・・
 *  &lt;/formset&gt;
 * </pre></code>
 * <p>
 * 配列・コレクション型のインデックス番号をエラーメッセージに
 * 埋め込むためには、<code>&lt;arg0～3&gt;</code>要素の何れかの
 * <code>key</code>に <code>&quot;##INDEX&quot;</code>
 * という文字列を指定し、<code>&lt;resource&gt;</code> 要素を
 * <code>&quot;false&quot;</code> にする。<br>
 * 単体のフィールド検証と同様、<code>&lt;depends&gt;</code>
 * で複数の検証を行う場合カンマ区切りで複数の検証ルールを指定できる。<br>
 * <code>Validator</code> フレームワークを利用したフォームは、
 * <code>
 * {@link DynaValidatorActionFormEx}
 * </code>、
 * <code>
 * {@link ValidatorActionFormEx}
 * </code> を参照。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see
 * jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see
 * jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 * @see jp.terasoluna.fw.beans.JXPathIndexedBeanWrapperImpl
 *
 */
public class FieldChecksEx extends FieldChecks {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -5669122584668175380L;

    /**
     * ログクラス。
     */
    private static Log log
                  = LogFactory.getLog(FieldChecksEx.class);

    /**
     * 配列インデックス型フィールドのIndex値。
     */
    public static final String INDEX = "##INDEX";

    /**
     * 半角カナ文字列である。<code>ApplicationResources</code>
     * ファイルに定義する。
     */
    protected static String hankakuKanaList
        = "ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣";

    /**
     * 全角カナ文字列。<code>ApplicationResources</code>
     * ファイルに定義する。
     */
    protected static String zenkakuKanaList
        = "アイウヴエオァィゥェォカキクケコヵヶガギグゲゴサシスセソ"
            + "ザジズゼゾタチツテトダヂヅデドナニヌネノハヒフヘホ"
            + "バビブベボパピプペポマミムメモヤユヨャュョラリルレロ"
            + "ワヮヰヱヲッンー";

    /**
     * <code>ApplicationResources</code>
     * ファイルに定義された半角文字テーブルを取得するキー。
     */
    protected static final String HANKAKU_KANA_LIST_KEY
        = "validation.hankaku.kana.list";

    /**
     * <code>ApplicationResources</code>
     * ファイルに定義された全角文字テーブルを取得するキー。
     */
    protected static final String ZENKAKU_KANA_LIST_KEY
        = "validation.zenkaku.kana.list";

    /**
     * UNICODE文字コード'\u0000'から'\u00ff'の範囲内に存在する
     * 全角文字列。
     */
    protected static final String ZENKAKU_BEGIN_U00_LIST
         = "＼￠￡§¨￢°±´¶×÷";

    static {
        String value = null;
        // 半角カナ文字列テーブルを取得
        value = PropertyUtil.getProperty(HANKAKU_KANA_LIST_KEY);
        if (value != null) {
            hankakuKanaList = value;
        }

        // 全角カナ文字列テーブルを取得
        value = PropertyUtil.getProperty(ZENKAKU_KANA_LIST_KEY);
        if (value != null) {
            zenkakuKanaList = value;
        }
    }

    /**
     * 指定された文字が半角カナ文字であることをチェックする。
     *
     * @param c 文字
     * @return 半角カナ文字であれば <code>true</code>
     */
    protected static boolean isHankakuKana(char c) {
        return hankakuKanaList.indexOf(c) >= 0;
    }

    /**
     * 指定された文字が半角文字であることをチェックする。
     *
     * @param c 文字
     * @return 半角文字であれば <code>true</code>
     */
    protected static boolean isHankaku(char c) {
        return (c <= '\u00ff' && ZENKAKU_BEGIN_U00_LIST.indexOf(c) < 0)
             || isHankakuKana(c);
    }

    /**
     * 指定された文字が全角文字であることをチェックする。
     *
     * @param c 文字
     * @return 全角文字であれば <code>true</code>
     */
    protected static boolean isZenkaku(char c) {
        return !isHankaku(c);
    }

    /**
     * 指定された文字が全角カナ文字であることをチェックする。
     *
     * @param c 文字
     * @return 全角カナ文字であれば <code>true</code>
     */
    protected static boolean isZenkakuKana(char c) {
        return zenkakuKanaList.indexOf(c) >= 0;
    }

    /**
     * 指定されたフィールドが英数字であることをチェックする。
     *
     * <p>エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。
     * エラーメッセージのフォーマットは、<code>validation-rules.xml</code>
     * に記述する。</p>
     *
     * @param bean 検査対象のオブジェクト
     * @param va Strutsにより用意されたValidatorAction
     * @param field フィールドオブジェクト
     * @param errors ActionMessages アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateAlphaNumericString(Object bean,
                                          ValidatorAction va, Field field,
                                          ActionMessages errors,
                                          Validator validator,
                                          HttpServletRequest request) {
        // フィールドのクローン作成
        Field fieldClone = (Field) field.clone();
        fieldClone.addVar("mask", "^[0-9a-zA-Z]*$", "false");
        // 英数値チェックを行う
        return validateMask(bean, va, fieldClone, errors, validator, request);
    }

    /**
     * 指定されたフィールドが大文字英数字であることをチェックする。
     *
     * <p>エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。
     * エラーメッセージのフォーマットは、<code>validation-rules.xml</code>
     * に記述する。</p>
     *
     * @param bean 検査対象のオブジェクト
     * @param va Strutsにより用意されたValidatorAction
     * @param field フィールドオブジェクト
     * @param errors ActionMessages アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateCapAlphaNumericString(Object bean,
                                          ValidatorAction va, Field field,
                                          ActionMessages errors,
                                          Validator validator,
                                          HttpServletRequest request) {

        // フィールドのクローン作成
        Field fieldClone = (Field) field.clone();
        fieldClone.addVar("mask", "^[0-9A-Z]*$", "false");
        // 英数値チェックを行う
        return validateMask(bean, va, fieldClone, errors, validator, request);
    }

    /**
     * 指定されたフィールドが数値であることをチェックする。
     *
     * まず、入力された文字列を用い、<code>BigDecimal</code> 型を生成する
     * ここで生成不可能ならばエラー用のActionMessageを作成し、<code>false</code> を返却する。
     *
     * 次に整数部の桁数が指定されている場合に、桁数の確認を行う。
     * <code>validation.xml</code> で <code>isAccordedInteger()</code>
     * が <code>"true"</code> 指定されている場合のみ
     * 整数桁数の同一チェックが行われる。
     * チェックに引っかかった場合は、エラー用のActionMessageを作成し、falseを返却する。
     *
     * 最後に小数部の桁数が指定されている場合に、桁数の確認を行う。
     * validation.xmlでisAccordedScaleが"true"である場合のみ
     * 小数桁数の同一チェックが行われる。
     *
     * <p>エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。
     * エラーメッセージのフォーマットは、<code>validation-rules.xml</code>
     * に記述する。<br>
     * 下記は、整数部3、小数部2である数値を検証する例である。
     * </p>
     *
     * <h5>validation.xmlの記述例</h5>
     * <code><pre>
     * &lt;form name=&quot;/sample&quot;&gt;
     *  ・・・
     *  &lt;field property=&quot;escape&quot;
     *      depends=&quot;number&quot;&gt;
     *    &lt;arg0 key=&quot;sample.escape&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;integerLength&lt;/var-name&gt;
     *      &lt;var-value&gt;3&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;scale&lt;/var-name&gt;
     *      &lt;var-value&gt;2&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;isAccordedInteger&lt;/var-name&gt;
     *      &lt;var-value&gt;true&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;isAccordedScale&lt;/var-name&gt;
     *      &lt;var-value&gt;true&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  ・・・
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xmlに設定を要する&lt;var&gt;要素</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>必須性</b></center></td>
     *   <td><center><b>概要</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> <code>integerLength</code> </td>
     *   <td> 整数部桁数 </td>
     *   <td> <code>false</code> </td>
     *   <td>整数の桁数を設定する、<code>isAccordedInteger</code>指定が
     *       無いときは、指定桁数以内の検証を行う。省略時、または非数値を
     *       設定したときは、検証を行なわない。</td>
     *  </tr>
     *  <tr>
     *   <td> <code>scale</code> </td>
     *   <td> 小数部桁数 </td>
     *   <td> <code>false</code> </td>
     *   <td>小数値の桁数を設定する、<code>isAccordedScale</code>指定が
     *       無いときは、指定桁数以内の検証を行う。省略時、または非数値を
     *       設定したときは、検証を行なわない。</td>
     *  </tr>
     *  <tr>
     *   <td> <code>isAccordedInteger</code> </td>
     *   <td> 整数桁数一致チェック </td>
     *   <td> <code>false</code> </td>
     *   <td> <code>true</code>が指定されたとき、整数桁数の一致チェックが
     *        行なわれる。省略時、<code>true</code>以外の文字列が設定された時は
     *        桁数以内チェックとなる。</td>
     *  </tr>
     *  <tr>
     *   <td> <code>isAccordedScale</code> </td>
     *   <td> 小数桁数一致チェック </td>
     *   <td> <code>false</code> </td>
     *   <td> <code>true</code>が指定されたとき、小数桁数の一致チェックが
     *        行なわれる。省略時、<code>true</code>以外の文字列が設定された時は
     *        桁数以内チェックとなる。</td>
     *  </tr>
     * </table>
     *
     * @param bean 検査対象のオブジェクト
     * @param va Strutsにより用意されたValidatorAction
     * @param field フィールドオブジェクト
     * @param errors ActionMessages アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateNumber(Object bean,
                                          ValidatorAction va, Field field,
                                          ActionMessages errors,
                                          Validator validator,
                                          HttpServletRequest request) {

        // beanがnullの時、エラーログを出力し、trueを返却する。
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }
        // 整数部桁数取得
        String integerLength = field.getVarValue("integerLength");
        // 小数部桁数取得
        String scaleStr = field.getVarValue("scale");
        // 整数桁数一致チェックか
        String isAccordedInteger = field.getVarValue("isAccordedInteger");
        // 小数桁数一致チェックか
        String isAccordedScale = field.getVarValue("isAccordedScale");

        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // 検証値がnullまたは空文字の時、true返却
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!isHankaku(chars[i])) {
                errors.add(field.getKey(),
                        Resources.getActionMessage(
                                validator, request, va, field));
                return false;
            }
        }
        // 整数桁数が指定されている場合、整数部チェックを行う
        if (GenericValidator.isInt(integerLength)) {
            try {
                BigDecimal bd = new BigDecimal(value);
                // 整数部絶対値のみ抽出
                BigInteger bi = bd.toBigInteger().abs();
                // 整数桁数
                int length = bi.toString().length();
                // validation.xmlで指定された桁数
                int checkLength = Integer.valueOf(integerLength).intValue();
                // 桁数オーバ時は、falseを返却
                if (length > checkLength) {
                    errors.add(field.getKey(),
                                Resources.getActionMessage(
                                        validator, request, va, field));
                    return false;
                }
                // 一致指定されているとき
                if (isAccordedInteger != null
                    && "true".equals(isAccordedInteger)) {
                    // 桁数不一致は、falseを返却
                    if (length != checkLength) {
                        errors.add(field.getKey(),
                            Resources.getActionMessage(
                                    validator, request, va, field));
                        return false;
                    }
                }
            } catch (NumberFormatException nfe) {
                // 数値型に変換できない時、falseを返却
                errors.add(field.getKey(),
                            Resources.getActionMessage(
                                    validator, request, va, field));
                return false;
            }
        }

        // 小数桁数が指定されている場合、小数部チェックを行う
        if (GenericValidator.isInt(scaleStr)) {
            int scale = 0;
            int checkScale = 0;
            try {
                BigDecimal bd = new BigDecimal(value);
                scale = bd.scale();
                // validation.xmlで指定された桁数
                checkScale = Integer.valueOf(scaleStr).intValue();
            } catch (NumberFormatException e) {
                // 数値型に変換できない時、falseを返却
                errors.add(field.getKey(),
                            Resources.getActionMessage(
                                    validator, request, va, field));
                return false;
            }
            // 桁数オーバ時は、falseを返却
            if (scale > checkScale) {
                errors.add(field.getKey(),
                    Resources.getActionMessage(
                            validator, request, va, field));
                return false;
            }
            // 一致指定されているとき
            if (isAccordedScale != null
                    && "true".equals(isAccordedScale)) {
                // 桁数不一致は、falseを返却
                if (scale != checkScale) {
                    errors.add(field.getKey(),
                        Resources.getActionMessage(
                                validator, request, va, field));
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 指定されたフィールドが半角カナ文字列であることをチェックする。
     *
     * <p>エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。
     * エラーメッセージのフォーマットは、<code>validation-rules.xml</code>
     * に記述する。</p>
     *
     * @param bean 検査対象のオブジェクト
     * @param va <code>Validator</code>により用意された
     *    <code>ValidatorAction</code>
     * @param field フィールドオブジェクト
     * @param errors ActionMessages アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateHankakuKanaString(Object bean,
                                          ValidatorAction va, Field field,
                                          ActionMessages errors,
                                          Validator validator,
                                          HttpServletRequest request) {

       // beanがnullの時、エラーログを出力し、trueを返却する。
       if (bean == null) {
           log.error("bean is null.");
           return true;
       }
       String value = null;
       if (isString(bean)) {
           value = (String) bean;
       } else {
           value = ValidatorUtils.getValueAsString(bean, field.getProperty());
       }
       // 検証値がnullまたは空文字の時、true返却
       if (GenericValidator.isBlankOrNull(value)) {
           return true;
       }
       char[] chars = value.toCharArray();
       for (int i = 0; i < chars.length; i++) {
           if (!isHankakuKana(chars[i])) {
               errors.add(field.getKey(),
                           Resources.getActionMessage(
                                   validator, request, va, field));
               return false;
           }
       }
       return true;
    }


    /**
     * 指定されたフィールドが半角文字列であることをチェックする。
     *
     * <p>エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。エラーメッセージのフォーマットは、
     * <code>validation-rules.xml</code>に記述する。</p>
     *
     * @param bean 検査対象のオブジェクト
     * @param va <code>Validator</code>により用意された
     *    <code>ValidatorAction</code>
     * @param field フィールドオブジェクト
     * @param errors アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateHankakuString(Object bean,
                                         ValidatorAction va, Field field,
                                         ActionMessages errors,
                                         Validator validator,
                                         HttpServletRequest request) {

        // beanがnullの時、エラーログを出力し、trueを返却する。
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // 検証値がnullまたは空文字の時、true返却
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!isHankaku(chars[i])) {
                errors.add(field.getKey(),
                            Resources.getActionMessage(
                                    validator, request, va, field));
                return false;
            }
        }
        return true;
    }

    /**
     * 指定されたフィールドが全角文字列であることをチェックする。
     *
     * <p>エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。エラーメッセージのフォーマットは、
     * <code>validation-rules.xml</code>に記述する。</p>
     *
     * @param bean 検査対象のオブジェクト
     * @param va <code>Validator</code>により用意された
     *    <code>ValidatorAction</code>
     * @param field フィールドオブジェクト
     * @param errors アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateZenkakuString(Object bean,
                                     ValidatorAction va, Field field,
                                     ActionMessages errors,
                                     Validator validator,
                                     HttpServletRequest request) {

        // beanがnullの時、エラーログを出力し、trueを返却する。
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // 検証値がnullまたは空文字の時、true返却
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!isZenkaku(chars[i])) {
                errors.add(field.getKey(),
                       Resources.getActionMessage(
                               validator, request, va, field));
                return false;
            }
        }
        return true;
    }


    /**
     * 指定されたフィールドが全角カタカナ文字列であることをチェックする。
     *
     * <p>エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。エラーメッセージのフォーマットは、
     * <code>validation-rules.xml</code>に記述する。</p>
     *
     * @param bean 検査対象のオブジェクト
     * @param va <code>Validator</code>により用意された
     *    <code>ValidatorAction</code>
     * @param field フィールドオブジェクト
     * @param errors アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateZenkakuKanaString(Object bean,
                                 ValidatorAction va, Field field,
                                 ActionMessages errors,
                                 Validator validator,
                                 HttpServletRequest request) {

        // beanがnullの時、エラーログを出力し、trueを返却する。
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // 検証値がnullまたは空文字の時、true返却
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!isZenkakuKana(chars[i])) {
                errors.add(field.getKey(),
                   Resources.getActionMessage(
                           validator, request, va, field));
                return false;
            }
        }
        return true;
    }

    /**
     * 指定されたフィールドに入力禁止文字列が混入しているか
     * どうかをチェックする。
     *
     * <p>エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。
     * エラーメッセージのフォーマットは、
     * <code>validation-rules.xml</code>に記述する。<br>
     * 以下は、禁止文字列チェックの設定例である。
     * </p>
     * <h5>validation.xmlの記述例</h5>
     * <code><pre>
     * &lt;form name=&quot;/sample&quot;&gt;
     *  ・・・
     *  &lt;field property=&quot;escape&quot;
     *      depends=&quot;prohibited&quot;&gt;
     *    &lt;arg0 key=&quot;sample.escape&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;chars&lt;/var-name&gt;
     *      &lt;var-value&gt;!&quot;#$%&amp;'()&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  ・・・
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xmlに設定を要する&lt;var&gt;要素</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>必須性</b></center></td>
     *   <td><center><b>概要</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> chars </td>
     *   <td>入力禁止キャラクタ</td>
     *   <td>false</td>
     *   <td>入力文字列が、入力禁止キャラクタの何れかに対応するとき、
     *       <code>false</code>を返却する。省略時は<code>true</code></td>
     *  </tr>
     * </table>
     *
     * @param bean 検査対象のオブジェクト
     * @param va <code>Validator</code>により用意された
     *   <code>ValidatorAction</code>
     * @param field フィールドオブジェクト
     * @param errors アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateProhibited(Object bean,
                            ValidatorAction va, Field field,
                            ActionMessages errors,
                            Validator validator,
                            HttpServletRequest request) {

        // beanがnullの時、エラーログを出力し、trueを返却する。
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }

        // 入力禁止文字列
        String prohibitedStr = field.getVarValue("chars");
        if (prohibitedStr == null || "".equals(prohibitedStr)) {
            // 入力禁止文字列がnullまたは空文字の時、trueを返却
            return true;
        }

        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // 検証値がnullまたは空文字の時、true返却
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }
        char[] chars = value.toCharArray();

        // 入力禁止文字列が未設定の場合、チェックを行わない
        for (int i = 0; i < chars.length; i++) {
            if (prohibitedStr.indexOf(chars[i]) >= 0) {
                errors.add(field.getKey(),
                   Resources.getActionMessage(
                           validator, request, va, field));
                return false;
            }
        }
        return true;
    }

    /**
     * 指定されたフィールドが数字のみからなる文字列であることを
     * チェックする。
     *
     * <p>エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。
     * エラーメッセージのフォーマットは、
     * <code>validation-rules.xml</code>に記述する。</p>
     *
     * @param bean 検査対象のオブジェクト
     * @param va <code>Validator</code>により用意された
     *   <code>ValidatorAction</code>
     * @param field フィールドオブジェクト
     * @param errors アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateNumericString(Object bean,
                            ValidatorAction va, Field field,
                            ActionMessages errors,
                            Validator validator,
                            HttpServletRequest request) {
        // フィールドのクローン作成
        Field fieldClone = (Field) field.clone();
        fieldClone.addVar("mask", "^[0-9]*$", "false");
        // 数値チェックを行う
        return validateMask(bean, va, fieldClone, errors, validator, request);
    }

    /**
     * 指定されたフィールドの文字列長が一致していることをチェックする。
     *
     * <p>エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。
     * エラーメッセージのフォーマットは、
     * <code>validation-rules.xml</code>に記述する。<br>
     * 以下は、文字列長が5であるときのみ<code>true</code>を返却する
     * 検証の設定例である。
     * </p>
     * <h5>validation.xmlの記述例</h5>
     * <code><pre>
     * &lt;form name=&quot;/sample&quot;&gt;
     *  ・・・
     *  &lt;field property=&quot;escape&quot;
     *      depends=&quot;stringLength&quot;&gt;
     *    &lt;arg0 key=&quot;sample.escape&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;length&lt;/var-name&gt;
     *      &lt;var-value&gt;5&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  ・・・
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xmlに設定を要する&lt;var&gt;要素</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>必須性</b></center></td>
     *   <td><center><b>概要</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> stringLength </td>
     *   <td>入力文字列桁数</td>
     *   <td>false</td>
     *   <td>入力文字列長が指定された桁数であるときは <code>true</code>
     *       省略時は<code>true</code>が返却される。</td>
     *  </tr>
     * </table>
     *
     * @param bean 検査対象のオブジェクト
     * @param va Validatorにより用意されたValidatorAction
     * @param field フィールドオブジェクト
     * @param errors アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateStringLength(Object bean,
            ValidatorAction va, Field field,
            ActionMessages errors,
            Validator validator,
            HttpServletRequest request) {

        // beanがnullの時、エラーログを出力し、trueを返却する。
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }

        // 文字列長
        String lengthStr = field.getVarValue("stringLength");
        // 文字列長指定が存在しない時、チェックを行わない
        if (lengthStr == null || "".equals(lengthStr)) {
            return true;
        }
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // 検証値がnullまたは空文字の時、true返却
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }

        int checkLength = 0;
        try {
            checkLength = Integer.valueOf(lengthStr).intValue();
        } catch (NumberFormatException e) {
            // 文字列長指定がint変換不可能な場合
            log.error("stringLength is not numeric(integer).", e);
            return true;
        }
        // 入力された文字列とlength長が等しくない時、falseを返却
        if (value.length() != checkLength) {
            errors.add(field.getKey(),
               Resources.getActionMessage(validator, request, va, field));
            return false;
        }

        return true;
    }

    /**
     * 指定されたフィールドのバイト列長が一致していることをチェックする。
     *
     * <p>エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。
     * エラーメッセージのフォーマットは、
     * <code>validation-rules.xml</code>に記述する。<br>
     * 以下は、バイト列長が5であるときのみ<code>true</code>を返却する
     * 検証の設定例である。
     * </p>
     * <h5>validation.xmlの記述例</h5>
     * <code><pre>
     * &lt;form name=&quot;/sample&quot;&gt;
     *  ・・・
     *  &lt;field property=&quot;escape&quot;
     *      depends=&quot;byteLength&quot;&gt;
     *    &lt;arg0 key=&quot;sample.escape&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;byteLength&lt;/var-name&gt;
     *      &lt;var-value&gt;5&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;encoding&lt;/var-name&gt;
     *      &lt;var-value&gt;Windows-31J&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  ・・・
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xmlに設定を要する&lt;var&gt;要素</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>必須性</b></center></td>
     *   <td><center><b>概要</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> byteLength </td>
     *   <td>入力文字列バイト数</td>
     *   <td>false</td>
     *   <td>入力文字列バイト長を検証するためのバイト長。</td>
     *  </tr>
     *  <tr>
     *   <td> encoding </td>
     *   <td>文字エンコーディング</td>
     *   <td>false</td>
     *   <td>入力文字をバイト列に変換する際に使用する文字エンコーディング。
     *   省略された場合はVMのデフォルトエンコーディングが使用される。</td>
     *  </tr>
     * </table>
     *
     * @param bean 検査対象のオブジェクト
     * @param va Validatorにより用意されたValidatorAction
     * @param field フィールドオブジェクト
     * @param errors アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateByteLength(Object bean,
            ValidatorAction va, Field field,
            ActionMessages errors,
            Validator validator,
            HttpServletRequest request) {

        // beanがnullの時、エラーログを出力し、trueを返却する。
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }

        // 入力値を取得
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // 検証値がnullまたは空文字の時、true返却
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }

        // バイト長
        String lengthStr = field.getVarValue("byteLength");
        // バイト長指定が存在しない時、チェックを行わない
        if (lengthStr == null || "".equals(lengthStr)) {
            if (log.isInfoEnabled()) {
                log.info("length is not specified.");
            }
            return true;
        }
        int checkLength = 0;
        try {
            checkLength = Integer.valueOf(lengthStr).intValue();
        } catch (NumberFormatException e) {
            // 文字列長指定がint変換不可能な場合
            log.error("byteLength is not numeric(integer).", e);
            return true;
        }

        // エンコーディング
        String encoding = field.getVarValue("encoding");

        int bytesLength = getByteLength(value, encoding);

        // 入力された文字列のバイト数とlength長が等しくない時、falseを返却
        if (bytesLength != checkLength) {
            errors.add(field.getKey(),
                Resources.getActionMessage(validator, request, va, field));
            return false;
        }

        return true;
    }

    /**
     * 指定されたフィールドのバイト列長が
     * 指定した範囲内であることをチェックする。
     *
     * <p>エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。
     * エラーメッセージのフォーマットは、
     * <code>validation-rules.xml</code>に記述する。<br>
     * 以下は、バイト列長が5以上、10以下であるときのみ <code>true</code>
     * を返却する検証の設定例である。
     * </p>
     * <h5>validation.xmlの記述例</h5>
     * <code><pre>
     * &lt;form name=&quot;/sample&quot;&gt;
     *  ・・・
     *  &lt;field property=&quot;escape&quot;
     *      depends=&quot;byteRange&quot;&gt;
     *    &lt;arg0 key=&quot;sample.escape&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;maxByte&lt;/var-name&gt;
     *      &lt;var-value&gt;10&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;minByte&lt;/var-name&gt;
     *      &lt;var-value&gt;5&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;encoding&lt;/var-name&gt;
     *      &lt;var-value&gt;Windows-31J&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  ・・・
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xmlに設定を要する&lt;var&gt;要素</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>必須性</b></center></td>
     *   <td><center><b>概要</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> maxByte </td>
     *   <td>最大バイト数</td>
     *   <td>false</td>
     *   <td>入力文字列バイト長を検証するための最大バイト長。
     *   省略した場合はint型の最大値。</td>
     *  </tr>
     *  <tr>
     *   <td> minByte </td>
     *   <td>最大バイト数</td>
     *   <td>false</td>
     *   <td>入力文字列バイト長を検証するための最小バイト長。
     *   省略した場合は0。</td>
     *  </tr>
     *  <tr>
     *   <td> encoding </td>
     *   <td>文字エンコーディング</td>
     *   <td>false</td>
     *   <td>入力文字をバイト列に変換する際に使用する文字エンコーディング。
     *   省略された場合はVMのデフォルトエンコーディングが使用される。</td>
     *  </tr>
     * </table>
     *
     * @param bean 検査対象のオブジェクト
     * @param va Validatorにより用意されたValidatorAction
     * @param field フィールドオブジェクト
     * @param errors アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateByteRange(Object bean,
            ValidatorAction va, Field field,
            ActionMessages errors,
            Validator validator,
            HttpServletRequest request) {

        // beanがnullの時、エラーログを出力し、trueを返却する。
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }

        // 入力値を取得
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }

        // 検証値がnullまたは空文字の時、true返却
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }

        // エンコーディング
        String encoding = field.getVarValue("encoding");

        // 入力文字のバイト長
        int bytesLength = getByteLength(value, encoding);

        int min = 0;
        try {
            min = Integer.parseInt(field.getVarValue("minByte"));
        } catch (NumberFormatException e) {
            log.error("", e);
        }

        int max = Integer.MAX_VALUE;
        try {
            max = Integer.parseInt(field.getVarValue("maxByte"));
        } catch (NumberFormatException e) {
            log.error("", e);
        }

        if (!GenericValidator.isInRange(bytesLength, min, max)) {
            errors.add(field.getKey(), Resources.getActionMessage(
                    validator, request, va, field));
             return false;
        }
        return true;
    }

    /**
     * 日付が指定した範囲内であるかどうかチェックする。
     *
     * <p>エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。
     * エラーメッセージのフォーマットは、
     * <code>validation-rules.xml</code>に記述する。<br>
     * 以下は、日付が2000/01/01から2010/12/31の範囲内であるかどうかの
     * 検証の設定例である。
     * </p>
     * <h5>validation.xmlの記述例</h5>
     * <code><pre>
     * &lt;form name=&quot;/sample&quot;&gt;
     *  ・・・
     *  &lt;field property=&quot;date&quot;
     *      depends=&quot;dateRange&quot;&gt;
     *    &lt;arg key=&quot;label.date&quot; position=&quot;0&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;startDate&lt;/var-name&gt;
     *      &lt;var-value&gt;2000/01/01&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;endDate&lt;/var-name&gt;
     *      &lt;var-value&gt;2010/12/31&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;datePattern&lt;/var-name&gt;
     *      &lt;var-value&gt;yyyy/MM/dd&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  ・・・
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xmlに設定を要する&lt;var&gt;要素</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>必須性</b></center></td>
     *   <td><center><b>概要</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> startDate </td>
     *   <td>開始日付</td>
     *   <td>false</td>
     *   <td>日付範囲の開始の閾値となる日付。
     *   datePatternまたはdatePatternStrictで指定した日付フォーマットと一致すること。</td>
     *  </tr>
     *  <tr>
     *   <td> endDate </td>
     *   <td>終了日付</td>
     *   <td>false</td>
     *   <td>日付範囲の終了の閾値となる日付。
     *   datePatternまたはdatePatternStrictで指定した日付フォーマットと一致すること。</td>
     *  </tr>
     *  <tr>
     *   <td> datePattern </td>
     *   <td>日付パターン</td>
     *   <td>false</td>
     *   <td>日付のパターンを示す文字列。Date型のフォーマットルールに従う。</td>
     *  </tr>
     *  <tr>
     *   <td> datePatternStrict </td>
     *   <td>日付のパターンを示す文字列。Date型のフォーマットルールに従う。</td>
     *   <td>false</td>
     *   <td>日付パターンのチェックを厳密に行うかどうか。
     *   日付パターンがyyyy/MM/ddの場合、2001/1/1はエラーとなる。
     *   datePatternが指定されている場合、datePatternで指定されたフォーマット
     *   が優先される。</td>
     *  </tr>
     * </table>
     *
     * @param bean 検査対象のオブジェクト
     * @param va Validatorにより用意されたValidatorAction
     * @param field フィールドオブジェクト
     * @param errors アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateDateRange(Object bean,
            ValidatorAction va, Field field,
            ActionMessages errors,
            Validator validator,
            HttpServletRequest request) {

        // beanがnullの時、エラーログを出力し、trueを返却する。
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }

        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // 検証値がnullまたは空文字の時、true返却
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }

        //入力日付の妥当性チェック
        String datePattern = field.getVarValue("datePattern");
        String datePatternStrict = field.getVarValue("datePatternStrict");
        Locale locale = RequestUtils.getUserLocale(request, null);
        Date result = null;
        try {
            if (datePattern != null && datePattern.length() > 0) {
                result = GenericTypeValidator.formatDate(
                        value, datePattern, false);
            } else if (datePatternStrict != null
                    && datePatternStrict.length() > 0) {
                result = GenericTypeValidator.formatDate(
                            value, datePatternStrict, true);
            } else {
                result = GenericTypeValidator.formatDate(value, locale);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        if (result == null) {
            errors.add(field.getKey(),
                Resources.getActionMessage(validator, request, va, field));
            return false;
        }

        // 日付範囲チェック
        // 開始日
        String startDateStr = field.getVarValue("startDate");
        // 終了日
        String endDateStr = field.getVarValue("endDate");

        if (startDateStr == null && endDateStr == null) {
            // 日付範囲が指定されていない場合は正常とみなす
            return true;
        }

        // 開始日付以降かどうかチェック
        if (startDateStr != null && startDateStr.length() > 0) {
            Date startDate = null;

            if (datePattern != null && datePattern.length() > 0) {
                startDate = GenericTypeValidator.formatDate(
                        startDateStr, datePattern, false);
            } else if (datePatternStrict != null
                    && datePatternStrict.length() > 0) {
                startDate = GenericTypeValidator.formatDate(
                        startDateStr, datePatternStrict, true);
            } else {
                startDate =
                    GenericTypeValidator.formatDate(startDateStr, locale);
            }

            if (startDate == null) {
                errors.add(field.getKey(),
                    Resources.getActionMessage(validator, request, va, field));
                return false;
            }
            if (result.before(startDate)) {
                errors.add(field.getKey(),
                    Resources.getActionMessage(validator, request, va, field));
                return false;
            }
        }

        // 終了日付以前かどうかチェック
        if (endDateStr != null && endDateStr.length() > 0) {
            Date endDate = null;

            if (datePattern != null && datePattern.length() > 0) {
                endDate = GenericTypeValidator.formatDate(
                        endDateStr, datePattern, false);
            } else if (datePatternStrict != null
                    && datePatternStrict.length() > 0) {
                endDate = GenericTypeValidator.formatDate(
                        endDateStr, datePatternStrict, true);
            } else {
                endDate =
                    GenericTypeValidator.formatDate(endDateStr, locale);
            }

            if (endDate == null) {
                errors.add(field.getKey(),
                    Resources.getActionMessage(validator, request, va, field));
                return false;
            }
            if (result.after(endDate)) {
                errors.add(field.getKey(),
                    Resources.getActionMessage(validator, request, va, field));
                return false;
            }
        }

        return true;
    }

    /**
     * 複数フィールドの相関チェックを行う。
     *
     * この検証ルールの実行には{@link MultiFieldValidator} の実装クラスが
     * 必要。実装したクラスは <code>validation.xml</code> に設定を行う。<br>
     * エラーとなった場合には、エラー情報を生成し、
     * 指定されたエラー情報リストに追加する。
     * この検証ルールにはデフォルトのエラーメッセージがないため、
     * メッセージは <code>validation.xml</code> に必ず記述すること。<br>
     * アクションフォームのvalueフィールドの値が、value1フィールドの値以上、
     * value2フィールドの値以下であることを検証する場合、以下のように実装、
     * 設定を行う。
     * <h5>{@link MultiFieldValidator} の実装例</h5>
     * <code><pre>
     * public boolean validate(String value, String[] depends) {
     *     int value0 = Integer.parseInt(value);
     *     int value1 = Integer.parseInt(depends[0]);
     *     int value2 = Integer.parseInt(depends[1]);
     *     return (value1 <= value0 && value2 >= value0);
     * }
     * </pre></code>
     * <h5>validation.xmlの設定例</h5>
     * <code><pre>
     * &lt;form name=&quot;/validateMultiField&quot;&gt;
     *   &lt;field property=&quot;value&quot; depends=&quot;multiField&quot;&gt;
     *     &lt;msg key=&quot;errors.multiField&quot;
     *             name=&quot;multiField&quot;/&gt;
     *     &lt;arg key=&quot;label.value&quot; position=&quot;0&quot; /&gt;
     *     &lt;arg key=&quot;label.value1&quot; position=&quot;1&quot; /&gt;
     *     &lt;arg key=&quot;label.value2&quot; position=&quot;2&quot; /&gt;
     *     &lt;var&gt;
     *       &lt;var-name&gt;fields&lt;/var-name&gt;
     *       &lt;var-value&gt;value1,value2&lt;/var-value&gt;
     *     &lt;/var&gt;
     *     &lt;var&gt;
     *       &lt;var-name&gt;multiFieldValidator&lt;/var-name&gt;
     *       &lt;var-value&gt;sample.SampleMultiFieldValidator&lt;/var-value&gt;
     *     &lt;/var&gt;
     *   &lt;/field&gt;
     * &lt;/form&gt;
     * </pre></code>
     * <h5>メッセージリソースファイルの設定例</h5>
     * <code>
     * errors.multiField={0}は{1}から{2}の間の値を入力してください。
     * </code>
     *
     * <h5>validation.xmlに設定を要する&lt;var&gt;要素</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>必須性</b></center></td>
     *   <td><center><b>概要</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> fields </td>
     *   <td>検証に必要な他のフィールド名</td>
     *   <td>false</td>
     *   <td>複数のフィールドを指定する場合はフィールド名をカンマ区切りで
     *   指定する。</td>
     *  </tr>
     *  <tr>
     *   <td> multiFieldValidator </td>
     *   <td>{@link MultiFieldValidator} 実装クラス名</td>
     *   <td>false</td>
     *   <td>複数フィールドの相関チェックを行う {@link MultiFieldValidator}
     *   実装クラス名。</td>
     *  </tr>
     * </table>
     *
     * @param bean 検査対象のオブジェクト
     * @param va Validatorにより用意されたValidatorAction
     * @param field フィールドオブジェクト
     * @param errors アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 入力値が正しければ <code>true</code>
     */
    public static boolean validateMultiField(Object bean,
                                             ValidatorAction va,
                                             Field field,
                                             ActionMessages errors,
                                             Validator validator,
                                             HttpServletRequest request) {

        // beanがnullの時、エラーログを出力し、trueを返却する。
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }

        // 検証対象の値を取得
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // 他のフィールドに依存した必須入力チェックを考慮し、
        // 検証値がnullまたは空文字の場合でも複数フィールドチェックは実行する。

        // MultiFieldValidator実装クラス名を取得
        String multiFieldValidatorClass
            = field.getVarValue("multiFieldValidator");

        if (multiFieldValidatorClass == null
                || "".equals(multiFieldValidatorClass)) {
            log.error("var value[multiFieldValidator] is required.");
            throw new IllegalArgumentException(
                    "var value[multiFieldValidator] is required.");
        }

        MultiFieldValidator mfv = null;
        try {
            mfv = (MultiFieldValidator) ClassUtil.create(
                    multiFieldValidatorClass);
        } catch (ClassLoadException e) {
            log.error("var value[multiFieldValidator] is invalid.", e);
            throw new IllegalArgumentException(
                    "var value[multiFieldValidator] is invalid.", e);
        } catch (ClassCastException e) {
            log.error("var value[multiFieldValidator] is invalid.", e);
            throw new IllegalArgumentException(
                    "var value[multiFieldValidator] is invalid.", e);
        }

        // 検証の依存フィールドの値を取得
        String fields = field.getVarValue("fields");
        List<String> valueList = new ArrayList<String>();
        if (fields != null) {
            StringTokenizer st = new StringTokenizer(fields, ",");
            while (st.hasMoreTokens()) {
                String f = st.nextToken();
                f = f.trim();
                valueList.add(ValidatorUtils.getValueAsString(bean, f));
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("dependent fields:"
                    + valueList);
        }

        String[] values = new String[valueList.size()];

        valueList.toArray(values);

        boolean result = mfv.validate(value, values);

        if (!result) {
            errors.add(field.getKey(),
                    Resources.getActionMessage(validator, request, va, field));
            return false;
        }

        return true;
    }

    /**
     * 配列・コレクション型のフィールド値を全要素チェックする。
     * <p>
     *  配列・コレクション型のフォームの要素をすべて検証し、発生した
     *  インデックス情報を <code>ActionMessage</code>に追加する。
     *  実行されるサブメソッドの引数の型、順序は、このメソッドと
     *  一致している必要がある。
     *  Fieldのproperty属性にはJXPathIndexedBeanWrapperImplの仕様に従い、
     *  ネストしたプロパティ名を設定することが可能である。
     * </p>
     *
     * @param bean 検査対象のオブジェクト
     * @param va Strutsにより用意された<code>ValidatorAction</code>
     * @param field フィールドオブジェクト
     * @param errors アクションエラー
     * @param validator Validatorインスタンス
     * @param request HTTPリクエスト
     * @return 要素すべての入力値が正しければ <code>true</code>
     */
    public static boolean validateArraysIndex(
            @SuppressWarnings("unused") Object bean,
            ValidatorAction va,
            Field field,
            ActionMessages errors,
            Validator validator,
            HttpServletRequest request) {

        // 入力引数の数
        int methodArgCount = 6;

        // 配列・コレクションの総合結果（一つでもfalseが返却された場合、false）
        boolean totalResult = true;
        try {
            // コンストラクタの引数となるクラスパターンを取得する
            Class[] paramClass = getParamClass(va);
            if (paramClass == null) {
                log.error("Can not get class pattern.");
                return true;
            }
            // paramClassが要素数=0のとき、エラーログを出力し、true返却
            if (paramClass.length == 0) {
                log.error("Class pattern length is zero.");
                return true;
            }


            // 各要素の検証を行うメソッドを取得する
            Method method = getMethod(va, paramClass);
            if (method == null) {
                log.error("Can not get validateMethod.");
                return true;
            }

            // フォームオブジェクトを取得する
            ActionForm form = getActionForm(request);
            if (form == null) {
                log.error("Can not get ActionForm.");
                return true;
            }

            // 要素ごとのバリデーションルールに共通である、
            // 事前設定不要のオブジェクト
            Object[] argParams = new Object[methodArgCount];
            argParams[0] = form;
            argParams[1] = va;
            argParams[3] = errors;
            argParams[4] = validator;
            argParams[5] = request;

            IndexedBeanWrapper wrapper = new JXPathIndexedBeanWrapperImpl(form);
            Map<String, Object> propertyMap =
                wrapper.getIndexedPropertyValues(field.getKey());

            int index = 0;

            for (String key : propertyMap.keySet()) {
                // インデックス付きのプロパティ名でフィールドを書き換える
                Field indexedField = (Field) field.clone();
                indexedField.setIndexedListProperty(null);
                indexedField = getArrayIndexField(indexedField, index);
                indexedField.setKey(key);
                indexedField.setProperty(key);

                argParams[2] = indexedField; // フィールド

                // 入力チェックメソッドの呼び出し
                Object resultObj =
                    method.invoke(
                        FieldChecksEx.class, argParams);
                // 結果解析
                if (!isValid(resultObj)) {
                    totalResult = false;
                }
                index++;
            }

        } catch (IllegalArgumentException e) {
            log.error("", e);
            return true;
        } catch (IllegalAccessException e) {
            log.error("", e);
            return true;
        } catch (InvocationTargetException e) {
            log.error("", e);
            return true;
        }
        return totalResult;
    }

    /**
     * フォーム実装オブジェクトを取得する。
     *
     * <b>このメソッドはアクションフォームを対象としているため
     * フォーム以外で、流用を行なうことを推奨しない。</b>
     *
     * @param request HTTPリクエスト
     * @return フォーム実装オブジェクト
     */
    protected static ActionForm getActionForm(HttpServletRequest request) {

        // リクエスト属性で設定されているアクションフォーム名を取得する
        String formName = ActionFormUtil.getActionFormName(request);
        // フォーム名が取得できない場合nullを返却
        if (formName == null) {
            return null;
        }

        // セッションからフォームを取得
        Object obj = request.getSession(true).getAttribute(formName);
        ActionForm form = null;
        if (obj instanceof ActionForm) {
            form = (ActionForm) obj;
        }

        // セッションに存在しない時、リクエストから取得
        if (form == null) {
            obj = request.getAttribute(formName);
            if (obj instanceof ActionForm) {
                form = (ActionForm) obj;
            }
        }
        return form;
    }

    /**
     * 検証ルールメソッドに渡される引数クラス配列を取得する。
     *
     * <b>このメソッドはアクションフォームを対象としているため
     * フォーム以外で、流用を行なうことを推奨しない。</b>
     *
     * @param va Strutsにより用意された<code>ValidatorAction</code>
     * @return 引数クラス配列
     */
    protected static Class[] getParamClass(ValidatorAction va) {

        String methodParams = va.getMethodParams();
        if (methodParams == null) {
            return null;
        }

        StringTokenizer st = new StringTokenizer(methodParams, ",");
        Class[] paramClass = new Class[st.countTokens()];

        for (int i = 0; st.hasMoreTokens(); i++) {
            try {
                String key = st.nextToken().trim();
                paramClass[i] = ClassUtils.getClass(key);
            } catch (ClassNotFoundException e) {
                log.error("", e);
                return null;
            }
        }
        return paramClass;
    }

    /**
     * 配列・コレクションの要素を検証するメソッドを取得する。
     *
     * <b>このメソッドはアクションフォームを対象としているため
     * フォーム以外で、流用を行なうことを推奨しない。</b>
     *
     * @param va Strutsにより用意された<code>ValidatorAction</code>
     * @param paramClass 引数クラス配列
     * @return 検証メソッドオブジェクト
     */
    protected static Method getMethod(ValidatorAction va,
            Class[] paramClass) {

        String methodNameSource = va.getName();
        if (methodNameSource == null || "".equals(methodNameSource)) {
            // メソッド名指定がnullまたは空文字のときnull返却。
            return null;
        }

        // name属性から"Array"を除去したメソッド名を生成する
        // xxxxArray→validateXxxx
        char[] chars = methodNameSource.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        String validate =  "validate" + new String(chars);
        String methodName = validate.substring(0,
            validate.length() - "Array".length());

        Method method = null;
        try {
            method = FieldChecksEx.class.getMethod(methodName, paramClass);
        } catch (NoSuchMethodException e) {
            try {
                method = ValidWhen.class.getMethod(methodName, paramClass);
            } catch (NoSuchMethodException e1) {
                log.error("", e);
                log.error("", e1);
                return null;
            }
        }
        return method;
    }


    /**
     * リフレクションを用いて実行されたメソッドの結果
     * オブジェクトから、検証が成功したか否かをチェックする。
     *
     * <b>このメソッドはアクションフォームを対象としているため
     * フォーム以外で、流用を行なうことを推奨しない。</b>
     *
     * @param result 結果オブジェクト
     * @return 検証成功時は <code>true</code>
     */
    protected static boolean isValid(Object result) {
        boolean isValid = false;

        if (result instanceof Boolean) {
            Boolean valid = (Boolean) result;
            isValid = valid.booleanValue();
        } else {
            // Boolean型ではないとき、trueを返却する。
            return true;
        }

        return isValid;
    }

    /**
     * 指定されたIndexのフィールドを取得する。
     *
     * <b>このメソッドはアクションフォームを対象としているため
     * フォーム以外で、流用を行なうことを推奨しない。</b>
     *
     * @param field 置換元フィールドオブジェクト
     * @param pos 走査中のインデックス
     * @return 置換先のフィールドオブジェクト
     */
    protected static Field getArrayIndexField(Field field, int pos) {
        Field cloneField = (Field) field.clone();

        // ##index文字列を、現在の要素数に書き換える
        Arg argParam = null;
        String argStr = null;

        argParam = cloneField.getArg(0);
        if (argParam != null) {
            argStr = argParam.getKey();
            cloneField.getArg(0).setKey(replaceIndexString(argStr, pos + 1));
        }

        argParam = cloneField.getArg(1);
        if (argParam != null) {
            argStr = argParam.getKey();
            cloneField.getArg(1).setKey(replaceIndexString(argStr, pos + 1));
        }

        argParam = cloneField.getArg(2);
        if (argParam != null) {
            argStr = argParam.getKey();
            cloneField.getArg(2).setKey(replaceIndexString(argStr, pos + 1));
        }

        argParam = cloneField.getArg(3);
        if (argParam != null) {
            argStr = argParam.getKey();
            cloneField.getArg(3).setKey(replaceIndexString(argStr, pos + 1));
        }

        return cloneField;
    }

    /**
     * <code>arg</code>の値が##INDEXの時、走査中のインデックス値に
     * 置換する
     *
     * <b>このメソッドはアクションフォームを対象としているため
     * フォーム以外で、流用を行なうことを推奨しない。</b>
     *
     * @param arg <code>ActionMessage</code>の置換元キー情報
     * @param pos 走査中のインデックス
     * @return キーが##INDEXの時、現在のインデックスを返却する
     */
    protected static String replaceIndexString(String arg, int pos) {
        if (arg == null) {
            return null;
        }
        if (INDEX.equalsIgnoreCase(arg)) {
            return Integer.toString(pos);
        }
        return arg;
    }

    /**
     * 指定された文字列のバイト列長を取得する。<br>
     * 第二引数のエンコーディングでバイト列に変換するが、指定されていなかったり
     * 、エンコードに失敗する場合はデフォルトのエンコーディングで
     * バイト列に変換を行う。
     *
     * @param value バイト列長を取得する対象の文字列
     * @param encoding 文字エンコーディング
     * @return バイト列長
     */
    protected static int getByteLength(String value, String encoding) {
        if (value == null || "".equals(value)) {
            return 0;
        }
        byte[] bytes = null;
        if (encoding == null || "".equals(encoding)) {
            bytes = value.getBytes();
        } else {
            try {
                bytes = value.getBytes(encoding);
            } catch (UnsupportedEncodingException e) {
                if (log.isWarnEnabled()) {
                    log.warn(encoding + " is not supported.");
                }
                bytes = value.getBytes();
            }
        }
        return bytes == null ? 0 : bytes.length;
    }

    /**
     * 半角カナのリストを取得する。
     * @return 半角カナリスト
     */
    public static String getHankakuKanaList() {
        return hankakuKanaList;
    }

    /**
     * 全角カナのリストを取得する。
     * @return 全角カナリスト
     */
    public static String getZenkakuKanaList() {
        return zenkakuKanaList;
    }

}
