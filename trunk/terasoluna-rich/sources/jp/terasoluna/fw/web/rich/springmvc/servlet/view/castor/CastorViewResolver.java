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
 * Castor�p��ViewResolver�����N���X�B
 * �r���[�����󕶎��A�܂���Null�̏ꍇ�ACastor�r���[���g�p����B
 * 
 * <p>DispacherServlet��ɂĕ�����ViewResolver���g�p����ꍇ�����邽�߁A
 * �g�p����ViewResolver�̏������`����Ordered�C���^�t�F�[�X���������Ă���B
 * �ȉ��̂悤�ɁABean��`�t�@�C���ɂ�int�^��{@link #order}������ݒ肷��ƁA
 * DispacherServlet��{@link #order}�l�̏�����ViewResolver���g�p����B
 * 
 * <p><strong>Bean��`�t�@�C���̐ݒ��</strong>
 * <pre><code>
 * &lt;!--
 *    Castor�pView Resolver
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
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(CastorViewResolver.class);
    
    /**
     * ����ViewResolver�N���X���g�p����鏇�ԁB
     */
    private int order = Integer.MAX_VALUE;
    
    /**
     * XML���I�u�W�F�N�g�ϊ��N���X�B
     */
    private OXMapper oxmapper = null;
    
    /**
     * �r���[�N���X�Ƃ���CastorView�N���X��ݒ肷��R���X�g���N�^�B
     */
    public CastorViewResolver() {
        setViewClass(CastorView.class);
    }
    
    /**
     * order��ݒ肷��B
     * @param order ����ViewResolver�N���X���g�p����鏇�ԁB
     */
    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * order���擾����B
     * @return ����ViewResolver�N���X���g�p����鏇�ԁB
     */
    @Override
    public int getOrder() {
        return order;
    }
    
    /**
     * oxmapper���擾����B
     *
     * @return oxmapper����
     */
    public OXMapper getOxmapper() {
        return oxmapper;
    }

    /**
     * oxmapper��ݒ肷��B
     *
     * @param oxmapper oxmapper�ɐݒ肷��l
     */
    public void setOxmapper(OXMapper oxmapper) {
        this.oxmapper = oxmapper;
    }
    
    /**
     * DI�R���e�i�N�����A�{�N���X���C���X�^���X�����ꂽ����ɌĂ΂�郁�\�b�h�B
     * OXMapper�i�I�u�W�F�N�g�|XML�ϊ��N���X�j���ݒ肳��Ă��Ȃ��ꍇ�A
     * ��O�𓊂���B
     */
    public void afterPropertiesSet() {
        // OXMapper���ݒ肳��Ă��Ȃ��ꍇ�A��O�Ƃ���
        if (oxmapper == null) {
            log.error("OXMapper class isn't set in CastorViewResolver. " 
                    + "Check Spring Bean definition file.");
            throw new IllegalStateException(
                    "OXMapper class isn't set in CastorViewResolver. " 
                    + "Check Spring Bean definition file.");
        }   
    }

    /**
     * ��������ׂ��r���[�N���X��Ԃ��B
     * @see CastorView
     * @return CastorView�N���X�B
     */
    @Override
    protected Class requiredViewClass() {
        return CastorView.class;
    }

    /**
     * �r���[��ǂݍ��ށB
     * �r���[�N���X�̃C���X�^���X����{@link #buildView(String)}���\�b�h��
     * �Ϗ����ADI�R���e�i�ɂĈȉ��̃��\�b�h���Ăяo���B
     * <ul>
     * <li>ApplicationContextAware�N���X��<code>setApplicationContext</code>
     * <li>InitializingBean�N���X��<code>afterPropertiesSet</code>
     * </ul>
     * �r���[�������͂���Ă���ꍇ�ANull��Ԃ��B
     * @see #buildView(String)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
     * @param viewName �r���[��
     * @param locale ���P�[��
     * @return Castor�r���[
     * @throws Exception ��O
     */
    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        CastorView view = (CastorView) buildView(viewName);
        
        // CastorView���擾�ł��Ȃ��ꍇ�ANull��Ԃ�
        if (view == null) {
            return null;
        }
        
        view.setOxmapper(this.oxmapper);
        view.setApplicationContext(getApplicationContext());
        view.afterPropertiesSet();
        return view;
    }

    /**
     * �r���[�𐶐�����B
     * �r���[�������͂���Ă��Ȃ��ꍇ�̂݁ACastor�r���[�𐶐�����B
     * �r���[�������͂���Ă���ꍇ�A���̃r���[���g�p������̂Ɣ��f���A
     * Null��Ԃ��B
     * @param viewName �r���[��
     * @return �r���[�C���X�^���X
     * @throws Exception ��O
     */
    @Override
    protected AbstractUrlBasedView buildView(String viewName) 
        throws Exception {
        // �r���[�������͂���Ă���ꍇ�ANull��Ԃ�
        if (StringUtils.hasText(viewName)) {
            return null;
        }
        return super.buildView(viewName);
    }
}
