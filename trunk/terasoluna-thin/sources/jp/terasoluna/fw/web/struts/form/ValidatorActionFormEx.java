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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.util.BeanUtil;
import jp.terasoluna.fw.util.PropertyAccessException;
import jp.terasoluna.fw.web.struts.ModuleUtil;
import jp.terasoluna.fw.web.struts.reset.Resetter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * Validatorフレームワーク対応アクションフォーム基底クラス。
 *
 * <p>
 * Strutsの ValidatorActionForm を継承した
 * アクションフォーム基底クラスである。<br>
 * 下記は、Struts設定ファイル(struts-config.xml) で、このクラスを継承した
 * アクションフォームの設定例である。
 * <p>
 * <strong> Struts設定ファイル(struts-config.xml) のアクションフォーム設定例</strong>
 * </p>
 * <code><pre>
 * &lt;form-beans&gt;
 *  <b>&lt;form-bean
 *    name="_validateSampleForm"
 *    type="jp.terasoluna.sample.xxxx.SampleValidatorActionFormEx"
 *  &gt;</b>
 *  &lt;/form-bean&gt;
 * &lt;/form-beans&gt;
 * </pre></code>
 * &lt;form-bean&gt;タグ内の name 属性に
 * フォーム論理名を指定し、type 属性にクラス名を指定する。<br>
 * フォーム論理名の先頭に &quot;_&quot を付け、コントローラに
 * RequestProcessorExを指定することにより、
 * セッション上のフォームの唯一性が保障される。<br>
 * フォームの実装例は下記のようになる。<br>
 * <p>
 * <strong>フォーム実装例</strong>
 * </p>
 * <code><pre>
 * public class SampleValidatorActionForm extends ValidatorActionFormEx {
 *
 *    // 会社ID
 *    private String companyId = null;
 *    // ユーザID
 *    private String userId = null;
 *    // パスワード
 *    private String password = null;
 *    ・・・
 *    // 会社IDのsetter
 *    public void setCompanyId(String companyId) {
 *        this.companyId = companyId;
 *    }
 *
 *    // 会社IDのgetter
 *    public String getCompanyId() {
 *        return companyId;
 *    }
 *    ・・・
 * }
 * </pre></code>
 * フォーム内で保持すべき情報のフィールドを作成し、そのフィールドの
 * getter/setter を記述する。
 *
 * <p>
 * <strong>拡張点で提供する機能</strong>
 * </p>
 * <ul>
 * <li>リセット機能。<br>
 * サーブレットコンテキストに設定されている ResetterImpl クラス
 * の reset() メソッドを起動する。<br></li>
 * <li>インデックス付プロパティ件数取得機能。<br>
 *  getIndexCount(String fieldName) により、対象のフィールドの
 * 件数を取得できる。<br></li>
 * <li>インデックス付プロパティ設定機能。<br>
 * 配列、 List のインデックス範囲外のセッターメソッドが呼ばれた
 * ときに動的にインデックスの要素を増加させる。<br></li>
 * <li>インデックス付プロパティ取得機能。<br>
 * 配列、 List のインデックス範囲外のゲッターメソッドが呼ばれた
 * ときに null を返却する。<br></li>
 * <li>フィールド値変更フラグ。<br>
 * BLogicMapperにてアクションフォームのフィールド値変更の際に
 * modified属性をtrueに設定し、変更が行われたかどうかを検知できる。
 * <br></li>
 * </ul>
 *
 * 機能の詳細として参考にすべき事項
 * <ul>
 *  <li> Struts設定ファイル(struts-config.xml) に記述することにより、
 *   動的にフォームを作成する場合の設定例については、
 *    DynaValidatorActionFormEx を参照。</li>
 *  <li>フォームの唯一性、フィールド値変更フラグの詳細については、
 *     RequestProcessorEx を参照。</li>
 *  <li>リセット・フィールド値クリア機能についての詳細は、 ResetterImpl 、
 *     ResetterPlugIn を参照。</li>
 *  <li>拡張バリデーション機能については  FieldChecksEx を参照</li>
 * </ul>
 * </p>
 *
 * @see
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.action.RequestProcessorEx
 * @see jp.terasoluna.fw.web.struts.reset.Resetter
 * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
 * @see jp.terasoluna.fw.web.struts.form.FieldChecksEx
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 *
 */
public class ValidatorActionFormEx
    extends ValidatorActionForm
    implements FormEx {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -744848917166154997L;

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(ValidatorActionFormEx.class);

    /**
     * 値変更フラグ。
     */
    private boolean modified = false;

    /**
     * 指定したインデックスのプロパティ値を取得する。
     *
     * @param name 取得対象のプロパティ名
     * @param index 取得対象のインデックス
     * @return プロパティ値
     */
    public Object getIndexedValue(String name, int index) {
        Object field = null;
        try {
            field = BeanUtil.getBeanProperty(this, name);
        } catch (PropertyAccessException e) {
            // DynaValidatorActionFormExとの仕様統一の為、
            // 例外発生時の処理は行なわない。
        }
        if (field == null) {
            throw new NullPointerException(
                "No indexed value for '" + name + "[" + index + "]'");
        } else if (field.getClass().isArray()) {
            try {
                return (Array.get(field, index));
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }
        } else if (field instanceof List) {
            try {
                return ((List) field).get(index);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        } else {
            throw new IllegalArgumentException(
                "Non-indexed property for '" + name + "[" + index + "]'");
        }
    }

    /**
     * 指定されたフィールドの件数を取得する。
     *
     * @param fieldName 件数取得対象のフィールド名。
     * @return フィールド値の件数。
     * <p>プロパティ名が取得できなかった場合は0を返す。</p>
     */
    public int getIndexCount(String fieldName) {
        if (log.isDebugEnabled()) {
            log.debug("getIndexCount(" + fieldName + ") called.");
        }
        int count = 0;
        Object value = null;
        try {
            value = BeanUtil.getBeanProperty(this, fieldName);
        } catch (PropertyAccessException e) {
            // DynaValidatorActionFormExとの仕様統一の為、
            // 例外発生時の処理は行なわない。
        }
        if (value == null) {
            count = 0;
        } else if (value.getClass().isArray()) {
            count = ((Object[]) value).length;
        } else if (value instanceof List) {
            count = ((List) value).size();
        } else if (value instanceof Map) {
            count = ((Map) value).size();
        } else {
            count = 1;
        }
        if (log.isDebugEnabled()) {
            log.debug("size = [" + count + "]");
        }
        return count;
    }

    /**
     * 指定したインデックスの位置にプロパティ値を設定する。
     *
     * @param name 設定対象のインデックス付プロパティ名
     * @param index 設定対象のインデックス位置
     * @param value 設定するプロパティ値
     */
    @SuppressWarnings("unchecked")
    public void setIndexedValue(String name, int index, Object value) {
        if (log.isDebugEnabled()) {
            log.debug(
                "set(" + name + ", " + index + ", " + value + ") called.");
        }

        Object prop = null;
        try {
            prop = BeanUtil.getBeanProperty(this, name);
        } catch (PropertyAccessException e) {
            // DynaValidatorActionFormExとの仕様統一の為、
            // 例外発生時の処理は行なわない。
        }
        if (prop == null) {
            throw new NullPointerException(
                "No indexed value for '" + name + "[" + index + "]'");
        } else if (prop.getClass().isArray()) {
            if (index < Array.getLength(prop)) {
                Array.set(prop, index, value);
            } else {
                // 新規の配列を生成
                Object newArray =
                    Array.newInstance(prop.getClass().getComponentType(),
                                                                    index + 1);
                // 配列のコンポーネントをコピー
                System.arraycopy(prop, 0, newArray, 0, Array.getLength(prop));
                // 追加分のコンポーネントをセット
                Array.set(newArray, index, value);
                // 参照のコピー
                prop = newArray;
            }
            try {
                BeanUtil.setBeanProperty(this, name, prop);
            } catch (PropertyAccessException e) {
                throw new IllegalArgumentException(
                    "Cannot set property for '" + name + "[" + index + "]'");
            }
        } else if (prop instanceof List) {
            if (index < ((List) prop).size()) {
                ((List) prop).set(index, value);
            } else {
                Object[] oldValues = ((List) prop).toArray();
                Object[] newValues =
                    (Object[]) Array.newInstance(
                        oldValues.getClass().getComponentType(),
                        index + 1);
                System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
                newValues[index] = value;
                ((List) prop).clear();
                ((List) prop).addAll(Arrays.asList(newValues));
            }
            try {
                BeanUtil.setBeanProperty(this, name, prop);
            } catch (PropertyAccessException e) {
                throw new IllegalArgumentException(
                    "Cannot set property for '" + name + "[" + index + "]'");
            }
        } else {
            throw new IllegalArgumentException(
                "Non-indexed property for '" + name + "[" + index + "]'");
        }

    }

    /**
     * 値変更フラグを取得する。
     *
     * @return 値変更フラグ
     */
    public boolean isModified() {
        return this.modified;
    }

    /**
     * 値変更フラグを設定する。
     *
     * @param modified 値変更フラグ
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    /**
     * アクションフォームのフィールド値リセットを行う。
     *
     * @param mapping マッピング情報
     * @param request リクエスト情報
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("reset() called.");
        }

        //Prefixを取得
        String prefix = ModuleUtil.getPrefix(request);

        try {
            Resetter resetter =
                (Resetter) getServlet().getServletContext().getAttribute(
                    Resetter.RESETTER_KEY + prefix);
            if (resetter == null) {
                if (log.isDebugEnabled()) {
                    log.debug("resetter class is not specified.");
                }
                return;
            }
            resetter.reset(this, mapping, request);
        } catch (ClassCastException e) {
            log.error("", e);
            return;
        }
    }

}
