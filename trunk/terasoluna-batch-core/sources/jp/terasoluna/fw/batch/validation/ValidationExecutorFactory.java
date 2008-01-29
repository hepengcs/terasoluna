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

import org.springframework.validation.Validator;

import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.CollectedDataHandlerFactory;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * 入力チェック用チャンカーを生成するファクトリクラス。
 *
 */
public class ValidationExecutorFactory 
    implements CollectedDataHandlerFactory {

    /**
     * 対象データ用チャンク作成用クラス。
     */
    private CollectedDataHandlerFactory collectedDataHandlerFactory = null;
    
    /**
         * バリデータ。
         */
    private Validator validator = null;
    
        /**
        * バリデータ結果ハンドラ。
        */
    private ValidationResultHandler validationResultHandler = null;
    
    /**
     * 入力チェック処理用チャンカーを生成する。
     *
     * @param workQueue チャンカーが生成したチャンクをセットするキュー
     * @param jobContext ジョブコンテクスト
     * @return チャンカー
     */
    public CollectedDataHandler getHandler(WorkQueue workQueue,
            JobContext jobContext) {
        CollectedDataHandler handler =
            collectedDataHandlerFactory.getHandler(workQueue, jobContext);
        return 
          new ValidationExecutor(handler , validator , validationResultHandler);
    }

    /**
     * バリデータ結果ハンドラを設定する。
     * 
     * @param validationResultHandler バリデータ結果ハンドラ
     */
    public void setValidationResultHandler(
            ValidationResultHandler validationResultHandler) {
        this.validationResultHandler = validationResultHandler;
    }

    /**
     * バリデータを設定する。
     * 
     * @param validator バリデータ
     */
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * 対象データ用チャンク作成用クラスを設定する。
     * 
     * @param collectedDataHandlerFactory 対象データ用チャンク作成用クラス
     */
    public void setCollectedDataHandlerFactory(
            CollectedDataHandlerFactory collectedDataHandlerFactory) {
        this.collectedDataHandlerFactory = collectedDataHandlerFactory;
    }
}
