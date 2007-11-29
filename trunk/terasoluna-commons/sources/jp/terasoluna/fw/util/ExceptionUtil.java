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

package jp.terasoluna.fw.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ��O�Ɋւ��郆�[�e�B���e�B�N���X�B
 * 
 * <p>
 *  ��O�̃X�^�b�N�g���[�X�����ׂďo�͂���@�\�ł���B<br>
 *  ���O�̋@�\�ɂ���ẮA�����ƂȂ�����O�X�^�b�N�g���[�X��
 *  �Ō�܂ŕ\�����Ȃ��B
 *  �{�@�\�́A�����ƂȂ�����O���ċA�I�Ɏ擾���A
 *  �X�^�b�N�g���[�X�ƂȂ镶������擾����B�g�p��͉��L�̂Ƃ���ł���B
 *  
 *  <strong>ExceptionUtil�̎g�p��</strong><br>
 *  <code><pre>
 *  �E�E�E
 *  try {
 *     �E�E�E
 *  } catch (Exception e) {
 *      // ��O�X�^�b�N�g���[�X���Ō�܂ŏo��
 *      log.error("error-message", ExceptionUtil.getStackTrace(e));
 *  }
 *  �E�E�E
 * </pre></code>
 * </p>
 * 
 */
public final class ExceptionUtil {
    
    /**
     * ���O�N���X
     */
    private static Log log = LogFactory.getLog(ExceptionUtil.class);
    
    /**
     * ServletException�̂݁A��O���̃X�^�b�N�g���[�X�̏������قȂ�̂ŁA
     * ��������ʂ��邽�߂Ɏg�p����B
     */
    private static final String SERVLET_EXCEPTION_NAME = 
        "javax.servlet.ServletException";
    
    /**
     * ServletException�����������ۂɎg�p���郁�\�b�h���B
     * Servlet �̗�O�������N�����ꂽ���ɂȂ�����O��Ԃ����\�b�h�ł���B
     */
    private static final String GET_ROOT_CAUSE = "getRootCause";
    
    /**
     * �w�肵����O�̃X�^�b�N�g���[�X���擾����B
     * 
     * <p>
     *  �w�肵����O�̌����ƂȂ�����O���擾�ł���΁A
     *  ���̗�O�̃X�^�b�N�g���[�X���ċA�I�Ɏ擾����B
     *  ������getRootCause()�ŏE�����̂ɂ��Ă�ServletException�̂ݑΉ��B
     * </p>
     *
     * @param throwable ��O
     * @return �ċA�I�ɒH��ꂽ�X�^�b�N�g���[�X
     */
    public static String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (throwable != null) {
            baos.reset();
            throwable.printStackTrace(new PrintStream(baos));
            sb.append(baos.toString());
            
            //throwable����Class�I�u�W�F�N�g�����o���B
            Class throwableClass = throwable.getClass();
            
            // ServletException �Ȃ�� getRootCause ���g��
            if (SERVLET_EXCEPTION_NAME.equals(throwableClass.getName())) {
                try {
                    //throwable = ((ServletException) throwable).getRootCause()
                    //Class�I�u�W�F�N�g���烁�\�b�h�����w�肵�Ď��s����B
                    Method method = throwableClass.getMethod(GET_ROOT_CAUSE);
                    throwable = (Throwable) method.invoke(throwable);
                } catch (NoSuchMethodException e) {
                    //��v���郁�\�b�h��������Ȃ��ꍇ
                    log.error(e.getMessage());
                    throwable = null;
                } catch (IllegalAccessException e) {
                    //��{�ƂȂ郁�\�b�h�ɃA�N�Z�X�ł��Ȃ��ꍇ
                    log.error(e.getMessage());
                    throwable = null;
                } catch (InvocationTargetException e) {
                    //��{�ƂȂ郁�\�b�h����O���X���[����ꍇ
                    log.error(e.getMessage());
                    throwable = null;
                }
                
            } else {
                throwable = throwable.getCause();  
            }
        }
        return sb.toString();
    }

}
