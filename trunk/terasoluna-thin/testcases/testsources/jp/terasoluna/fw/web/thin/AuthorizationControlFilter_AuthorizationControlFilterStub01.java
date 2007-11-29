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

package jp.terasoluna.fw.web.thin;

public class AuthorizationControlFilter_AuthorizationControlFilterStub01 extends AuthorizationControlFilter{
	/**
	 * getController()が返す値。
	 */
	public AuthorizationController newController = null;	
	/**
	 * getController()が呼ばれたことを表すフラグ。
	 */	
	public boolean isCalled = false;
	
	/**
	 * getController():
	 * AuthorizationControlFilter_AuthorizationControlFilterStub01インスタンス
	 */
	@Override	
	protected AuthorizationController getController() {
		isCalled = true;
		return newController ;
	}
	
}
