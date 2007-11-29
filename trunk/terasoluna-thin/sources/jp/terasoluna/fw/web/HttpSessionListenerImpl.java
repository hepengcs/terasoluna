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

package jp.terasoluna.fw.web;

import java.io.File;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import jp.terasoluna.fw.util.FileUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>HTTPセッションのライフサイクルイベントを処理するクラス。</p>
 *
 * <p>HTTPセッションの無効化時に、セッションに紐付けられていたディレクトリが
 * 存在していれば削除する。</p>
 *
 * <p><code>web.xml</code> において、以下のように設定する。</p>
 * <h5> <code>web.xml</code> 設定例</h5>
 * <pre><code>
 * &lt;listener&gt;
 *   &lt;listener-class&gt;
 *     jp.terasoluna.fw.web.HttpSessionListenerImpl
 *   &lt;/listener-class&gt;
 * &lt;/listener&gt;
 * </pre></code>
 * セッションディレクトリ生成・削除処理についてはFileUtilを参照。
 *
 * @see jp.terasoluna.fw.util.FileUtil
 *
 */
public class HttpSessionListenerImpl implements HttpSessionListener {

    /**
     * ログクラス。
     */
    private static Log log
          = LogFactory.getLog(HttpSessionListenerImpl.class);

    /**
     * <p>HTTPセッションの作成イベントを処理する。</p>
     *
     * @param event セッションイベント
     */
    public void sessionCreated(HttpSessionEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("session created.");
        }
    }

    /**
     * <p>HTTPセッションの無効化イベントを処理する。</p>
     * <p>
     *  セッションに紐付けられていたディレクトリが
     *  存在していれば、削除する。
     * </p>
     *
     * @param event セッションイベント
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("session destroyed.");
        }

        HttpSession session = event.getSession();
        File dir = FileUtil.getSessionDirectory(session.getId());
        if (dir.exists() && dir.isDirectory()) {
            if (FileUtil.removeSessionDirectory(session.getId())) {
                log.debug("\"" + dir.getPath() + "\" removed.");
            } else {
                log.error("can't remove \"" + dir.getPath() + "\".");
            }
        }
    }

}
