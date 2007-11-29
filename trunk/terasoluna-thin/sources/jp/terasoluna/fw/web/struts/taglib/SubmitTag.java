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

package jp.terasoluna.fw.web.struts.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import jp.terasoluna.fw.web.struts.form.ActionFormUtil;

import org.apache.struts.taglib.TagUtils;

/**
 * <p>�g��submit�^�O�B�t�H�[���̃^�[�Q�b�g���w�肵�܂��B</p>
 *
 * <p>
 * �t�H�[���̃T�u�~�b�g����target�̑����l��ݒ肵�܂��B(�g�p���@���Q��)
 * </p>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 *
 * <p><code>submitTag</code> �ł́A�ȉ��̑������T�|�[�g����B</p>
 *
 * <div align="center">
 *  <table width="90%" border="1">
 *   <tr>
 *    <td> <b>������</b> </td>
 *    <td> <b>�f�t�H���g�l</b> </td>
 *    <td> <b>�K�{��</b> </td>
 *    <td> <b>���s����</b> </td>
 *    <td> <b>�T�v</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>target</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      �^�[�Q�b�g����w�肷��B
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <p>
 *  ���̑��̑����ɂ��ẮA
 *  <code>&lt;html:submit&gt;</code> �^�O�� <code>API</code> ���Q�ƁB
 * </p>
 * <br>
 * <br>
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p> <code>&lt;html:submit&gt;</code> �^�O�� <code>API</code> ���Q�ƁB</p>
 * <br>
 * <h5>�g�p���@</h5>
 * <code><pre>
 *  &lt;ts:submit value=" submit " target=" rightFrame "/&gt;
 * </code></pre>
 *
 */
public class SubmitTag extends org.apache.struts.taglib.html.SubmitTag {

    /**
	 * �V���A���o�[�W����ID
	 */
	private static final long serialVersionUID = -795727425454129052L;

	/**
     * �w�肷��^�[�Q�b�g��
     */
    protected String target = null;

    /**
     * <p>�^�[�Q�b�g�����擾����B</p>
     *
     * @return target �^�[�Q�b�g��
     */
    public String getTarget() {
        return this.target;
    }

    /**
     * <p>�^�[�Q�b�g����ݒ肷��B</p>
     *
     * @param target �^�[�Q�b�g��
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * <p>���ׂẴA���P�[�g���ꂽ�������������B</p>
     */
    @Override
    public void release() {
        super.release();
        this.target = null;
    }

    /**
     * <p>�^�O�I���������B</p>
     *
     * @return EVAL_PAGE
     * @exception JspException ��O
     */
    @Override
    public int doEndTag() throws JspException {

        // Acquire the label value we will be generating
        String label = value;
        if ((label == null) && (text != null)) {
            label = text;
        }
        if ((label == null) || (label.length() < 1)) {
            label = "Submit";
        }

        // Generate an HTML element
        StringBuffer results = new StringBuffer();

        //�������g���ӏ���������
        if (target != null
                && pageContext.getAttribute("frameScript") == null) {
            HttpServletRequest req
                = (HttpServletRequest) pageContext.getRequest();
            String formName = ActionFormUtil.getActionFormName(req);
            results.append(System.getProperty("line.separator")
                + "<script type=\"text/javascript\">"
                + System.getProperty("line.separator"));
            results.append("<!--"
                + System.getProperty("line.separator"));
            results.append("  function __setFrameTarget(__frTarget) {"
                + System.getProperty("line.separator"));
            results.append("    document.");
            results.append(formName);
            results.append(".target = ");
            results.append("__frTarget;"
                + System.getProperty("line.separator"));
            results.append("  }"
                + System.getProperty("line.separator"));
            results.append("//-->"
                + System.getProperty("line.separator"));
            results.append("</script>"
                + System.getProperty("line.separator")
                + System.getProperty("line.separator"));

            pageContext.setAttribute("frameScript", "frameScript");
        }
        //�������g���ӏ������܂�

        results.append("<input type=\"submit\"");
        if (property != null) {
            results.append(" name=\"");
            results.append(property);
            if (indexed) {
                prepareIndex(results, null);
            }
            results.append("\"");
        }

        if (accesskey != null) {
            results.append(" accesskey=\"");
            results.append(accesskey);
            results.append("\"");
        }
        if (tabindex != null) {
            results.append(" tabindex=\"");
            results.append(tabindex);
            results.append("\"");
        }
        results.append(" value=\"");
        results.append(label);
        results.append("\"");

        //�������g���ӏ���������
        if (target != null && getOnclick() == null) {
            setOnclick("__setFrameTarget(\'" + this.target + "\')");
        } else if (target != null && getOnclick() != null) {
            String old = getOnclick();
            setOnclick("__setFrameTarget(\'" + this.target + "\');" + old);
        }
        //�������g���ӏ������܂�

        results.append(prepareEventHandlers());
        results.append(prepareStyles());
        results.append(">");
        //TagUtils�I�v�V�������擾�B
        TagUtils tagUtils = TagUtils.getInstance();
        // Render this element to our writer
        tagUtils.write(pageContext, results.toString());

        // Evaluate the remainder of this page
        return EVAL_PAGE;
    }

}
