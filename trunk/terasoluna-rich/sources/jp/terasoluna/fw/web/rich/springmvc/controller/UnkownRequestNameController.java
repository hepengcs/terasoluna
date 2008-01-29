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

import jp.terasoluna.fw.web.rich.springmvc.exception.UnknownRequestNameException;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * �s���ȃ��N�G�X�g�������N�G�X�g����Ă����ꍇ�Ɏ��s����郊�N�G�X�g�R���g���[���B
 * 
 * <p>
 * �K��UnkownRequestNameException���X���[����B
 * 
 * BeanNameUrlHandlerMappingEx�Ń��N�G�X�g���ɑΉ����郊�N�G�X�g�R���g���[����
 * ���݂��Ȃ������ꍇ�Ɏ��s����邱�Ƃ�z�肵�Ă���B
 * 
 * �X���[������O�́ASimpleMappingExceptionResolverEx��
 * �K�؂Ƀn���h�����O����邱�Ƃ�z�肵�Ă���B
 * </p>
 * 
 * <p>
 * Bean��`�t�@�C���Ɉȉ��̐ݒ�����邱�ƁB
 * </p>
 * 
 * <p>
 *  �y<code>Bean��`�t�@�C��</code>�̐ݒ��z<br>
 *  <code><pre>
 *  &lt;bean id="unkownRequestNameController"
 *      class="jp.terasoluna.fw.web.rich.springmvc.controller.UnkownRequestNameController"/&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * �܂��A�n���h���}�b�s���O��Bean��`�̍ہA�v���p�e�BdefaultHandler�ɏ�LBean��ݒ肷�邱�ƁB
 * �ڍׂ́ABeanNameUrlHandlerMappingEx���Q�Ƃ̂��ƁB
 * </p>
 * 
 * <p>
 * ����ɁAhandlerExceptionResolver��Bean��`�̍ۂ�
 * UnknownRequestNameException���X���[���ꂽ�ꍇ�̏������`���邱�ƁB
 * �ڍׂ́ASimpleMappingExceptionResolverEx���Q�Ƃ̂��ƁB
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.BeanNameUrlHandlerMappingEx
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx
 * 
 */
public class UnkownRequestNameController implements Controller {

    /**
     * ���N�G�X�g�������s���B
     * �K��UnknownRequestNameException���X���[����B
     * 
     * @param request HTTP���N�G�X�g�B
     * @param response HTTP���X�|���X�B
     * @return ���X�|���X�������_�����O���邽�߂�ModelAndView�B
     * @throws Exception ��O�B
     */
    public ModelAndView handleRequest(
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        throw new UnknownRequestNameException();
    }
}
