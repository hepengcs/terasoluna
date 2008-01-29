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

package jp.terasoluna.fw.web.rich.springmvc.servlet.handler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
/**
 * 例外クラス名とビュー名のマッピングを行うException resolverの拡張クラス。
 * 
 * <p>
 * Springが提供する SimpleMappingExceptionResolverの
 * 属性exceptionMappingsは、設定項目の順序性の保持が出来ない。<br>
 * 本クラスは、SimpleMappingExceptionResolverを拡張し、
 * 代わりに順序性の保持が可能な属性linkedExceptionMappingsを提供するものである。
 * </p>
 * 
 * <p>
 * リクエストコントローラで発生した例外をハンドリングし、
 * Bean定義ファイルに従いスローされた例外型に対応するViewインスタンスと
 * Modelインスタンスを返却する。<br>
 * Modelインスタンスに情報を格納する処理は、{@link ExceptionResolveDelegator}実装クラスに委譲する。<br>
 * Viewインスタンスで、Modelインスタンスに格納された情報を利用して
 * エラーレスポンスのレンダリングを行うことを想定している。
 * </p>
 * 
 * <p>
 * レスポンスに例外が発生したことを通知するヘッダを設定する。<br>
 * ヘッダに情報を設定する処理は、{@link ExceptionResolveDelegator}実装クラスに委譲する。
 * </p>
 * 
 * <p>
 * 本クラスを利用する場合、本クラスのBean定義を行うこと。
 * また、以下のプロパティを必ず設定すること。
 * </p>
 * 
 * <p>
 *   <table border="1" CELLPADDING="8">
 *     <th>属性名</th>
 *     <th>必須</th>
 *     <th>説明</th>
 * 
 *     <tr>
 *       <td align=center><b>linkedExceptionMappings</b></td>
 *       <td>○</td>
 *       <td>
 *           例外クラス名とView名のマッピング（map形式）。<br>
 *           entryのキーは、例外クラス名を設定する。<br>
 *           entryの値は、使用する{@link ExceptionResolveDelegator}実装クラスのjavadocを参考に設定すること。
 *       </td>
 *     </tr>
 *     <tr>
 *       <td align=center><b>exceptionResolveDelegatorClass</b></td>
 *       <td>○</td>
 *       <td>
 *           ExceptionResolveDelegator実装クラス
 *       </td>
 *     </tr>
 *     <tr>
 *       <td align=center><b>exceptionResolveDelegatorParams</b></td>
 *       <td>&nbsp;</td>
 *       <td>
 *           ExceptionResolveDelegator実装クラスが利用する情報のマッピング。<br>
 *           entryのキーは、情報名。<br>
 *           entryの値は、情報値。
 *       </td>
 *     </tr>
 *     <tr>
 *       <td align=center><b>outputErrorLogHandledException</b></td>
 *       <td>&nbsp;</td>
 *       <td>
 *           ハンドリングした例外の情報をエラーログ出力するかをあらわすboolean値。<br>
 *           trueの場合、エラーログ出力を行う。<br>
 *           デフォルトはtrueである。<br>
 *           プロジェクトごとのログ監視の要件などで
 *           ハンドリングした例外の情報をエラーログ出力したくない場合のみ設定すること。
 *           通常は本設定を利用する必要はない。
 *       </td>
 *     </tr>
 *  </table>
 * 
 * </p>
 * ExceptionResolveDelegator実装クラスとして、ExceptionResolveDelegatorImplを使用する場合の設定例を以下に記す。
 * 【<code>Bean定義ファイル</code>の設定例】<br>
 * <code><pre>
 *   &lt;bean id="handlerExceptionResolver"
 *       class="jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx"&gt;
 *     &lt;property name="linkedExceptionMappings"&gt;
 *       &lt;map&gt;
 *         &lt;entry key="jp.terasoluna.fw.web.rich.exception.UnknownRequestNameException"&gt;
 *           &lt;value&gt;exception,kind01,8004C003&lt;/value&gt;
 *         &lt;/entry&gt;
 *         &lt;entry key="org.springframework.validation.BindException"&gt;
 *           &lt;value&gt;bindException,kind02&lt;/value&gt;
 *         &lt;/entry&gt;
 *         &lt;entry key="jp.terasoluna.fw.service.rich.exception.SystemException"&gt;
 *           &lt;value&gt;systemException,kind03&lt;/value&gt;
 *         &lt;/entry&gt;
 *         &lt;entry key="jp.terasoluna.fw.service.rich.exception.ServiceException"&gt;
 *           &lt;value&gt;serviceException,kind04&lt;/value&gt;
 *         &lt;/entry&gt;
 *         &lt;entry key="java.lang.Exception"&gt;
 *           &lt;value&gt;exception,kind05,8004C999&lt;/value&gt;
 *         &lt;/entry&gt;
 *       &lt;/map&gt;
 *     &lt;/property&gt;
 *     &lt;property name="exceptionResolveDelegatorClass" value="jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl" /&gt;
 *     &lt;property name="exceptionResolveDelegatorParams"&gt;
 *       &lt;map&gt;
 *         &lt;entry key="errorTypeHeaderName"&gt;
 *           &lt;value&gt;errorType&lt;/value&gt;
 *         &lt;/entry&gt;
 *       &lt;/map&gt;
 *     &lt;/property&gt; 
 *   &lt;/bean&gt;
 * </pre></code>
 * 
 * <p>
 * たとえば、上記の設定を行った上で、
 * UnknownRequestNameExceptionがスローされてきた場合、
 * 「exception」という名称のビューと、
 * スローされた例外インスタンスと、エラーコード文字列「8004C003」を
 * 格納したモデルを返却する。<br>
 * レスポンスヘッダには、「errorType」をキーとして「kind01」を設定する。
 * </p>
 * 
 * <p>
 * SimpleMappingExceptionResolverExは、スローされた例外の型と
 * linkedExceptionMappingsのキーを設定ファイルに記述された順に比較する。
 * 設定ファイルに記述された例外型が、スローされた例外型の
 * 同一の型、または親の型だった場合に、対応する値の名称のViewを生成し返却する。
 * 
 * スローされた型と一致する設定が複数あった場合は、
 * より先に記述した設定が採用される。
 * 最後に全ての例外の親クラスとなるjava.lang.Exceptionの設定を行うことで、
 *　予期せぬ例外が発生した場合でも必ず、java.lang.Exceptionの設定で
 * エラーレスポンスのレンダリングが出来る。
 * </p>
 * 
 * <p>
 * ここで処理可能な例外は、リクエストコントローラで発生した例外のみである。
 * ViewやFilter等、DispacherServlet外で発生した例外のハンドリングは、
 * 本クラスは責務を持たない。
 * それらの例外をハンドリングするためには、Servletが提供している
 * エラーページの機能を利用すること。
 * 例外の型ごとに任意の固定電文をレスポンスにレンダリングすることが出来る。
 * </p>
 * 
 * <p>
 * 【<code>web.xml</code>の設定例】<br>
 * <code><pre>
 *   &lt;error-page&gt;
 *     &lt;exception-type&gt;java.lang.Exception&lt;/exception-type&gt;
 *     &lt;location&gt;/error/unknown-error.jsp&lt;/location&gt;
 *   &lt;/error-page&gt;
 * 　　※ あらかじめ固定のエラー電文を記述した/error/unknown-error.jspを用意しておくこと。
 * </pre></code>
 * </p>
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegator
 * 
 *
 */
public class SimpleMappingExceptionResolverEx extends SimpleMappingExceptionResolver implements InitializingBean {
    /**
     * ログクラス。
     */
    private final Log log = LogFactory.getLog(getClass());
    
    /**
     * 順序性を保持した例外とView名（＆エラー情報）のマッピング。
     */
    protected Map<String, Object> linkedExceptionMappings = null;

    /**
     * 本クラスが処理するべきハンドラのセット。
     * <p>処理すべきコントローラインスタンスを設定する。
     * {@link #resolveException(HttpServletRequest, HttpServletResponse, Object, Exception)}
     * メソッド内で使用される。コントローラオブジェクトの比較を行うため、
     * コントローラをシングルトン設定にしなければならない。</p>
     */
    protected Set mappedHandlers = null;

    /**
     * 例外が発生した場合にレスポンスに設定するエラーコード。
     */
    protected Integer defaultStatusCode = null;
    
    /**
     * スローされた例外に対応する設定が無かった場合に実行するViewの名称。
     * linkedExceptionMappingsで、java.lang.Exceptionの定義をしていた場合は不要。
     */
    protected String defaultErrorView = null;

    /**
     * ExceptionResolveDelegator実装クラスの型
     */
    protected Class< ? extends ExceptionResolveDelegator> exceptionResolveDelegatorClass = jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl.class;
    
    /**
     * 例外の型をキーにExceptionResolveDelegatorを格納するMap
     */
    protected LinkedHashMap<String, ExceptionResolveDelegator> exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
    
    /**
     * ExceptionResolveDelegatorのパラメーターのキーと値を格納するMap
     */
    protected Map<String, String> exceptionResolveDelegatorParams = null;
    
    /**
     * ハンドリングした例外の情報をエラーログ出力するか
     */
    protected boolean outputErrorLogHandledException = true;
    
    /**
     * 本クラスがインスタンス化された直後に呼ばれるメソッド。 
     * ExceptionResolveDelegatorを生成し、属性に格納する。
     */
    public void afterPropertiesSet() {
        if (this.linkedExceptionMappings == null) {
            return;
        }

        // exceptionResolveDelegatorClass属性のnullチェック
        if (this.exceptionResolveDelegatorClass == null) {
            String message = "SimpleMappingExceptionResolverEx must be set exceptionResolveDelegatorClass. "
                    + "Check Spring Bean definition file.";
            log.error(message);
            throw new IllegalStateException(message);
        }

        for (String mappingKey : this.linkedExceptionMappings.keySet()) {
            ExceptionResolveDelegator exceptionResolveDelegator = null;
            try {
                // exceptionResolveDelegatorClass属性のインスタンスが、ExceptionResolveDelegator型であることのチェック
                if (!(ExceptionResolveDelegator.class
                        .isAssignableFrom(exceptionResolveDelegatorClass))) {
                    String message = exceptionResolveDelegatorClass.getName()
                            + " is not ExceptionResolveDelegator type. "
                            + "Check Spring Bean definition file.";
                    log.error(message);
                    throw new IllegalStateException(message);
                }
                exceptionResolveDelegator = exceptionResolveDelegatorClass
                        .newInstance();
            } catch (InstantiationException e) {
                // exceptionResolveDelegatorClass属性のインスタンス化に失敗した場合、例外をスローする
                String message = exceptionResolveDelegatorClass.getName()
                        + " cannot be instantiated. "
                        + "Check Spring Bean definition file.";
                log.error(message, e);
                throw new IllegalStateException(message, e);
            } catch (IllegalAccessException e) {
                // exceptionResolveDelegatorClass属性のインスタンス化に失敗した場合、例外をスローする
                String message = exceptionResolveDelegatorClass.getName()
                        + " cannot be instantiated. "
                        + "Check Spring Bean definition file.";
                log.error(message, e);
                throw new IllegalStateException(message, e);
            }
            // ExceptionResolveDelegatorに、エラーの型とエラー情報をマッピングする
            exceptionResolveDelegator.initMapping(mappingKey,
                    this.linkedExceptionMappings.get(mappingKey),
                    this.exceptionResolveDelegatorParams);
            exceptionResolveDelegatorMap.put(mappingKey,
                    exceptionResolveDelegator);
        }
    }   

    /**
     * スローされた例外に対応する設定が無かった場合に実行Viewの名称を設定する。
     * 親クラスで保持されているが、本クラスで参照できないため、
     * 本クラスでも属性として管理する。
     * 
     * @param defaultErrorView スローされた例外に対応する設定が無かった場合に実行Viewの名称
     */
    @Override
    public void setDefaultErrorView(String defaultErrorView) {
        super.setDefaultErrorView(defaultErrorView);
        this.defaultErrorView = defaultErrorView;
        if (logger.isInfoEnabled()) {
            logger.info("Default error view is '"
                            + this.defaultErrorView + "'");
        }
    }

    /**
     * 例外とビュー名（＆エラー情報）のマッピングを保持するクラスを設定する
     * @param exceptionResolveDelegatorClass 例外とビュー名（＆エラー情報）のマッピングを保持するクラス
     */
    public void setExceptionResolveDelegatorClass(
            Class< ? extends ExceptionResolveDelegator> exceptionResolveDelegatorClass) {
        this.exceptionResolveDelegatorClass = exceptionResolveDelegatorClass;
    }
     
    /**　
     * ExceptionResolveDelegatorのパラメーターのキーと値を格納するMapを設定する
     * @param exceptionResolveDelegatorParams ExceptionResolveDelegatorのパラメーターのキーと値を格納するMap
     */
    public void setExceptionResolveDelegatorParams(
            Map<String, String> exceptionResolveDelegatorParams) {
        this.exceptionResolveDelegatorParams = exceptionResolveDelegatorParams;
    }
    
    /**
     * mappedHandlersを設定する。
     * 親クラスで保持されているが、本クラスで参照できないため、
     * 本クラスでも属性として管理する。
     * @param mappedHandlers mappedHandlers
     */
    @Override
    public void setMappedHandlers(Set mappedHandlers) {
        super.setMappedHandlers(mappedHandlers);
        this.mappedHandlers = mappedHandlers;
    }

    /**
     * 例外が発生した場合にレスポンスに設定するエラーコードを設定する。
     * 
     * @param defaultStatusCode HTTPステータスコード値
     */
    @Override
    public void setDefaultStatusCode(int defaultStatusCode) {
        super.setDefaultStatusCode(defaultStatusCode);
        this.defaultStatusCode = Integer.valueOf(defaultStatusCode);
    }
    
    /**
     * 順序性を保持できない例外とView名（＆エラー情報）のマッピングを設定する。
     * 代わりに順序性を保持する機能を提供しているので、
     * 必ずUnsupportedOperationExceptionをスローする。
     * @deprecated 代わりにlinkedExceptionMappingsを利用すること。
     * @param mappings 例外とView名のマッピング
     */
    @Deprecated
    @Override
    public void setExceptionMappings(Properties mappings) {
        throw new UnsupportedOperationException();
    }

    
    /**
     * 順序性を保持する順序性を保持した例外とView名(&エラー情報)の
     * マッピングを設定する。
     * @param linkedExceptionMappings 順序性を保持した例外とView名（＆エラー情報）のマッピング
     */
    public void setLinkedExceptionMappings(
            Map<String, Object> linkedExceptionMappings) {
        this.linkedExceptionMappings = linkedExceptionMappings;
    }
    
    /**
     * ハンドリングした例外の情報をエラーログ出力するかのboolean値を設定する。
     * 
     * <p>
     * デフォルトはtrueなのでプロジェクトごとのログ監視の要件などで
     * ハンドリングした例外の情報をエラーログ出力したくない場合のみ本メソッドを利用すること。
     * 通常は本メソッドを利用する必要はない。
     * </p>
     * 
     * @param outputErrorLogHandledException falseならば出力しない。
     */
    public void setOutputErrorLogHandledException(boolean outputErrorLogHandledException) {
        this.outputErrorLogHandledException = outputErrorLogHandledException;
    }

    /**
     * スローされた例外に対応するViewとModelを返却する。
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param handler ハンドラ
     * @param ex スローされた例外
     * @return モデル(例外とエラー情報(任意)を格納)とビュー
     */
    @Override
    public ModelAndView resolveException (
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

        // 本クラスが処理すべきハンドラか
        if (this.mappedHandlers != null
                && !this.mappedHandlers.contains(handler)) {
            return null;
        }

        // スローされた例外の型と定義されている例外の型をチェック
        ExceptionResolveDelegator exceptionResolveDelegator = null;
        for (String mappingKey : exceptionResolveDelegatorMap.keySet()) {
            int depth = getDepth(mappingKey, ex);
            if (depth >= 0) {
                exceptionResolveDelegator = exceptionResolveDelegatorMap
                        .get(mappingKey);
                break;
            }
        }

        // 定義されていない例外がスローされてきた場合の処理
        if (exceptionResolveDelegator == null) {
            return null;
        }

        String viewName = exceptionResolveDelegator.getViewName();
        // ビュー名が設定されていない場合の処理
        if (viewName == null && this.defaultErrorView != null) {
            viewName = this.defaultErrorView;
        }

        if (viewName != null) {
            // スローされた例外が定義されている場合は ログを出力
            if (this.outputErrorLogHandledException) {
                log.error("Handled the following exception.", ex);
            }

            // HTTPエラーステータスをレスポンスに設定する
            if (this.defaultStatusCode != null) {
                response.setStatus(this.defaultStatusCode.intValue());
            }

            exceptionResolveDelegator.setHeader(response);

            // Viewの決定
            ModelAndView mv = getModelAndView(viewName, ex);
            
            exceptionResolveDelegator.addObjectToModel(mv);

            return mv;
        } 
        return null;
    }

}
