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

package jp.terasoluna.fw.web;


import java.io.Serializable;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.ClassUtil;
import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ログオンユーザ情報抽象クラス</p>
 *
 * <p>
 *  ログオン中のユーザ情報を表すオブジェクト(<code>UVO</code>)抽象クラスである。
 *  継承クラスの生成は、<code>ApplicationResources.properties</code>に
 *  <code>user.value.object</code>をキーとして継承クラス名を設定し、
 *  <code>createUserValueObject()</code>を行うことにより生成される。
 * </p>
 * <h5><code>ApplicationResources.properties</code>の設定例</h5>
 * <ul>
 *   <li>user.value.object=jp.terasoluna.sample.xxxx.SampleUVO</li>
 * </ul>
 * <h5> <code>SampleUVO</code> の実装例</h5>
 * <code><pre>
 * public class SampleUVO extends UserValueObject {
 *   // 必要に応じて実装
 *   public String[] getFieldNames() {
 *       return new String[]{"companyId", "userId", "address", ...};
 *   }
 *
 *   // 会社ID
 *   String companyId = null;
 *   // ユーザID
 *   String userId = null;
 *   // 住所
 *   String address = null;
 *   ・・・
 *   // フィールドのgetter、setter等
 *   ・・・
 * }
 * </pre></code>
 * <h5>ログオン業務ロジックの実装例</h5>
 * <code><pre>
 * public ResultBean execute(LogonBean params) {
 *    ・・・
 *    // UVOの生成
 *    SampleUVO uvo = (SampleUVO) UserValueObject.createUserValueObject();
 *    ・・・
 *    // UVOにユーザ情報を設定
 *    uvo.setCompanyId(companyId);
 *    uvo.setUserId(userId);
 *    uvo.setAddress(address);
 *    ・・・
 * }
 * </pre></code>
 *
 */
public abstract class UserValueObject implements Serializable {

    /**
     * ログクラス
     */
    private static Log log
          = LogFactory.getLog(UserValueObject.class);

    /**
     * <code>ApplicationResources</code> ファイルで
     * <code>UserValueObject</code> 継承クラスを指定するキー。
     */
    public static final String USER_VALUE_OBJECT_PROP_KEY = "user.value.object";

    /**
     * <code>UserValueObject</code> 継承クラスのインスタンス作成失敗
     * を示すエラーコード。
     */
    private static final String UVO_CLASS_ERROR = "errors.uvo.class";

    /**
     * <code>UserValueObject</code> 継承クラスを
     * HTTPセッションから取得する際に用いるキー。
     */
    public static final String USER_VALUE_OBJECT_KEY = "USER_VALUE_OBJECT";

    /**
     * ユーザ情報オブジェクトを生成する。
     *
     * @return ユーザ情報オブジェクト
     */
    public static UserValueObject createUserValueObject() {

        UserValueObject userValueObject = null;
        String className =
            PropertyUtil.getProperty(USER_VALUE_OBJECT_PROP_KEY);
        if (className != null) {
            try {
                userValueObject = (UserValueObject) ClassUtil.create(className);
            } catch (ClassLoadException e) {
                log.error("illegal uvo class:" + className, e);
                throw new SystemException(e, UVO_CLASS_ERROR, className);
            } catch (ClassCastException e) {
                log.error("illegal uvo class:" + className, e);
                throw new SystemException(e, UVO_CLASS_ERROR, className);
            }
        } else {
            log.error("specify " + USER_VALUE_OBJECT_PROP_KEY + ".");
            return null;
        }

        return userValueObject;
    }

}
