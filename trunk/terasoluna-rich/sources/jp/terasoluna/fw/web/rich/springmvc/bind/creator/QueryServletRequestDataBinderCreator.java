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

package jp.terasoluna.fw.web.rich.springmvc.bind.creator;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

/**
 * クエリ形式のリクエストデータに対応したServletRequestDataBinder実装クラスを返却する。
 * 
 * <p>
 * Springに定義されているServletRequestDataBinderを実装クラスとして返却する。
 * </p>
 * 
 * <p>
 * マルチパート形式で定義されたリクエストデータにも対応している。
 * </p>
 * 
 * <p>
 * 本クラスの使用方法は、{@link jp.terasoluna.fw.web.rich.springmvc.controller.TerasolunaController}を参照すること。
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.TerasolunaController
 * @see org.springframework.web.bind.ServletRequestDataBinder
 * 
 */
public class QueryServletRequestDataBinderCreator implements
        ServletRequestDataBinderCreator {

    /**
     * クエリ形式のリクエストデータに対応したServletRequestDataBinder実装クラスを返却する。
     * 
     * <p>
     * マルチパート形式のリクエストデータをバイト配列に変換することを可能にするプロパティエディタを設定する。
     * </p>
     * 
     * @param request クエリ形式のリクエスト
     * @param command コマンドオブジェクト
     * @param requestName リクエスト名
     * @return Springに定義されているServletRequestDataBinder
     */
    public ServletRequestDataBinder create(HttpServletRequest request,
            Object command, String requestName) {
        // クエリ形式のリクエストデータに対応したServletRequestDataBinder実装クラスを生成する。
        ServletRequestDataBinder binder = new ServletRequestDataBinder(command,
                requestName);
        // ServletRequestDataBinder実装クラスに、
        // マルチパート形式のリクエストデータをバイト配列に変換することを可能にするプロパティエディタを生成する
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
        return binder;
    }

}
