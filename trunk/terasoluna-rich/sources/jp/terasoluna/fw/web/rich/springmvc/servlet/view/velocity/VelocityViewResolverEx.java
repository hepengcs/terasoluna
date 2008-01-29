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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.velocity;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.velocity.VelocityView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

/**
 * エンコーディング指定が可能なVelocityViewResolver拡張クラス。
 * 
 * SpringのVelocityViewResolverを拡張し、
 * 生成するViewのエンコーディング指定を可能にしている。
 * 
 * <p>
 * VelocityViewResolverで設定可能なプロパティに加え、
 * 以下のプロパティを設定すること。
 * </p>
 * 
 * <p>
 *   <table border="1" CELLPADDING="8">
 *     <th>属性名</th>
 *     <th>説明</th>
 *  
 *     <tr>
 *       <td align=center><b>encoding</b></td>
 *       <td>Viewのエンコーディング設定</td>
 *     </tr>
 *  </table>
 * 
 * </p>
 *  【Bean定義ファイルの設定例】<br>
 * <code><pre>
 *   &lt;bean id="viewResolver" 
 *           class="jp.terasoluna.fw.web.rich.springmvc.servlet.view.velocity.VelocityViewResolverEx"&gt;
 *       &lt;property name="cache"&gt;&lt;value&gt;true&lt;/value&gt;&lt;/property&gt;
 *       &lt;property name="requestContextAttribute" value="rc"/&gt;
 *       &lt;property name="prefix"&gt;&lt;value&gt;&lt;/value&gt;&lt;/property&gt;
 *       &lt;property name="suffix"&gt;&lt;value&gt;.vm&lt;/value&gt;&lt;/property&gt;
 *       &lt;property name="exposeSpringMacroHelpers"&gt;&lt;value&gt;true&lt;/value&gt;&lt;/property&gt;
 *       &lt;property name="contentType"&gt;&lt;value&gt;text/xml;charset=UTF-8&lt;/value&gt;&lt;/property&gt;
 *       &lt;property name="encoding"&gt;&lt;value&gt;UTF-8&lt;/value&gt;&lt;/property&gt;
 *   &lt;/bean&gt;
 * </pre></code>
 * 
 */
public class VelocityViewResolverEx extends VelocityViewResolver {
    
    /**
     * Viewのエンコーディング。
     */ 
    private String encoding = null;
    
    /**
     * Viewのエンコーディングを取得する。
     * @return Viewのエンコーディング。
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Viewのエンコーディングを設定する。
     * @param encoding Viewのエンコーディング。
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Viewインスタンスを作成する。
     * 生成したViewインスタンスにエンコーディングの設定を行う。
     * 
     * @param viewName view名
     * @return Viewインスタンス
     * @throws Exception 例外
     */
    @Override
    protected AbstractUrlBasedView buildView(String viewName)
            throws Exception {
        VelocityView view = (VelocityView) super.buildView(viewName);
        if (encoding != null) {
            view.setEncoding(encoding);
        }
        return view;
    }
}
