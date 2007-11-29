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

package jp.terasoluna.fw.web.struts.actions;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.service.thin.AbstractBLogicMapper;
import jp.terasoluna.fw.service.thin.BLogicIO;
import jp.terasoluna.fw.service.thin.BLogicMessage;
import jp.terasoluna.fw.service.thin.BLogicMessages;
import jp.terasoluna.fw.service.thin.BLogicResources;
import jp.terasoluna.fw.service.thin.BLogicResult;
import jp.terasoluna.fw.web.struts.ModuleUtil;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


/**
 * ビジネスロジック起動抽象クラス。
 *
 * <p>
 *  ビジネスロジックの起動を行うアクションクラスに共通する機能を
 *  集約した抽象クラスである。全てのビジネスロジック起動アクションクラスは
 *  このクラスを継承して実装する。
 *  BLogicActionもこのクラスを継承している。
 *  提供する機能一覧は下記のとおりである。
 * <ol>
 *  <li>struts-config.xmlのアクション設定から
 *   ビジネスロジッククラス名を取得する。</li>
 *  <li>ビジネスロジックの入力情報となる JavaBean を生成する。</li>
 *  <li>継承先クラスのエントリポイントとなるdoExecuteBLogic()を実行する。</li>
 *  <li>ビジネスロジックが正常終了した場合、BLogicResultの結果を処理する。</li>
 * </ol>
 *
 * <p>
 *  ビジネスロジック起動アクションクラスは、
 *  Bean定義ファイルにて起動するビジネスロジックを設定した上で、
 *  実行する。
 *  下記の例は、ビジネスロジックであるSampleBLogicをSampleActionから
 *  起動するための設定である。
 *  あわせてstruts-config.xmlの設定例も以下に示す。
 * </p>
 * <p>
 * <strong>Bean定義ファイルの設定</strong>
 * <code><pre>
 * &lt;bean name="/SampleAction" singleton="false"
 *   <b>class="jp.terasoluna.sample1.actions.SampleAction"</b>&gt
 *   <b>&lt;property name="sampleBLogic"&gt;
 *     &lt;ref bean="SampleBLogic"&gt;&lt;/ref&gt;
 *   &lt;/property&gt;</b>
 * &lt;/bean&gt
 * &lt;bean id="SampleBLogic"
 *   <b>class="jp.terasoluna.sample1.blogic.SampleBLogic"</b>&gt
 * &lt;/bean&gt
 * </pre></code>
 * </p>
 * <p>
 * <strong>struts-config.xmlの設定</strong>
 * <code><pre>
 *    &lt;action path="/SampleAction"
 *       name="_sampleForm"
 *       validate="true"
 *       scope="session"
 *       input="/sample.jsp"&gt;
 *    &lt;forward name="success" path="/sampleSCR.do"/&gt;
 *    &lt;forward name="failure" path="/errorSCR.do"/&gt;
 *  &lt;/action&gt;
 * </pre></code>
 * </p>
 * BLogicResultの利用、設定については、
 * BLogicIOPlugIn、BLogicResult、AbstractBLogicMapperを参照のこと。
 * </p>
 *
 * <p>
 *  また、ビジネスロジックの実行に失敗した場合などで、
 *  メッセージを設定したい際は、次のようにBLogicResultにメッセージを格納する。
 * </p>
 * <p>
 * <code><pre>
 * public BLogicResult doExecuteBLogic(ParamsBean params) {
 *
 *     // BLogicResultを生成する。
 *     BLogicResult result = new BLogicResult();
 *     ・・・
 *     //ビジネスロジック
 *     ・・・
 *     //エラー判定
 *     if (// エラー判定処理 ) {
 *         // Web層にに反映すべき情報を設定する。
 *         ・・・
 *         return result;
 *     } else {
 *         // ビジネスロジック内のエラーが発生
 *         // BLogicMessagesを生成
 *         BLogicMessages messages = new BLogicMessages();
 *         // GROUP_ERRORグループのメッセージとして、BLogicMessageを格納
 *         messages.add("GROUP_ERROR", new BLogicMessage("message.error.sample", "sample"));
 *         // エラー用BLogicMessagesをBLogicResultに設定
 *         result.setErrors(messages);
 *         // 実行結果に"failure"を指定
 *         result.setResultString("failure");
 *         return result;
 *     }
 * }
 * </pre></code>
 * </p>
 *
 *
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMessage
 * @see jp.terasoluna.fw.service.thin.BLogicMessages
 *
 * @param <P> ビジネスロジックへの入力値となるJavaBeanを指定する
 *
 */
public abstract class AbstractBLogicAction<P> extends ActionEx {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(AbstractBLogicAction.class);

    /**
     * 拡張アクションフォーム設定エラー時のエラーコード。
     */
    protected static final String BLOGIC_FORM_ILLEGAL_ERROR =
                                                         "errors.blogic.form";

    /**
     * 拡張アクションマッピング設定エラー時のエラーコード。
     */
    protected static final String BLOGIC_MAPPING_ILLEGAL_ERROR =
        "errors.blogic.mapping";

    /**
     * 拡張アクションリソース設定エラー時のエラーコード。
     */
    protected static final String BLOGIC_RESOURCES_ILLEGAL_ERROR =
        "errors.blogic.resources";

    /**
     * BLogicResultがnullで返却された時のエラーコード。
     */
    protected static final String BLOGIC_RESULT_NULL_ERROR =
        "errors.blogic.result.null";

    /**
     * AbstractBLogicMapperがnullだった場合のエラーコード。
     */
    protected static final String NULL_MAPPER_KEY =
        "errors.blogic.mapper.null";

    /**
     * メッセージ保存先スコープ。
     *
     * ビジネスロジック内で生成したBLogicMessagesの保存先を
     * requestまたはsessionのいずれかで指定する。
     */
    private String saveMessageScope = null;

    /**
     * メッセージ保存先スコープを設定する。
     *
     * @param saveMessageScope メッセージ保存先スコープ
     */
    public void setSaveMessageScope(String saveMessageScope) {
        this.saveMessageScope = saveMessageScope;
    }

    /**
     * ビジネスロジックを実行する。
     * <p>
     *  ビジネスロジックの実行に必要な下記の共通処理を行う。<br>
     *  <ul>
     *   <li>ビジネスロジックの入力情報であるJavaBeanの生成</li>
     *   <li>ビジネスロジックの出力情報であるBLogicResultの解析</li>
     *   <li>フォワード先の決定</li>
     *  </ul>
     * </p>
     *
     * @param mapping アクションマッピング
     * @param form    フォーム
     * @param request リクエスト
     * @param response レスポンス
     *
     * @return 遷移先アクションフォワード
     * @throws Exception サブクラスからスローされた、予期しない例外
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping,
                                         ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response)
            throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("*** doExecute() called. action path=["
                + mapping.getPath() + "] ***");
        }

        ActionMappingEx mappingEx = null;
        try {
            mappingEx = (ActionMappingEx) mapping;
        } catch (ClassCastException e) {
            log.error("Illegal ActionMapping.");
            throw new SystemException(e, BLOGIC_MAPPING_ILLEGAL_ERROR);
        }

        P params = getBLogicParams(mappingEx, request, response);

        if (log.isDebugEnabled()) {
            log.debug("*** BLogicParams is prepared. ***");
            if (params != null) {
                // paramsの設定情報をダンプする。
                log.debug("BLogicParams:" + params.toString());
            } else {
                // paramsがnullの場合
                log.debug("BLogicParams:null");
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("*** starting blogic action["
                 + this.getClass().getName() + "] ***");
        }

        BLogicResult result = null;

        // 前処理
        preDoExecuteBLogic(request, response, params);

        // ビジネスロジックを起動
        result = doExecuteBLogic(params);

        // 後処理
        postDoExecuteBLogic(request, response, params, result);

        if (log.isDebugEnabled()) {
            log.debug("*** finished blogic action["
                 + this.getClass().getName() + "] ***");
        }

        if (result != null) {
            // BLogicResultがnullで返却されなかった場合のみ
            // 評価を行う。
            evaluateBLogicResult(
                result, request, response, mappingEx);
            // ActionForward取得
            return mapping.findForward(result.getResultString());
        }
        log.error("BLogicResult is null.");
        // nullで返却された場合はSystemExceptionをスロー。
        throw new SystemException(new NullPointerException(),
            BLOGIC_RESULT_NULL_ERROR);
    }

    /**
     * ビジネスロジックの実行前処理。
     *
     * @param request リクエスト
     * @param response レスポンス
     * @param params パラメータ（JavaBean）
     * @throws Exception 予期しない例外
     */
    protected void preDoExecuteBLogic(HttpServletRequest request,
            HttpServletResponse response, P params) throws Exception {

    }

    /**
     * ビジネスロジックの実行後処理。
     *
     * <p>ビジネスロジックで例外が発生しなかった場合のみ、実行される。</p>
     *
     * @param request リクエスト
     * @param response レスポンス
     * @param params パラメータ（JavaBean）
     * @param result ビジネスロジック実行結果
     * @throws Exception 予期しない例外
     */
    protected void postDoExecuteBLogic(HttpServletRequest request,
            HttpServletResponse response, P params, BLogicResult result)
            throws Exception {

    }

    /**
     * BLogicResultの評価、Web層のオブジェクトへの結果反映を行う。
     *
     * @param result BLogicResultインスタンス
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param mappingEx 拡張アクションマッピング
     */
    protected void evaluateBLogicResult(
            BLogicResult result,
            HttpServletRequest request,
            HttpServletResponse response,
            ActionMappingEx mappingEx) {

        // BLogicResultのnullチェック
        if (result == null) {
            log.error("BLogicResult is null.");
            throw new SystemException(new NullPointerException(),
                    BLOGIC_RESULT_NULL_ERROR);
        }

        // BLogicResultの設定情報をダンプする。
        if (log.isDebugEnabled()) {
            log.debug("*** BLogicResult is prepared. ***");
            log.debug("BLogicResult:" + result.toString());
        }

        ActionMessages errors = convertMessages(result.getErrors());
        ActionMessages messages = convertMessages(result.getMessages());

        // アクションマッピングに指定されたエラー・メッセージ情報の
        // 保存先を取得する。
        if ("session".equalsIgnoreCase(saveMessageScope)) {
            // 保存先がセッションで指定されている場合、
            // セッションに対してエラー・メッセージ情報を追加する。

            // セッションの取得
            HttpSession session = request.getSession(true);

            addErrors(session, errors);
            addMessages(session, messages);
        } else {
            // それ以外の場合、リクエストに追加する。
            addErrors(request, errors);
            addMessages(request, messages);
        }

        if (isErrorsEmpty(result)) {
            // エラー情報が空であるとき、Web層に結果を反映する
            if (log.isDebugEnabled()) {
                log.debug(
                    "*** setting result into web layer. ***");
            }
            getBLogicMapper(request).mapBLogicResult(
                request, response, getBLogicIO(mappingEx, request), result);
        }
    }

    /**
     * BLogicMessagesの内容をActionMessagesに格納しなおす。
     *
     * @param blogicMessages BLogicMessagesインスタンス
     * @return ActionMessages
     */
    protected ActionMessages convertMessages(BLogicMessages blogicMessages) {

        if (blogicMessages == null) {
            // blogicMessagesがnullの場合、nullを返却する
            return null;
        }

        ActionMessages messages = new ActionMessages();
        // BLogicMessages取得用イテレータ
        Iterator itr = blogicMessages.get();
        // メッセージグループ名取得用イテレータ
        Iterator groupItr = blogicMessages.getGroup();
        while (itr.hasNext()) {

            // BLogicMessageを取得
            BLogicMessage blogicMessage = (BLogicMessage) itr.next();

            // ActionMessageを作成
            ActionMessage actionMessage = null;
            if (blogicMessage.isResource()) {
                actionMessage = new ActionMessage(blogicMessage.getKey(),
                        blogicMessage.getValues());
            } else {
                actionMessage = new ActionMessage(blogicMessage.getKey(),
                        blogicMessage.isResource());
            }

            // メッセージグループ名を取得
            String group = (String) groupItr.next();

            // メッセージ情報を格納
            messages.add(group, actionMessage);
        }
        return messages;
    }

    /**
     * ビジネスロジック実行抽象メソッド。
     * サブクラスで実装する。
     *
     * @param param ビジネスロジック入力情報
     * @return ビジネスロジック出力情報
     * @throws Exception 予期しない例外
     */
    public abstract BLogicResult doExecuteBLogic(P param) throws Exception;

    /**
     * BLogicResultに格納されているエラー情報が
     * nullであるか空であるとき、trueを返却する。
     *
     * @param result ビジネスロジック実行結果
     * @return エラー用BLogicMessagesがnullまたは、空であるときtrue
     */
    protected boolean isErrorsEmpty(BLogicResult result) {
        // BLogicResult nullチェック
        if (result == null) {
            log.error("BLogicResult is null.");
            throw new SystemException(new NullPointerException(),
                    BLOGIC_RESULT_NULL_ERROR);
        }

        BLogicMessages errors = result.getErrors();
        if (errors == null) {
            return true;
        }
        return errors.isEmpty();
    }

    /**
     * BLogicMapperインスタンスを取得する。
     *
     * @param req HTTPリクエスト
     * @return BLogicMapperインスタンス
     */
    protected AbstractBLogicMapper getBLogicMapper(HttpServletRequest req) {
        String moduleName = ModuleUtil.getPrefix(req);

        AbstractBLogicMapper mapper = null;
        try {
            mapper = (AbstractBLogicMapper) this.servlet.getServletContext()
            .getAttribute(BLogicIOPlugIn.BLOGIC_MAPPER_KEY + moduleName);
        } catch (ClassCastException e) {
            // AbstractBLogicMapperインスタンスでない場合、例外を起こす。
            log.error("Cannot cast BLogicMapper : " + e.getMessage());
            throw new SystemException(e, BLOGIC_MAPPING_ILLEGAL_ERROR);
        }

        // BLogicMapperがnullの場合、例外発生
        if (mapper == null) {
            log.error("BLogicMapper is null.");
            throw new SystemException(new NullPointerException(),
                    NULL_MAPPER_KEY);
        }

        return mapper;
    }

    /**
     * paramsを生成し、返却する。
     *
     * @param mapping アクションマッピング
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @return ビジネスロジック入力情報
     * @throws Exception 予期しない例外
     */
    @SuppressWarnings("unchecked")
    protected P getBLogicParams(ActionMappingEx mapping,
                                          HttpServletRequest request,
                                          HttpServletResponse response)
            throws Exception {

        BLogicIO io = getBLogicIO(mapping, request);

        // フォーム、セッションからの設定値取得
        P bean = (P) getBLogicMapper(request).mapBLogicParams(
                request, response, io);

        return bean;
    }

    /**
     * BLogicIOを取得する。
     *
     * @param mapping アクションマッピング
     * @param request HTTPリクエスト
     * @return ビジネスロジック入出力情報
     */
    protected BLogicIO getBLogicIO(ActionMapping mapping,
            HttpServletRequest request) {

        String moduleName = ModuleUtil.getPrefix(request);

        BLogicResources resource = null;
        try {
            resource =
                (BLogicResources) this.servlet.getServletContext().getAttribute(
                BLogicResources.BLOGIC_RESOURCES_KEY + moduleName);
        } catch (ClassCastException e) {
            // BLogicResourcesインスタンスでない場合、例外を起こす。
            log.error("Cannot cast BLogicResources : " + e.getMessage());
            throw new SystemException(e, BLOGIC_RESOURCES_ILLEGAL_ERROR);
        }

        if (resource != null) {
            return resource.getBLogicIO(mapping.getPath());
        }
        return null;
    }
}
