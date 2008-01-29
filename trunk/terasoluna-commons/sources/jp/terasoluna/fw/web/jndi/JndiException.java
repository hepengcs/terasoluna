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

package jp.terasoluna.fw.web.jndi;

/**
 * JNDI�֘A��O�N���X�B
 *
 * <p>
 *  JNDI�֘A�G���[��\������B
 * </p>
 *
 *  JndiException�N���X�́ARuntimeException�N���X�̃T�u�N���X�ł���B
 *  JndiException���X���[����ꍇ�ɂ́A���̃��\�b�h��thorws��ɖ����I�ɋL�q����
 *  �K�v�͂Ȃ��B<br>
 *
 *  �R���X�g���N�^�ł̐������ɁA�G���[���b�Z�[�W���w�肷�邱�Ƃ��ł���B
 * </p>
 *
 */
public class JndiException extends RuntimeException {
    
    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = -7105599934896030074L;

    /**
     * �R���X�g���N�^�B
     *
     * @param cause �����ƂȂ�����O
     */
    public JndiException(Throwable cause) {
        super(cause);
    }
    
    /**
     * �R���X�g���N�^�B
     *
     * @param message �G���[���b�Z�[�W
     * @param cause �����ƂȂ�����O
     */
    public JndiException(String message,
                            Throwable cause) {
        super(message, cause);
    }
}
