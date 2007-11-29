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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * DispatchActionTestで使用する。<br>
 *
 * cancelled()が、"cancelled"アクションパスを返却する。<br>
 *
 * @version 2005/01/22
 */
public class DispatchActionImpl01 extends DispatchAction{

    /**
     * リクエストにキャンセルフラグが設定されている場合の遷移先を
     * 決定する。注意点として、ActionForwardはnullとして返却されるため、
     * キャンセル時の遷移先は、このクラスを継承したクラスの
     * オーバライドメソッドで実装する必要がある。
     *
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @return アクションフォワード
     */
    @Override
    protected ActionForward cancelled(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // "cancelled"のアクションフォワードの生成
        ActionForward forward = new ActionForward();
        forward.setName("cancelled");
        return forward;

    }

}
