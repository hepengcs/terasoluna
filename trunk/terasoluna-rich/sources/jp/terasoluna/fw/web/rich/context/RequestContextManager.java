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

package jp.terasoluna.fw.web.rich.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 制御情報を管理するマネージャ。
 * 
 * <p>
 * 制御情報をスレッド単位で管理する。
 * 通常、Webコンテナでは、1つのリクエストに対する処理は1つのスレッドが行うため、
 * 同じリクエストスコープから呼び出された場合、必ず同じ制御情報に対して処理を行うことが出来る。
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.context.RequestContext
 * @see jp.terasoluna.fw.web.rich.context.support.AbstractRequestContextSupport
 *
 */
public class RequestContextManager {
    /**
     * ログ。
     */
    private static Log logger
        = LogFactory.getLog(RequestContextManager.class);

    /**
     * スレッド単位でコンテキストを管理するThreadLocal。
     */
    private static ThreadLocal<RequestContext> resources
        = new ThreadLocal<RequestContext>();

    /**
     * 実行スレッドに対応する制御情報を取得する。
     * 制御情報が設定ない場合、例外が発生する。
     * @return 制御情報
     */
    public static RequestContext getRequestContext() {
        RequestContext ctx = resources.get(); 
        if (ctx == null) {
            logger.error("No RequestContext bound to thread!");
            throw new IllegalStateException(
                    "No RequestContext  bound to thread ["
                        + Thread.currentThread().getName() + "]");
        }
        return ctx;
    }

    /**
     * 実行スレッドに対応する制御情報が登録されているか判定する。
     * @return 登録されていたらtrue
     */
    public static boolean hasRequestContext() {
        return (resources.get() != null);
    }

    /**
     * 実行スレッドに対応する制御情報を登録する。
     * @param ctx 制御情報。
     */
    public static void bindRequestContext(
            RequestContext ctx) {
        if (ctx == null) {
            logger.error("RequestContext cannot set null.");
            throw new IllegalArgumentException(
                    "RequestContext cannot set null.");
        }
        
        RequestContext alreadyBoundCtx = resources.get();
        
        // スレッドに制御情報が設定されていなければ、引数の制御情報を設定する
        if (alreadyBoundCtx == null) {
            resources.set(ctx);
            if (logger.isDebugEnabled()) {
                logger.debug("Bound RequestContext ["
                        + ctx + "] to thread ["
                        + Thread.currentThread().getName() + "]");
            }
        } else {
            logger.error("Already RequestContext bound to thread!");
            throw new IllegalStateException("Already RequestContext ["
                    + alreadyBoundCtx + "]" + "   ["
                    + Thread.currentThread().getName() + "]");
        }
    }

    /**
     * 実行スレッドに対応する制御情報を削除する。
     */
    public static void unbindRequestContext() {
        RequestContext ctx = resources.get();
        if (ctx == null) {
            logger.error("No RequestContext bound to thread!");
            throw new IllegalStateException(
                    "No RequestContext  bound to thread ["
                        + Thread.currentThread().getName() + "]");
        }
        
        resources.remove();

        if (logger.isDebugEnabled()) {
            logger.debug("Removed RequestContext [" + ctx
                + "] from thread ["
                + Thread.currentThread().getName() + "]");
        }
    }
}
