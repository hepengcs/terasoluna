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

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.MethodUtils;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.PropertyAccessException;
import jp.terasoluna.fw.util.ClassUtil;
import jp.terasoluna.fw.util.BeanUtil;
import jp.terasoluna.fw.util.StringUtil;

/**
 * ビジネスロジック入出力情報反映抽象クラス。
 *
 * <p>
 *  ビジネスロジック入出力情報を保持したBLogicResourcesをもとに、
 *  Web層のオブジェクトと、ビジネスロジック間のデータのマッピングを行う機能を
 *  集約した抽象クラスである。全てのビジネスロジック入出力情報反映クラスは
 *  このクラスを継承して実装する。
 *  AbstractBLogicMapperの主な役割は、以下の２つ
 *  <ul>
 *   <li>BLogicResourcesの設定から、アクションごとの
 *    ビジネスロジックの入力情報クラスとなるJavaBean
 *    を生成する機能を提供する</li>
 *   <li>ビジネスロジックの出力情報クラスである
 *    BLogicResultを用い、BLogicResourcesの設定から、
 *    Web層のオブジェクトに反映する機能を提供する</li>
 *  </ul>
 * </p>
 * <p>
 *  AbstractBLogicMapperのサブクラスとして、
 *  デフォルトではBLogicMapperを提供しているが、
 *  この機能を置き換えることもできる。
 *  その際、AbstractBLogicMapperまたはBLogicMapperを
 *  継承したビジネスロジック入出力情報反映クラスを作成する必要がある。
 *  拡張したビジネスロジック入出力情報反映クラスでは、
 *  blogic-io.xmlのsource属性にrequest、session、application、
 *  dest属性にrequest、session以外の任意の文字列を
 *  指定した場合の入力値取得処理、出力値反映処理を実装する。<br>
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
 *  尚、デフォルトのBLogicMapperがサポートするrequest、session、applicationは
 *  それぞれ、リクエスト属性、セッション属性、サーブレットコンテキスト属性
 *  を対象にしている。
 * </p>
 * <p>
 *  Strutsを使用した場合のビジネスロジック入出力情報反映クラスの入れ替え・
 *  struts-config.xmlの記述方法については、BLogicIOPlugInを参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 */
public abstract class AbstractBLogicMapper {


    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(AbstractBLogicMapper.class);

    /**
     * BLogicResultがnullだった場合のエラーコード。
     */
    private static final String NULL_RESULT_KEY =
        "errors.blogic.mapper.result";

    /**
     * 入力値の取得元の指定に誤りがあった場合のエラーコード。
     */
    private static final String ERROR_SOURCE =
        "errors.blogic.mapper.source";

    /**
     * 出力値の設定先の指定に誤りがあった場合のエラーコード。
     */
    private static final String ERROR_DEST =
        "errors.blogic.mapper.dest";

    /**
     * ビジネスロジックに入力するJavaBeanの生成に失敗した場合のエラーコード。
     */
    private static final String ERROR_BEAN_CREATE =
        "errors.blogic.mapper.create";

    /**
     * プロパティ値をJavaBeanから取得できなかった場合のエラーコード。
     */
    private static final String ERROR_GETPROPERTY =
        "errors.blogic.mapper.getproperty";

    /**
     * プロパティ値をJavaBeanに設定できなかった場合のエラーコード。
     */
    private static final String ERROR_SETPROPERTY =
        "errors.blogic.mapper.setproperty";

    /**
     * 値を入力元のインスタンスから取得できなかった場合のエラーコード。
     */
    private static final String ERROR_GETVALUE =
        "errors.blogic.mapper.getvalue";

    /**
     * 値を出力先のインスタンスに反映できなかった場合のエラーコード。
     */
    private static final String ERROR_SETVALUE =
        "errors.blogic.mapper.setvalue";

    /**
     * 結果反映時にioがnullなのにresult.getResultObject()で取得した値がnullでなかった
     * 場合のエラーコード。
     */
    private static final String ERROR_BEAN_NOTNULL =
        "errors.blogic.mapper.notnull";

    /**
     * Web層のオブジェクトに格納された値をJavaBeanにマッピングする。
     *
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param io ビジネスロジック入出力情報
     * @return Web層に格納された必要なパラメータを全て格納したJavaBean、ioに
     * クラスが指定されていない場合はnullを返却する。
     */
    public Object mapBLogicParams(HttpServletRequest request,
            HttpServletResponse response, BLogicIO io) {
        if (io == null) {
            // ioがnullの場合、nullを返却する
            return null;
        }

        if (io.getInputBeanName() == null) {
            // 入力Beanが指定されていない場合はnullを返却する。
            if (log.isDebugEnabled()) {
                log.debug("blogic-io.inputBeanName is null.");
            }
            return null;
        }

        // イテレーターの生成
        Iterator it = io.getBLogicParams().iterator();

        // 戻り値で返すJavaBeanの生成
        Object bean = null;
        // blogic-ioにblogic-paramsが設定されていた場合のみインスタンスを作成
        if (it.hasNext()) {
            try {
                bean = ClassUtil.create(io.getInputBeanName());
            } catch (ClassLoadException e) {
                log.error("bean creation failure.");
                throw new SystemException(e, ERROR_BEAN_CREATE);
            }
        } else {
            return null;
        }

        // List内の全てのBLogicPropertyインスタンスについて、処理を行う
        while (it.hasNext()) {

            // BLogicPropertyインスタンスを取得
            BLogicProperty property = (BLogicProperty) it.next();
            // プロパティ名を取得
            String propertyName = property.getProperty();

            // ビジネスロジック内で使用されるプロパティ名を取得
            String blogicPropertyName = property.getBLogicProperty();

            // blogicPropertyNameが未設定の場合、propertyNameを代入する
            if (blogicPropertyName == null) {
                blogicPropertyName = propertyName;
            }

            // 入力値格納元の判定を行い、JavaBeanに格納する
            if (!"".equals(property.getSource())
                    && property.getSource() != null) {
                // 起動する入力値設定メソッドに対する引数の配列
                Object[] methodParams = new Object[] { propertyName, request,
                        response };
                Class[] parameterTypes = new Class[] { String.class,
                        HttpServletRequest.class, HttpServletResponse.class };
                Object value = null;
                try {
                    // 入力値取得処理を実行
                    value = MethodUtils.invokeMethod(this, "getValueFrom"
                            + StringUtil
                                    .capitalizeInitial(property.getSource()),
                            methodParams, parameterTypes);
                } catch (NoSuchMethodException e) {
                    log.error("no such method.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_GETVALUE, new String[] { propertyName });
                } catch (IllegalAccessException e) {
                    log.error("illegal access to the method.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_GETVALUE, new String[] { propertyName });
                } catch (InvocationTargetException e) {
                    log.error("exception is thrown out by invokeMethod.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_GETVALUE, new String[] { propertyName });
                }

                try {
                    // 入力値設定メソッドを実行
                    BeanUtil.setBeanProperty(bean, blogicPropertyName, value);
                } catch (PropertyAccessException e) {
                    log.error("setBeanProperty failure.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_SETPROPERTY,
                            new String[] { blogicPropertyName });
                }

            } else {
                // 格納元の指定が間違っているため例外をスロー
                log.error("source is illegal.");
                throw new SystemException(new BLogicMapperException(),
                        ERROR_SOURCE);
            }
        }
        return bean;
    }

    /**
     * リクエストから指定されたプロパティ値を取得する。
     *
     * @param propName プロパティ名
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @return プロパティ値
     */
    public abstract Object getValueFromRequest(String propName,
            HttpServletRequest request, HttpServletResponse response);

    /**
     * セッションから指定のプロパティ名をキーに値を取得する。
     *
     * @param propName プロパティ名
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @return プロパティ値
     */
    public abstract Object getValueFromSession(String propName,
            HttpServletRequest request, HttpServletResponse response);

    /**
     * BLogicIOに従い、Web層のオブジェクトに値を格納する。
     *
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param io ビジネスロジック入出力情報
     * @param result ビジネスロジックの出力情報
     */
    public void mapBLogicResult(HttpServletRequest request,
                                          HttpServletResponse response,
                                          BLogicIO io,
                                          BLogicResult result) {
        // BLogicResultのnullチェック
        if (result == null) {
            log.error("BLogicResult is null.");
            throw new SystemException(new BLogicMapperException(
                    new NullPointerException()), NULL_RESULT_KEY);
        }

        // resultからJavaBeanを取得
        Object bean = result.getResultObject();

        // ioが未設定なのに、beanが格納されていた場合のチェック
        if (io == null) {
            if (bean != null) {
                if (log.isDebugEnabled()) {
                    log.debug("The bean should be null.");
                }
                log.error("bean is not null.");
                throw new SystemException(new BLogicMapperException(),
                        ERROR_BEAN_NOTNULL);
            }
            // 以降の処理は行なわない
            return;
        }

        // イテレーターの生成
        Iterator it = io.getBLogicResults().iterator();

        // blogic-resultが未設定なのに、beanが格納されていた場合のチェック
        if (!it.hasNext()) {
            if (bean != null) {
                if (log.isDebugEnabled()) {
                    log.debug("The bean should be null.");
                }
                log.error("bean is not null.");
                throw new SystemException(new BLogicMapperException(),
                        ERROR_BEAN_NOTNULL);
            }
            // 以降の処理は行なわない
            return;
        }

        // List内の全てのBLogicPropertyクラスインスタンスについて、処理を行う
        while (it.hasNext()) {

            // BLogicPropertyインスタンスを取得
            BLogicProperty property = (BLogicProperty) it.next();

            // プロパティ名を取得
            String propertyName = property.getProperty();

            // ビジネスロジック内で使用されるプロパティ名を取得
            String blogicPropertyName = property.getBLogicProperty();

            // blogicPropertyNameが未設定の場合、propertyNameを代入する
            if (blogicPropertyName == null) {
                blogicPropertyName = propertyName;
            }

            if (!"".equals(property.getDest()) && property.getDest() != null) {

                Object value = null;
                try {
                    // 出力値取得メソッドを実行
                    value = BeanUtil.getBeanProperty(bean, blogicPropertyName);
                } catch (PropertyAccessException e) {
                    log.error("getBeanProperty failure.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_GETPROPERTY,
                            new String[] { blogicPropertyName });
                }

                // 格納先の判定を行い、出力値を格納する
                Object[] methodParams = new Object[] { value, propertyName,
                       request, response };
                Class[] parameterTypes = new Class[] { Object.class,
                        String.class, HttpServletRequest.class,
                        HttpServletResponse.class };
                try {
                    MethodUtils.invokeMethod(this, "setValueTo"
                            + StringUtil
                                    .capitalizeInitial(property.getSource()),
                            methodParams, parameterTypes);
                } catch (NoSuchMethodException e) {
                    log.error("no such method.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_SETVALUE, new String[] { propertyName });
                } catch (IllegalAccessException e) {
                    log.error("illegal access to the method.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_SETVALUE, new String[] { propertyName });
                } catch (InvocationTargetException e) {
                    log.error("exception is thrown out by invokeMethod.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_SETVALUE, new String[] { propertyName });
                }
            } else {
                // 格納先の指定が間違ってるため、例外をスロー
                log.error("dest is illegal.");
                throw new SystemException(new BLogicMapperException(),
                        ERROR_DEST);
            }
        }
    }

    /**
     * リクエストの指定されたプロパティに値を格納する。
     *
     * @param value 出力値
     * @param propName プロパティ名
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     */
    public abstract void setValueToRequest(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response);

    /**
     * セッションに指定のプロパティ名をキーに値を格納する。
     *
     * @param value 出力値
     * @param propName プロパティ名
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     */
    public abstract void setValueToSession(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response);

    /**
     * サーブレットコンテキストから指定されたプロパティ値を取得する。
     *
     * @param propName プロパティ名
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @return プロパティ値
     */
    public abstract Object getValueFromApplication(String propName,
            HttpServletRequest request, HttpServletResponse response);

}
