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

import jp.terasoluna.fw.web.struts.ModuleUtil;
import jp.terasoluna.fw.web.struts.reset.Resetter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorActionForm;

/**
 * 動的アクションフォーム基底クラス。
 *
 * <p>
 * DynaValidatorActionFormEx を用いることで、
 * Struts設定ファイル(struts-config.xml) に設定情報を定義するだけで、
 * アクションフォームを動的に生成することができる。
 * <p><strong> Struts設定ファイル(struts-config.xml) のアクションフォーム作成例</strong>
 * </p>
 *
 * <code><pre>
 * &lt;form-beans&gt;
 *  <b>&lt;form-bean
 *  name="_validateSampleForm"
 *  type="jp.terasoluna.fw.web.thin.form.DynaValidatorActionFormEx"&gt;</b>
 *    &lt;form-property name="companyId" type="java.lang.String" /&gt;
 *    &lt;form-property name="userId"    type="java.lang.String" /&gt;
 *    &lt;form-property name="password"  type="java.lang.String" /&gt;
 *    &lt;form-property name="longName"  type="java.lang.String" /&gt;
 *    &lt;form-property name="codeArray" type="java.util.ArrayList" /&gt;
 *  &lt;/form-bean&gt;
 * &lt;/form-beans&gt;
 * </pre></code>
 * &lt;form-bean&gt;タグ内の name 属性に
 * フォーム論理名を指定し、type 属性にクラス名を指定する。<br>
 * フォーム論理名の先頭に &quot;_&quot を付け、コントローラに
 * RequestProcessorExを指定することにより、
 * セッション上のフォームの唯一性が保障される。<br>
 * <br>
 * <strong>拡張点で提供する機能</strong><br>
 * <ul>
 * <li>リセット機能。<br>
 * サーブレットコンテキストに設定されている ResetterImpl クラス
 * のreset()メソッドを起動する。<br></li>
 * <li>インデックス付プロパティ件数取得機能。<br>
 * getIndexCount(String fieldName)により、対象のフィールドの
 * 件数を取得できる。<br></li>
 * <li>インデックス付プロパティ設定機能。<br>
 * 配列、Listのインデックス範囲外のセッターメソッドが呼ばれた
 * ときに動的にインデックスの要素を増加させる。<br></li>
 * <li>インデックス付プロパティ取得機能。<br>
 * 配列、Listのインデックス範囲外のゲッターメソッドが呼ばれた
 * ときにnullを返却する。<br></li>
 * <li>フィールド値変更フラグ。<br>
 * BLogicMapperにてアクションフォームのフィールド値変更の際に
 * modified属性をtrueに設定し、変更が行われたかどうかを検知できる。
 * <br></li>
 * </ul>
 *
 * 機能の詳細として参考にすべき事項
 * <ul>
 *  <li>フォーム毎にクラスを作成する場合の実装例については、
 *   ValidatorActionFormExを参照。</li>
 *  <li>フォームの唯一性、フィールド値変更フラグの詳細については、
 *   RequestProcessorExを参照。</li>
 *  <li>リセット・フィールド値クリア機能についての詳細は、ResetterImpl、
 *    ResetterPlugInを参照。</li>
 *  <li>拡張バリデーション機能については FieldChecksEx を参照。</li>
 * </ul>
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.action.RequestProcessorEx
 * @see jp.terasoluna.fw.web.struts.reset.Resetter
 * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
 * @see jp.terasoluna.fw.web.struts.form.FieldChecksEx
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 *
 */
public class DynaValidatorActionFormEx
    extends DynaValidatorActionForm
    implements FormEx {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -8958002981280692562L;

    /**
     * ログクラス。
     */
    private static Log log =
        LogFactory.getLog(DynaValidatorActionFormEx.class);

    /**
     * 値変更フラグ。
     */
    private boolean modified = false;

    /**
     * インデックス付のプロパティを取得する。
     *
     * <p>
     *  インデックスが範囲外のときにnull
     *  を返すようにStrutsのデフォルトから拡張している。
     * </p>
     *
     * @param name フィールド名
     * @param index インデックス
     * @return プロパティ値
     */
    @Override
    public Object get(String name, int index) {
        if (log.isDebugEnabled()) {
            log.debug("get(" + name + ", " + index + ") called.");
        }

        Object value = dynaValues.get(name);
        if (value == null) {
            throw new NullPointerException(
                "No indexed value for '" + name + "[" + index + "]'");
        } else if (value.getClass().isArray()) {
            try {
                return (Array.get(value, index));
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }
        } else if (value instanceof List) {
            try {
                return ((List) value).get(index);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        } else {
            throw new IllegalArgumentException(
                "Non-indexed property for '" + name + "[" + index + "]'");
        }
    }

    /**
     * インデックス付のプロパティを取得する。
     *
     * <p>
     *  インデックスが範囲外のときにnull
     *  を返すようにStrutsのデフォルトから拡張している。
     * </p>
     *
     * @param name フィールド名
     * @param index インデックス
     * @return プロパティ値
     */
    public Object getIndexedValue(String name, int index) {
        return this.get(name, index);
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
        Object value = dynaValues.get(fieldName);
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

    /**
     * インデックス付のプロパティをセットする。
     *
     * <p>
     *  StrutsのDynaActionFormではプロパティの型を
     *  Listまたは配列型にすると使用する前に初期化し、
     *  サイズを決定しなければならないが、動的にサイズを
     *  変更することで、その処理を省略できるように拡張している。
     * </p>
     *
     * @param name セット対象のフィールド名
     * @param index セット対象のインデックス
     * @param value セット対象のフィールド値
     */
    public void setIndexedValue(String name, int index, Object value) {

        if (log.isDebugEnabled()) {
            log.debug(
                "setIndexedValue(" + name + ", " + index + ", " + value + ") called.");
        }

        this.set(name, index, value);
    }


    /**
     * インデックス付のプロパティをセットする。
     *
     * <p>
     *  StrutsのDynaActionFormではプロパティの型を
     *  Listまたは配列型にすると使用する前に初期化し、
     *  サイズを決定しなければならないが、動的にサイズを
     *  変更することで、その処理を省略できるように拡張している。
     * </p>
     *
     * @param name セット対象のフィールド名
     * @param index セット対象のインデックス
     * @param value セット対象のフィールド値
     */
    @SuppressWarnings("unchecked")
    @Override
    public void set(String name, int index, Object value) {

        if (log.isDebugEnabled()) {
            log.debug(
                "set(" + name + ", " + index + ", " + value + ") called.");
        }

        Object prop = dynaValues.get(name);
        if (prop == null) {
            throw new NullPointerException(
                "No indexed value for '" + name + "[" + index + "]'");
        } else if (prop.getClass().isArray()) {
            if (index < Array.getLength(prop)) {
                Array.set(prop, index, value);
            } else {
                // 新規の配列を生成
                Object newArray = Array.newInstance(
                     prop.getClass().getComponentType(), index + 1);
                // 配列のコンポーネントをコピー
                System.arraycopy(prop, 0, newArray, 0, Array.getLength(prop));
                // 追加分のコンポーネントをセット
                Array.set(newArray, index, value);
                // 参照のコピー
                prop = newArray;
            }
            dynaValues.put(name, prop);
        } else if (prop instanceof List) {
            if (index < ((List) prop).size()) {
                ((List) prop).set(index, value);
            } else {
                Object[] oldValues = ((List) prop).toArray();
                Object[] newValues = (Object[]) Array.newInstance(
                           oldValues.getClass().getComponentType(), index + 1);
                System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
                newValues[index] = value;
                ((List) prop).clear();
                ((List) prop).addAll(Arrays.asList(newValues));
            }
            dynaValues.put(name, prop);
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

}
