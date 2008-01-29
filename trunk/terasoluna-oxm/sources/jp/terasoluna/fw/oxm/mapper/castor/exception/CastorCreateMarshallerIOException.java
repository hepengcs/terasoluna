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
 * Castor��Marshaller��������IO��O�����������ꍇ�A�X���[������O�B
 * 
 * <p>
 * OXMappingException�̃T�u�N���X�Ƃ��Ē񋟂��Ă���B
 * </p>
 * 
 */
public class CastorCreateMarshallerIOException extends OXMappingException {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = 3801084076373436920L;

    /**
     * �R���X�g���N�^�B
     * 
     * @param cause ��O
     */
    public CastorCreateMarshallerIOException(Throwable cause) {
        super(cause);
    }

}
