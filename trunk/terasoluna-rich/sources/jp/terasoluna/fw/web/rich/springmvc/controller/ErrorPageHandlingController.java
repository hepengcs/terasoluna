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

package jp.terasoluna.fw.web.rich.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Servlet��Filter���Ŕ���������O�ȂǁA
 * web.xml��error-page�ݒ�ɂ���O�n���h�����O��
 * �s���Ƃ��Ɏg�p����R���g���[���B
 * 
 * <p>���̃N���X�ł�Servlet��Filter�Ŕ���������O���X���[���A
 * Spring-MVC�̗�O�n���h�����O�@�\�ɏ����𓊂��邱�Ƃ�Ӗ��Ƃ���B
 * �X���[������O�́A{@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx}
 * �œK�؂Ƀn���h�����O����邱�Ƃ�z�肵�Ă���B</p>
 * 
 * <p>Bean��`�t�@�C���Ɉȉ��̐ݒ�����邱�ƁB</p>
 * 
 * <hr>
 * <p>
 *  �y<code>Bean��`�t�@�C��</code>�̐ݒ��z<br>
 *  <code><pre>
 *  &lt;bean name="/exceptionController" 
        class="jp.terasoluna.fw.web.rich.springmvc.controller.ErrorPageHandlingController"/&gt;
 * </pre></code>
 * </p>
 * <hr>
 * 
 * �܂��A���̃N���X���g�p����ɂ�
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping}
 * �N���X��Bean��`�t�@�C���ɐݒ肵�Ă����K�v������B�ڍׂ�
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping}
 * �N���X��JavaDoc���Q�Ƃ��邱�ƁB
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx
 * 
 */
public class ErrorPageHandlingController implements Controller {

    /**
     * HTTP���N�G�X�g���Ŕ���������O��ێ����Ă���L�[�B
     */
    private static final String JAVAX_SERVLET_ERROR_EXCEPTION = 
        "javax.servlet.error.exception";

    /**
     * ���N�G�X�g�������s���B
     * ���N�G�X�g���ێ����Ă����O���X���[����B
     * ���N�G�X�g�ɗ�O���Ȃ��ꍇ�A{@link java.lang.IllegalArgumentException}
     * ���X���[�����B
     * 
     * @param request HTTP���N�G�X�g�B
     * @param response HTTP���X�|���X�B
     * @return ���X�|���X�������_�����O���邽�߂�ModelAndView�B
     * @throws Exception ��O�B
     */
    public ModelAndView handleRequest(
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        Exception e = 
            (Exception) request.getAttribute(JAVAX_SERVLET_ERROR_EXCEPTION);
        
        if (e == null) {
            String message = "ErrorPageHandlingController must be set "
                + "Exception in Request Data.";
            throw new IllegalStateException(message);
        }
        throw e;
    }
}
