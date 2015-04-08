/* 
 * PROJECT: NyARToolkit
 * --------------------------------------------------------------------------------
 * This work is based on the original ARToolKit developed by
 *   Hirokazu Kato
 *   Mark Billinghurst
 *   HITLab, University of Washington, Seattle
 * http://www.hitl.washington.edu/artoolkit/
 *
 * The NyARToolkit is Java edition ARToolKit class library.
 * Copyright (C)2008-2009 Ryo Iizuka
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * For further information please contact.
 *	http://nyatla.jp/nyatoolkit/
 *	<airmail(at)ebony.plala.or.jp> or <nyatla(at)nyatla.jp>
 * 
 */
package jp.nyatla.nyartoolkit.core.transmat;

import jp.nyatla.nyartoolkit.core.NyARException;
import jp.nyatla.nyartoolkit.core.squaredetect.NyARSquare;
import jp.nyatla.nyartoolkit.core.types.matrix.NyARDoubleMatrix44;


/**
 * このインタフェイスは、二次元矩形から３次元位置姿勢を推定する関数を定義します。
 * ARToolKitの３次元推定処理の一部です。
 */
public interface INyARTransMat
{
	/**
	 * この関数は、理想座標系の四角系を元に、位置姿勢変換行列を求めます。
	 * 位置姿勢変換行列は、オフセット位置を基準とした変換行列です。
	 * 実装クラスでは、マーカの二次元座標から、位置姿勢行列を計算する処理を実装します。
	 * @param i_square
	 * 矩形情報を格納したオブジェクトです。
	 * @param i_offset
	 * カメラ座標系での、矩形のオフセット位置です。通常、原点中心のマーカ座標になります。
	 * @param o_result
	 * 結果を格納するオブジェクトです。
	 * @param o_param
	 * 結果パラメータを格納するオブジェクトです。nullの場合無視します。
	 * @throws NyARException
	 */
	public boolean transMat(NyARSquare i_square,NyARRectOffset i_offset, NyARDoubleMatrix44 o_result,NyARTransMatResultParam o_param) throws NyARException;
	/**
	 * この関数は、理想座標系の四角系を元に、位置姿勢変換行列を求めます。
	 * 位置姿勢変換行列は、オフセット位置を基準とした変換行列です。
	 * 実装クラスでは、マーカの二次元座標から、位置姿勢行列を計算する処理を実装します。
	 * 姿勢推定にi_prev_resultにある過去の情報を参照するため、変移が少ない場合は、{@link #transMat}と比較して高品質な値を返します。
	 * <p>使い方 -
	 * この関数は、連続して同じ対象（マーカ）の姿勢行列を求めるときに、効果を発揮します。フレーム毎に、そのマーカの前回求めた姿勢変換行列を繰り返し
	 * 入力してください。
	 * </p>
	 * @param i_square
	 * 矩形情報を格納したオブジェクトです。
	 * @param i_offset
	 * カメラ座標系での、矩形のオフセット位置です。通常、原点中心のマーカ座標になります。
	 * @param i_prev_result
	 * 参照する過去のオブジェクトです。このオブジェクトとo_resultには同じものを指定できます。
	 * @param o_result
	 * 結果を格納するオブジェクトです。
	 * @param o_param
	 * 結果パラメータを格納するオブジェクトです。nullの場合無視します。
	 * @return
	 * トラッキングに失敗した場合はfalseを返します。その場合は{@link #transMat}からやり直してください。
	 * @throws NyARException
	 */
	public boolean transMatContinue(NyARSquare i_square,NyARRectOffset i_offset, NyARDoubleMatrix44 i_prev_result,double i_prev_err,NyARDoubleMatrix44 o_result,NyARTransMatResultParam o_param) throws NyARException;
}
