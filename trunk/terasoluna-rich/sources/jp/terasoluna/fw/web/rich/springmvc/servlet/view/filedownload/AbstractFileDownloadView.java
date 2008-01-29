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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.filedownload;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.AbstractView;

/**
 * バイナリファイルをダウンロードする際に利用するView抽象クラス。
 * 
 * <p>
 * バリナリデータをレスポンスに書き込む。
 * 必要に応じて、
 * レスポンスボディに書き込むストリームの取得処理と、
 * サブクラスにて、レスポンスヘッダの情報設定処理を実装すること。
 * </p>
 * 
 * <p>
 * 本クラスの実装クラスを利用する場合、まず、ResourceBundleViewResolverをBean定義すること。
 * </p>
 * 
 * <p>
 *  【Bean定義ファイルの設定例】<br>
 * <code><pre>
 *   &lt;bean id="fileDownloadViewResolver"
 *       class="org.springframework.web.servlet.view.ResourceBundleViewResolver"&gt;
 *     &lt;property name="basename" value="views"/&gt;
 *   &lt;/bean&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * 次に、バイナリデータをレスポンスとするコントローラのBean定義で、
 * property「viewName」を定義すること。
 * </p>
 * 
 * <p>
 *  【Bean定義ファイルの設定例】<br>
 * <code><pre>
 *   &lt;id name="fileDownloadSampleController"
 *           class="jp.terasoluna.sample2.web.controller.FileDownloadSampleController"
 *           parent="queryRequestController" singleton="false"&gt;
 *   &lt;property name="viewName"&gt;&lt;value&gt;FileDownloadSample&lt;/value&gt;&lt;/property&gt;
 *  </bean>
 * </pre></code>
 * </p>
 * 
 * <p>
 * さらに、fileDownloadViewResolverの属性basenameのプロパティファイル（上記の設定の場合、view.properties）に、
 * 「<I><コントローラのBean定義の属性viewNameプロパティ値>.class</I>」のキーで
 * 実行するViewクラスを指定すること。
 * </p>
 * 
 * <p>
 *  【view.propertiesの設定例】<br>
 * <code><pre>
 *   FileDownloadSample.class=jp.terasoluna.sample2.web.view.SampleFileDownloadView
 * </pre></code>
 * </p>
 * 
 */
public abstract class AbstractFileDownloadView extends AbstractView {
    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(AbstractFileDownloadView.class);
    
    /**
     * チャンクサイズ。
     */
    protected int chunkSize = 256;
    
    /**
     * レスポンスをレンダリングする。
     * 
     * @param model モデルオブジェクト
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @throws IOException IO例外
     * 
     */   
    @Override
    protected void renderMergedOutputModel(Map model,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("FileDownload start.");
        }      
       
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // ダウンロードファイルデータを取得する。
            try {
                inputStream = getInputStream(model, request);
            } catch (IOException e) {
                // ダウンロードに失敗した場合
                log.error("FileDownload Failed.", e);
                throw e;
            }
            if (inputStream == null) {
                log.error("FileDownload Failed. InputStream is null.");
                throw new IOException(
                    "FileDownload Failed. InputStream is null.");          
            }

            // HTTPレスポンスの出力ストリームに書き込む
            try {
                outputStream
                    = new BufferedOutputStream(response.getOutputStream());
            } catch (IOException e) {
                // ダウンロードに失敗した場合
                log.error("FileDownload Failed.", e);
                throw e;
            }
            
            // ヘッダ編集
            addResponseHeader(model, request, response);
            
            try {
                writeResponseStream(inputStream, outputStream);
            } catch (IOException e) {
                // ダウンロードに失敗した場合
                log.error("FileDownload Failed.", e);
                throw e;
            }
            try {
                outputStream.flush();
            } catch (IOException e) {
                // ダウンロードに失敗した場合
                log.error("FileDownload Failed.", e);
                throw e;
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioe) {
                    log.warn("Cannot close InputStream.", ioe);
                }
            }
 
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ioe) {
                    log.warn("Cannot close OutputStream.", ioe);
                }
            }
        }
    }
    
    /**
     * レスポンスボディに書き込むストリームを取得する。
     * 
     * @param model モデルオブジェクト
     * @param request HTTPリクエスト
     * @return リクエストに書き込むためのストリーム
     * @throws IOException 入出力例外
     */
    protected abstract InputStream getInputStream(
            Map model,
            HttpServletRequest request) throws IOException; 
    
    /**
     * ダウンロードファイルをHTTPレスポンスのストリームに書き込む。
     * 
     * @param inputStream ダウンロードするファイルデータの入力ストリーム
     * @param outputStream レスポンスの出力ストリーム
     * @throws IOException 入出力例外(例外処理は呼び元で行う)
     */
    protected void writeResponseStream(
            InputStream inputStream, 
            OutputStream outputStream) throws IOException {
        if (inputStream == null || outputStream == null) {
            return;
        }

        byte[] buffer = new byte[chunkSize]; 
        int length = 0;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }   
    }
    
    /**
     * レスポンスヘッダを追加する。
     * 
     * @param model モデルオブジェクト
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     */
    protected abstract void addResponseHeader(Map model,
            HttpServletRequest request,
            HttpServletResponse response);
}
