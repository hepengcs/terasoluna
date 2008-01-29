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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.web.rich.springmvc.Constants;

/**
 * Castor�𗘗p����HTTP���X�|���X�������s���N���X�B
 * �Ɩ��������ʂł��郂�f���I�u�W�F�N�g����XML�`���̃f�[�^���쐬���A
 * HTTP���X�|���X�ɐݒ肷��B
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorViewResolver
 */
public class CastorView extends AbstractUrlBasedView {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(CastorView.class);
    
    /**
     * XML���I�u�W�F�N�g�ϊ��N���X�B
     */
    private OXMapper oxmapper = null;
    
    /**
     * Castor�r���[�̕\���������s�Ȃ��B
     * @param model �Ɩ������̌���
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @throws Exception ��O�B
     */
    @Override
    protected void renderMergedOutputModel(Map model, 
            HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        // Object��XML
        StringWriter stringWriter = new StringWriter();
        oxmapper.marshal(model.get(Constants.RESULT_KEY), stringWriter);
        
        // XML�f�[�^��HTTP���X�|���X�ɏ����o��
        Writer responseWriter = null;
        try {
            response.setContentType(this.getContentType());
            addResponseHeader(model, request, response);
            responseWriter = response.getWriter();
            responseWriter.write(stringWriter.getBuffer().toString());
        } catch (IOException e) {
            log.error("Cannot get Response Writer object.");
            throw e;
        } finally {
            if (responseWriter != null) {
                try {
                    responseWriter.close();
                } catch (IOException ee) {
                    log.error("Cannot close response writer.", ee);
                }
            }
        }
    }

    /**
     * ���X�|���X�w�b�_��ǉ�����B
     * ���X�|���X�w�b�_��ǉ�����ꍇ�A���̃��\�b�h���I�[�o���C�h����B
     *
     * @param model �Ɩ������̌���
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     */
    protected void addResponseHeader(Map model, 
            HttpServletRequest request, HttpServletResponse response) {
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

}
