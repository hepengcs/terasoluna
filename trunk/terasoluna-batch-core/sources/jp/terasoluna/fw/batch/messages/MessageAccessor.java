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

package jp.terasoluna.fw.batch.messages;

/**
 * メッセージ取得クラスインタフェース
 *
 */
public interface MessageAccessor {

    /**
     * メッセージキーで指定したメッセージを取得する。
     *
     * @param code メッセージキー
     * @param args メッセージ中のプレースホルダに埋め込む文字列 
     * @return String メッセージ
     */
    String getMessage(String code, Object[] args);
}
