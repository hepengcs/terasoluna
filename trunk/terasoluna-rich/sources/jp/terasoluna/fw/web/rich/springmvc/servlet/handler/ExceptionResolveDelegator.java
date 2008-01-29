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

package jp.terasoluna.fw.web.rich.springmvc.servlet.handler;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
/**
 * エラー情報をレスポンスヘッダとModelAndViewに反映するためのインタフェース。
 * <p>
 * 保持した内容を、レスポンスヘッダ及びModelAndViewに設定する機能も提供する。
 * </p>
 * 
 */
public interface ExceptionResolveDelegator {
    
    /**
     * 例外の型とビュー名（＆エラー情報）を属性に格納する。
     * @param mappingKey  例外の型
     * @param mappingValues ビュー名とエラー情報
     * @param params 別の情報のキーと値を格納するMap
     */
    void initMapping (String mappingKey, Object mappingValues, 
            Map<String, String> params);
    
    /**
     * レスポンスヘッダにエラー情報を設定する。
     * @param response HTTPレスポンス
     */
    void setHeader(HttpServletResponse response);
    
    /**
     * ModelAndViewにエラー情報を設定する。
     * @param mv ModelAndView ModelAndViewオブジェクト
     */
    void addObjectToModel(ModelAndView mv);
    
    /**
     * ビュー名を取得する。
     * @return ビュー名
     */    
    String getViewName();

}
