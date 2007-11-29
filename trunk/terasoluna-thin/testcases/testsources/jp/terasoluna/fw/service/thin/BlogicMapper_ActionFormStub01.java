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

package jp.terasoluna.fw.service.thin;

import java.util.Map;

import org.apache.struts.action.ActionForm;

/**
 * BLogicMapperTestで使用されるActionFormのスタブクラス。
 */
@SuppressWarnings("serial")
public class BlogicMapper_ActionFormStub01 extends ActionForm {
	
	/**
	 * ダミープロパティparam1。
	 */
	private String param1 = null;
	
	/**
	 * ダミープロパティparam3。
	 */
	private Map param3 = null;
	
	/**
	 * param1を取得する。
	 * @return param1
	 */
	public String getParam1() {
		return param1;
	}
	
	/**
	 * param1を設定する。
	 * @param param1 param1
	 */
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	
	/**
	 * param3を取得する。
	 * @return param3
	 */
	public Map getParam3() {
		return param3;
	}
	
	/**
	 * param3を設定する。
	 * @param param3 param3
	 */
	public void setParam3(Map param3) {
		this.param3 = param3;
	}
	

}
