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

import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

/**
 * ���N�G�X�g���ێ������O�̗L���ɂ���ăR���g���[����Ԃ��n���h���B
 * 
 * <p>Servlet���A��������Filter�ŗ�O�����������ꍇ��
 * �R���g���[����Ԃ��B
 * �ԋp����R���g���[����Bean��`�t�@�C���ɐݒ肵�Ă������ƁB
 * �R���g���[����Bean��`ID��{@link #beanId}�Œ�`����B
 * �f�t�H���g�l��<code><pre>/exceptionController</pre></code>
 * �ƂȂ��Ă���B</p>
 * 
 * <p>���̃N���X���g�p����ɂ́ABean��`�t�@�C���Ɉȉ��̐ݒ肪�K�v�ł���B
 * 
 * <code><pre>
 * &lt;bean id="urlHandlerMapping" 
 *     class="jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping"&gt;
 *   &lt;property name="order" value="1"/&gt;
 * &lt;/bean&gt;
 * </pre></code>
 * </p>
 */
public class ErrorPageHandlerMapping extends BeanNameUrlHandlerMapping {

    /**
     * HTTP���N�G�X�g���Ŕ���������O��ێ����Ă���L�[�B
     */
    private static final String JAVAX_SERVLET_ERROR_EXCEPTION = 
        "javax.servlet.error.exception";

    /**
     * ���̃n���h�����Ԃ��R���g���[����Bean��`ID�B
     */
    private String beanId = DEFAULT_BEAN_ID;
    
    /**
     * ���̃n���h�����Ԃ��f�t�H���g�̃R���g���[����Bean��`ID�B
     */
    private static final String DEFAULT_BEAN_ID = "/exceptionController";
    
    /**
     * �f�t�H���g�R���X�g���N�^�B
     */
    public ErrorPageHandlerMapping() {
        super();
    }

    /**
     * beanId��Ԃ��B
     * @return �R���g���[����Bean��`ID�B
     */
    public String getBeanId() {
        return beanId;
    }

    /**
     * beanId��ݒ肷��B
     * @param beanId �R���g���[����Bean��`ID�B
     */
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    /**
     * �n���h�����O�����B
     * Servlet�AFilter�ŗ�O�����������ꍇ�Ɏg�p����R���g���[����Ԃ��B
     * Servlet�AFilter�ɂė�O���������Ă��Ȃ��ꍇ�Anull��Ԃ��B
     * @param request HttpServletRequest
     * @return Object �R���g���[��
     * @throws Exception ��O
     */
    @Override
    protected Object getHandlerInternal(HttpServletRequest request)
            throws Exception {
        Object exception = request.getAttribute(JAVAX_SERVLET_ERROR_EXCEPTION);
        
        if (exception != null && exception instanceof Exception) {
            return super.lookupHandler(beanId, request);
        }
        return null;
    }
}
