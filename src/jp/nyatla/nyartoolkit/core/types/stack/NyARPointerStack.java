/* 
 * PROJECT: NyARToolkit(Extension)
 * --------------------------------------------------------------------------------
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
package jp.nyatla.nyartoolkit.core.types.stack;

import java.lang.reflect.Array;

import jp.nyatla.nyartoolkit.core.NyARException;

/**
 * このクラスは、オブジェクトの参照値を格納する可変長配列です。
 * このクラスの実体化は禁止しています。継承して使ってください。
 * @param <T>
 * 配列型を指定します。
 */
public class NyARPointerStack<T>
{
	/** オブジェクトの参照値を格納するバッファ*/
	protected T[] _items;
	/** 配列の有効な長さ。({@link #_items}の配列長とは異なることに注意してください。*/
	protected int _length;
	
	/**
	 * コンストラクタです。
	 * クラスの実体化を禁止するために宣言しています。
	 * 継承クラスから呼び出してください。
	 * @throws NyARException
	 */
	protected NyARPointerStack() throws NyARException
	{
	}

	/**
	 * この関数は、インスタンスを初期化します。
	 * この関数は、このクラスを継承したクラスのコンストラクタから呼び出します。
	 * @param i_length
	 * 配列の最大長さ
	 * @param i_element_type
	 * 配列型を示すクラスタイプ
	 * @throws NyARException
	 */
	@SuppressWarnings("unchecked")
	protected void initInstance(int i_length,Class<T> i_element_type) throws NyARException
	{
		//領域確保
		this._items = (T[])Array.newInstance(i_element_type, i_length);
		//使用中個数をリセット
		this._length = 0;
		return;		
	}

	/**
	 * この関数は、配列の最後尾にオブジェクトを追加します。
	 * @param i_object
	 * 追加するオブジェクト
	 * @return
	 * 追加したオブジェクト。失敗するとnullを返します。
	 */
	public T push(T i_object)
	{
		// 必要に応じてアロケート
		if (this._length >= this._items.length){
			return null;
		}
		// 使用領域を+1して、予約した領域を返す。
		this._items[this._length]=i_object;
		this._length++;
		return i_object;
	}
	/**
	 * この関数は、配列の最後尾にオブジェクトを追加します。
	 * {@link #push}との違いは、失敗したときにASSERT、または例外を発生することです。
	 * 確実に成功することがわっかっていない場合は、{@link #push}を使ってください。
	 * @param i_object
	 * 追加するオブジェクト
	 * @return
	 * 追加したオブジェクト。
	 */
	public T pushAssert(T i_object)
	{
		// 必要に応じてアロケート
		assert(this._length < this._items.length);
		// 使用領域を+1して、予約した領域を返す。
		this._items[this._length]=i_object;
		this._length++;
		return i_object;
	}
	
	/** 
	 * この関数は、配列の最後尾の要素を取り除いて返します。
	 * @return
	 * 最後尾のオブジェクト。
	 */
	public T pop()
	{
		assert(this._length>=1);
		this._length--;
		return this._items[this._length];
	}
	/**
	 * この関数は、配列の最後尾から指定個数の要素を取り除きます。
	 * @param i_count
	 * 取り除く個数
	 */
	public final void pops(int i_count)
	{
		assert(this._length>=i_count);
		this._length-=i_count;
		return;
	}	
	/**
	 * この関数は、配列全体を返します。
	 * 有効な要素の数は、先頭から{@link #getLength}個です。
	 * @return
	 * 配列の参照ポインタ
	 */
	public final T[] getArray()
	{
		return this._items;
	}
	/**
	 * この関数は、指定したインデクスの配列要素を返します。
	 * @param i_index
	 * 要素のインデクス番号。
	 * 有効な値は、0から{@link #getLength}-1です。
	 * @return
	 * 配列要素の参照値
	 */
	public final T getItem(int i_index)
	{
		return this._items[i_index];
	}
	/**
	 * この関数は、配列の有効な要素数返します。
	 * @return
	 * 有効な要素数
	 */
	public final int getLength()
	{
		return this._length;
	}
	/**
	 * この関数は、配列の最大サイズを返します。
	 * @return
	 */
	public final int getArraySize()
	{
		return this._items.length;
	}
	/**
	 * この関数は、指定したインデクスの要素を配列から取り除きます。
	 * 要素は、前方詰めで詰められます。
	 * @param i_index
	 * 削除する要素のインデクス
	 */
	public void remove(int i_index)
	{
		assert(this._length>i_index && i_index>=0);
		
		if(i_index!=this._length-1){
			int i;
			final int len=this._length-1;
			T[] items=this._items;
			for(i=i_index;i<len;i++)
			{
				items[i]=items[i+1];
			}
		}
		this._length--;
	}
	/**
	 * この関数は、指定したインデクスの要素を配列から取り除きます。
	 * 要素の順番は、削除したインデクス以降が不定になります。
	 * このAPIは、最後尾の有効要素と、削除対象の要素を交換することで、削除を実現します。
	 * {@link #remove}より高速ですが、要素の順序が重要な処理では注意して使ってください。
	 * @param i_index
	 * 削除する要素のインデクス
	 */
	public void removeIgnoreOrder(int i_index)
	{
		assert(this._length>i_index && i_index>=0);
		//値の交換
		if(i_index!=this._length-1){
			this._items[i_index]=this._items[this._length-1];
		}
		this._length--;
	}
	/**
	 * この関数は、配列の長さを0にリセットします。
	 */
	public void clear()
	{
		this._length = 0;
	}
	/**
	 * 配列の長さを変更します。
	 * @param i_length
	 */
	public void setLength(int i_length)
	{
		this._length=i_length;
	}
	/**
	 * インスタンスの配列の内容を、o_destへコピーします。
	 * @param o_dest
	 *//*
	protected void copyTo(NyARPointerStack<T> o_dest)
	{
		assert(o_dest._items.length>this._length);
		T[] s=this._items;
		T[] d=o_dest._items;
		
		o_dest._length=this._length;
		for(int i=this._length-1;i>=0;i--){
			d[i]=s[i];
		}
	}*/
}