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

import java.util.Map;

/**
 * DBMessageResourcesがDBアクセスする際に用いられるDAOクラスが実装すべき
 * インタフェース。
 * 
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResources
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory
 * 
 */
public interface MessageResourcesDAO {

    /**
     * メッセージリソースを取得する。
     * 
     * 与えられたSQLの第一カラムをMapのキー、第二カラムをMapの値として
     * 設定したMapを返す。
     * SQLで返されたビューの第一カラムがuniqueでない場合の動作は保証しない。
     * 
     * @param sql メッセージリソースを取得するためのSQL
     * 
     * @return キーにメッセージキー、値にメッセージ文言の入ったMap
     */
    Map<String, String> queryMessageMap(String sql);

}
