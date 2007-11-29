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

package jp.terasoluna.fw.web.struts.reset;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.beans.IndexedBeanWrapper;
import jp.terasoluna.fw.beans.JXPathIndexedBeanWrapperImpl;
import jp.terasoluna.fw.util.BeanUtil;
import jp.terasoluna.fw.util.PropertyAccessException;
import jp.terasoluna.fw.web.RequestUtil;
import jp.terasoluna.fw.web.struts.form.FormEx;
import jp.terasoluna.fw.web.struts.ModuleUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;

/**
 * デフォルトのリセット実装クラス。
 *
 * <p>
 * TERASOLUNAがデフォルトで用意している Resetter 実装クラス。<br>
 * このクラスは
 * {@link  jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn}
 * を利用し、XML ファイルに設定された情報を元に
 * フォームフィールドのリセットを行う。<br>
 * アクションフォームのreset()メソッドを拡張している為、リセット処理は
 * Strutsのライフサイクル内のみで実行される。リセット処理はチェックボックスや
 * ラジオボタンの値を持つプロパティの初期化を目的として提供される為、
 * その他の目的での利用については動作は保証しない。<br>
 * ※単純にフィールドを初期化したい場合は、ビジネスロジックなどを通し、
 * モデルの値変更として実行すること。Strutsのライフサイクルではリセット実行後、
 * リクエストパラメータでそのプロパティを再度書き換える。<br>
 * <br>
 * リセット処理は対象のフィールドの値がnullの場合、型判定ができない為、
 * nullのまま、値変更を実行しない。null以外のObject型フィールドはnull値に、
 * boolean型はfalseに、その他のプリミティブ型フィールドは0に初期化を行う。
 * DynaValidatorActionFormExを使用する場合、
 * struts-config.xmlに記述した初期値にはリセットされないので、注意すること。<br>
 * リセット機能として、 『通常のリセット処理』 及び 『指定範囲リセット機能』
 * を持ち、指定範囲リセット機能では、配列又は List オブジェクトの
 * 任意の範囲のみをリセットする事が可能。<br>
 * リセットする任意の範囲はリクエストパラメータに &quot;startIndex&quot;,
 * &quot;endIndex&quot;をキーとして格納しておく必要がある。
 * </p>
 *
 * <strong>使用方法</strong><br>
 * フォームリセット定義ファイル(reset.xml)にはアクションごとに
 * リセット対象フィールドを設定する。 <code><pre>
 *  &lt;reset&gt;
 *    &lt;action path=&quot;/resetAction&quot;&gt;
 *      &lt;property-reset name=&quot;field1&quot; /&gt;
 *      &lt;property-reset name=&quot;field2&quot; select=&quot;true&quot; /&gt;
 *    &lt;/action&gt;
 *  &lt;/reset&gt;
 * </pre></code> 上記の設定の場合、/resetAction.do が呼ばれると
 * リクエストパラメータがフォームに反映される前にフォームの
 * 指定のフィールドがクリアされる。<br>
 * クリア対象のフィールドは property-reset タグの name 属性で指定された、
 * &quot;field1&quot; と
 * &quot;field2&quot; となる。<br>
 * &quot;field2&quot; については、 select 属性が true となっている為、
 * リクエストパラメタの&quot;startIndex&quot; ～ &quot;endIndex&quot;
 * で指定されたインデックス間のフィールド値のみが null
 * に初期化される。<br>
 * また、本クラスはネストしたプロパティのリセットに対応している。
 * アクションフォームのネストしたプロパティをリセットする場合は、
 * プロパティ名を「.」で連結した複合参照式を使用すること。
 * 以下に、ネストしたプロパティをリセットする場合の例を示す。<br><br>
 * <strong>ネストしたプロパティのリセット方法</strong><br>
 * <code><pre>
 * public class MyActionForm extends ValidatorActionFormEx {
 *     private List<Row> rows = null;
 *     public void setRows(List<Row> rows) {
 *         this.rows = rows;
 *     }
 *     public List<Row> getRows() {
 *         return this.rows;
 *     }
 *     private Map<String, String> map = new HashMap();
 *     //以下省略
 * }
 * public class Row {
 *     private String value = null;
 *     public void setValue(String value) {
 *         this.value = value;
 *     }
 *     public String getValue() {
 *         return this.value;
 *     }
 * }
 * </pre></code>
 * 上記のMyActionFormが持つRowクラスのリストの各valueプロパティ、
 * Map型のmapプロパティで「field」というキー名の値
 * をリセットする場合は以下のようにreset.xmlに記述する。
 * <code><pre>
 *  &lt;reset&gt;
 *    &lt;action path=&quot;/resetAction&quot;&gt;
 *      &lt;property-reset name=&quot;rows.value&quot; /&gt;
 *      &lt;property-reset name=&quot;map(field)&quot; /&gt;
 *    &lt;/action&gt;
 *  &lt;/reset&gt;
 * </pre></code>
 * 複合参照式はプロパティ名のみを記述する。要素が配列やListの場合は
 * フレームワークが自動的に認識し、全ての要素をリセットする。
 * 通常のプロパティ同様、select属性にtrueを指定することで、
 * 範囲指定リセットが可能である。
 *
 * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
 * @see jp.terasoluna.fw.web.struts.reset.ActionReset
 * @see jp.terasoluna.fw.web.struts.reset.FieldReset
 * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 *
 */
public class ResetterImpl implements Resetter {

    /**
     * ログクラス
     */
    private static Log log = LogFactory.getLog(ResetterImpl.class);

    /**
     * フォームのフィールド値のリセットを行う。
     *
     * @param form
     *            アクションフォーム
     * @param mapping
     *            マッピング情報
     * @param request
     *            リクエスト情報
     *
     * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
     * @see jp.terasoluna.fw.web.struts.reset.ActionReset
     * @see jp.terasoluna.fw.web.struts.reset.FieldReset
     * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
     * @see jp.terasoluna.fw.web.struts.form.FormEx
     * @see jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
     * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
     */
    public void reset(FormEx form, ActionMapping mapping,
            HttpServletRequest request) {
        // form,mappingがnullの場合はここまで到達しないので
        // NullPointer発生は考慮しない
        if (log.isDebugEnabled()) {
            log.debug(mapping.getName() + " reset() called.");
        }
        ActionReset reset = getActionReset(mapping, request);
        if (reset == null) {
            return;
        }
        Iterator<String> it = reset.getFieldNames();
        IndexedBeanWrapper wrapper = new JXPathIndexedBeanWrapperImpl(form);
        while (it.hasNext()) {

            // フィールド名を取得
            String fieldName = it.next();
            if (log.isDebugEnabled()) {
                log.debug("reset[" + fieldName + "]");
            }

            Map<String, Object> propMap =
                wrapper.getIndexedPropertyValues(fieldName);
            // select指定されている場合は指定範囲のみリセットする
            // 但し、フィールドが配列、Listでない場合は通常のリセットを実行する
            if (reset.isSelectField(fieldName) && propMap.size() > 1) {
                resetSelectField(form, propMap, request);
            } else {
                for (Entry<String, Object> e : propMap.entrySet()) {
                    resetValue(form, e);
                }
            }
        }
    }

    /**
     * アクションフォームの指定されたプロパティをリセットする。
     * プロパティの型がboolean型、Boolean型の場合falseを設定する。
     * その他のプリミティブ型およびそのラッパー型の場合は、0を設定する。
     * プロパティの型がラッパー型以外のObject型の場合はnullを設定する。<br>
     * 尚、引数のentryにはnullが渡されることはない。
     *
     * @param form 現在のリクエストで使用するアクションフォーム
     * @param entry リセット対象のプロパティ名と現在の値のエントリ
     * @throws PropertyAccessException プロパティの値設定に失敗した場合
     */
    protected void resetValue(FormEx form, Entry<String, Object> entry) {
        if (log.isDebugEnabled()) {
            log.debug("resetValue(" + form + ", " +
                    entry.getKey() + ") called.");
        }
        String propName = entry.getKey();
        try {
            Object value = entry.getValue();
            if (value == null) {
                return;
            }
            Class type = null;
            type = value.getClass();
            if (type != null) {
                // 型の種類で処理をわける。
                if (type == Boolean.TYPE || type == Boolean.class) {
                    BeanUtil.setBeanProperty(form, propName, Boolean.FALSE);
                } else if (type == Byte.TYPE || type == Byte.class) {
                    BeanUtil.setBeanProperty(
                            form, propName, new Byte((byte) 0));
                } else if (type == Character.TYPE || type == Character.class) {
                    BeanUtil.setBeanProperty(form, propName,
                            new Character((char) 0));
                } else if (type == Double.TYPE || type == Double.class) {
                    BeanUtil.setBeanProperty(form, propName,
                            new Double(0.0));
                } else if (type == Float.TYPE || type == Float.class) {
                    BeanUtil.setBeanProperty(form, propName,
                            new Float((float) 0.0));
                } else if (type == Integer.TYPE || type == Integer.class) {
                    BeanUtil.setBeanProperty(
                            form, propName, new Integer(0));
                } else if (type == Long.TYPE || type == Long.class) {
                    BeanUtil.setBeanProperty(form, propName, new Long(0));
                } else if (type == Short.TYPE || type == Short.class) {
                    BeanUtil.setBeanProperty(
                            form, propName, new Short((short) 0));
                } else {
                    // プリミティブ型、ラッパー型でない場合はnullを設定する
                    BeanUtil.setBeanProperty(form, propName, null);
                }
            }
        } catch (PropertyAccessException e) {
            log.error("cannot access property " + form + "." + propName, e);
        }
    }

    /**
     * ResetterResources のインスタンスを取得する。
     *
     * @param request リクエスト情報
     * @return ResetterResources インスタンス
     * @see jp.terasoluna.fw.web.struts.reset.ActionReset
     * @see jp.terasoluna.fw.web.struts.reset.FieldReset
     */
    protected ResetterResources getResetterResources(
            HttpServletRequest request) {

        // Prefixを取得
        String prefix = ModuleUtil.getPrefix(request);

        // ServletContextを取得
        ServletContext application = RequestUtil.getServletContext(request);

        // ResetterResourceを取得
        ResetterResources resources = (ResetterResources) application
                .getAttribute(ResetterResources.RESETTER_RESOURCES_KEY + prefix);

        return resources;
    }

    /**
     * アクションパスに紐づいた ActionReset のインスタンスを取得する。
     *
     * @param mapping 現在のリクエストのマッピング情報
     * @param request リクエスト情報
     * @return ActionReset のインスタンス
     * @see jp.terasoluna.fw.web.struts.reset.FieldReset
     * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
     */
    protected ActionReset getActionReset(ActionMapping mapping,
            HttpServletRequest request) {

        ResetterResources resources = getResetterResources(request);
        if (resources == null) {
            if (log.isDebugEnabled()) {
                log.debug("ResetterResources is null.");
            }
            return null;
        }

        ActionReset reset = resources.get(mapping.getPath());

        if (reset == null) {
            if (log.isDebugEnabled()) {
                log.debug("action path:" + mapping.getPath()
                        + " is not specified to reset.");
            }
        }
        return reset;

    }

    /**
     * 選択フィールドのリセットを行う。
     *
     * select 属性に true が設定されていたときは リクエストパラメタの
     * &quot;startIndex&quot; ～ &quot;endIndex&quot;
     * で指定されたインデックス間のフィールド値を null に初期化する。<br>
     * リクエストパラメタに &quot;startIndex&quot;, &quot;endIndex&quot;
     * が含まれていなければ全ての要素のリセットを行う。<br>
     * また、フィールド値の型が配列型、List 型で宣言されている場合は、
     * 通常のリセット処理を行う。
     *
     * @param form 現在のリクエストで使用するアクションフォーム
     * @param propMap プロパティ名と現在の値のMap
     * @param request リクエスト情報
     *
     * @see jp.terasoluna.fw.web.struts.reset.ActionReset
     * @see jp.terasoluna.fw.web.struts.reset.FieldReset
     * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
     */
    protected void resetSelectField(FormEx form,
                                    Map<String, Object> propMap,
                                    HttpServletRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("resetSelectField() called.");
        }
        int startIndex = 0;
        int size = propMap.size();
        int endIndex = size;
        try {
            String startIndexStr = request.getParameter("startIndex");
            String endIndexStr = request.getParameter("endIndex");
            if (startIndexStr != null) {
                startIndex = Integer.parseInt(startIndexStr);
            }
            if (endIndexStr != null) {
                endIndex = Integer.parseInt(endIndexStr);
            }
            if (log.isDebugEnabled()) {
                log.debug("startIndex = [" + startIndex + "]");
                log.debug("endIndex = [" + endIndex + "]");
            }
        } catch (NumberFormatException e) {
            log.error("startIndex or endIndex is not Number.", e);
            return;
        }

        // リセット処理を行う。
        Set<Map.Entry<String, Object>> set = propMap.entrySet();
        int index = 0;
        for (Entry<String, Object> e : set) {
            if (index >= startIndex &&
                    startIndex < size && index <= endIndex && index < size) {
                resetValue(form, e);
            }
            index++;
        }
    }
}
