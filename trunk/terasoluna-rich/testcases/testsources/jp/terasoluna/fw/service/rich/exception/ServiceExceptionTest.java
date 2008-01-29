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

package jp.terasoluna.fw.service.rich.exception;

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.service.rich.exception.ServiceException;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.rich.exception.ServiceException}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �T�[�r�X�w�̃N���X�ŐV�K���������O������킷�N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.service.rich.exception.ServiceException
 */
public class ServiceExceptionTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ServiceExceptionTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public ServiceExceptionTest(String name) {
        super(name);
    }

    /**
     * testServiceException01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC, D
     * <br><br>
     * ���͒l�F(����) errorCode:"AAA"<br>
     *         (����) options:{"AAA", "BBB","CCC"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) errorCode:"AAA"<br>
     *         (��ԕω�) options:{"AAA", "BBB","CCC"}<br>
     *         
     * <br>
     * �������̒l�𐳏�ɑ����ɐݒ肷�邱�ƁB(�G���[�R�[�hnot null)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testServiceException01() throws Exception {
        // �O����
        // errorCode : "AAA"
        String errorCode = "AAA";
        // options : {"AAA", "BBB", "CCC"}
        String[] options = {"AAA", "BBB", "CCC"};

        // �e�X�g���{
        ServiceException e = new ServiceException(errorCode, options);

        // ����
        // errorCode
        assertEquals(errorCode, UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        for(int i=0; i<options.length; i++) {
            assertEquals(options[i], getOptions[i]);
        }
        assertEquals(3, getOptions.length);
        
        // options�������C���X�^���X�����`�F�b�N
        // �ȉ�����͂��̔���͏ȗ�
        assertSame(options, getOptions);
    }

    /**
     * testServiceException02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC, D
     * <br><br>
     * ���͒l�F(����) errorCode:""<br>
     *         (����) options:{"AAA", "BBB","CCC"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) errorCode:""<br>
     *         (��ԕω�) options:{"AAA", "BBB","CCC"}<br>
     *         
     * <br>
     * �������̒l�𐳏�ɑ����ɐݒ肷�邱�ƁB(�G���[�R�[�h�󕶎�)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testServiceException02() throws Exception {
        // �O����
        // errorCode : ""
        String errorCode = "";
        // options : {"AAA", "BBB", "CCC"}
        String[] options = {"AAA", "BBB", "CCC"};

        // �e�X�g���{
        ServiceException e = new ServiceException(errorCode, options);

        // ����
        // errorCode
        assertEquals(errorCode, UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        for(int i=0; i<options.length; i++) {
            assertEquals(options[i], getOptions[i]);
        }
        assertEquals(3, getOptions.length);
    }

    /**
     * testServiceException03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC, D
     * <br><br>
     * ���͒l�F(����) errorCode:null<br>
     *         (����) options:{"AAA", "BBB","CCC"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) errorCode:null<br>
     *         (��ԕω�) options:{"AAA", "BBB","CCC"}<br>
     *         
     * <br>
     * �������̒l�𐳏�ɑ����ɐݒ肷�邱�ƁB(�G���[�R�[�h null)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testServiceException03() throws Exception {
        // �O����
        // errorCode : null
        String errorCode = null;
        // options : {"AAA", "BBB", "CCC"}
        String[] options = {"AAA", "BBB", "CCC"};

        // �e�X�g���{
        ServiceException e = new ServiceException(errorCode, options);

        // ����
        // errorCode
        assertNull(UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        for(int i=0; i<options.length; i++) {
            assertEquals(options[i], getOptions[i]);
        }
        assertEquals(3, getOptions.length);
    }

    /**
     * testServiceException04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC, D
     * <br><br>
     * ���͒l�F(����) errorCode:"AAA"<br>
     *         (����) options:{"AAA"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) errorCode:"AAA"<br>
     *         (��ԕω�) options:{"AAA"}<br>
     *         
     * <br>
     * �������̒l�𐳏�ɑ����ɐݒ肷�邱�ƁB(options not null)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testServiceException04() throws Exception {
        // �O����
        // errorCode : "AAA"
        String errorCode = "AAA";
        // options : {"AAA"}
        String[] options = {"AAA"};

        // �e�X�g���{
        ServiceException e = new ServiceException(errorCode, options);

        // ����
        // errorCode
        assertEquals(errorCode, UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        for(int i=0; i<options.length; i++) {
            assertEquals(options[i], getOptions[i]);
        }
        assertEquals(1, getOptions.length);
    }

    /**
     * testServiceException05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC, D
     * <br><br>
     * ���͒l�F(����) errorCode:"AAA"<br>
     *         (����) options:{}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) errorCode:"AAA"<br>
     *         (��ԕω�) options:{}<br>
     *         
     * <br>
     * �������̒l�𐳏�ɑ����ɐݒ肷�邱�ƁB(options �󕶎�)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testServiceException05() throws Exception {
        // �O����
        // errorCode : "AAA"
        String errorCode = "AAA";
        // options : {}
        String[] options = {};

        // �e�X�g���{
        ServiceException e = new ServiceException(errorCode, options);

        // ����
        // errorCode
        assertEquals(errorCode, UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        assertEquals(0, getOptions.length);
    }

    /**
     * testServiceException06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC, D
     * <br><br>
     * ���͒l�F(����) errorCode:"AAA"<br>
     *         (����) options:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) errorCode:"AAA"<br>
     *         (��ԕω�) options:null<br>
     *         
     * <br>
     * �������̒l�𐳏�ɑ����ɐݒ肷�邱�ƁB(options  null)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testServiceException06() throws Exception {
        // �O����
        // errorCode : "AAA"
        String errorCode = "AAA";
        // options : null
        String[] options = null;

        // �e�X�g���{
        ServiceException e = new ServiceException(errorCode, options);

        // ����
        // errorCode
        assertEquals(errorCode, UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        assertNull(getOptions);
    }

    /**
     * testServiceException07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC, D
     * <br><br>
     * ���͒l�F(����) errorCode:"AAA"<br>
     *         (����) options:�w�肵�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) errorCode:"AAA"<br>
     *         (��ԕω�) options:{}<br>
     *         
     * <br>
     * �������̒l�𐳏�ɑ����ɐݒ肷�邱�ƁB(options���w�肵�Ȃ�)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testServiceException07() throws Exception {
        // �O����
        // errorCode : "AAA"
        String errorCode = "AAA";

        // �e�X�g���{
        ServiceException e = new ServiceException(errorCode);

        // ����
        // errorCode
        assertEquals(errorCode, UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        assertEquals(0, getOptions.length);
    }

    /**
     * testGetErrorCode01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) errorCode:"AAA"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) errorCode:"AAA"<br>
     *         
     * <br>
     * �����ɐݒ肳��Ă���l�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetErrorCode01() throws Exception {
        // �O����
        // errorCode : "AAA"
        String errorCode = "AAA";
        
        ServiceException e = new ServiceException(errorCode);

        // �e�X�g���{
        String getErrorCode = e.getErrorCode();

        // ����
        assertEquals(errorCode, getErrorCode);
    }

    /**
     * testGetOptions01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) options:["aaa", "bbb"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) options:�����Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * �����ɐݒ肳��Ă���l�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetOptions01() throws Exception {
        // �O����
        // errorCode : "AAA"
        String errorCode = "AAA";
        // options : {"aaa", "bbb"}
        String[] options = {"aaa", "bbb"};
        
        ServiceException e = new ServiceException(errorCode, options);

        // �e�X�g���{
        String[] getOptions = e.getOptions();

        // ����
        assertSame(options, getOptions);
    }

}
