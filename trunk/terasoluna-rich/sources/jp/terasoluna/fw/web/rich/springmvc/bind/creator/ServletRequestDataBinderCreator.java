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

/**
 * ServletRequestDataBinderを返却するクラスが実装すべきインタフェース。
 * 
 * <p>
 * XML形式で定義されたリクエストを扱う場合、通常は実装クラスとして、
 * XMLServletRequestDataBinderCreatorを利用すればよい。 <br>
 * クエリ形式で定義されたリクエストを扱う場合、通常は実装クラスとして、
 * QueryServletRequestDataBinderCreatorを利用すればよい。 <br>
 * 他形式で定義されたリクエストを扱う場合、もしくはデフォルトで用意されている実装クラスでは業務の要件が満たせない場合にのみ、
 * 本インタフェースを実装した業務要件を満たすクラスを作成すること。
 * </p>
 * 
 * @see QueryServletRequestDataBinderCreator
 * @see XMLServletRequestDataBinderCreator
 * @see org.springframework.web.bind.ServletRequestDataBinder
 */
public interface ServletRequestDataBinderCreator {

    /**
     * ServletRequestDataBinderを生成するクラスを返却する。
     * 
     * @param request リクエスト
     * @param command コマンドオブジェクト
     * @param requestName リクエスト名
     * @return ServletRequestDataBinderを生成するクラス
     */
    ServletRequestDataBinder create(HttpServletRequest request, Object command,
            String requestName);
}
