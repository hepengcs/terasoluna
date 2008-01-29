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

package jp.terasoluna.fw.web.rich.context;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.context.RequestContext;
import jp.terasoluna.fw.web.rich.context.RequestContextManager;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.context.RequestContextManager} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ��������Ǘ�����}�l�[�W���B<br>
 * �O������FThreadLocal��Null�l�ɂȂ�Ȃ��B
 * <p>
 *
 * @see jp.terasoluna.fw.web.rich.context.RequestContextManager
 */
public class RequestContextManagerTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(RequestContextManagerTest.class);
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
    public RequestContextManagerTest(String name) {
        super(name);
    }

    /**
     * testGetRequestContext01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) this.resources:ThreadLocal<br>
     *                �iRequestContext�o<br>
     *                �@requestName="test"<br>
     *                �p�j<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) RequestContext:ThreadLocal<br>
     *                  �iRequestContext�o<br>
     *                  �@requestName="test"<br>
     *                  �p�j<br>
     *
     * <br>
     * resources������getter���\�b�h�̃e�X�g�B
     * �X���b�h���[�J���ɐݒ肳��Ă��鐧�����Ԃ��B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetRequestContext01() throws Exception {
        // �O����
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        tlocal.set(ctx);
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // �e�X�g���{
        // ����
        assertSame(ctx, RequestContextManager.getRequestContext());
    }

    /**
     * testGetRequestContext02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(���) this.resources:ThreadLocal<br>
     *                �i��j<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    ("No RequestContext  bound to thread [<br>
     *                    (Thread.currentThread().getName())])<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    No RequestContext bound to thread!<br>
     *
     * <br>
     * resources������getter���\�b�h�̃e�X�g�B
     * �X���b�h���[�J���ɐ����񂪐ݒ肳��Ă��Ȃ����߁A��O����������B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetRequestContext02() throws Exception {
        // �O����
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        try {
            // �e�X�g���{
            RequestContextManager.getRequestContext();
            fail();
        } catch (IllegalStateException e) {
            // ����
            assertEquals("No RequestContext  bound to thread ["
                    + Thread.currentThread().getName() + "]", e.getMessage());
            assertTrue(
                    LogUTUtil.checkError("No RequestContext bound to thread!"));
        }
    }

    /**
     * testHasRequestContext01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) this.requestName:ThreadLocal<br>
     *                �iRequestContext�o<br>
     *                �@requestName="test"<br>
     *                �p�j<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ThreadLocal�ɒl���ݒ肳��Ă��邩�������郁�\�b�h�B
     * ThreadLocal�Ɏ��s�X���b�h�̃I�u�W�F�N�g���Z�b�g�����p�^�[���̃e�X�g�B
     * TRUE��Ԃ��B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasRequestContext01() throws Exception {
        // �O����
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        tlocal.set(ctx);
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // �e�X�g���{
        // ����
        assertTrue(RequestContextManager.hasRequestContext());
    }

    /**
     * testHasRequestContext02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) this.requestName:ThreadLocal<br>
     *                �i��j<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ThreadLocal�ɒl���ݒ肳��Ă��邩�������郁�\�b�h�B
     * ���ThreadLocal�̃p�^�[���̃e�X�g�BFALSE��Ԃ��B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasRequestContext02() throws Exception {
        // �O����
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // �e�X�g���{
        // ����
        assertFalse(RequestContextManager.hasRequestContext());
    }

    /**
     * testBindRequestContext01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) ������:RequestContext�o<br>
     *                �@requestName="arg"<br>
     *                �p<br>
     *         (���) this.resources:ThreadLocal<br>
     *                �i��j<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) this.resources:ThreadLocal<br>
     *                    �iRequestContext�o<br>
     *                    �@requestName="arg"<br>
     *                    �p�j<br>
     *
     * <br>
     * ������𐶐����郁�\�b�h�̃e�X�g�B
     * �����񂪋�̃X���b�h���[�J���I�u�W�F�N�g�ɁA�R���e�L�X�g��ݒ肷��B
     * ����P�[�X�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBindRequestContext01() throws Exception {
        // �O����
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        RequestContext ctx = new RequestContext();
        ctx.setRequestName("arg");

        // �e�X�g���{
        RequestContextManager.bindRequestContext(ctx);

        // ����
        ThreadLocal t =
            (ThreadLocal) UTUtil.getPrivateField(
                    RequestContextManager.class, "resources");
        Object result = t.get();
        assertSame(ctx, result);
    }

    /**
     * testBindRequestContext02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) ������:RequestContext�o<br>
     *                �@requestName="arg"<br>
     *                �p<br>
     *         (���) this.resources:ThreadLocal<br>
     *                �iRequestContext�o<br>
     *                �@requestName="test"<br>
     *                �p�j<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    �iAlready RequestContext [(�R���e�L�X�g�̕�����\��)]<br>
     *                     [�iThread.currentThread().getName()�j]�j<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Already RequestContext bound to thread!<br>
     *
     * <br>
     * ������𐶐����郁�\�b�h�̃e�X�g�B
     * �����񂪊��ɐݒ肳��Ă���X���b�h���[�J���I�u�W�F�N�g�ɁA
     * �R���e�L�X�g��ݒ肷��B����P�[�X�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBindRequestContext02() throws Exception {
        // �O����
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        RequestContext rctx = new RequestContext();
        tlocal.set(rctx);
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        RequestContext ctx = new RequestContext();
        ctx.setRequestName("arg");

        try {
            // �e�X�g���{
            RequestContextManager.bindRequestContext(ctx);
            fail();
        } catch (IllegalStateException e) {
            // ����
            assertEquals("Already RequestContext [" + rctx + "]" + "   ["
                    + Thread.currentThread().getName() + "]", e.getMessage());
            assertTrue(LogUTUtil.checkError(
                    "Already RequestContext bound to thread!"));
        }
    }

    /**
     * testBindRequestContext03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) ������:null<br>
     *         (���) this.resources:ThreadLocal<br>
     *                �i��j<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException(<br>
     *                    "RequestContext cannot set null.")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    RequestContext cannot set null.<br>
     *
     * <br>
     * ������𐶐����郁�\�b�h�̃e�X�g�B
     * Null�̐������ݒ肵�悤�Ƃ���P�[�X�B��O�𓊂���B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBindRequestContext03() throws Exception {
        // �O����
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // �e�X�g���{
        try {
            RequestContextManager.bindRequestContext(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("RequestContext cannot set null.", e.getMessage());
            assertTrue(LogUTUtil.checkError("RequestContext cannot set null."));
        }
    }

    /**
     * testUnbindRequestContext01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(���) this.resources:ThreadLocal<br>
     *                �i��j<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    �iNo RequestContext bound to thread<br>
     *                     [�iThread.currentThread().getName()�j]�j<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    No RequestContext bound to thread!<br>
     *
     * <br>
     * �������j�����郁�\�b�h�B
     * �X���b�h���[�J���I�u�W�F�N�g�ɉ��������񂪓����Ă��Ȃ��p�^�[����
     * �e�X�g�B��O����������B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testUnbindRequestContext01() throws Exception {
        // �O����
        ThreadLocal tlocal = new ThreadLocal();
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // �e�X�g���{
        try {
            RequestContextManager.unbindRequestContext();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("No RequestContext  bound to thread ["
                    + Thread.currentThread().getName() + "]", e.getMessage());
            assertTrue(
                    LogUTUtil.checkError("No RequestContext bound to thread!"));
        }
    }

    /**
     * testUnbindRequestContext02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(���) this.resources:ThreadLocal<br>
     *                �iRequestContext�o<br>
     *                �@requestName="test"<br>
     *                �p�j<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) this.resources:ThreadLocal<br>
     *                    �i��j<br>
     *
     * <br>
     * �������j�����郁�\�b�h�B
     * �X���b�h���[�J���I�u�W�F�N�g�ɐ����񂪓����Ă���p�^�[���̃e�X�g�B
     * �����񂪍폜�����B����P�[�X�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testUnbindRequestContext02() throws Exception {
        // �O����
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        tlocal.set(ctx);
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // �e�X�g���{
        RequestContextManager.unbindRequestContext();

        // ����
        ThreadLocal t = (ThreadLocal)
            UTUtil.getPrivateField(RequestContextManager.class, "resources");
        assertNull(t.get());
    }

}
