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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.web.rich.springmvc.Constants;

/**
 * Castorを利用してHTTPレスポンス生成を行うクラス。
 * 業務処理結果であるモデルオブジェクトからXML形式のデータを作成し、
 * HTTPレスポンスに設定する。
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorViewResolver
 */
public class CastorView extends AbstractUrlBasedView {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(CastorView.class);
    
    /**
     * XML→オブジェクト変換クラス。
     */
    private OXMapper oxmapper = null;
    
    /**
     * Castorビューの表示処理を行なう。
     * @param model 業務処理の結果
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @throws Exception 例外。
     */
    @Override
    protected void renderMergedOutputModel(Map model, 
            HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        // Object→XML
        StringWriter stringWriter = new StringWriter();
        oxmapper.marshal(model.get(Constants.RESULT_KEY), stringWriter);
        
        // XMLデータをHTTPレスポンスに書き出す
        Writer responseWriter = null;
        try {
            response.setContentType(this.getContentType());
            addResponseHeader(model, request, response);
            responseWriter = response.getWriter();
            responseWriter.write(stringWriter.getBuffer().toString());
        } catch (IOException e) {
            log.error("Cannot get Response Writer object.");
            throw e;
        } finally {
            if (responseWriter != null) {
                try {
                    responseWriter.close();
                } catch (IOException ee) {
                    log.error("Cannot close response writer.", ee);
                }
            }
        }
    }

    /**
     * レスポンスヘッダを追加する。
     * レスポンスヘッダを追加する場合、このメソッドをオーバライドする。
     *
     * @param model 業務処理の結果
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     */
    protected void addResponseHeader(Map model, 
            HttpServletRequest request, HttpServletResponse response) {
    }

    /**
     * oxmapperを取得する。
     *
     * @return oxmapper属性
     */
    public OXMapper getOxmapper() {
        return oxmapper;
    }

    /**
     * oxmapperを設定する。
     *
     * @param oxmapper oxmapperに設定する値
     */
    public void setOxmapper(OXMapper oxmapper) {
        this.oxmapper = oxmapper;
    }

}
