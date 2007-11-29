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

package jp.terasoluna.fw.service.thin;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.web.struts.form.FormEx;
import jp.terasoluna.fw.util.BeanUtil;
import jp.terasoluna.fw.util.PropertyAccessException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * ビジネスロジック入出力情報反映クラス。
 *
 * <p>
 *  BLogicIOPlugInによって生成されたBLogicResourcesをもとに、
 *  Web層のオブジェクトと、ビジネスロジック間のデータのマッピングを行う。<br>
 *  Web層からの入力はリクエスト属性(request)、セッション属性(session)、
 *  サーブレットコンテキスト(application)が対象となる。<br>
 *  ビジネスロジックからの出力はリクエスト属性(request)、セッション属性(session)
 *  が対象となる。※ビジネスロジックからサーブレットコンテキストへの出力は
 *  サポートしていない。
 * </p>
 * <p>
 *  AbstractBLogicMapperのサブクラスとして、
 *  デフォルトでこのクラスを提供しているが、
 *  struts-config.xmlのBLogicIOPlugInの設定によって、
 *  この機能を置き換えることもできる。
 *  その際、AbstractBLogicMapperまたはBLogicMapperを
 *  継承したビジネスロジック入出力情報反映クラスを作成する必要がある。
 *  拡張したビジネスロジック入出力情報反映クラスでは、
 *  blogic-io.xmlのsource属性にrequest、session、application、
 *  dest属性にrequest、session以外の
 *  任意の文字列を指定した場合の入力値取得処理、出力値反映処理を実装する。<br>
 *  入力値取得処理のメソッド名は、
 *  "getValueFrom" + source属性に指定する文字列とする。
 *  source属性に"factory"と指定する場合、メソッド名はgetValueFromFactoryとなる。
 *  引数は全ての入力値取得メソッドで共通で、
 *  getValueFromForm()メソッドと同じ引数をとる。<br>
 *  出力値反映処理のメソッド名は、
 *  "setValueTo" + dest属性に指定する文字列とする。
 *  dest属性に"factory"と指定する場合、メソッド名はgetValueToFactoryとなる。
 *  引数は全ての出力値反映メソッドで共通で、
 *  getValueToForm()メソッドと同じ引数をとる。<br>
 *  尚、値の取得先、元がActionFormの場合はネストしたプロパティ名を指定
 *  することが可能である。
 * </p>
 * <p>
 *  ビジネスロジック入出力情報反映クラスの入れ替え・
 *  struts-config.xmlの記述方法については、BLogicIOPlugInを参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 *
 */
public class BLogicMapper extends AbstractBLogicMapper {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(BLogicMapper.class);

    /**
     * リソースファイルがなかった場合のエラーコード。
     */
    private static final String ERROR_RESOURCES_FILE = "errors.resources.file";

    /**
     * コンストラクタ。
     */
    public BLogicMapper(){
        
    }
    
    /**
     * コンストラクタ。
     * @param resources リソースのパス
     */
    public BLogicMapper(String resources) {
        if (resources == null || "".equals(resources)) {
            log.error("resources file location is not specified");
            throw new IllegalArgumentException(ERROR_RESOURCES_FILE);
        }
    }
    
    /**
     * リクエストから指定されたプロパティ値を取得する。
     *
     * @param propName プロパティ名
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @return プロパティ値
     */
    @Override
    public Object getValueFromRequest(String propName,
            HttpServletRequest request, HttpServletResponse response) {

        if (propName == null || "".equals(propName)) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw new IllegalArgumentException();
        }

        // リクエストから値を取得する
        Object value = request.getAttribute(propName);

        if (log.isDebugEnabled()) {
            if (request.getAttribute(propName) == null) {
                log.debug("Request's attribute is null:key =[" + propName
                            + "]");
            }
        }

        return value;
    }

    /**
     * フォームから指定されたプロパティ値を取得する。
     *
     * @param propName プロパティ名
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @return プロパティ値
     */
    public Object getValueFromForm(String propName, HttpServletRequest request,
            @SuppressWarnings("unused") HttpServletResponse response) throws PropertyAccessException {
        // フォームを取得する
        ActionForm form = getActionForm(request);

        // Formから値を取得する
        Object value = null;
        try {
            value = BeanUtil.getBeanProperty(form, propName);
        } catch (PropertyAccessException e) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw e;
        }
        return value;
    }

    /**
     * セッションから指定のプロパティ名をキーに値を取得する。
     *
     * @param propName プロパティ名
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @return プロパティ値
     */
    @Override
    public Object getValueFromSession(String propName,
            HttpServletRequest request, HttpServletResponse response) {

        if (propName == null || "".equals(propName)) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw new IllegalArgumentException();
        }

        // セッションの取得
        HttpSession session = request.getSession(true);
        // セッションから値を取得する
        Object value = session.getAttribute(propName);

        if (log.isDebugEnabled()) {
            if (session.getAttribute(propName) == null) {
                log.debug("Session's attribute is null:key =[" + propName
                            + "]");
            }
        }
        return value;
    }

    /**
     * リクエストの指定されたプロパティに値を格納する。
     *
     * @param value 出力値
     * @param propName プロパティ名
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setValueToRequest(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {

        if (propName == null || "".equals(propName)) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw new IllegalArgumentException();
        }

        if (log.isDebugEnabled()) {
            Enumeration<String> enumeration = request.getAttributeNames();
            int existFlag = 0;
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                if (key.equals(propName)) {
                    existFlag = 1;
                    break;
                }
            }
            if (existFlag != 1) {
                log.debug("Request's key does not exist:key =[" + propName
                        + "]");
            }
        }
        // リクエストに値を格納する
        request.setAttribute(propName, value);
    }

    /**
     * フォームの指定されたプロパティに値を格納する。<br>
     * フォームがFormExのインスタンスであれば、modifiedフラグをtrueに
     * 設定する。
     *
     * @param value 出力値
     * @param propName プロパティ名
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     */
    public void setValueToForm(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response)
                throws PropertyAccessException {
        // フォームを取得する
        ActionForm form = getActionForm(request);

        // フォームに値を格納する
        try {
            BeanUtil.setBeanProperty(form, propName, value);
            if (form instanceof FormEx) {
                FormEx formEx = (FormEx) form;
                formEx.setModified(true);
            }
        } catch (PropertyAccessException e) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw e;
        }
    }

    /**
     * セッションに指定のプロパティ名をキーに値を格納する。
     *
     * @param value 出力値
     * @param propName プロパティ名
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setValueToSession(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {

        if (propName == null || "".equals(propName)) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw new IllegalArgumentException();
        }

        // セッションの取得
        HttpSession session = request.getSession(true);

        if (log.isDebugEnabled()) {
            Enumeration<String> enumeration = session.getAttributeNames();
            int existFlag = 0;
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                if (key.equals(propName)) {
                    existFlag = 1;
                    break;
                }
            }
            if (existFlag != 1) {
                log.debug("Session's key does not exist:key =[" + propName
                        + "]");
            }
        }
        // セッションに値を格納する
        session.setAttribute(propName, value);
    }

    /**
     * リクエストまたはセッションに格納されているActionForm
     * インスタンスを取得する。
     *
     * @param request HTTPリクエスト
     * @return ActionFormインスタンス
     */
    protected ActionForm getActionForm(HttpServletRequest request) {

        ActionMapping mapping = (ActionMapping) request
                .getAttribute(Globals.MAPPING_KEY);
        ActionForm form = null;
        if ("request".equals(mapping.getScope())) {
            form = (ActionForm) request.getAttribute(mapping.getAttribute());
        } else {
            HttpSession session = request.getSession();
            form = (ActionForm) session.getAttribute(mapping.getAttribute());
        }
        // ActionFormを返却する
        return form;
    }

    /**
     * サーブレットコンテキストから指定のプロパティ名をキーに値を取得する。
     *
     * @param propName プロパティ名
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @return プロパティ値
     */
    @Override
    public Object getValueFromApplication(String propName,
            HttpServletRequest request, HttpServletResponse response) {

        if (propName == null || "".equals(propName)) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw new IllegalArgumentException();
        }

        // サーブレットコンテキストの取得
        HttpSession session = request.getSession(true);
        ServletContext context = session.getServletContext();
        // セッションから値を取得する
        Object value = context.getAttribute(propName);

        if (log.isDebugEnabled()) {
            if (value == null) {
                log.debug("ServletContext's attribute is null:key =[" + propName
                            + "]");
            }
        }
        return value;
    }
}
