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

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * RequestProcessorExTestクラスで利用する。
 * 
 * @version 2004/03/18
 */
public class RequestProcessorEx_ValidatorActionFormExStub01
        extends ValidatorActionFormEx {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 7446703253436814028L;

	/**
     * 会社ID
     */
    private String companyId = null;

    /**
     * 会社名
     */
    private String companyName = null;

    /**
     * ユーザID
     */
    private String userId = null;

    /**
     * ユーザ名
     */
    private String userName = null;

    /**
     * 会社IDを設定する。
     * 
     * @param companyId
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * 会社IDを取得する。
     * 
     * @return 会社ID
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 会社名を設定する。
     * 
     * @param companyName 会社名
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 会社名を取得する。
     * 
     * @return 会社名
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * ユーザIDを設定する。
     * 
     * @param userId ユーザID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * ユーザIDを取得する。
     * 
     * @return ユーザID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * ユーザ名を設定する。
     * 
     * @param userName ユーザ名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * ユーザ名を取得する。
     * 
     * @return ユーザ名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 会社IDを検証する。
     * 
     * @param errors アクションエラー
     * @return 検証結果
     */
    public boolean validateCompanyId(ActionErrors errors) {
        errors.add(Globals.ERROR_KEY,
            new ActionMessage("RequestProcessorEx_ValidateActionFormExStub01"));

        return false;
    }

    /**
     * このフォームのフィールドを検証する。
     * 
     * @return アクションエラー
     */
    @Override
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request){
        ActionErrors errors = new ActionErrors();
        validateCompanyId(errors);
        return errors;
    }
}
