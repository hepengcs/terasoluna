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

import javax.servlet.http.HttpServletRequest;
import jp.terasoluna.fw.web.rich.context.support.RequestContextSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

/**
 * ���N�G�X�g���Ǝ��s���郊�N�G�X�g�R���g���[��Bean��`�̃}�b�s���O���s���n���h���B
 * 
 * <p>
 * DispacherServlet�����s����A
 * ������̃��N�G�X�g���Ƃ��炩���ߒ�`�����ړ����E�ڔ��������������������Bean���Ƃ��āA
 * DI�R���e�i��胊�N�G�X�g�R���g���[���̎擾���s���A�ԋp����B
 * DispacherServlet�́A�ԋp���ꂽ���N�G�X�g�R���g���[�������s����B
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
 *     <th>����</th>
 * 
 *     <tr>
 *       <td align=center><b>ctxSupport</b></td>
 *       <td>���N�G�X�g�����擾���邽�߂̃T�|�[�g���W�b�N�N���X�B</td>
 *     </tr>
 *     
 *     <tr>
 *       <td align=center><b>prefix</b></td>
 *       <td>���N�G�X�g���ɕt�^����ړ����B</td>
 *     </tr>
 * 
 *     <tr>
 *       <td align=center><b>suffix</b></td>
 *       <td>���N�G�X�g���ɕt�^����ڔ����B</td>
 *     </tr>
 *     
 *     <tr>
 *       <td align=center><b>defaultHandler</b></td>
 *       <td>���N�G�X�g���ɑΉ�����R���g���[�������݂��Ȃ��ꍇ�̃R���g���[���B</td>
 *     </tr>
 * 
 *  </table>
 * 
 * </p>
 * 
 * <p>
 * �y<code>Bean��`�t�@�C��</code>�̐ݒ��z<br>
 * <code><pre>
 *   &lt;bean id=&quot;defaultHandlerMapping&quot;
 *       class=&quot;jp.terasoluna.fw.web.rich.springmvc.servlet.handler.BeanNameUrlHandlerMappingEx&quot;&gt;
 *     &lt;property name=&quot;ctxSupport&quot; ref=&quot;ctxSupport&quot;/&gt;
 *     &lt;property name=&quot;prefix&quot; value=&quot;/secure/blogic/&quot;/&gt;
 *     &lt;property name=&quot;suffix&quot; value=&quot;.do&quot;/&gt;
 *     &lt;property name=&quot;defaultHandler&quot; ref=&quot;unknownRequestNameController&quot;/&gt;
 *   &lt;/bean&gt;
 *   �� ctxSupport�́A ������������T�|�[�g���W�b�N��Bean��`�B
 *   �� unknownRequestNameController�́A���N�G�X�g���ɑΉ�����R���g���[�������݂��Ȃ��ꍇ�Ɏ��s�����R���g���[����Bean��`�B
 * </pre></code>
 * </p>
 * 
 * <p>
 * ��L�̐ݒ���s�����ꍇ�A
 * ���Ƃ��΁A���N�G�X�g�����usum�v�������ꍇ�A
 * DI�R���e�i���u/secure/blogic/sum.do�v�Ƃ������̂�Bean���擾���A�ԋp����B
 * �������ABean���擾�ł��Ȃ�����(���݂��Ȃ�����)�ꍇ�́A
 * �uunknownRequestNameController�v�Ƃ������̂�
 * Bean��DI�R���e�i���擾���A�ԋp����B
 * �iBean���擾�ł��Ȃ������ꍇ�̏ڍׂ́AUnkownRequestNameController���Q�Ƃ̂��ƁB�j
 * </p>
 * 
 */
public class BeanNameUrlHandlerMappingEx extends BeanNameUrlHandlerMapping 
    implements InitializingBean {
    /**
     * ���O�N���X�B
     */
    private static Log log = 
        LogFactory.getLog(BeanNameUrlHandlerMappingEx.class);
    
    /**
     * ������T�|�[�g���W�b�N�N���X�B
     */
    protected RequestContextSupport ctxSupport = null;
    
    /**
     * ���N�G�X�g���ɕt�^����ړ����B
     */
    protected String prefix = "";

    /**
     * ���N�G�X�g���ɕt�^����ڔ����B
     */
    protected String suffix = "";
    
    /**
     * ���N�G�X�g���ɕt�^����ړ�����ݒ肷��B
     * @param prefix ���N�G�X�g���ɕt�^����ړ����B
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * ���N�G�X�g���ɕt�^����ڔ�����ݒ肷��B
     * @param suffix ���N�G�X�g���ɕt�^����ڔ����B
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * ������T�|�[�g���W�b�N�N���X��ݒ肷��B
     * @param ctxSupport ������T�|�[�g���W�b�N�N���X
     */
    public void setCtxSupport(
            RequestContextSupport ctxSupport) {
        this.ctxSupport = ctxSupport;
    }
    
    /**
     * ���N�G�X�g�������ƂɁA���N�G�X�g�R���g���[���̃C���X�^���X�����b�N�A�b�v����B
     * @param urlPath URL�p�X
     * @param request HttpServletRequest
     * @return ���N�G�X�g�R���g���[���̃C���X�^���X
     */
    @Override
    protected Object lookupHandler(String urlPath, HttpServletRequest request) {
        String requestName = ctxSupport.getRequestName();
        String newUrlPath = prefix + requestName + suffix;
        
        // �R���g���[���̎擾
        Object handler = super.lookupHandler(newUrlPath, request); 
        
        if (handler == null) {
            log.error("Controller is not found. " + "BeanName:'" 
                    + newUrlPath + "'");
        }
        return handler; 
    }

    /**
     * �{�N���X���C���X�^���X�����ꂽ����ɌĂ΂�郁�\�b�h�B
     * �R���e�L�X�g�擾�N���X��Null�`�F�b�N���s���B
     */
    public void afterPropertiesSet() {
        if (ctxSupport == null) {
            log.error("BeanNameUrlHandlerMappingEx must be set ctxSupport.");
            throw new IllegalStateException(
                    "BeanNameUrlHandlerMappingEx must be set ctxSupport.");
        }
    }
}
