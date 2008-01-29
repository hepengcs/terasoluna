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

import jp.terasoluna.fw.batch.core.CollectedDataHandler;

import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

/**
 * 入力チェック実行用、対象データのハンドラクラス。
 * 
 * <p>入力チェック処理後、対象データ用チャンクを作成する。</p>
 *
 */
public class ValidationExecutor implements CollectedDataHandler {

    /**
     * バリデータ。
     */
    private Validator validator = null;
    
    /**
     * バリデータ結果ハンドラ。
     */
    private ValidationResultHandler validationResultHandler = null;

    /**
     * 対象データ用チャンク作成用クラス。
     */
    private CollectedDataHandler collectedDataHandler;
    
    /**
     * コンストラクタ。
     * 
     * @param collectedDataHandler 対象データ用チャンク作成用クラス
     * @param validator バリデータ
     * @param validationResultHandler バリデータ結果ハンドラ
     */
    public ValidationExecutor(CollectedDataHandler collectedDataHandler,
            Validator validator, 
            ValidationResultHandler validationResultHandler) {
        this.collectedDataHandler = collectedDataHandler;
        this.validator = validator;
        this.validationResultHandler = validationResultHandler;
    }
    
    /**
     * 入力チェック後、処理対象データをキューに追加する。
     * バリデータ結果ハンドラから<code>false</code>が返却された場合はキューへの
     * 追加処理は行わない。
     *
     * @param collectedData 処理対象データ
     * @param index 処理対象データのインデックス
     */
    public void handle(Object collectedData, int index) {
        BindException bindException = new BindException(collectedData,
                collectedData.getClass().getName());
        validator.validate(collectedData, bindException);
        
        if (validationResultHandler.handle(bindException, collectedData)) {
            collectedDataHandler.handle(collectedData, index);
        }
    }

    /**
     * クローズ処理を行う。
     *
     */
    public void close() {
        collectedDataHandler.close();
    }
}
