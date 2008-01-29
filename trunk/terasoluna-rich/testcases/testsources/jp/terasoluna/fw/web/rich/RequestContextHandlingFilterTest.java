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

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import jp.terasoluna.utlib.MockFilterConfig;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.spring.SpringTestCase;
import jp.terasoluna.fw.web.rich.RequestContextHandlingFilter;

/**
 * {@link jp.terasoluna.fw.web.rich.RequestContextHandlingFilter} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ������̐����E�j�����s���T�[�u���b�g�t�B���^�B<br>
 * �O������FFilterConfig�AFilterChain�A�T�[�u���b�g���N�G�X�g�A�T�[�u���b�g���X�|���X��Null�l�ɂȂ�Ȃ��B�T�[�u���b�g���N�G�X�g�͕K��HTTP���N�G�X�g�ɃL���X�g�ł���B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.RequestContextHandlingFilter
 */
public class RequestContextHandlingFilterTest extends SpringTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(RequestContextHandlingFilterTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.SpringTestCase#doOnSetUp()
     */
    @Override
    protected void doOnSetUp() throws Exception {
        
    }

    /**
     * Bean��`�t�@�C���̏ꏊ���擾����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.SpringTestCase#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[]{
            "jp/terasoluna/fw/web/rich/RequestContextHandlingFilterTest.xml"
        };
    }

    /**
     * testInit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) config:FilterConfig�����N���X<br>
     *                �ogetInitParamter("ctxSupportBeanID"�j�Fnull�p<br>
     *         (���) this.ctxSupprt:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.ctxSupprt:context.getBean("ctxSupport")�̖߂�l�Ɠ��������Ƃ��m�F����B<br>
     *         
     * <br>
     * RequestContextSupport�N���X��BeanID���w�肹���A�f�t�H���g��BeanID���g�p����p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit01() throws Exception {
        // FilterConfig�̐ݒ�
        MockFilterConfig config = new MockFilterConfig();
        config.setServletContext(servletContext);

        // RequestContextHandlingFilter�̐���
        RequestContextHandlingFilter filter = new RequestContextHandlingFilter();

        // �e�X�g���{
        filter.init(config);

        // ����
        ApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(config.getServletContext());
        assertSame(context.getBean("ctxSupport"), UTUtil.getPrivateField(
                filter, "ctxSupport"));
    }

    /**
     * testInit02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:FilterConfig�����N���X<br>
     *                �ogetInitParamter("ctxSupportBeanID"�j�F"sampleCtxSupport"�p<br>
     *         (���) this.ctxSupprt:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.ctxSupprt:context.getBean("sampleCtxSupport")�̖߂�l�Ɠ��������Ƃ��m�F����B<br>
     *         
     * <br>
     * RequestContextSupport�N���X��BeanID�𒼐ڎw�肷��e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit02() throws Exception {
        // FilterConfig�̐ݒ�
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("ctxSupportBeanID", "sampleCtxSupport");
        config.setServletContext(servletContext);

        // RequestContextHandlingFilter�̐���
        RequestContextHandlingFilter filter = new RequestContextHandlingFilter();

        // �e�X�g���{
        filter.init(config);

        // ����
        ApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(config.getServletContext());
        assertSame(context.getBean("sampleCtxSupport"), UTUtil.getPrivateField(
                filter, "ctxSupport"));
    }

    /**
     * testDoFilter01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) chain:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ctxSupport.generateContext():���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B�������󂯎�������Ƃ��m�F����B<br>
     *         (��ԕω�) chain.doFilter():���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B�������󂯎�������Ƃ��m�F����B<br>
     *         (��ԕω�) ctxSupprt.destroyContext():���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �t�B���^�����̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter01() throws Exception {
        // RequestContextHandlingFilter�̐���
        RequestContextHandlingFilter filter = new RequestContextHandlingFilter();
        RequestContextHandlingFilter_RequestContextSupportStub01 support = new RequestContextHandlingFilter_RequestContextSupportStub01();
        UTUtil.setPrivateField(filter, "ctxSupport", support);

        // �����̐���
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        RequestContextHandlingFilter_FilterChainStub01 chain
            = new RequestContextHandlingFilter_FilterChainStub01();

        // �e�X�g���s
        filter.doFilter(req, res, chain);

        // ����        
        assertTrue(chain.isDoFilterCalled());
        assertTrue(support.isGenerateContextCalled());
        assertTrue(support.isDestroyContextCalled());
    }
}
