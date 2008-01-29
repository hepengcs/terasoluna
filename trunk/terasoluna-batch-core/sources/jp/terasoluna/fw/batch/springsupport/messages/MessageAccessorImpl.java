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

package jp.terasoluna.fw.batch.springsupport.messages;

import org.springframework.context.support.ApplicationObjectSupport;

import jp.terasoluna.fw.batch.messages.MessageAccessor;

/**
 * メッセージ取得クラスの実装クラス<br>
 * Spring Frameworkのアプリケーションコンテキストに保持されているメッセージを取
 * 得するクラス<br>
 * org.springframework.context.support.ApplicationObjectSupportに定義されている
 * MessageSourceAccesor内のgetMessageメソッドを使用している
 * <br><br>
 * <strong>使用方法</strong><br>
 * このクラスを利用するには、Bean定義ファイルにてメッセージを利用するクラスのプ
 * ロパティとして設定する。<br>
 * <br>
 * <strong>設定例</strong><br>
 * ビジネスロジックでメッセージ取得用クラスを利用するためのBean定義ファイルの記
 * 述例<br>
 * 
 * <pre>
 * &lt;bean id = &quot;blogic&quot;
 *   class = &quot;jp.terasoluna.batch.sample.SampleBLogic&quot;&gt;
 *   &lt;property name = &quot;messageAccessor&quot;&gt;
 *     &lt;ref bean = &quot;messageAccessor&quot;&gt;&lt;/ref&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * フレームワークBean定義ファイルの定義<br>
 * 
 * <pre>
 * &lt;bean id = &quot;messageAccessor&quot;
 *   class = &quot;jp.terasoluna.fw.batch.springsupport.messages.MessageAccessorImpl&quot;&gt;&lt;/ref&gt;
 * </pre>
 * 
 * 
 */
public class MessageAccessorImpl extends ApplicationObjectSupport 
    implements MessageAccessor {

    /**
     * メッセージキーで指定したメッセージを取得する。
     * 指定されたメッセージIDに対応するメッセージが存在しない場合には
     * メッセージIDを返却する。
     * 
     * @param code メッセージキー
     * @param args メッセージ中のプレースホルダに埋め込む文字列 
     * @return メッセージ
     */
    public String getMessage(String code, Object[] args) {
        

         // デフォルトメッセージ(デフォルトメッセージとしてメッセージIDを設定)
        String defaultMessage = code;
        
        // メッセージを返却する
        return 
            getMessageSourceAccessor().getMessage(code, args, defaultMessage);

    }

}
