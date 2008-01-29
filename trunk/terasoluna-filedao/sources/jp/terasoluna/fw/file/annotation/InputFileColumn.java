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
 * ���͐ݒ�p�̃A�m�e�[�V�����B
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface InputFileColumn {
    /**
     * �J����Index�B<br>
     * <br>
     * �J������Index�i���ԁj�������B<b>���͕K�{����</b>�B<br>
     * �J����Index�͓����N���X�̒��ŏd�����Ȃ��悤�Ɏ������邱�ƁB
     *
     */
    int columnIndex();

    /**
     * �J�����̃t�H�[�}�b�g�B<br>
     * <br>
     * DATE�^�ABigDecimal�^�̓��͒l�̃t�H�[�}�b�g�������B<br>
     * (��)"yyyy/MM/dd","###,###,###"<br>
     *
     */
    String columnFormat() default "";

    /**
     * �J�����̃o�C�g���B
     * <p>
     * �Œ蒷���o�͂̊e�J�����̃o�C�g���������B�܂��A���̑��̃t�@�C���Ńp�f�B���O�������s���ꍇ�ɓ��͂��s���B
     * </p>
     * <b>�Œ蒷�t�@�C���̏ꍇ�A���͕K�{���ځB</b><br>
     * <b>�p�f�B���O�������s���ꍇ�A���͕K�{���ځB</b><br>
     *
     */
    int bytes() default -1;

    /**
     * �p�f�B���O��ʁB<br>
     * <br>
     * �p�f�B���O�̎��(�E�l/���l/�p�f�B���O�Ȃ�[LEFT/RIGHT/NONE])�������B
     *
     */
    PaddingType paddingType() default PaddingType.NONE;

    /**
     * �p�f�B���O�����B<br>
     * <br>
     * �p�f�B���O���镶���������B<b>(���p����1�����̂ݐݒ�\�B)</b><br>
     * �p�f�B���O�����́A�W���uBean��`�t�@�C���ɐݒ肳�ꂽ�������1�����ڂ̔��p�����̂ݗL���ƂȂ�B<br>
     * �S�p���������͂��ꂽ�ꍇ�̓G���[�ƂȂ菈�����I������B<br>
     * 2�����ȏ���͂��Ă�1�����ڂ̂ݏ����ɗ��p�����B<br>
     * ��؂蕶���A�͂ݕ����A�s��؂蕶���Ɠ��������𗘗p���邱�Ƃ͂ł��Ȃ��B
     *
     */
    char paddingChar() default ' ';

    /**
     * �����ϊ���ʁB<br>
     * <br>
     * String�^�̍��ڂɂ��āA�啶���ϊ��E�������ϊ��E���ϊ��������B<br>
     * �啶���ϊ�:StringConverterToUpperCase.class<br>
     * �������ϊ�:StringConverterToLowerCase.class<br>
     * ���ϊ�:NullStringConverter.class<br>
     *
     */
    Class<? extends StringConverter> stringConverter()
            default NullStringConverter.class;

    /**
     * �g������ʁB<br>
     * <br>
     * �g�����̎��(�E�l/���l/�g�����Ȃ�[LEFT/RIGHT/NONE])�������B
     *
     */
    TrimType trimType() default TrimType.NONE;

    /**
     * �g���������B<br>
     * <br>
     * �g�������镶���������B<b>(���p�����̂ݐݒ�\)</b><br>
     * �g���������́A�W���uBean��`�t�@�C���ɐݒ肳�ꂽ�������1�����ڂ̔��p�����̂ݗL���ƂȂ�B<br>
     * �S�p���������͂��ꂽ�ꍇ�̓G���[�ƂȂ菈�����I������B<br>
     * 2�����ȏ���͂��Ă�1�����ڂ̂ݏ����ɗ��p�����B<br>
     * ��؂蕶���A�͂ݕ����A�s��؂蕶���Ɠ��������𗘗p���邱�Ƃ͂ł��Ȃ��B
     *
     */
    char trimChar() default ' ';
}
