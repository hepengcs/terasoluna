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

package jp.terasoluna.fw.web.rich.springmvc.servlet.handler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
/**
 * ��O�N���X���ƃr���[���̃}�b�s���O���s��Exception resolver�̊g���N���X�B
 * 
 * <p>
 * Spring���񋟂��� SimpleMappingExceptionResolver��
 * ����exceptionMappings�́A�ݒ荀�ڂ̏������̕ێ����o���Ȃ��B<br>
 * �{�N���X�́ASimpleMappingExceptionResolver���g�����A
 * ����ɏ������̕ێ����\�ȑ���linkedExceptionMappings��񋟂�����̂ł���B
 * </p>
 * 
 * <p>
 * ���N�G�X�g�R���g���[���Ŕ���������O���n���h�����O���A
 * Bean��`�t�@�C���ɏ]���X���[���ꂽ��O�^�ɑΉ�����View�C���X�^���X��
 * Model�C���X�^���X��ԋp����B<br>
 * Model�C���X�^���X�ɏ����i�[���鏈���́A{@link ExceptionResolveDelegator}�����N���X�ɈϏ�����B<br>
 * View�C���X�^���X�ŁAModel�C���X�^���X�Ɋi�[���ꂽ���𗘗p����
 * �G���[���X�|���X�̃����_�����O���s�����Ƃ�z�肵�Ă���B
 * </p>
 * 
 * <p>
 * ���X�|���X�ɗ�O�������������Ƃ�ʒm����w�b�_��ݒ肷��B<br>
 * �w�b�_�ɏ���ݒ肷�鏈���́A{@link ExceptionResolveDelegator}�����N���X�ɈϏ�����B
 * </p>
 * 
 * <p>
 * �{�N���X�𗘗p����ꍇ�A�{�N���X��Bean��`���s�����ƁB
 * �܂��A�ȉ��̃v���p�e�B��K���ݒ肷�邱�ƁB
 * </p>
 * 
 * <p>
 *   <table border="1" CELLPADDING="8">
 *     <th>������</th>
 *     <th>�K�{</th>
 *     <th>����</th>
 * 
 *     <tr>
 *       <td align=center><b>linkedExceptionMappings</b></td>
 *       <td>��</td>
 *       <td>
 *           ��O�N���X����View���̃}�b�s���O�imap�`���j�B<br>
 *           entry�̃L�[�́A��O�N���X����ݒ肷��B<br>
 *           entry�̒l�́A�g�p����{@link ExceptionResolveDelegator}�����N���X��javadoc���Q�l�ɐݒ肷�邱�ƁB
 *       </td>
 *     </tr>
 *     <tr>
 *       <td align=center><b>exceptionResolveDelegatorClass</b></td>
 *       <td>��</td>
 *       <td>
 *           ExceptionResolveDelegator�����N���X
 *       </td>
 *     </tr>
 *     <tr>
 *       <td align=center><b>exceptionResolveDelegatorParams</b></td>
 *       <td>&nbsp;</td>
 *       <td>
 *           ExceptionResolveDelegator�����N���X�����p������̃}�b�s���O�B<br>
 *           entry�̃L�[�́A��񖼁B<br>
 *           entry�̒l�́A���l�B
 *       </td>
 *     </tr>
 *     <tr>
 *       <td align=center><b>outputErrorLogHandledException</b></td>
 *       <td>&nbsp;</td>
 *       <td>
 *           �n���h�����O������O�̏����G���[���O�o�͂��邩������킷boolean�l�B<br>
 *           true�̏ꍇ�A�G���[���O�o�͂��s���B<br>
 *           �f�t�H���g��true�ł���B<br>
 *           �v���W�F�N�g���Ƃ̃��O�Ď��̗v���Ȃǂ�
 *           �n���h�����O������O�̏����G���[���O�o�͂������Ȃ��ꍇ�̂ݐݒ肷�邱�ƁB
 *           �ʏ�͖{�ݒ�𗘗p����K�v�͂Ȃ��B
 *       </td>
 *     </tr>
 *  </table>
 * 
 * </p>
 * ExceptionResolveDelegator�����N���X�Ƃ��āAExceptionResolveDelegatorImpl���g�p����ꍇ�̐ݒ����ȉ��ɋL���B
 * �y<code>Bean��`�t�@�C��</code>�̐ݒ��z<br>
 * <code><pre>
 *   &lt;bean id="handlerExceptionResolver"
 *       class="jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx"&gt;
 *     &lt;property name="linkedExceptionMappings"&gt;
 *       &lt;map&gt;
 *         &lt;entry key="jp.terasoluna.fw.web.rich.exception.UnknownRequestNameException"&gt;
 *           &lt;value&gt;exception,kind01,8004C003&lt;/value&gt;
 *         &lt;/entry&gt;
 *         &lt;entry key="org.springframework.validation.BindException"&gt;
 *           &lt;value&gt;bindException,kind02&lt;/value&gt;
 *         &lt;/entry&gt;
 *         &lt;entry key="jp.terasoluna.fw.service.rich.exception.SystemException"&gt;
 *           &lt;value&gt;systemException,kind03&lt;/value&gt;
 *         &lt;/entry&gt;
 *         &lt;entry key="jp.terasoluna.fw.service.rich.exception.ServiceException"&gt;
 *           &lt;value&gt;serviceException,kind04&lt;/value&gt;
 *         &lt;/entry&gt;
 *         &lt;entry key="java.lang.Exception"&gt;
 *           &lt;value&gt;exception,kind05,8004C999&lt;/value&gt;
 *         &lt;/entry&gt;
 *       &lt;/map&gt;
 *     &lt;/property&gt;
 *     &lt;property name="exceptionResolveDelegatorClass" value="jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl" /&gt;
 *     &lt;property name="exceptionResolveDelegatorParams"&gt;
 *       &lt;map&gt;
 *         &lt;entry key="errorTypeHeaderName"&gt;
 *           &lt;value&gt;errorType&lt;/value&gt;
 *         &lt;/entry&gt;
 *       &lt;/map&gt;
 *     &lt;/property&gt; 
 *   &lt;/bean&gt;
 * </pre></code>
 * 
 * <p>
 * ���Ƃ��΁A��L�̐ݒ���s������ŁA
 * UnknownRequestNameException���X���[����Ă����ꍇ�A
 * �uexception�v�Ƃ������̂̃r���[�ƁA
 * �X���[���ꂽ��O�C���X�^���X�ƁA�G���[�R�[�h������u8004C003�v��
 * �i�[�������f����ԋp����B<br>
 * ���X�|���X�w�b�_�ɂ́A�uerrorType�v���L�[�Ƃ��āukind01�v��ݒ肷��B
 * </p>
 * 
 * <p>
 * SimpleMappingExceptionResolverEx�́A�X���[���ꂽ��O�̌^��
 * linkedExceptionMappings�̃L�[��ݒ�t�@�C���ɋL�q���ꂽ���ɔ�r����B
 * �ݒ�t�@�C���ɋL�q���ꂽ��O�^���A�X���[���ꂽ��O�^��
 * ����̌^�A�܂��͐e�̌^�������ꍇ�ɁA�Ή�����l�̖��̂�View�𐶐����ԋp����B
 * 
 * �X���[���ꂽ�^�ƈ�v����ݒ肪�����������ꍇ�́A
 * ����ɋL�q�����ݒ肪�̗p�����B
 * �Ō�ɑS�Ă̗�O�̐e�N���X�ƂȂ�java.lang.Exception�̐ݒ���s�����ƂŁA
 *�@�\�����ʗ�O�����������ꍇ�ł��K���Ajava.lang.Exception�̐ݒ��
 * �G���[���X�|���X�̃����_�����O���o����B
 * </p>
 * 
 * <p>
 * �����ŏ����\�ȗ�O�́A���N�G�X�g�R���g���[���Ŕ���������O�݂̂ł���B
 * View��Filter���ADispacherServlet�O�Ŕ���������O�̃n���h�����O�́A
 * �{�N���X�͐Ӗ��������Ȃ��B
 * �����̗�O���n���h�����O���邽�߂ɂ́AServlet���񋟂��Ă���
 * �G���[�y�[�W�̋@�\�𗘗p���邱�ƁB
 * ��O�̌^���ƂɔC�ӂ̌Œ�d�������X�|���X�Ƀ����_�����O���邱�Ƃ��o����B
 * </p>
 * 
 * <p>
 * �y<code>web.xml</code>�̐ݒ��z<br>
 * <code><pre>
 *   &lt;error-page&gt;
 *     &lt;exception-type&gt;java.lang.Exception&lt;/exception-type&gt;
 *     &lt;location&gt;/error/unknown-error.jsp&lt;/location&gt;
 *   &lt;/error-page&gt;
 * �@�@�� ���炩���ߌŒ�̃G���[�d�����L�q����/error/unknown-error.jsp��p�ӂ��Ă������ƁB
 * </pre></code>
 * </p>
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegator
 * 
 *
 */
public class SimpleMappingExceptionResolverEx extends SimpleMappingExceptionResolver implements InitializingBean {
    /**
     * ���O�N���X�B
     */
    private final Log log = LogFactory.getLog(getClass());
    
    /**
     * ��������ێ�������O��View���i���G���[���j�̃}�b�s���O�B
     */
    protected Map<String, Object> linkedExceptionMappings = null;

    /**
     * �{�N���X����������ׂ��n���h���̃Z�b�g�B
     * <p>�������ׂ��R���g���[���C���X�^���X��ݒ肷��B
     * {@link #resolveException(HttpServletRequest, HttpServletResponse, Object, Exception)}
     * ���\�b�h���Ŏg�p�����B�R���g���[���I�u�W�F�N�g�̔�r���s�����߁A
     * �R���g���[�����V���O���g���ݒ�ɂ��Ȃ���΂Ȃ�Ȃ��B</p>
     */
    protected Set mappedHandlers = null;

    /**
     * ��O�����������ꍇ�Ƀ��X�|���X�ɐݒ肷��G���[�R�[�h�B
     */
    protected Integer defaultStatusCode = null;
    
    /**
     * �X���[���ꂽ��O�ɑΉ�����ݒ肪���������ꍇ�Ɏ��s����View�̖��́B
     * linkedExceptionMappings�ŁAjava.lang.Exception�̒�`�����Ă����ꍇ�͕s�v�B
     */
    protected String defaultErrorView = null;

    /**
     * ExceptionResolveDelegator�����N���X�̌^
     */
    protected Class< ? extends ExceptionResolveDelegator> exceptionResolveDelegatorClass = jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl.class;
    
    /**
     * ��O�̌^���L�[��ExceptionResolveDelegator���i�[����Map
     */
    protected LinkedHashMap<String, ExceptionResolveDelegator> exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
    
    /**
     * ExceptionResolveDelegator�̃p�����[�^�[�̃L�[�ƒl���i�[����Map
     */
    protected Map<String, String> exceptionResolveDelegatorParams = null;
    
    /**
     * �n���h�����O������O�̏����G���[���O�o�͂��邩
     */
    protected boolean outputErrorLogHandledException = true;
    
    /**
     * �{�N���X���C���X�^���X�����ꂽ����ɌĂ΂�郁�\�b�h�B 
     * ExceptionResolveDelegator�𐶐����A�����Ɋi�[����B
     */
    public void afterPropertiesSet() {
        if (this.linkedExceptionMappings == null) {
            return;
        }

        // exceptionResolveDelegatorClass������null�`�F�b�N
        if (this.exceptionResolveDelegatorClass == null) {
            String message = "SimpleMappingExceptionResolverEx must be set exceptionResolveDelegatorClass. "
                    + "Check Spring Bean definition file.";
            log.error(message);
            throw new IllegalStateException(message);
        }

        for (String mappingKey : this.linkedExceptionMappings.keySet()) {
            ExceptionResolveDelegator exceptionResolveDelegator = null;
            try {
                // exceptionResolveDelegatorClass�����̃C���X�^���X���AExceptionResolveDelegator�^�ł��邱�Ƃ̃`�F�b�N
                if (!(ExceptionResolveDelegator.class
                        .isAssignableFrom(exceptionResolveDelegatorClass))) {
                    String message = exceptionResolveDelegatorClass.getName()
                            + " is not ExceptionResolveDelegator type. "
                            + "Check Spring Bean definition file.";
                    log.error(message);
                    throw new IllegalStateException(message);
                }
                exceptionResolveDelegator = exceptionResolveDelegatorClass
                        .newInstance();
            } catch (InstantiationException e) {
                // exceptionResolveDelegatorClass�����̃C���X�^���X���Ɏ��s�����ꍇ�A��O���X���[����
                String message = exceptionResolveDelegatorClass.getName()
                        + " cannot be instantiated. "
                        + "Check Spring Bean definition file.";
                log.error(message, e);
                throw new IllegalStateException(message, e);
            } catch (IllegalAccessException e) {
                // exceptionResolveDelegatorClass�����̃C���X�^���X���Ɏ��s�����ꍇ�A��O���X���[����
                String message = exceptionResolveDelegatorClass.getName()
                        + " cannot be instantiated. "
                        + "Check Spring Bean definition file.";
                log.error(message, e);
                throw new IllegalStateException(message, e);
            }
            // ExceptionResolveDelegator�ɁA�G���[�̌^�ƃG���[�����}�b�s���O����
            exceptionResolveDelegator.initMapping(mappingKey,
                    this.linkedExceptionMappings.get(mappingKey),
                    this.exceptionResolveDelegatorParams);
            exceptionResolveDelegatorMap.put(mappingKey,
                    exceptionResolveDelegator);
        }
    }   

    /**
     * �X���[���ꂽ��O�ɑΉ�����ݒ肪���������ꍇ�Ɏ��sView�̖��̂�ݒ肷��B
     * �e�N���X�ŕێ�����Ă��邪�A�{�N���X�ŎQ�Ƃł��Ȃ����߁A
     * �{�N���X�ł������Ƃ��ĊǗ�����B
     * 
     * @param defaultErrorView �X���[���ꂽ��O�ɑΉ�����ݒ肪���������ꍇ�Ɏ��sView�̖���
     */
    @Override
    public void setDefaultErrorView(String defaultErrorView) {
        super.setDefaultErrorView(defaultErrorView);
        this.defaultErrorView = defaultErrorView;
        if (logger.isInfoEnabled()) {
            logger.info("Default error view is '"
                            + this.defaultErrorView + "'");
        }
    }

    /**
     * ��O�ƃr���[���i���G���[���j�̃}�b�s���O��ێ�����N���X��ݒ肷��
     * @param exceptionResolveDelegatorClass ��O�ƃr���[���i���G���[���j�̃}�b�s���O��ێ�����N���X
     */
    public void setExceptionResolveDelegatorClass(
            Class< ? extends ExceptionResolveDelegator> exceptionResolveDelegatorClass) {
        this.exceptionResolveDelegatorClass = exceptionResolveDelegatorClass;
    }
     
    /**�@
     * ExceptionResolveDelegator�̃p�����[�^�[�̃L�[�ƒl���i�[����Map��ݒ肷��
     * @param exceptionResolveDelegatorParams ExceptionResolveDelegator�̃p�����[�^�[�̃L�[�ƒl���i�[����Map
     */
    public void setExceptionResolveDelegatorParams(
            Map<String, String> exceptionResolveDelegatorParams) {
        this.exceptionResolveDelegatorParams = exceptionResolveDelegatorParams;
    }
    
    /**
     * mappedHandlers��ݒ肷��B
     * �e�N���X�ŕێ�����Ă��邪�A�{�N���X�ŎQ�Ƃł��Ȃ����߁A
     * �{�N���X�ł������Ƃ��ĊǗ�����B
     * @param mappedHandlers mappedHandlers
     */
    @Override
    public void setMappedHandlers(Set mappedHandlers) {
        super.setMappedHandlers(mappedHandlers);
        this.mappedHandlers = mappedHandlers;
    }

    /**
     * ��O�����������ꍇ�Ƀ��X�|���X�ɐݒ肷��G���[�R�[�h��ݒ肷��B
     * 
     * @param defaultStatusCode HTTP�X�e�[�^�X�R�[�h�l
     */
    @Override
    public void setDefaultStatusCode(int defaultStatusCode) {
        super.setDefaultStatusCode(defaultStatusCode);
        this.defaultStatusCode = Integer.valueOf(defaultStatusCode);
    }
    
    /**
     * ��������ێ��ł��Ȃ���O��View���i���G���[���j�̃}�b�s���O��ݒ肷��B
     * ����ɏ�������ێ�����@�\��񋟂��Ă���̂ŁA
     * �K��UnsupportedOperationException���X���[����B
     * @deprecated �����linkedExceptionMappings�𗘗p���邱�ƁB
     * @param mappings ��O��View���̃}�b�s���O
     */
    @Deprecated
    @Override
    public void setExceptionMappings(Properties mappings) {
        throw new UnsupportedOperationException();
    }

    
    /**
     * ��������ێ����鏇������ێ�������O��View��(&�G���[���)��
     * �}�b�s���O��ݒ肷��B
     * @param linkedExceptionMappings ��������ێ�������O��View���i���G���[���j�̃}�b�s���O
     */
    public void setLinkedExceptionMappings(
            Map<String, Object> linkedExceptionMappings) {
        this.linkedExceptionMappings = linkedExceptionMappings;
    }
    
    /**
     * �n���h�����O������O�̏����G���[���O�o�͂��邩��boolean�l��ݒ肷��B
     * 
     * <p>
     * �f�t�H���g��true�Ȃ̂Ńv���W�F�N�g���Ƃ̃��O�Ď��̗v���Ȃǂ�
     * �n���h�����O������O�̏����G���[���O�o�͂������Ȃ��ꍇ�̂ݖ{���\�b�h�𗘗p���邱�ƁB
     * �ʏ�͖{���\�b�h�𗘗p����K�v�͂Ȃ��B
     * </p>
     * 
     * @param outputErrorLogHandledException false�Ȃ�Ώo�͂��Ȃ��B
     */
    public void setOutputErrorLogHandledException(boolean outputErrorLogHandledException) {
        this.outputErrorLogHandledException = outputErrorLogHandledException;
    }

    /**
     * �X���[���ꂽ��O�ɑΉ�����View��Model��ԋp����B
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @param handler �n���h��
     * @param ex �X���[���ꂽ��O
     * @return ���f��(��O�ƃG���[���(�C��)���i�[)�ƃr���[
     */
    @Override
    public ModelAndView resolveException (
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

        // �{�N���X���������ׂ��n���h����
        if (this.mappedHandlers != null
                && !this.mappedHandlers.contains(handler)) {
            return null;
        }

        // �X���[���ꂽ��O�̌^�ƒ�`����Ă����O�̌^���`�F�b�N
        ExceptionResolveDelegator exceptionResolveDelegator = null;
        for (String mappingKey : exceptionResolveDelegatorMap.keySet()) {
            int depth = getDepth(mappingKey, ex);
            if (depth >= 0) {
                exceptionResolveDelegator = exceptionResolveDelegatorMap
                        .get(mappingKey);
                break;
            }
        }

        // ��`����Ă��Ȃ���O���X���[����Ă����ꍇ�̏���
        if (exceptionResolveDelegator == null) {
            return null;
        }

        String viewName = exceptionResolveDelegator.getViewName();
        // �r���[�����ݒ肳��Ă��Ȃ��ꍇ�̏���
        if (viewName == null && this.defaultErrorView != null) {
            viewName = this.defaultErrorView;
        }

        if (viewName != null) {
            // �X���[���ꂽ��O����`����Ă���ꍇ�� ���O���o��
            if (this.outputErrorLogHandledException) {
                log.error("Handled the following exception.", ex);
            }

            // HTTP�G���[�X�e�[�^�X�����X�|���X�ɐݒ肷��
            if (this.defaultStatusCode != null) {
                response.setStatus(this.defaultStatusCode.intValue());
            }

            exceptionResolveDelegator.setHeader(response);

            // View�̌���
            ModelAndView mv = getModelAndView(viewName, ex);
            
            exceptionResolveDelegator.addObjectToModel(mv);

            return mv;
        } 
        return null;
    }

}
