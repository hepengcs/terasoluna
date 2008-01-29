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

package jp.terasoluna.fw.file.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ���o�͐ݒ�p�̃A�m�e�[�V�����B
 * 
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface FileFormat {
    /**
     * �s��؂蕶���B
     * <p>
     * �s��؂蕶����ݒ肷��B�s��؂蕶���͔��p�����Ɍ���B�Ȃ��A�G�X�P�[�v�V�[�P���X���g���s��؂蕶���͈ȉ��ɋ�������̂Ɍ���B<br>
     * <ul>
     * <li>\r</li>
     * <li>\n</li>
     * <li>\r\n</li>
     * </ul>
     * �f�t�H���g�l�͎��s���Ɉˑ�����B
     * </p>
     */
    String lineFeedChar() default "";

    /**
     * ��؂蕶���B
     * <p>
     * CSV,�ϒ��t�@�C���̋�؂蕶����ݒ肷��B��؂蕶���͔��p�����Ɍ���B<br>
     * �f�t�H���g�l�́u','�i�J���}�j�v�B
     * </p>
     */
    char delimiter() default ',';
    
    /**
     * �͂ݕ����B
     * <p>
     * CSV,�ϒ��t�@�C���̈͂ݕ�����ݒ肷��B�͂ݕ����͔��p�����Ɍ���B<br>
     * �u'\u0000'�ichar�^�̍ŏ��l�j�v��ݒ肷��ƁA�t���[�����[�N�͈͂ݕ��������Ɣ��f����B
     * �f�t�H���g�l�́u'\u0000'�ichar�^�̍ŏ��l�j�v�B
     * </p>
     */
    char encloseChar() default Character.MIN_VALUE;

    /**
     * �t�@�C���G���R�[�f�B���O�B
     * <p>
     * ���o�͂��s���t�@�C���̃G���R�[�f�B���O��ݒ肷��B<br>
     * �f�t�H���g�l�͎��s���Ɉˑ�����B
     */
    String fileEncoding() default "";

    /**
     * �w�b�_�s���B
     * <p>
     * ���̓t�@�C���̃w�b�_���ɑ�������s����ݒ肷��B<br>
     * �f�t�H���g�l�́u0�i�[���j�v�B
     */
    int headerLineCount() default 0;

    /**
     * �g���C���s���B
     * <p>
     * ���̓t�@�C���̃g���C�����ɑ�������s����ݒ肷��B<br>
     * �f�t�H���g�l�́u0�i�[���j�v�B
     * </p>
     */
    int trailerLineCount() default 0;

    /**
     * �㏑���t���O�B
     * <p>
     * �o�̓t�@�C���Ɠ����t�@�C�������݂���ꍇ�ɏ㏑�����邩�ǂ�����ݒ肷��B<br>
     * �f�t�H���g�l�́ufalse�i�㏑�����Ȃ��j�v�B
     * </p>
     */
    boolean overWriteFlg() default false;
}
