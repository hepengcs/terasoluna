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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletResponse;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.view.filedownload.AbstractFileDownloadView} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �o�C�i���t�@�C�����_�E�����[�h����ۂɗ��p����View���ۃN���X�B<br>
 * �O������Fmodel�AHTTP���N�G�X�g�AHTTP���X�|���X��Null�l�ɂȂ�Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.view.filedownload.AbstractFileDownloadView
 */
public class AbstractFileDownloadViewTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractFileDownloadViewTest.class);
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
    public AbstractFileDownloadViewTest(String name) {
        super(name);
    }

    /**
     * testRenderMergedOutputModel01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) model:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) getInputStream():Null��Ԃ��p�^�[���B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IOException<br>
     *                    �iFileDownload Failed. InputStream is null.�j<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    FileDownload Failed. InputStream is null.<br>
     *         
     * <br>
     * �T�u�N���X�Œ�`������̓X�g���[���擾���\�b�h��Null��Ԃ����p�^�[���B
     * IO��O�𓊂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenderMergedOutputModel01() throws Exception {
        // �O����
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        MockHttpServletResponse response = 
            new MockHttpServletResponse();
        Map<String, String> model = new HashMap<String, String>();

        // �e�X�g���{
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (IOException e) {
            // OK
            String expect = "FileDownload Failed. InputStream is null.";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }

        // ����
        assertFalse(view.isAddResponseHeader);
    }

    /**
     * testRenderMergedOutputModel02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) model:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) getInputStream():IO��O�����������p�^�[���B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IOException<br>
     *         (��ԕω�) ���O:���O���x���F�G���[FileDownload Failed.<br>
     *         
     * <br>
     * �T�u�N���X�Œ�`������̓X�g���[���擾���\�b�h��IO��O�𓊂����p�^�[���B
     * ���O�o�͂��A��O�����̂܂ܓ�����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenderMergedOutputModel02() throws Exception {
        // �O����
        AbstractFileDownloadViewStub02 view = 
            new AbstractFileDownloadViewStub02();
        MockHttpServletResponse response = 
            new MockHttpServletResponse();
        Map<String, String> model = new HashMap<String, String>();

        // �e�X�g���{
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (IOException e) {
            // OK
            assertTrue(LogUTUtil.checkError("FileDownload Failed.", e));
        }

        // ����
        assertFalse(view.isAddResponseHeader);
    }

    /**
     * testRenderMergedOutputModel03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) model:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) getInputStream():����ɓ��̓X�g���[����Ԃ��p�^�[���B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) inputStream.close():
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) ��O:IOException<br>
     *         (��ԕω�) ���O:���O���x���F�G���[FileDownload Failed.<br>
     *         
     * <br>
     * �o�̓X�g���[���������ɗ�O����������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenderMergedOutputModel03() throws Exception {
        // �O����
        AbstractFileDownloadViewStub03 view = 
            new AbstractFileDownloadViewStub03();
        AbstractFileDownloadView_ByteArrayInputStreamStub01 in = 
            new AbstractFileDownloadView_ByteArrayInputStreamStub01(
                    "test".getBytes());
        view.setInputStream(in);
        AbstractFileDownloadView_MockHttpServletResponseStub01 response = 
            new AbstractFileDownloadView_MockHttpServletResponseStub01();
        Map<String, String> model = new HashMap<String, String>();

        // �e�X�g���{
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (IOException e) {
            // OK
            assertTrue(LogUTUtil.checkError("FileDownload Failed.", e));
        }

        // ����
        assertTrue(in.isClose);
    }

    /**
     * testRenderMergedOutputModel04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) model:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) addResponseHeader():����ɏ������s�Ȃ��p�^�[���B<br>
     *         (���) getInputStream():����ɓ��̓X�g���[����Ԃ��p�^�[���B<br>
     *         (���) writeResponseStream�i�j:IO��O��Ԃ��p�^�[���B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) addResponseHeader():
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         (��ԕω�) inputStream.close():
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) outputStream.close():
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) ��O:IOException<br>
     *         (��ԕω�) ���O:���O���x���F�G���[FileDownload Failed.<br>
     *         
     * <br>
     * �������݃��\�b�hwriteResponseStream��IO��O����������p�^�[���̃e�X�g�B
     * ���O�o�͂��A��O�����̂܂ܓ�����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenderMergedOutputModel04() throws Exception {
        // �O����
        AbstractFileDownloadViewStub04 view = 
            new AbstractFileDownloadViewStub04();
        AbstractFileDownloadView_ByteArrayInputStreamStub01 in = 
            new AbstractFileDownloadView_ByteArrayInputStreamStub01(
                    "test".getBytes());
        view.setInputStream(in);
        MockHttpServletResponse response =  new MockHttpServletResponse();
        AbstractFileDownloadView_ServletOutputStreamStub01 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub01();
        response.setOutputStream(out);
        Map<String, String> model = new HashMap<String, String>();

        // �e�X�g���{
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (IOException e) {
            // OK
            assertTrue(LogUTUtil.checkError("FileDownload Failed.", e));
        }

        // ����
        assertTrue(view.isAddResponseHeader);
        assertTrue(in.isClose);
        assertTrue(out.isClose);
    }

    /**
     * testRenderMergedOutputModel05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) model:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) addResponseHeader():
     *         ����ɏ������s�Ȃ��p�^�[���B<br>
     *         (���) getInputStream():����ɓ��̓X�g���[����Ԃ��p�^�[���B<br>
     *         (���) writeResponseStream�i�j:����ɏ������݂��s��<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) addResponseHeader():
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         (��ԕω�) inputStream.close():
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) outputStream.close():
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) ��O:IOException<br>
     *         (��ԕω�) ���O:���O���x���F�G���[FileDownload Failed.<br>
     *         
     * <br>
     * �o�̓X�g���[���̃t���b�V���Ɏ��s�����ꍇ�̃e�X�g�B
     * �t���b�V�����ɓ�����ꂽ��O�����̂܂܃X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenderMergedOutputModel05() throws Exception {
        // �O����
        AbstractFileDownloadViewStub03 view = 
            new AbstractFileDownloadViewStub03();
        AbstractFileDownloadView_ByteArrayInputStreamStub01 in = 
            new AbstractFileDownloadView_ByteArrayInputStreamStub01(
                    "test".getBytes());
        view.setInputStream(in);
        MockHttpServletResponse response =  new MockHttpServletResponse();
        AbstractFileDownloadView_ServletOutputStreamStub02 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub02();
        response.setOutputStream(out);
        Map<String, String> model = new HashMap<String, String>();

        // �e�X�g���{
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (IOException e) {
            // OK
            assertTrue(LogUTUtil.checkError("FileDownload Failed.", e));
        }

        // ����
        assertTrue(view.isAddResponseHeader);
        assertTrue(in.isClose);
        assertTrue(out.isClose);
    }

    /**
     * testRenderMergedOutputModel06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) model:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) addResponseHeader():����ɏ������s�Ȃ��p�^�[���B<br>
     *         (���) getInputStream():����ɓ��̓X�g���[����Ԃ��p�^�[���B<br>
     *         (���) writeResponseStream�i�j:����ɏ������݂��s��<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) response:���̓X�g���[���̃f�[�^���������܂��B<br>
     *         (��ԕω�) addResponseHeader():
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         (��ԕω�) inputStream.close():
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) outputStream.close():
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ����ɏ������݂��������A
     * ���̓X�g���[���E�o�̓X�g���[�����N���[�Y����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenderMergedOutputModel06() throws Exception {
        // �O����
        AbstractFileDownloadViewStub03 view = 
            new AbstractFileDownloadViewStub03();
        byte[] bytearray = "test".getBytes();
        AbstractFileDownloadView_ByteArrayInputStreamStub01 in = 
            new AbstractFileDownloadView_ByteArrayInputStreamStub01(
                    bytearray);
        view.setInputStream(in);
        MockHttpServletResponse response =  new MockHttpServletResponse();
        AbstractFileDownloadView_ServletOutputStreamStub01 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub01();
        response.setOutputStream(out);
        Map<String, String> model = new HashMap<String, String>();

        // �e�X�g���{
        view.renderMergedOutputModel(model, null, response);
        
        // ����
        for (int i=0;i<bytearray.length;i++) {
            // byte�z��̒l���m�F
            assertEquals(bytearray[i], out.responseData[i]);
        }
        assertTrue(view.isAddResponseHeader);
        assertTrue(in.isClose);
        assertTrue(out.isClose);
    }

    /**
     * testWriteResponseStream01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) inputStream:null<br>
     *         (����) outputStream:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) outputStream:�ω��Ȃ�<br>
     *         
     * <br>
     * ���̓X�g���[����Null�̏ꍇ�̃e�X�g�B<br>
     * ���������������I������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testWriteResponseStream01() throws Exception {
        // �O����
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        AbstractFileDownloadView_ServletOutputStreamStub03 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub03();
        
        // �e�X�g���{
        view.writeResponseStream(null, out);
        
        // ����
        assertFalse(out.isWrite);
    }

    /**
     * testWriteResponseStream02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) inputStream:not null<br>
     *         (����) outputStream:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) outputStream:�ω��Ȃ�<br>
     *         
     * <br>
     * �o�̓X�g���[����Null�̏ꍇ�̃e�X�g�B<br>
     * ���������������I������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testWriteResponseStream02() throws Exception {
        // �O����
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        AbstractFileDownloadView_ByteArrayInputStreamStub01 in = 
            new AbstractFileDownloadView_ByteArrayInputStreamStub01(
                    "test".getBytes());
        
        // �e�X�g���{
        view.writeResponseStream(in, null);
        
        // ����
        assertFalse(in.isRead);
    }

    /**
     * testWriteResponseStream03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) inputStream:not null<br>
     *                �t�@�C���T�C�Y�F�O�o�C�g<br>
     *         (����) outputStream:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) outputStream:�ω��Ȃ�<br>
     *         
     * <br>
     * ���̓X�g���[���̃t�@�C���T�C�Y���O�̏ꍇ�̃e�X�g�B���������������I������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testWriteResponseStream03() throws Exception {
        // �O����
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        byte[] byteData = new byte[0]; 
        InputStream in = new ByteArrayInputStream(byteData);
        AbstractFileDownloadView_ServletOutputStreamStub04 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub04();
        
        // �e�X�g���{
        view.writeResponseStream(in, out);
        
        // ����
        assertEquals(0, out.count);
    }

    /**
     * testWriteResponseStream04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,B,D
     * <br><br>
     * ���͒l�F(����) inputStream:not null<br>
     *                �t�@�C���T�C�Y�F�Q�T�U�o�C�g<br>
     *         (����) outputStream:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) outputStream:�o�̓X�g���[����
     * ���̓X�g���[���̃f�[�^����������<br>
     *         
     * <br>
     * ���̓X�g���[���̃t�@�C���T�C�Y���Q�T�U�̏ꍇ�̃e�X�g�B
     * �`�����N�T�C�Y�Q�T�U�o�C�g�Ɠ����o�C�g���̃t�@�C�����������ރp�^�[���B
     * �������݂̃��[�v���P��s�Ȃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testWriteResponseStream04() throws Exception {
        // �O����
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        byte[] byteData = new byte[256]; 
        for (int i=0;i<256;i++) {
            byteData[i] = 1;
        }
        InputStream in = new ByteArrayInputStream(byteData);
        AbstractFileDownloadView_ServletOutputStreamStub04 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub04();
        
        // �e�X�g���{
        view.writeResponseStream(in, out);
        
        // ����
        assertEquals(1, out.count);
    }

    /**
     * testWriteResponseStream05()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA,B,D
     * <br><br>
     * ���͒l�F(����) inputStream:not null<br>
     *                �t�@�C���T�C�Y�F�Q�T�V�o�C�g<br>
     *         (����) outputStream:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) outputStream:�o�̓X�g���[����
     * ���̓X�g���[���̃f�[�^����������<br>
     *         
     * <br>
     * ���̓X�g���[���̃t�@�C���T�C�Y���Q�T�V�̏ꍇ�̃e�X�g�B
     * �`�����N�T�C�Y�Q�T�U�o�C�g���傫���t�@�C�����������ރp�^�[���B
     * �������݂̃��[�v���Q��s�Ȃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testWriteResponseStream05() throws Exception {
        // �O����
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        byte[] byteData = new byte[257]; 
        for (int i=0;i<257;i++) {
            byteData[i] = 1;
        }
        InputStream in = new ByteArrayInputStream(byteData);
        AbstractFileDownloadView_ServletOutputStreamStub04 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub04();
        
        // �e�X�g���{
        view.writeResponseStream(in, out);
        
        // ����
        assertEquals(2, out.count);
    }

    /**
     * testWriteResponseStream06()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA,B,D
     * <br><br>
     * ���͒l�F(����) inputStream:not null<br>
     *                �t�@�C���T�C�Y�F�V�O�O�o�C�g<br>
     *         (����) outputStream:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) outputStream:�o�̓X�g���[����
     * ���̓X�g���[���̃f�[�^����������<br>
     *         
     * <br>
     * ���̓X�g���[���̃t�@�C���T�C�Y���V�O�O�̏ꍇ�̃e�X�g�B
     * �`�����N�T�C�Y�Q�T�U�o�C�g���傫���t�@�C�����������ރp�^�[���B
     * �������݂̃��[�v���R��s�Ȃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testWriteResponseStream06() throws Exception {
        // �O����
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        byte[] byteData = new byte[700]; 
        for (int i=0;i<700;i++) {
            byteData[i] = 1;
        }
        InputStream in = new ByteArrayInputStream(byteData);
        AbstractFileDownloadView_ServletOutputStreamStub04 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub04();
        
        // �e�X�g���{
        view.writeResponseStream(in, out);
        
        // ����
        assertEquals(3, out.count);
    }

}
