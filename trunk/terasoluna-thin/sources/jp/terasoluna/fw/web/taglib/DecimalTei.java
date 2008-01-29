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

package jp.terasoluna.fw.web.taglib;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * <p><code>decimal</code> タグのための <code>TagExtraInfo</code> クラス。</p>
 *
 * <p>{@link DecimalTag} クラスが作成するスクリプティング変数についての
 * 情報を提供する。</p>
 *
 * <p>作成されるスクリプティング変数の変数名や型、およびスコープについては、
 * {@link DecimalTag} クラスのドキュメントを参照のこと。</p>
 *
 * @see jp.terasoluna.fw.web.taglib.DecimalTag
 *
 */
public class DecimalTei extends TagExtraInfo {

    /**
     * <p>{@link DecimalTag} クラスがサポートするカスタムタグが作成する、
     * <code>id</code> 属性に対応したスクリプト変数に関する情報を取得する。</p>
     *
     * @param data カスタムタグの属性、およびその値の情報
     * @return カスタムタグのスクリプティング変数情報
     */
    @Override
    public VariableInfo[] getVariableInfo(TagData data) {

        if (data.getId() != null) {
            return new VariableInfo [] {
                new VariableInfo(data.getId(),
                                    "java.lang.String",
                                    true,
                                    VariableInfo.AT_BEGIN)};
        }
        return new VariableInfo [] {};
    }

}
