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

package jp.terasoluna.fw.web.rich.springmvc.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.util.ClassUtil;
import jp.terasoluna.fw.web.rich.context.support.RequestContextSupport;
import jp.terasoluna.fw.web.rich.springmvc.Constants;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.ServletRequestDataBinderCreator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

/**
 * サービス層のクラスを実行するリクエストコントローラ抽象クラス。
 *
 * <p>本コントローラは、<code>DispachServlet</code>から起動され、
 * 以下の機能を提供する。
 * </p>
 * <ul>
 * <li>HTTPリクエストをJavaBean（コマンド）にバインド</li>
 * <li>入力チェック実行</li>
 * <li>POJOの業務ロジッククラス実行</li>
 * <li>JavaBan(モデル)とViewを返却</li>
 * </ul>
 * 
 * <p>
 * 本クラスは、抽象クラスである。
 * 業務開発者が、リクエストごとに本クラスの実装クラスを作成すること。
 * 本クラスは、型パラメータを利用して宣言されている。
 * 型パラメータPは、HTTPリクエストをバインドしたJavaBean（コマンド)、
 * 型パラメータRは、Viewに反映させる情報を保持するJavaBean(モデル)の型をあらわしている。
 * 実装クラス宣言時に、型パラメータに実際の型を指定し、
 * 抽象メソッドの型変数も指定した型にあわせて実装することで
 * 実装クラスの型の安全性を保障させている。
 * 型パラメータにP,Rには、必ず引き数なしコンストラクタを持つJavaBeanを指定すること。
 * インタフェース、抽象クラス、引き数なしコンストラクタを持たないクラスの指定は出来ない。
 * </p>
 * 
 * <p>
 * <u>サービス層のクラスの実行</u><br>
 * サービス層のクラスは、DIコンテナを利用して本コントローラに設定されることを前提とするので、
 * 業務開発者はサービス層のクラスを属性として用意し、setter/getterメソッドを設けること。
 * また、呼び出し処理は、抽象メソッドであるexecuteServiceメソッドに実装すること。
 * </p>
 * 
 * <p> 
 * 業務処理で例外が発生した場合、
 * Spring MVCの例外処理機構でハンドリングされる。
 * </p>
 *
 * <p>
 * <u>バインド処理</u><br>
 * ServletRequestDataBinder継承クラスが行う。
 * ServletRequestDataBinderを生成するためのクラスであるDataBinderCreatorを
 * DIコンテナを利用して本コントローラに設定すること。
 * DataBinderCreatorは、リクエストの形式(XML or Query)により使いわける。
 * バインド処理で、バインドエラーが発生した場合、BindExceptionがスローされ、
 * Spring MVCの例外処理機構でハンドリングされる。
 * </p>
 * 
 * <p>
 * <u>入力チェック処理</u><br>
 * Validatorインタフェース実装クラスが行う。
 * Validatorインタフェース実装クラスを
 * DIコンテナを利用して本コントローラに設定すること。
 * 入力チェック処理で、入力チェックエラーが発生した場合、
 * BindExceptionがスローされ、
 * Spring MVCの例外処理機構でハンドリングされる。
 * </p>
 * 
 * <p>
 * <u>ビュー名の設定</u><br>
 * <code>DispachServlet</code>でのビュー解決に使用されるビュー名を
 * {@link #handle(HttpServletRequest, HttpServletResponse, Object, BindException)}
 * メソッド内で設定している。
 * ビュー名は、以下の順番で決定される。
 * <ul>
 * <li>本コントローラの{@link #viewName}属性が入力されていれば、その属性値</li>
 * <li>本コントローラの{@link #useRequestNameView}属性がtrueならば、
 *     ”/”＋「リクエスト名」　</li>
 * <li>上記にあてはまらない場合、空文字　※</li>
 * </ul>
 * ※･･･TERASOLUNAの初期設定では、ビュー名に空文字が入っていると
 * Castorビューを使用する仕様になっている。
 * </p>
 * 
 * <p>
 * トランザクション管理の責務は、サービス層が受け持つ。
 * （ただし、AOPによる宣言的トランザクションを利用するので、
 * サービス層のクラスが意識する必要はない。）
 * </p>
 *
 * <p>
 * 作成した実装クラスを利用するには、Beanの定義を行うこと。
 * </p>
 *
 * <p>
 * 【<code>xxx-servlet.xml</code>の定義例】<br>
 * <code><pre>
 *   &lt;bean name="/secure/blogic/sum.do"
 *       class="jp.terasoluna.sample2.web.controller.SumController"
 *       parent="xmlRequestController" singleton ="false"&gt;
 *     &lt;property name="sumService" ref="sumService"/&gt;
 *     &lt;property name="ctxSupport" ref="ctxSupport"/&gt;  
 *     &lt;property name="dataBinderCreator" ref="xmlDataBinderCreator"/&gt;
 *     &lt;property name="validator" ref="sumValidator"/&gt;
 *   &lt;/bean&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * 以下のプロパティを設定すること。
 *   <table border="1" CELLPADDING="8">
 *     <th>属性名</th>
 *     <th>必須</th>
 *     <th>説明</th>
 *  
 *     <tr>
 *       <td align=center><b>ctxSupport</b></td>
 *       <td>○</td>
 *       <td>制御情報サポートクラス。</td>
 *     </tr>
 *     
 *     <tr>
 *       <td align=center><b>dataBinderCreator</b></td>
 *       <td>○</td>
 *       <td>リクエストデータバインダ生成クラス。</td>
 *     </tr>
 *   
 *     <tr>
 *       <td align=center><b>validator</b></td>
 *       <td>×</td>
 *       <td>入力チェッククラス。</td>
 *     </tr> 
 *     
 *     <tr>
 *       <td align=center><b>viewName</b></td>
 *       <td>×</td>
 *       <td>ビュー名。
 *           デフォルト以外のView技術(Velocity,バイナリデータ, PDF, Excel等)を
 *           利用する場合に設定する。</td>
 *     </tr>
 *     <tr>
 *       <td align=center><b>useRequestNameView</b></td>
 *       <td>×</td>
 *       <td>ビュー名にリクエスト名を使用するか判断するフラグ。
 *           デフォルトはfalse。リクエスト名を使用したい場合、trueを設定する。
 *           trueが設定されていても、viewNameが入力されていればviewNameに
 *           対応するビューが使用される。
 *       </td>
 *     </tr> 
 *  </table>
 *  このほかに実行するサービス層のクラスをプロパティに設定すること。
 *  コマンドクラス名を指定する、commandclass属性の指定は不要である。
 *  コマンドクラスの型は、本クラスの型パラメータより、自動的に判断するためである。
 * </p>
 * 
 * 
 * <p>
 * また、制御情報サポートクラス、リクエストデータバインダ生成クラスの設定は、
 * 複数のコントローラ定義で同一になる。
 * よって、抽象Bean定義をあらかじめ設定しておき、
 * 実装クラスのBean定義は、抽象Bean定義を継承して行うと
 * 設定ファイルの記述がシンプルになる。
 * </p>
 * 
 * <p>
 * 【抽象Bean定義を利用した<code>xxx-servlet.xml</code>の定義例】<br>
 * <code><pre>
 *   &lt;!-- コントローラの抽象Bean定義 --&gt;
 *   &lt;bean id="xmlRequestController" abstract="true"&gt;
 *     &lt;property name="cxtSupport" ref="ctxSupport"/&gt;  
 *     &lt;property name="dataBinderCreator" ref="xmlDataBinderCreator"/&gt;
 *   &lt;/bean&gt;
 *
 *   &lt;!-- 抽象Bean定義を継承したコントローラの定義 --&gt;
 *   &lt;bean name="/secure/blogic/sum.do"
 *       class="jp.terasoluna.sample2.web.controller.SumController"
 *       parent="xmlRequestController" scope="singleton"&gt;
 *     &lt;property name="sumService" ref="sumService"/&gt;
 *     &lt;property name="validator" ref="sumValidator"/&gt;
 *   &lt;/bean&gt;
 * </code></pre>
 * </p>
 * 
 * <p>
 * TERASOLUNAでは、
 * あらかじめいくつかの抽象Bean定義が用意されている。必要に応じて利用すること。
 *   <table border="1" CELLPADDING="8">
 *     <th>抽象Bean名</th>
 *     <th>受信リクエスト</th>
 *     <th>起動サービス</th>
 *  
 *     <tr>
 *       <td align=center><b>xmlRequestController</b></td>
 *       <td>XML形式</td>
 *       <td>POJO</td>
 *     </tr>
 *     
 *     <tr>
 *       <td align=center><b>queryRequestController</b></td>
 *       <td>クエリ形式</td>
 *       <td>POJO</td>
 *     </tr>
 *   
 *     <tr>
 *       <td align=center><b>xmlRequestBLogicExecuteController</b></td>
 *       <td>XML形式</td>
 *       <td>BLogic</td>
 *     </tr> 
 *     
 *     <tr>
 *       <td align=center><b>queryRequestBLogicExecuteController</b></td>
 *       <td>クエリ形式</td>
 *       <td>BLogic</td>
 *     </tr>   
 *  </table>
 * 
 * </p>
 *   
 * </pre></code>
 * </p>
 *
 * <p>
 * 適用先システムに特化した業務前処理、後処理を追加したい場合
 * （例えば業務処理パラメータや業務処理結果に
 * セッションの情報を反映したい場合等）、
 * preService、postServiceメソッドをオーバーライドした実装クラスを作成し、
 * 利用すること。<br>
 * サブクラスで抽象クラスを使用する場合、{@link #getCommandType()}メソッドを
 * オーバーライドする必要がある。
 * </p>
 * 
 * <p>
 * 起動するサービス層のクラスとしてPOJOではなく、
 * BLogicインタフェース実装クラスを利用することも可能である。
 * 詳細はBLogicControllerを参照のこと。
 * </p>
 * 
 * 
 * @param <P> コマンドクラス。サービス層のクラスへ渡すクラス。
 * @param <R> モデルクラス。サービス層のクラスから返されるクラス。
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.BLogicController
 * 
 */
public abstract class TerasolunaController<P, R>
        extends AbstractCommandController implements InitializingBean {
    
    /**
     * 型パラメータ<P, R>を定義しているコントローラクラス。
     */
    protected Class parameterizedControllerClass = TerasolunaController.class;

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(TerasolunaController.class);
    
    /**
     * 制御情報を扱うサポートロジッククラス。
     */
    protected RequestContextSupport ctxSupport = null;
    
    /**
     * ServletRequestDataBinder継承クラスを生成するクラス。
     */
    protected ServletRequestDataBinderCreator dataBinderCreator = null;
    
    /**
     * デフォルト以外のビュー技術(Velocity,バイナリデータ, PDF, Excel)を
     * 利用する場合に設定するビュー名。
     */
    protected String viewName = null;
    
    /**
     * ビュー名にリクエスト名を使用するか判断するフラグ。
     * <p>trueを設定した場合、ビュー名にリクエスト名を設定する。
     */
    protected boolean useRequestNameView = false;

    /**
     * コンテキストを扱うサポートロジッククラスを設定する。
     *
     * @param ctxSupport 制御情報を扱うサポートロジッククラス
     */
    public void setCtxSupport(RequestContextSupport ctxSupport) {
        this.ctxSupport = ctxSupport;
    }
    
    /**
     * DataBinder生成するクラスを設定する。
     * 
     * @param dataBinderCreator DataBinder生成するクラス
     */
    public void setDataBinderCreator(
            ServletRequestDataBinderCreator dataBinderCreator) {
        this.dataBinderCreator = dataBinderCreator;
    }

    /**
     * ビュー名を設定する。
     * 
     * @param viewName デフォルト以外のView技術を利用する場合に
     * 設定するView名
     */
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
    
    /**
     * ビュー名にリクエスト名を使用するか判断するフラグを設定する。
     *
     * @param useRequestNameView ビュー名にリクエスト名を使用するか判断するフラグ。
     */
    public void setUseRequestNameView(boolean useRequestNameView) {
        this.useRequestNameView = useRequestNameView;
    }
    
    /**
     * DIコンテナによってインスタンス化された直後に呼ばれるメソッド。
     * 必須属性のNullチェックを行う。
     */
    public void afterPropertiesSet() {
        if (this.dataBinderCreator == null) {
            log.error("DataBinderCreator is Null.");
            throw new IllegalStateException("DataBinderCreator is Null.");
        }
        
        if (this.ctxSupport == null) {
            log.error("ContextSupport is Null.");
            throw new IllegalStateException("ContextSupport is Null.");
        }
    }

    /**
     * リクエストの情報を格納するためのJavaBean(コマンド)を取得する。
     * 型パラメータから実装コントローラに対応するJavaBean(コマンド)の型を判定し、
     * インスタンス化する。
     * 
     * @param request HTTPリクエスト
     * @return object 空のコマンドオブジェクト
     * @throws Exception 例外
     */
    @Override
    protected Object getCommand(
            HttpServletRequest request) throws Exception {
        // コマンドクラスの型パラメータを取得する
        Type commandType = getCommandType();
            
        if (logger.isDebugEnabled()) {
            logger.debug(
                "Creating new command of class ["
                    + ((Class) commandType).getName() + "]");
        }
        
        // 型パラメータがObject型（指定されていない）
        if (commandType == Object.class) { 
            String message = "Cannot get Command type. "
                + "Controller cannot specify the Object type "
                + "for parameterized type P.";
            log.error(message);
            throw new IllegalStateException(message);
        }
        
        try {
            // コマンドクラスをインスタンス化する
            return ClassUtil.create(((Class) commandType).getName());
        } catch (Exception e) {
            log.error("Invalid Command type.", e);
            throw new IllegalStateException("Invalid Command type.");
        }
    }

    /**
     * 本クラスのサブクラス定義された、コマンドクラスの実タイプを取得する。
     * 
     * @return コマンドクラスのタイプ。
     */
    protected Type getCommandType() {
        Class childClass = this.getClass();
        
        // ２世代以上の継承をしている場合、
        // TerasolunaControllerの子にあたるクラスを取得する
        while (childClass.getSuperclass() != parameterizedControllerClass) {
            childClass = childClass.getSuperclass();
        }
        
        // TerasolunaControllerの型情報（型パラメータの情報付き）
        Type terasolunaControllerType = childClass.getGenericSuperclass();
        if (!(terasolunaControllerType instanceof ParameterizedType)) {
            log.error("Controller class must be set ParameterizedType");
            throw new IllegalStateException(
                    "Controller class must be set ParameterizedType");
        }
        ParameterizedType pt = (ParameterizedType) terasolunaControllerType;
        
        // 型パラメータ
        return pt.getActualTypeArguments()[0];
    } 
   
    /**
     *  リクエストの情報をJavaBean(コマンド)に格納するための
     *  データバインダを生成する。
     *  <code>bindAndValidate</code>メソッドより呼び出される。
     *  
     * @param request HTTPリクエスト
     * @param command バインドされるコマンドオブジェクト
     * @return 生成されたデータバインダ
     * @throws Exception 例外
     * 
     */
    @Override
    protected ServletRequestDataBinder createBinder(
            HttpServletRequest request, Object command) throws Exception {
        // データバインダの生成
        ServletRequestDataBinder binder = dataBinderCreator.create(
                      request, command, ctxSupport.getRequestName());
        
        if (binder == null) {
            log.error("DataBinder is Null.");
            throw new IllegalStateException("DataBinder is Null.");
        }
        
        if (this.getMessageCodesResolver() != null) {
            binder.setMessageCodesResolver(this.getMessageCodesResolver());
        }
        if (getBindingErrorProcessor() != null) {
            binder.setBindingErrorProcessor(getBindingErrorProcessor());
        }
        if (getPropertyEditorRegistrars() != null) {
            for (int i = 0; i < getPropertyEditorRegistrars().length; i++) {
                getPropertyEditorRegistrars()[i].registerCustomEditors(binder);
            }
        }
        initBinder(request, binder);
        return binder;
    }
    
    /**
     * リクエストの情報をJavaBean(コマンド)に格納した後に実行される処理。
     * バインド後、入力チェック処理前のタイミングで呼び出される。
     * 
     * バインド処理でエラー情報が格納された場合、
     * BindExceptionをスローする。
     * 
     * @param request HTTPリクエスト
     * @param command バインド済みのJavaBean（コマンド）
     * @param errors バインド・入力チェックエラー保持クラス
     * @throws Exception 例外
     */
    @Override
    protected void onBind(
            HttpServletRequest request,
            Object command,
            BindException errors) throws Exception {
        if (errors.hasErrors()) {
            throw errors;
        }
    }
    
    /**
     * 入力チェックの後処理。
     * 注力チェック処理後、業務ロジック実行前のタイミングで呼び出される。
     * 
     * 入力チェック処理でエラー情報が格納された場合、
     * BindExceptionをスローする。
     * 
     * @param request HTTPリクエスト
     * @param command バインド済みのコマンドオブジェクト
     * @param errors バインド・入力チェックエラー保持クラス
     * @throws Exception 例外
     */
    @Override
    protected void onBindAndValidate(
            HttpServletRequest request,
            Object command,
            BindException errors) throws Exception {
        if (errors.hasErrors()) {
            throw errors;
        }
    }

    /**
     * 業務ロジック実行メソッドを呼び出し、モデルとビューを返却する。
     * 
     * 
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param command コマンドオブジェクト
     * @param errors バインド・入力チェックエラー保持クラス
     * @return モデルとビュー
     * @throws Exception 例外
     */
    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors) throws Exception {
        R model = executeService(request, response, (P) command);

        if (this.viewName != null) {
            // ビュー名を直接指定する場合
            return new ModelAndView(viewName, Constants.RESULT_KEY, model);
        } else if (this.useRequestNameView) {
            // Velocityビューを利用する場合
            return new ModelAndView(
                "/" + ctxSupport.getRequestName(), Constants.RESULT_KEY, model);
        } else {
            // ビュー名なしはCastorビューを利用する
            return new ModelAndView("", Constants.RESULT_KEY, model);
        }
    }
    
    /**
     * 業務ロジックを実行する。
     * 
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param command コマンドオブジェクト
     * @return モデルオブジェクト
     * @throws Exception 例外
     */
    protected R executeService(
            HttpServletRequest request,
            HttpServletResponse response,
            P command) throws Exception {
        // 前処理
        preService(request, response, command);

        // 業務ロジック実行処理
        R model = executeService(command);
        
        // 後処理
        postService(request, response, command, model);
        return model;
    }

    /**
     * 業務ロジック実行後処理。
     * 
     * セッションスコープのリクエストに対応するための拡張点。
     * サブクラスにて必要に応じてオーバーライドすること。
     * 
     * 業務ロジック処理にて例外が発生した場合は実行されない。
     * 
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param command コマンドオブジェクト
     * @param modelAndView モデルとビュー
     * @throws Exception 例外
     */
    protected void postService(
            HttpServletRequest request,
            HttpServletResponse response,
            P command,
            R modelAndView) throws Exception {
    }

    /**
     * 業務ロジック実行前処理。
     * 
     * セッションスコープのリクエストに対応するための拡張点。
     * サブクラスにて必要に応じてオーバーライドすること。
     * 
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param command コマンドオブジェクト
     * @throws Exception 例外
     */
    protected void preService(
            HttpServletRequest request,
            HttpServletResponse response,
            P command) throws Exception {
    }

    /**
     * 業務開発者が実装すべき、業務ロジックの実行処理。
     * @param command コマンド（業務パラメータ）
     * @return モデル
     * @throws Exception 例外
     */
    protected abstract R executeService (
            P command) throws Exception;

}
