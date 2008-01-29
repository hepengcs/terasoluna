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

package jp.terasoluna.fw.batch.openapi;

/**
 * �ԋp�R�[�h�̗񋓃N���X�B
 * 
 *<p><code>ReturnCode</code> �ł́A�ȉ��̕ԋp�R�[�h��񋟂���</p>
 *
 * <div align="center">
 *  <table width="90%" border="1">
 *   <tr>
 *    <td> <b>�ԋp�R�[�h</b> </td>
 *    <td> <b>�T�v</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>NORMAL_CONTINUE</code> </td>
 *    <td align="left">
 *    �������p������B�ʏ�͂��̕ԋp�R�[�h��ԋp����B
 *    �Ō�̓��̓p�����[�^�ł���ꍇ�ɂ̓g�����U�N�V�������R�~�b�g���A�W���u��
 *    ��I������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>NORMAL_END</code> </td>
 *    <td align="left">
 *      �Ώۃf�[�^�̓r���ŏ������I�����������ꍇ�ɕԋp����B
 *    �g�����U�N�V�������R�~�b�g���A�W���u�𐳏�I������B
 *    �i�����W���u�ł���ꍇ�ɂ́A�q�W���u�̂ݏI������j
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>ERROR_CONTINUE</code> </td>
 *    <td align="left">
 *      �G���[�f�[�^�Ƃ��ă��O�o�͂��s���A�������p������B
 *    �r�W�l�X���W�b�N���܂܂��g�����U�N�V�����ɂ͉e����^���Ȃ��B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>ERROR_END</code> </td>
 *    <td align="left">
 *      �G���[���b�Z�[�W�����O�ɏo�͂��A�W���u���I������B
 *    �g�����U�N�V�����̓��[���o�b�N�����B
 *    �i�����W���u�ł���ꍇ�ɂ́A�q�W���u�̂ݏI������j
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <p>
 */
public enum ReturnCode {

    /**
     * �ԋp�R�[�h�B
     */
    NORMAL_CONTINUE, NORMAL_END, ERROR_CONTINUE, ERROR_END

}
