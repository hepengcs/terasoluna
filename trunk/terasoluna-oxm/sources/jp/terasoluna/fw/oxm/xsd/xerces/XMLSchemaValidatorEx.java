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

package jp.terasoluna.fw.oxm.xsd.xerces;

import jp.terasoluna.fw.oxm.xsd.exception.ErrorReporterNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;

/**
 * ��͒���XML�̗v�f����XMLErrorReporterEx�̃X�^�b�N�Ɋi�[���邽�߂ɁAXMLSchemaValidator�̊g�����s�����N���X�B
 * 
 * <p>
 * �v�f�̊J�n���ɁA�v�f���ɃC���f�b�N�X��t��������������AXMLErrorReporterEx�̃X�^�b�N�Ƀv�b�V������B<br>
 * �v�f�̏I�����ɁA�X�^�b�N����|�b�v����B<br>
 * �v�f�̏�񂪓������X�^�b�N�𗘗p���邱�ƂŁA�G���[�����������ӏ�����肷�邱�Ƃ��ł���B
 * </p>
 * <p>
 * XML�̗v�f��񂪂ǂ̂悤�ɃX�^�b�N�Ɋi�[����邩���ȉ��ɋL���B
 * </p>
 * <p>
 * �yXML�f�[�^�̃T���v���z<br>
 * <code><pre>
 * &lt;sample-dto&gt;
 *   &lt;param-a&gt;
 *     &lt;param-b&gt;
 *       &lt;param-c&gt;...&lt;/param-c&gt;
 *       &lt;param-d&gt;...&lt;/param-d&gt;
 *     &lt;/param-b&gt;
 *   &lt;/param-a&gt;
 *   &lt;param-e&gt;
 *     &lt;param-f&gt;...&lt;/param-f&gt;
 *   &lt;/param-e&gt;
 *   &lt;param-e&gt;
 *     &lt;param-f&gt;...&lt;/param-f&gt;
 *   &lt;/param-e&gt;
 * &lt;/sample-dto&gt;
 * </pre></code>
 * </p>
 * <p>
 * �y��L��XML�f�[�^�����͂��ꂽ�ꍇ�́A�X�^�b�N�̏�Ԃ̕ω��z
 * <code><pre>
 * <ol>
 * <li>�i��j</li>
 * <li>{sample-dto[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}{param-b[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}{param-b[0]}{param-c[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}{param-b[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}{param-b[0]}{param-d[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}{param-b[0]}</li>
 * <li>{sample-dto[0]}{param-a[0]}</li>
 * <li>{sample-dto[0]}</li>
 * <li>{sample-dto[0]}{param-e[0]}</li>
 * <li>{sample-dto[0]}{param-e[0]}{param-f[0]}</li>
 * <li>{sample-dto[0]}{param-e[0]}</li>
 * <li>{sample-dto[0]}</li>
 * <li>{sample-dto[0]}{param-e[1]}</li>
 * <li>{sample-dto[0]}{param-e[1]}{param-f[0]}</li>
 * <li>{sample-dto[0]}{param-e[1]}</li>
 * <li>{sample-dto[0]}</li>
 * <li>�i��j</li>
 * </ol>
 * </pre></code>
 * </p>
 * 
 * <p>
 * �Ⴆ�΁A��L�T���v����param-c�v�f�ɕs���Ȓl�������Ă����ꍇ�A�G���[�������̃X�^�b�N�̏�Ԃ͈ȉ��̒ʂ�ł���B<br>
 * �X�^�b�N�̏�ԁF<code><b>{sample-dto[0]}{param-a[0]}{param-b[0]}{param-c[0]}</b></code>
 * </p>
 * 
 * <p>
 * XMLErrorReporterEx�͈ȉ��̂悤�ɃX�^�b�N�̒l���h�b�g�i"."�j�ŘA�����āA�G���[�̃t�B�[���h���𐶐�����B<br>
 * �t�B�[���h���F<code><b>sample-dto[0].param-a[0].param-b[0].param-c[0]</b></code>
 * </p>
 * 
 * <p>
 * ��͒���XML�̑����̏��͓��肷�邱�Ƃ��ł��Ȃ��B<br>
 * �G���[�����������������̓G���[���̒u�������񂩂�擾���邱�Ƃ��ł���B<br>
 * �ڍׂ́A{@link jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx}���Q�Ƃ��邱�ƁB
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx
 * 
 */
public class XMLSchemaValidatorEx extends XMLSchemaValidator {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(XMLSchemaValidatorEx.class);
    
    /**
     * XMLErrorReporterEx�C���X�^���X
     */
    private XMLErrorReporterEx reporter = null;

    /**
     * �R���X�g���N�^
     * 
     * @param reporter XMLErrorReporterEx�C���X�^���X
     */
    public XMLSchemaValidatorEx(XMLErrorReporterEx reporter) {
        this.reporter = reporter;
    }

    /**
     * �v�f�̊J�n���ɁA��͒��̃t�B�[���h����XMLErrorReporterEx�̃X�^�b�N�Ƀv�b�V������B
     * 
     * @param element XML�v�f�̏������C���X�^���X
     * @param attributes XML�����̏������C���X�^���X
     * @param augs �Ó������؂̌��ʂ��i�[�����C���X�^���X
     * @throws XNIException �p�[�T���Ŕ���������s����O
     */
    @Override
    public void startElement(QName element, XMLAttributes attributes,
            Augmentations augs) throws XNIException {

        if (reporter == null) {
            log.error("ErrorReporterEx is not found.");
            throw new ErrorReporterNotFoundException();
        }
        // �v�f���ɃC���f�b�N�X��t�������������ԋp����
        String pushStr = reporter.indexResolve(element.rawname);
        // XMLErrorReporterEx�̃X�^�b�N�ɉ�͒��̃t�B�[���h�����v�b�V������
        reporter.getTagStack().push(pushStr);
        super.startElement(element, attributes, augs);
    }
    
    /**
     * �v�f�̏I�����ɁAXMLErrorReporterEx�̃X�^�b�N����|�b�v����B
     * 
     * @param element XML�v�f�̏������C���X�^���X
     * @param augs �Ó������؂̌��ʂ��i�[�����C���X�^���X
     * @throws XNIException �p�[�T���Ŕ���������s����O
     */
    @Override
    public void endElement(QName element, Augmentations augs)
            throws XNIException {

        super.endElement(element, augs);
        if (reporter == null) {
            log.error("ErrorReporterEx is not found.");
            throw new ErrorReporterNotFoundException();
        }
        // �v�f�̏I���ŁAXMLErrorReporterEx�̃X�^�b�N����|�b�v����
        reporter.getTagStack().pop();
    }

}
