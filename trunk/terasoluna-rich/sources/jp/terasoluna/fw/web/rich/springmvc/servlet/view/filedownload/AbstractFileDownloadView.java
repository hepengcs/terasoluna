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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.filedownload;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.AbstractView;

/**
 * �o�C�i���t�@�C�����_�E�����[�h����ۂɗ��p����View���ۃN���X�B
 * 
 * <p>
 * �o���i���f�[�^�����X�|���X�ɏ������ށB
 * �K�v�ɉ����āA
 * ���X�|���X�{�f�B�ɏ������ރX�g���[���̎擾�����ƁA
 * �T�u�N���X�ɂāA���X�|���X�w�b�_�̏��ݒ菈�����������邱�ƁB
 * </p>
 * 
 * <p>
 * �{�N���X�̎����N���X�𗘗p����ꍇ�A�܂��AResourceBundleViewResolver��Bean��`���邱�ƁB
 * </p>
 * 
 * <p>
 *  �yBean��`�t�@�C���̐ݒ��z<br>
 * <code><pre>
 *   &lt;bean id="fileDownloadViewResolver"
 *       class="org.springframework.web.servlet.view.ResourceBundleViewResolver"&gt;
 *     &lt;property name="basename" value="views"/&gt;
 *   &lt;/bean&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * ���ɁA�o�C�i���f�[�^�����X�|���X�Ƃ���R���g���[����Bean��`�ŁA
 * property�uviewName�v���`���邱�ƁB
 * </p>
 * 
 * <p>
 *  �yBean��`�t�@�C���̐ݒ��z<br>
 * <code><pre>
 *   &lt;id name="fileDownloadSampleController"
 *           class="jp.terasoluna.sample2.web.controller.FileDownloadSampleController"
 *           parent="queryRequestController" singleton="false"&gt;
 *   &lt;property name="viewName"&gt;&lt;value&gt;FileDownloadSample&lt;/value&gt;&lt;/property&gt;
 *  </bean>
 * </pre></code>
 * </p>
 * 
 * <p>
 * ����ɁAfileDownloadViewResolver�̑���basename�̃v���p�e�B�t�@�C���i��L�̐ݒ�̏ꍇ�Aview.properties�j�ɁA
 * �u<I><�R���g���[����Bean��`�̑���viewName�v���p�e�B�l>.class</I>�v�̃L�[��
 * ���s����View�N���X���w�肷�邱�ƁB
 * </p>
 * 
 * <p>
 *  �yview.properties�̐ݒ��z<br>
 * <code><pre>
 *   FileDownloadSample.class=jp.terasoluna.sample2.web.view.SampleFileDownloadView
 * </pre></code>
 * </p>
 * 
 */
public abstract class AbstractFileDownloadView extends AbstractView {
    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(AbstractFileDownloadView.class);
    
    /**
     * �`�����N�T�C�Y�B
     */
    protected int chunkSize = 256;
    
    /**
     * ���X�|���X�������_�����O����B
     * 
     * @param model ���f���I�u�W�F�N�g
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @throws IOException IO��O
     * 
     */   
    @Override
    protected void renderMergedOutputModel(Map model,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("FileDownload start.");
        }      
       
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // �_�E�����[�h�t�@�C���f�[�^���擾����B
            try {
                inputStream = getInputStream(model, request);
            } catch (IOException e) {
                // �_�E�����[�h�Ɏ��s�����ꍇ
                log.error("FileDownload Failed.", e);
                throw e;
            }
            if (inputStream == null) {
                log.error("FileDownload Failed. InputStream is null.");
                throw new IOException(
                    "FileDownload Failed. InputStream is null.");          
            }

            // HTTP���X�|���X�̏o�̓X�g���[���ɏ�������
            try {
                outputStream
                    = new BufferedOutputStream(response.getOutputStream());
            } catch (IOException e) {
                // �_�E�����[�h�Ɏ��s�����ꍇ
                log.error("FileDownload Failed.", e);
                throw e;
            }
            
            // �w�b�_�ҏW
            addResponseHeader(model, request, response);
            
            try {
                writeResponseStream(inputStream, outputStream);
            } catch (IOException e) {
                // �_�E�����[�h�Ɏ��s�����ꍇ
                log.error("FileDownload Failed.", e);
                throw e;
            }
            try {
                outputStream.flush();
            } catch (IOException e) {
                // �_�E�����[�h�Ɏ��s�����ꍇ
                log.error("FileDownload Failed.", e);
                throw e;
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioe) {
                    log.warn("Cannot close InputStream.", ioe);
                }
            }
 
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ioe) {
                    log.warn("Cannot close OutputStream.", ioe);
                }
            }
        }
    }
    
    /**
     * ���X�|���X�{�f�B�ɏ������ރX�g���[�����擾����B
     * 
     * @param model ���f���I�u�W�F�N�g
     * @param request HTTP���N�G�X�g
     * @return ���N�G�X�g�ɏ������ނ��߂̃X�g���[��
     * @throws IOException ���o�͗�O
     */
    protected abstract InputStream getInputStream(
            Map model,
            HttpServletRequest request) throws IOException; 
    
    /**
     * �_�E�����[�h�t�@�C����HTTP���X�|���X�̃X�g���[���ɏ������ށB
     * 
     * @param inputStream �_�E�����[�h����t�@�C���f�[�^�̓��̓X�g���[��
     * @param outputStream ���X�|���X�̏o�̓X�g���[��
     * @throws IOException ���o�͗�O(��O�����͌Ăь��ōs��)
     */
    protected void writeResponseStream(
            InputStream inputStream, 
            OutputStream outputStream) throws IOException {
        if (inputStream == null || outputStream == null) {
            return;
        }

        byte[] buffer = new byte[chunkSize]; 
        int length = 0;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }   
    }
    
    /**
     * ���X�|���X�w�b�_��ǉ�����B
     * 
     * @param model ���f���I�u�W�F�N�g
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     */
    protected abstract void addResponseHeader(Map model,
            HttpServletRequest request,
            HttpServletResponse response);
}
