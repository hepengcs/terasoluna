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

package jp.terasoluna.fw.web.struts.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RequestProcessorExTestクラスで利用する。
 * 
 * RequestProcessor#process()内で最初に呼ばれる
 * processPath()をオーバライドすることにより、process()の処理を早期に
 * 終了させる。
 * 
 * @version 2004/03/18
 */
public class RequestProcessorExStub01 extends RequestProcessorEx {

    /**
     * HTTPリクエスト。
     */
    private HttpServletRequest req = null;

    /**
     * オーバライド用processpathメソッド。
     * 
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @return 常にnullを返却
     * @exception IOException IO例外
     */
    @Override
    protected String processPath(HttpServletRequest request,
                                  HttpServletResponse response)
                                     throws IOException {
        req = request;
        // nullを返却することにより、processPathで終了する。
        return null;
    }
    
    /**
     * リクエストオブジェクト確認用メソッド
     * 
     * @return HTTPリクエスト
     */
    protected HttpServletRequest getRequestEx() {
        return req;
    }
}
