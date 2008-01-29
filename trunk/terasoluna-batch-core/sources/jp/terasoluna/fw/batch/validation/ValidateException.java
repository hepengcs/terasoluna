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

import jp.terasoluna.fw.batch.core.JobException;

/**
 * 入力チェック用例外クラス。
 * 
 * <p>入力チェック処理時、入力チェックエラーが検出された場合、
 * この例外を発生させ<code>Collector</code>処理を中断させる。</p>
 * <p>このクラスでは、<code>BindException</code>、例外発生時に入力されたしていた
 * 入力データ、を保持する。</p>
 * <p>入力チェックエラーの詳細な内容は原因例外の<code>BindException</code>を解析
 * することで分析することができる。</p>
 *
 */
public class ValidateException extends JobException {

    /**
     * Serializable用バージョンID
     */
    private static final long serialVersionUID = -841154748141051078L;

    /**
     * 入力データ
     */
    private Object inputData = null;

    /**
     * コンストラクタ。
     *
     * @param bindException 原因例外
     */
    public ValidateException(BindException bindException) {
        super(bindException);
    }

    /**
     * コンストラクタ。
     *
     * @param bindException 原因例外
     * @param inputData 入力データ
     */
    public ValidateException(BindException bindException, Object inputData) {
        super(bindException);
        this.inputData = inputData;
    }

    /**
     * 入力データを取得する。
     *
     * @return 入力データ
     */
    public Object getInputData() {
        return inputData;
    }

}
