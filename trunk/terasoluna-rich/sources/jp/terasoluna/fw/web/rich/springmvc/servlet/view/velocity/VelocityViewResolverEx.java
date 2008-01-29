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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.velocity;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.velocity.VelocityView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

/**
 * �G���R�[�f�B���O�w�肪�\��VelocityViewResolver�g���N���X�B
 * 
 * Spring��VelocityViewResolver���g�����A
 * ��������View�̃G���R�[�f�B���O�w����\�ɂ��Ă���B
 * 
 * <p>
 * VelocityViewResolver�Őݒ�\�ȃv���p�e�B�ɉ����A
 * �ȉ��̃v���p�e�B��ݒ肷�邱�ƁB
 * </p>
 * 
 * <p>
 *   <table border="1" CELLPADDING="8">
 *     <th>������</th>
 *     <th>����</th>
 *  
 *     <tr>
 *       <td align=center><b>encoding</b></td>
 *       <td>View�̃G���R�[�f�B���O�ݒ�</td>
 *     </tr>
 *  </table>
 * 
 * </p>
 *  �yBean��`�t�@�C���̐ݒ��z<br>
 * <code><pre>
 *   &lt;bean id="viewResolver" 
 *           class="jp.terasoluna.fw.web.rich.springmvc.servlet.view.velocity.VelocityViewResolverEx"&gt;
 *       &lt;property name="cache"&gt;&lt;value&gt;true&lt;/value&gt;&lt;/property&gt;
 *       &lt;property name="requestContextAttribute" value="rc"/&gt;
 *       &lt;property name="prefix"&gt;&lt;value&gt;&lt;/value&gt;&lt;/property&gt;
 *       &lt;property name="suffix"&gt;&lt;value&gt;.vm&lt;/value&gt;&lt;/property&gt;
 *       &lt;property name="exposeSpringMacroHelpers"&gt;&lt;value&gt;true&lt;/value&gt;&lt;/property&gt;
 *       &lt;property name="contentType"&gt;&lt;value&gt;text/xml;charset=UTF-8&lt;/value&gt;&lt;/property&gt;
 *       &lt;property name="encoding"&gt;&lt;value&gt;UTF-8&lt;/value&gt;&lt;/property&gt;
 *   &lt;/bean&gt;
 * </pre></code>
 * 
 */
public class VelocityViewResolverEx extends VelocityViewResolver {
    
    /**
     * View�̃G���R�[�f�B���O�B
     */ 
    private String encoding = null;
    
    /**
     * View�̃G���R�[�f�B���O���擾����B
     * @return View�̃G���R�[�f�B���O�B
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * View�̃G���R�[�f�B���O��ݒ肷��B
     * @param encoding View�̃G���R�[�f�B���O�B
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * View�C���X�^���X���쐬����B
     * ��������View�C���X�^���X�ɃG���R�[�f�B���O�̐ݒ���s���B
     * 
     * @param viewName view��
     * @return View�C���X�^���X
     * @throws Exception ��O
     */
    @Override
    protected AbstractUrlBasedView buildView(String viewName)
            throws Exception {
        VelocityView view = (VelocityView) super.buildView(viewName);
        if (encoding != null) {
            view.setEncoding(encoding);
        }
        return view;
    }
}
