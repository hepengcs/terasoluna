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

import jp.terasoluna.fw.web.rich.exception.ForbiddenURIException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * ������Ă���URI�ȊO�ւ̃A�N�Z�X���֎~����t�B���^�B
 *
 * <p>
 * ForbiddenURIChecker�ŋ����ꂽURI�ȊO�͊O������A�N�Z�X�ł��Ȃ��悤�ɂ���B
 * </p>
 *
 * <p>�ȉ��̂悤�� web.xml�Ƀt�B���^�}�b�s���O��`���s���B</p>
 * 
 * <p>
 * �y�f�v���C�����g�f�B�X�N���v�^�iweb.xml�j�̐ݒ��z<br>
 * <code><pre>
 *   &lt;filter&gt;
 *     &lt;filter-name&gt;forbiddenURIFilter&lt;/filter-name&gt;
 *     &lt;filter-class&gt;jp.terasoluna.fw.web.rich.ForbiddenURIFilter&lt;/filter-class&gt;
 *   &lt;/filter&gt;
 *   &lt;filter-mapping&gt;
 *     &lt;filter-name&gt;forbiddenURIFilter&lt;/filter-name&gt;
 *     &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *   &lt;/filter-mapping&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * ����URI�̃`�F�b�N�̂��߁ADI�R���e�i����ForbiddenURIChecker���擾����B
 * �擾����BeanID�́hforbiddenURIChecker�h�Ƃ���B
 * ������ForbiddenURIChecker���擾����ۂɗ��p����BeanID���J�X�^�}�C�Y�������ꍇ�́A
 * �t�B���^�̏������p�����[�^�u�hcheckerBeanID�h�v��
 * ForbiddenURIChecker���擾���邷�邽�߂�Bean�����L�q���邱�ƂŒ��ڎw�肷�邱�Ƃ��ł���B
 * �ʏ�́AinitParamer�̎w��͕s�v�ł���B
 * </p>
 * 
 * <p>
 * �yinitParameter�𗘗p�����f�v���C�����g�f�B�X�N���v�^�iweb.xml�j�̐ݒ��z<br>
 * <code><pre>
 *   &lt;filter&gt;
 *     &lt;filter-name&gt;forbiddenURIFilter&lt;/filter-name&gt;
 *     &lt;filter-class&gt;jp.terasoluna.fw.web.rich.ForbiddenURIFilter&lt;/filter-class&gt;
 *     &lt;init-param&gt;
 *       &lt;param-name&gt;checkerBeanID&lt;/param-name&gt;
 *       &lt;param-value&gt;newChecker&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 *   &lt;/filter&gt;
 *   &lt;filter-mapping&gt;
 *     &lt;filter-name&gt;forbiddenURIFilter&lt;/filter-name&gt;
 *     &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *   &lt;/filter-mapping&gt;
 * </pre></code>
 * </p>
 *
 * <p>
 * ���N�G�X�g ��URI��
 * �A�N�Z�X��������URI�ł͂Ȃ������ꍇ�ɂ́A
 * ForbiddenURIException���X���[����B
 * �X���[���ꂽ��O�́A�T�[�u���b�g��Servlet���񋟂��Ă���G���[�y�[�W�̋@�\�𗘗p���邱�ƁB
 * SimpleMappingExceptionResolverEx���Q�Ƃ̂��ƁB
 * </p>
 * 
 * <p>
 * �y�f�v���C�����g�f�B�X�N���v�^�iweb.xml�j�̐ݒ��z<br>
 * <code><pre>
 *   &lt;error-page&gt;
 *       &lt;exception-type&gt;jp.terasoluna.fw.web.rich.exception.ForbiddenURIException&lt;/exception-type&gt;
 *       &lt;location&gt;/error/forbidden-uri-error.jsp&lt;/location&gt;
 *   &lt;/error-page&gt;
 * �@�@�� ���炩���ߌŒ�̃G���[�d�����L�q����/error/forbidden-uri-error.jsp��p�ӂ��Ă������ƁB
 * </pre></code>
 * </p>
 *
 * <p>
 * �s���ȃA�N�Z�X��}�~����t�B���^�ł��邽�߁A
 * �o���邾�������^�C�~���O�ōŏ��Ɏ��s�����悤�ɐݒ肷�邱�Ƃ��]�܂����B
 * </p>
 *
 */
public class ForbiddenURIFilter implements Filter {
    
    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(ForbiddenURIFilter.class);
    
    /**
     * DI�R���e�i����֎~URI�`�F�b�J���擾����ۂ�BeanID�̃f�t�H���g�l�B
     */
    private static final String DEFAULT_CHECKER_BEANID = 
        "forbiddenURIChecker";
    
    /**
     * �������p�����[�^����֎~URI�`�F�b�J��BeanID���擾���邽�߂̃L�[�B
     */
    private static final String INITPARAM_KEY_CHECKER_BEANID = 
        "checkerBeanID";

    /**
     * �֎~URI�`�F�b�J�B
     */
    private ForbiddenURIChecker checker = null;

    /**
     * �t�B���^�̏��������s���B
     * �֎~URI�`�F�b�J��DI�R���e�i���擾����B
     * 
     * @param config �t�B���^�ݒ�
     * @throws ServletException �T�[�u���b�g��O
     */
    public void init(FilterConfig config) throws ServletException {
        String checkerBeanID
            = config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID);
        if (checkerBeanID == null) {
            checkerBeanID = DEFAULT_CHECKER_BEANID;
        }
        // �R���e�L�X�g�̎擾
        ApplicationContext context 
            = WebApplicationContextUtils.getWebApplicationContext(
                config.getServletContext());
        try {
            this.checker = 
                (ForbiddenURIChecker) context.getBean(
                        checkerBeanID,
                        ForbiddenURIChecker.class);
        } catch (BeanNotOfRequiredTypeException e) {
            log.error("the bean is not of the required type"
                    + " - ForbiddenURIChecker.");
            throw e;
        } catch (NoSuchBeanDefinitionException e) {
            log.error("there's no such bean definition. BeanID="
                    + checkerBeanID + ".");
            throw e;
        } catch (BeansException e) {
            log.error("the bean could not be created. BeanID="
                    + checkerBeanID + ".");
            throw e;
        }
    }

    /**
     * �t�B���^��������B �֎~���ꂽURI���`�F�b�N����B
     * 
     * @param req HTTP���N�G�X�g
     * @param res HTTP���X�|���X
     * @param chain �`�F�C�����ꂽ�t�B���^
     * @throws IOException ���o�͗�O
     * @throws ServletException �T�[�u���b�g��O
     */
    public void doFilter(
            ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // Request�ɐݒ肳��Ă���URI�擾
        String requestURI =
            ((HttpServletRequest) req).getRequestURI().replaceFirst(
                    ((HttpServletRequest) req).getContextPath(), "");
        // �֎~���ꂽURI���`�F�b�N
        if (!checker.isAllowedURI(requestURI)) {
            log.error("request url is forbidden!");
            // �֎~����Ă���URI��������O�X���[
            throw new ForbiddenURIException();
        }
        chain.doFilter(req, res);
    }

    /**
     * �t�B���^�̔j���������s���B
     * �Ȃɂ����Ȃ��B
     */
    public void destroy() {
    }
}
