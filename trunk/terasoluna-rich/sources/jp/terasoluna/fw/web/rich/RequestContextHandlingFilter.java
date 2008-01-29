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

package jp.terasoluna.fw.web.rich;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.rich.context.support.RequestContextSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ������̐����E�j�����s���T�[�u���b�g�t�B���^�B
 * 
 * <p>
 * ���N�G�X�g�����J�n���ɐ�����𐶐����A���N�G�X�g�����I�����ɔj������B
 * ������̐���������`�F�C�����ꂽ�����̎��s���ɗ�O���������Ă��A�K��������͔j�������B
 *
 * ������̏ڍׂ́ARequestContextSupport���Q�Ƃ̂��ƁB
 * </p>
 * 
 * <p>
 * �{�T�[�u���b�g�t�B���^�𗘗p����ɂ́Aweb.xml�̐ݒ肪�K�v�ł���B
 * </p>
 * 
 * <p>
 * �y�f�v���C�����g�f�B�X�N���v�^�iweb.xml�j�̐ݒ��z<br>
 * <code><pre>
 *  &lt;filter&gt;
 *   &lt;filter-name&gt;requestContextHandlingFilter&lt;/filter-name&gt;
 *   &lt;filter-class&gt;
 *     jp.terasoluna.fw.web.rich.RequestContextHandlingFilter
 *   &lt;/filter-class&gt;
 *  &lt;/filter&gt;
 *  &lt;filter-mapping&gt;
 *    &lt;filter-name&gt;requestContextHandlingFilter&lt;/filter-name&gt;
 *    &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *  &lt;/filter-mapping&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * ������̎擾�̂��߁ADI�R���e�i���琧����T�|�[�g�N���X���擾����B
 * �擾����BeanID�́hctxSupport�h�Ƃ���B
 * ������RequestContextSupport���擾����ۂɗ��p����BeanID���J�X�^�}�C�Y�������ꍇ�́A
 * �t�B���^�̏������p�����[�^�uctxSupportBeanID�v��
 * RequestContextSupport���擾���邽�߂�Bean�����L�q���邱�ƂŒ��ڎw�肷�邱�Ƃ��ł���B
 * �ʏ�́A�������p�����[�^�̎w��͕s�v�ł���B
 * </p>
 * 
 * <p>
 * �yinitParameter�𗘗p�����f�v���C�����g�f�B�X�N���v�^�iweb.xml�j�̐ݒ��z<br>
 * <code><pre>
 *  &lt;filter&gt;
 *     &lt;filter-name&gt;requestContextHandlingFilter&lt;/filter-name&gt;
 *     &lt;filter-class&gt;
 *       jp.terasoluna.fw.web.rich.RequestContextHandlingFilter
 *     &lt;/filter-class&gt;
 *     &lt;init-param&gt;
 *       &lt;param-name&gt;ctxSupportBeanID&lt;/param-name&gt;
 *       &lt;param-value&gt;newCtxSupport&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 *   &lt;/filter&gt;
 *   &lt;filter-mapping&gt;
 *     &lt;filter-name&gt;requestContextHandlingFilter&lt;/filter-name&gt;
 *     &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *   &lt;/filter-mapping&gt;
 * </pre></code>
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.context.support.RequestContextSupport
 * 
 */
public class RequestContextHandlingFilter implements Filter {
    
    /**
     * DI�R���e�i����RequestContextSupport���擾����ۂ�BeanID�̃f�t�H���g�l�B
     */
    private static final String DEFAULT_CTXSUPPORT_BEANID = "ctxSupport";
    
    /**
     * �������p�����[�^����RequestContextSupport��BeanID���擾���邽�߂̃L�[�B
     */
    private static final String INITPARAM_KEY_CTXSUPPORT_BEANID
                                                     = "ctxSupportBeanID";
    
    /**
     * RequestContextSupport�B
     */
    private RequestContextSupport ctxSupport = null;

    /**
     * �t�B���^�̏��������s���B
     * RequestContextSupport��DI�R���e�i���擾����B
     * 
     * @param config �t�B���^�ݒ�
     * @throws ServletException �T�[�u���b�g��O
     */
    public void init(FilterConfig config) throws ServletException {
        String ctxSupportBeanID
            = config.getInitParameter(INITPARAM_KEY_CTXSUPPORT_BEANID);
        if (ctxSupportBeanID == null) {
            ctxSupportBeanID = DEFAULT_CTXSUPPORT_BEANID;
        }
        // �R���e�L�X�g�̎擾
        ApplicationContext context 
            = WebApplicationContextUtils.getWebApplicationContext(
                config.getServletContext());
        this.ctxSupport
            = (RequestContextSupport) context.getBean(ctxSupportBeanID);
    }

    /**
     * �t�B���^��������B
     * ������̓o�^�E�j�����s���B
     * 
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @param chain �`�F�C�����ꂽ�t�B���^
     * @throws IOException ���o�͗�O
     * @throws ServletException �T�[�u���b�g��O
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {
            // �R���e�L�X�g�̐���
            ctxSupport.generateContext((HttpServletRequest) request);
            chain.doFilter(request, response);
        } finally {
            // �R���e�X�g�̍폜
            ctxSupport.destroyContext();
        }
    }

    /**
     * �t�B���^�̔j���������s���B
     * �Ȃɂ����Ȃ��B
     */
    public void destroy() {
    }
}
