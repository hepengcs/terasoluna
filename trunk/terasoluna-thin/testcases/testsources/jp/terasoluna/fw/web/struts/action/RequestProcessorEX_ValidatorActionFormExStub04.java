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

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * RequestProcessorExTestクラスで利用する。
 * 
 * @version 2005/01/24
 */
public class RequestProcessorEX_ValidatorActionFormExStub04
        extends ValidatorActionFormEx {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 8267534061199834550L;

	/**
     * このフォームのフィールドを検証する。
     * 
     * @return アクションエラー
     */
    @Override
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request){
        ActionErrors errors = new ActionErrors();
        return errors;
    }
}
