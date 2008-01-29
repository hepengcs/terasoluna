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

package jp.terasoluna.fw.batch.validation;


import org.springframework.validation.BindException;


/**
 * 入力チェック実行結果のハンドラインタフェース。
 * 
 * <p>このインタフェースは、入力チェック処理が起動される毎に、結果処理として起動
 * される</p>
 * 
 * <p>このインタフェースの実装では、パラメータとして渡された<code>BindException
 * </code>のエラー有無を判定し、キューへのデータ追加有無を決めることができる。
 * また、入力データに異常がある場合でも異常データを正常データに修正することで
 * 異常データのハンドリングも可能である。
 * <code>false</code>を返却した場合はキューへのデータ追加は行わない。</p>
 * <p>入力チェックのエラー時に処理を中断する場合は例外を発生させる必要がある。
 * </p>
 *
 */
public interface ValidationResultHandler {

    /**
     * ビジネスロジック実行結果を処理する。
     *
     * @param bindException バリデータによって処理された後のバインド例外
     * @param value ビジネスロジック入力データ
     * @return 指定されたデータをキューイングし、後続処理を行う場合には
     *  <code>true</code>。
     *  指定されたデータをキューイングせず（データを後続処理に流さない）スキップ
     *  する場合には、<code>false</code>
     */
    boolean handle(BindException bindException, Object value);
}
