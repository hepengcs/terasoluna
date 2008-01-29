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

import javax.servlet.ServletContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockFilterConfig;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.spring.SpringTestCase;
import jp.terasoluna.fw.web.rich.ForbiddenURIChecker;
import jp.terasoluna.fw.web.rich.ForbiddenURIFilter;
import jp.terasoluna.fw.web.rich.exception.ForbiddenURIException;

/**
 * {@link jp.terasoluna.fw.web.rich.ForbiddenURIFilter} 
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ������Ă���URI�ȊO�ւ̃A�N�Z�X���֎~����t�B���^�B<br>
 * init���\�b�h�AdoFilter���\�b�h�̈�������not null�Ƃ���B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.ForbiddenURIFilter
 */
public class ForbiddenURIFilterTest extends SpringTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ForbiddenURIFilterTest.class);
    }
    
    @Override
    protected void doOnSetUp() throws Exception {
        LogUTUtil.flush();
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {
            "jp/terasoluna/fw/web/rich/ForbiddenURIFilterTest.xml" 
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
     * ���͒l�F(���) context.getBean("forbiddenURIChecker"):ForbiddenChecker�C���X�^���X<br>
     *         (���) config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID):null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) checker:getBean�̖߂�l�Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * getBean�̖߂�l�𐳏�ɑ����ɐݒ肷�邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit01() throws Exception {
        // �O����
        // ForbiddenURIFilter
        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // FilterConfig
        MockFilterConfig config = new MockFilterConfig();
        
        // config.servletContext : this
        ServletContext context = this.servletContext;
        
        // getBean��ForbiddenURIChecker�����N���X�̃C���X�^���X��Ԃ�
        // WebApplicationContext�����N���X
        WebApplicationContextImpl01 webContext =
            new WebApplicationContextImpl01();
        
        // ForbiddenURIChecker�����N���X
        ForbiddenURIFilter_ForbiddenURICheckerStub01 checker =
            new ForbiddenURIFilter_ForbiddenURICheckerStub01();
        UTUtil.setPrivateField(webContext, "checker", checker);
        
        context.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webContext); 
        
        config.setServletContext(context);
        
        // config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID) : null
        config.setInitParameter("checkerBeanID", null);
                
        // �e�X�g���{
        filter.init(config);
        
        // ����
        // getBean�̖߂�l�Ɠ���̃C���X�^���X
        assertSame(checker, UTUtil.getPrivateField(filter, "checker"));
        
        // �����Ăяo���m�F
        // name
        assertEquals("forbiddenURIChecker", webContext.name);
        
        // requiredType : �\�[�X�R�[�h�łׂ���������Ă���̂Ńe�X�g�̈Ӗ��͂Ȃ�
        assertEquals(ForbiddenURIChecker.class, webContext.requiredType);
    }

    /**
     * testInit02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) context.getBean("forbiddenURIChecker"):ForbiddenChecker�ȊO�̃C���X�^���X<br>
     *         (���) config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID):null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:BeanNotOfRequiredTypeException<br>
     *         (��ԕω�) ���O:�G���[���x��<br>
     *                    the bean is not of the required type - ForbiddenURIChecker.<br>
     *         
     * <br>
     * getBean�̖߂�l���z��O�̌^�������ꍇ�ABeanNotOfRequiredTypeException���X���[���邱��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit02() throws Exception {
        // �O����
        // ForbiddenURIFilter
        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // FilterConfig
        MockFilterConfig config = new MockFilterConfig();
        
        // config.servletContext : this
        ServletContext context = this.servletContext;
        
        // getBean��BeanNotOfRequiredTypeException��O���N����
        // WebApplicationContext�����N���X
        WebApplicationContextImpl02 webContext =
            new WebApplicationContextImpl02();
        
        context.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webContext);
        
        config.setServletContext(context);
        
        // config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID) : null
        config.setInitParameter("checkerBeanID", null);
        
        
        try {
            // �e�X�g���{
            filter.init(config);
            fail();
        } catch (BeanNotOfRequiredTypeException e) {
            // ����
            // log : error
            assertTrue(LogUTUtil.checkError("the bean is not of the required type - ForbiddenURIChecker."));
            
            // name
            assertEquals("forbiddenURIChecker", webContext.name);
            
            // requiredType : �\�[�X�R�[�h�łׂ���������Ă���̂Ńe�X�g�̈Ӗ��͂Ȃ�
            assertEquals(ForbiddenURIChecker.class, webContext.requiredType);
        }
    }

    /**
     * testInit03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) context.getBean("forbiddenURIChecker"):null<br>
     *         (���) config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID):null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NoSuchBeanDefinitionException<br>
     *         (��ԕω�) ���O:�G���[���x��<br>
     *                    there's no such bean definition. BeanID=checkerBeanID.<br>
     *         
     * <br>
     * getBean�Ŏ擾�ł��Ȃ������ꍇ�ANoSuchBeanDefinitionException���X���[���邱��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit03() throws Exception {
        // �O����
        // ForbiddenURIFilter
        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // FilterConfig
        MockFilterConfig config = new MockFilterConfig();
        
        // config.servletContext : this
        ServletContext context = this.servletContext;
        
        // getBean��NoSuchBeanDefinitionException��O���N����
        // WebApplicationContext�����N���X
        WebApplicationContextImpl03 webContext =
            new WebApplicationContextImpl03();
        
        context.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webContext);
        
        config.setServletContext(context);
        
        // config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID) : null        
        config.setInitParameter("checkerBeanID", null);
        
        
        try {
            // �e�X�g���{
            filter.init(config);
            fail();
        } catch (NoSuchBeanDefinitionException e) {
            // ����
            // log : error
            assertTrue(LogUTUtil.checkError("there's no such bean definition. BeanID=forbiddenURIChecker."));
            
            // name
            assertEquals("forbiddenURIChecker", webContext.name);
            
            // requiredType : �\�[�X�R�[�h�łׂ���������Ă���̂Ńe�X�g�̈Ӗ��͂Ȃ�
            assertEquals(ForbiddenURIChecker.class, webContext.requiredType);
        }
    }

    /**
     * testInit04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) context.getBean("forbiddenURIChecker"):�����ł��Ȃ��C���X�^���X<br>
     *         (���) config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID):null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:BeansException<br>
     *         (��ԕω�) ���O:�G���[���x��<br>
     *                    the bean could not be created. BeanID=checkerBeanID.<br>
     *         
     * <br>
     * getBean�ŃC���X�^���X�������ł��Ȃ������ꍇ�ABeansException���X���[���邱��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit04() throws Exception {
        // �O����
        // ForbiddenURIFilter
        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // FilterConfig
        MockFilterConfig config = new MockFilterConfig();
        
        // config.servletContext : this
        ServletContext context = this.servletContext;
        
        // getBean��NoSuchBeanDefinitionException��O���N����
        // WebApplicationContext�����N���X
        WebApplicationContextImpl04 webContext =
            new WebApplicationContextImpl04();
        
        context.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webContext);
        
        config.setServletContext(context);
        
        // config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID) : null
        config.setInitParameter("checkerBeanID", null);
        
        
        try {
            // �e�X�g���{
            filter.init(config);
            fail();
        } catch (BeansException e) {
            // ����
            // log : error
            assertTrue(LogUTUtil.checkError("the bean could not be created. BeanID=forbiddenURIChecker."));
            
            // name
            assertEquals("forbiddenURIChecker", webContext.name);
            
            // requiredType : �\�[�X�R�[�h�łׂ���������Ă���̂Ńe�X�g�̈Ӗ��͂Ȃ�
            assertEquals(ForbiddenURIChecker.class, webContext.requiredType);
            
            // ��O��ފm�F�F
            // BeansException������OForbiddenURIFilter_BeansExceptionStub01
            assertEquals(
                    ForbiddenURIFilter_BeansExceptionStub01.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testInit05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) context.getBean("ABC"):ForbiddenChecker�C���X�^���X<br>
     *         (���) config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID):"ABC"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) checker:getBean�̖߂�l�Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * getBean�̖߂�l�𐳏�ɑ����ɐݒ肷�邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit05() throws Exception {
        // �O����
        // ForbiddenURIFilter
        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // FilterConfig
        MockFilterConfig config = new MockFilterConfig();
        
        // config.servletContext : this
        ServletContext context = this.servletContext;
        
        // getBean��ForbiddenURIChecker�����N���X�̃C���X�^���X��Ԃ�
        // WebApplicationContext�����N���X
        WebApplicationContextImpl01 webContext =
            new WebApplicationContextImpl01();
        
        // ForbiddenURIChecker�����N���X
        ForbiddenURIFilter_ForbiddenURICheckerStub01 checker =
            new ForbiddenURIFilter_ForbiddenURICheckerStub01();
        UTUtil.setPrivateField(webContext, "checker", checker);
        
        context.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webContext);
        
        config.setServletContext(context);
        
        // config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID) : "ABC"
        config.setInitParameter("checkerBeanID", "ABC");
        
        
        // �e�X�g���{
        filter.init(config);

        
        // ����
        // getBean�̖߂�l�Ɠ���̃C���X�^���X
        assertSame(checker, UTUtil.getPrivateField(filter, "checker"));
        
        // name : "ABC"
        assertEquals("ABC", webContext.name);
        
        // requiredType : �\�[�X�R�[�h�łׂ���������Ă���̂Ńe�X�g�̈Ӗ��͂Ȃ�
        assertEquals(ForbiddenURIChecker.class, webContext.requiredType);
    }

    /**
     * testDoFilter01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) req:HTTP���N�G�X�g<br>
     *         (����) req.getRequestURI():/sample2/secure/blogic.do<br>
     *         (����) req.getContextPath():/sample2<br>
     *         (����) res:HTTP���X�|���X<br>
     *         (����) chain:�t�B���^�`�F�C��<br>
     *         (���) checker.isAllowedURI():true<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * checker.isAllowedURI()��true�̏ꍇ�A��O�����������ɏ������I�����邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter01() throws Exception {
        // �O����
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChainImpl01 chain = new FilterChainImpl01();
        
        // requestURI : "/sample2/secure/blogic.do"
        req.setRequestURI("/sample2/secure/blogic.do");
        // contextPath : "/sample2"
        req.setContextPath("/sample2");

        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // checker.isAllowedURI() : true
        ForbiddenURIFilter_ForbiddenURICheckerStub01 checker =
            new ForbiddenURIFilter_ForbiddenURICheckerStub01();
        checker.isAllowed = true;
        UTUtil.setPrivateField(filter, "checker", checker);

        // �e�X�g���{
        filter.doFilter(req, res, chain);

        // ����
        assertTrue(chain.isDoFilter);
    }

    /**
     * testDoFilter02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) req:HTTP���N�G�X�g<br>
     *         (����) req.getRequestURI():/sample2/secure/blogic.do<br>
     *         (����) req.getContextPath():/sample2<br>
     *         (����) res:HTTP���X�|���X<br>
     *         (����) chain:�t�B���^�`�F�C��<br>
     *         (���) checker.isAllowedURI():false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ForbiddenURIException�X���[<br>
     *         (��ԕω�) ���O:request url is forbidden!<br>
     *         
     * <br>
     * checker.isAllowedURI()��false�̏ꍇ�AForbiddenURIException���X���[����
     * ���Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter02() throws Exception {
        // �O����
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChainImpl01 chain = new FilterChainImpl01();
        
        // requestURI : "/sample2/secure/blogic.do"
        req.setRequestURI("/sample2/secure/blogic.do");
        // contextPath : "/sample2"
        req.setContextPath("/sample2");

        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // checker.isAllowedURI() : false
        ForbiddenURIFilter_ForbiddenURICheckerStub01 checker =
            new ForbiddenURIFilter_ForbiddenURICheckerStub01();
        checker.isAllowed = false;
        UTUtil.setPrivateField(filter, "checker", checker);

        try {
            // �e�X�g���{
            filter.doFilter(req, res, chain);
            fail();
        } catch (ForbiddenURIException e) {
            // ����
            assertTrue(LogUTUtil.checkError("request url is forbidden!"));
            assertFalse(chain.isDoFilter);
        }
    }

    /**
     * testDoFilter03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) req:HTTP���N�G�X�g<br>
     *         (����) req.getRequestURI():�󕶎�<br>
     *         (����) req.getContextPath():�󕶎�<br>
     *         (����) res:HTTP���X�|���X<br>
     *         (����) chain:�t�B���^�`�F�C��<br>
     *         (���) checker.isAllowedURI():true<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * HTTP���N�G�X�g�ɐݒ肳��Ă���URI���󕶎��ŁA�t�B���^�ݒ�̋���URI��
     * �󕶎��̏ꍇ�ł��Achecker.isAllowedURI()��true�̏ꍇ�A��O������������
     * �������I�����邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter03() throws Exception {
        // �O����
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChainImpl01 chain = new FilterChainImpl01();
        
        // requestURI : �󕶎�
        req.setRequestURI("");
        // contextPath : �󕶎�
        req.setContextPath("");

        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // checker.isAllowedURI() : true
        ForbiddenURIFilter_ForbiddenURICheckerStub01 checker =
            new ForbiddenURIFilter_ForbiddenURICheckerStub01();
        checker.isAllowed = true;
        UTUtil.setPrivateField(filter, "checker", checker);

        // �e�X�g���{
        filter.doFilter(req, res, chain);

        // ����
        assertTrue(chain.isDoFilter);
    }

    /**
     * testDoFilter04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) req:HTTP���N�G�X�g<br>
     *         (����) req.getRequestURI():/sample2<br>
     *         (����) req.getContextPath():/sample2<br>
     *         (����) res:HTTP���X�|���X<br>
     *         (����) chain:�t�B���^�`�F�C��<br>
     *         (���) checker.isAllowedURI():true<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * HTTP���N�G�X�g�ɐݒ肳��Ă���URI�ƃR���e�L�X�g���̕����񒷂��������ꍇ
     * �ł��Achecker.isAllowedURI()��true�̏ꍇ�A��O�����������ɏ������I������
     * ���Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter04() throws Exception {
        // �O����
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChainImpl01 chain = new FilterChainImpl01();
        
        // requestURI : "/sample2"
        req.setRequestURI("/sample2");
        // contextPath : "/sample2"
        req.setContextPath("/sample2");

        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // checker.isAllowedURI() : true
        ForbiddenURIFilter_ForbiddenURICheckerStub01 checker =
            new ForbiddenURIFilter_ForbiddenURICheckerStub01();
        checker.isAllowed = true;
        UTUtil.setPrivateField(filter, "checker", checker);

        // �e�X�g���{
        filter.doFilter(req, res, chain);

        // ����
        assertTrue(chain.isDoFilter);
    }
}