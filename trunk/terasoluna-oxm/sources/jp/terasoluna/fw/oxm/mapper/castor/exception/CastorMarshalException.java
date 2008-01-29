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

package jp.terasoluna.fw.oxm.mapper.castor.exception;

import jp.terasoluna.fw.oxm.exception.OXMappingException;

/**
 * Castor�̃}�b�s���O��`�t�@�C���̃p�X���s���ȏꍇ�ɁA�X���[�����o�C���h������O�B
 * <p>
 * OXMappingException�̃T�u�N���X�Ƃ��Ē񋟂��Ă���B
 * </p>
 * 
 */
public class CastorMarshalException extends OXMappingException {

    /**
     * �V���A���o�[�W����ID�B 
     */
    private static final long serialVersionUID = 7726382871138186281L;

    /**
     * �R���X�g���N�^
     * 
     * @param cause ��O
     */
    public CastorMarshalException(Throwable cause) {
        super(cause);
    }

}
