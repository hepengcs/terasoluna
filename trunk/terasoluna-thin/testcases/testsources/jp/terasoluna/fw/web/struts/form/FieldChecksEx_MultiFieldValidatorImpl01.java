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

package jp.terasoluna.fw.web.struts.form;

/**
 * FieldChecksExTest08で使用するMultiFieldValidatorの実装クラス。
 * validateの戻り値を操作することができる。
 *
 *
 */
public class FieldChecksEx_MultiFieldValidatorImpl01 implements
        MultiFieldValidator {

    /**
     * validateメソッドの結果とする値。
     */
    public static boolean result = false;

    /**
     * validateメソッドの第一引数の値。
     */
    public static String value = null;

    /**
     * validateメソッドの第二引数の値。
     */
    public static String[] fields = null;

    /**
     * validateメソッドがコールされたカウント。
     */
    public static int validateCalledCount = 0;

    /**
     * インスタンス変数resultの値を返却する。
     * 引数の値をインスタンス変数にキャッシュする。
     */
    public boolean validate(String value, @SuppressWarnings("hiding") String[] fields) {
        FieldChecksEx_MultiFieldValidatorImpl01.value = value;
        FieldChecksEx_MultiFieldValidatorImpl01.fields = fields;
        FieldChecksEx_MultiFieldValidatorImpl01.validateCalledCount++;
        return FieldChecksEx_MultiFieldValidatorImpl01.result;
    }

}
