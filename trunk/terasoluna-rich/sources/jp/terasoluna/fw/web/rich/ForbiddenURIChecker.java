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

package jp.terasoluna.fw.web.rich;

/**
 * 文字列が許可するURIか判定するチェッカのインタフェース。
 * 
 * <p>
 * ForbiddenURIFilterより、
 * アプリケーションコンテキストを通して呼び出され、実行されることを想定する。
 * 
 * 本クラスの実装クラスをBean定義すること。定義例は実装クラスを参照すること。
 * 通常は実装クラスとして、ForbiddenURICheckerImplを利用すればよい。
 * ForbiddenURICheckerImplでは業務の要件が満たせない場合にのみ、
 * 本インタフェースを実装した業務要件を満たすクラスを作成すること。
 * </p>

 * 
 * @see jp.terasoluna.fw.web.rich.ForbiddenURIFilter
 * 
 */
public interface ForbiddenURIChecker {
    
    /**
     * 許可されているURIかチェックする。
     * 
     * @param requestURI チェック対象のURI
     * @return チェック結果（許可されていればtrue）
     */
    boolean isAllowedURI(String requestURI);
}
