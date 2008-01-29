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

package jp.terasoluna.fw.web.rich.context.support;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.rich.context.RequestContext;

/**
 * ��������������߂̕⏕���W�b�N�̃f�t�H���g�����N���X�B
 * 
 * <p>
 * ���N�G�X�g�w�b�_"requestName"�ɐݒ肵�Ă��镶��������N�G�X�g���Ƃ���B
 * �i�w�b�_����Bean��`�t�@�C���̐ݒ�ɂ��ύX�\�B�j
 * </p>
 * 
 * <p>
 * �{�N���X�𗘗p����ꍇ�ABean��`���s�����ƁB
 * �܂��AAbstractRequestContextSupport�̃v���p�e�B�ɉ����āA�ȉ��̃v���p�e�B��ݒ肷�邱�Ƃ��\�ł���B
 * </p>
 *
 * <p>
 *   <table border="1" CELLPADDING="8">
 *     <th>������</th>
 *     <th>�K�{</th>
 *     <th>����</th>
 *     
 *     <tr>
 *       <td align=center><b>requestNameHeaderKey</b></td>
 *       <td>�~</td>
 *       <td>���N�G�X�g����ێ����郊�N�G�X�g�w�b�_���B
 *       �f�t�H���g�̃w�b�_���hrequestName�h�ȊO�̃w�b�_���烊�N�G�X�g�����擾�������ꍇ�̂ݐݒ肷�邱�ƁB</td>
 *     </tr>
 *  
 *  </table>
 * </p>
 * 
 * <p>
 * �y<code>Bean��`�t�@�C��</code>�̐ݒ��z<br>
 * <code><pre>
 *   &lt;bean id="ctxSupport"
 *       class="jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl"&gt;
 *     &lt;property name="ctxClass"&gt;
 *       &lt;value&gt;jp.terasoluna.fw.web.rich.context.RequestContext&lt;/value&gt;
 *     &lt;/property&gt;
 *   &lt;/bean&gt;
 * </pre></code>
 * </p>
 * 
 */
public class DefaultRequestContextSupportImpl extends
        AbstractRequestContextSupport {
    
    /**
     * ���N�G�X�g����ێ����郊�N�G�X�g�w�b�_���B
     */
    protected String requestNameHeaderKey = DEFAULT_REQUEST_NAME_HEADER_KEY;
    
    /**
     * ���N�G�X�g����ێ����郊�N�G�X�g�w�b�_���̃f�t�H���g�l�B
     */
    private static final String DEFAULT_REQUEST_NAME_HEADER_KEY 
    	= "requestName";
    
    /**
     * ���N�G�X�g����ێ����郊�N�G�X�g�w�b�_����ݒ肷��B
     * @param requestNameHeaderKey ���N�G�X�g����ێ����郊�N�G�X�g�w�b�_���B
     */
    public void setRequestNameHeaderKey(String requestNameHeaderKey) {
        this.requestNameHeaderKey = requestNameHeaderKey;
    }

    /**
     * ������𐶐��E���������ĕԋp����B
     * @param request HTTP���N�G�X�g
     * @return ����������̐�����B
     */
    @Override
    protected RequestContext doGenerateContext(
            HttpServletRequest request) {
        RequestContext ctx = new RequestContext();
        ctx.setRequestName(request.getHeader(this.requestNameHeaderKey));
        return ctx;
    }
}
