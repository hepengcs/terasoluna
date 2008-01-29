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

package jp.terasoluna.fw.file.dao.standard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

/**
 * カラムフォーマット(ファイル書込）を行うクラス。
 * 
 * <p>
 * ファイル行オブジェクトからデータを取得し、文字列をFileUpdateDAOに返却する。
 * アノテーションの記述に従いDate型のフォーマット処理を行う。
 * </p>
 * 
 */
public class DecimalColumnFormatter implements ColumnFormatter {

    /**
     * BigDecimal型のフォーマット処理を行い、文字列を返却する。
     * 
     * @param t ファイル行オブジェクト
     * @param method カラムフォーマットを行う属性のゲッタメソッド
     * @param columnFormat カラムフォーマット用の文字列
     * @return 文字列
     * @throws IllegalArgumentException ファイル行オブジェクトのgetterメソッドのアクセスに失敗したとき
     * @throws IllegalAccessException ファイル行オブジェクトへの設定が失敗したとき
     * @throws InvocationTargetException ファイル行オブジェクトのメソッドが例外をスローしたとき
     */
    public String format(Object t, Method method, String columnFormat)
            throws IllegalArgumentException, IllegalAccessException,
                    InvocationTargetException {
        
        DecimalFormat decimalFormat = null;

        // 1000000(百万)⇒1,000,000に換える処理
        if (columnFormat != null && !"".equals(columnFormat)) {
            decimalFormat = new DecimalFormat(columnFormat);
        } else {
            decimalFormat = new DecimalFormat();
        }
        return decimalFormat.format(method.invoke(t));

    }
}
