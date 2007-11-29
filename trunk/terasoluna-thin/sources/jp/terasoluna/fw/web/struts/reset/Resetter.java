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

package jp.terasoluna.fw.web.struts.reset;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.struts.form.FormEx;

import org.apache.struts.action.ActionMapping;

/**
 * アクションフォームのフィールドを初期化するインタフェース。
 *
 * {@link jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx}
 * 、{@link jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx}
 * を利用し、resetメソッドが実行されると、サーブレットコンテキストに
 * "RESETTER"という名前で保存されたこのインタフェースの実装クラスインスタンス
 * を取得し、{@link #reset(FormEx, ActionMapping, HttpServletRequest)}
 * メソッドを実行する。
 * アプリケーションの要件に合わせてこのインタフェースを実装すること。
 * <br>
 * また、TERASOLUNAフレームワークではこのインタフェースのデフォルトの
 * 実装クラス{@link jp.terasoluna.fw.web.struts.reset.ResetterImpl}
 * を用意している。典型的な初期化処理であればResetterImplを使用することが
 * できる。
 *
 * @see jp.terasoluna.fw.web.struts.reset.ResetterImpl
 */
public interface Resetter {

    /**
     * このクラスのインスタンスをサーブレットコンテキスト
     * に保存する際のキー。
     */
    public static final String RESETTER_KEY = "RESETTER";

    /**
     * アクションフォームのプロパティの値を初期化する。
     *
     * @param form アクションフォームインスタンス
     * @param mapping アクションマッピングインスタンス
     * @param request リクエスト情報
     */
    void reset(FormEx form,
               ActionMapping mapping,
               HttpServletRequest request);

}
