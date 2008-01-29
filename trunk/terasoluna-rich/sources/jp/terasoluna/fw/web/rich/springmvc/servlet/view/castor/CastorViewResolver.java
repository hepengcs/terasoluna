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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor;

import java.util.Locale;

import jp.terasoluna.fw.oxm.mapper.OXMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * Castor用のViewResolver実装クラス。
 * ビュー名が空文字、またはNullの場合、Castorビューを使用する。
 * 
 * <p>DispacherServlet上にて複数のViewResolverを使用する場合があるため、
 * 使用するViewResolverの順序を定義するOrderedインタフェースを実装している。
 * 以下のように、Bean定義ファイルにてint型の{@link #order}属性を設定すると、
 * DispacherServletは{@link #order}値の昇順でViewResolverを使用する。
 * 
 * <p><strong>Bean定義ファイルの設定例</strong>
 * <pre><code>
 * &lt;!--
 *    Castor用View Resolver
 *  --&gt;
 * &lt;bean id="castorViewResolver" class="jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorViewResolver"&gt;
 *   &lt;property name="cache"&gt;&lt;value&gt;true&lt;/value&gt;&lt;/property&gt;
 *   &lt;property name="requestContextAttribute"&gt;&lt;value&gt;rc&lt;/value&gt;&lt;/property&gt;
 *   &lt;property name="contentType"&gt;&lt;value&gt;text/xml;charset=UTF-8&lt;/value&gt;&lt;/property&gt;
 *   &lt;property name="order"&gt;&lt;value&gt;2&lt;/value&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </code></pre>
 * 
 * @see org.springframework.web.servlet.DispatcherServlet
 */
public class CastorViewResolver extends UrlBasedViewResolver 
    implements Ordered, InitializingBean {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(CastorViewResolver.class);
    
    /**
     * このViewResolverクラスが使用される順番。
     */
    private int order = Integer.MAX_VALUE;
    
    /**
     * XML→オブジェクト変換クラス。
     */
    private OXMapper oxmapper = null;
    
    /**
     * ビュークラスとしてCastorViewクラスを設定するコンストラクタ。
     */
    public CastorViewResolver() {
        setViewClass(CastorView.class);
    }
    
    /**
     * orderを設定する。
     * @param order このViewResolverクラスが使用される順番。
     */
    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * orderを取得する。
     * @return このViewResolverクラスが使用される順番。
     */
    @Override
    public int getOrder() {
        return order;
    }
    
    /**
     * oxmapperを取得する。
     *
     * @return oxmapper属性
     */
    public OXMapper getOxmapper() {
        return oxmapper;
    }

    /**
     * oxmapperを設定する。
     *
     * @param oxmapper oxmapperに設定する値
     */
    public void setOxmapper(OXMapper oxmapper) {
        this.oxmapper = oxmapper;
    }
    
    /**
     * DIコンテナ起動時、本クラスがインスタンス化された直後に呼ばれるメソッド。
     * OXMapper（オブジェクト－XML変換クラス）が設定されていない場合、
     * 例外を投げる。
     */
    public void afterPropertiesSet() {
        // OXMapperが設定されていない場合、例外とする
        if (oxmapper == null) {
            log.error("OXMapper class isn't set in CastorViewResolver. " 
                    + "Check Spring Bean definition file.");
            throw new IllegalStateException(
                    "OXMapper class isn't set in CastorViewResolver. " 
                    + "Check Spring Bean definition file.");
        }   
    }

    /**
     * 生成するべきビュークラスを返す。
     * @see CastorView
     * @return CastorViewクラス。
     */
    @Override
    protected Class requiredViewClass() {
        return CastorView.class;
    }

    /**
     * ビューを読み込む。
     * ビュークラスのインスタンス化を{@link #buildView(String)}メソッドに
     * 委譲し、DIコンテナにて以下のメソッドを呼び出す。
     * <ul>
     * <li>ApplicationContextAwareクラスの<code>setApplicationContext</code>
     * <li>InitializingBeanクラスの<code>afterPropertiesSet</code>
     * </ul>
     * ビュー名が入力されている場合、Nullを返す。
     * @see #buildView(String)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
     * @param viewName ビュー名
     * @param locale ロケール
     * @return Castorビュー
     * @throws Exception 例外
     */
    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        CastorView view = (CastorView) buildView(viewName);
        
        // CastorViewが取得できない場合、Nullを返す
        if (view == null) {
            return null;
        }
        
        view.setOxmapper(this.oxmapper);
        view.setApplicationContext(getApplicationContext());
        view.afterPropertiesSet();
        return view;
    }

    /**
     * ビューを生成する。
     * ビュー名が入力されていない場合のみ、Castorビューを生成する。
     * ビュー名が入力されている場合、他のビューを使用するものと判断し、
     * Nullを返す。
     * @param viewName ビュー名
     * @return ビューインスタンス
     * @throws Exception 例外
     */
    @Override
    protected AbstractUrlBasedView buildView(String viewName) 
        throws Exception {
        // ビュー名が入力されている場合、Nullを返す
        if (StringUtils.hasText(viewName)) {
            return null;
        }
        return super.buildView(viewName);
    }
}
