/*
 *  Copyright 2010 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.third.wheel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class ArrayWheelAdapter<T> implements WheelAdapter {
	
	/** The default items length */
	public static final int DEFAULT_LENGTH = -1;
	
	// items
	private T items[];
	// length
	private int length;

	/**
	 * Constructor
	 * @param items the items
	 * @param length the max items length
	 */
	public ArrayWheelAdapter(T items[], int length) {
		this.items = items;
		this.length = length;
		calNow.setTimeInMillis(System.currentTimeMillis());
	}
	
	/**
	 * Contructor
	 * @param items the items
	 */
	public ArrayWheelAdapter(T items[]) {
		this(items, DEFAULT_LENGTH);
		calNow.setTimeInMillis(System.currentTimeMillis());
	}

	private DateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日 E", Locale.getDefault());
	private DateFormat dateFormat2 = new SimpleDateFormat("M月d日 E", Locale.getDefault());
	private Calendar cal = Calendar.getInstance(Locale.getDefault());
	private Calendar calNow = Calendar.getInstance(Locale.getDefault());
	@Override
	public String getItem(int index) {
		if (index >= 0 && index < items.length) {
			if (items[index] instanceof Long) {
				cal.setTimeInMillis((Long)(items[index]));
				if (cal.get(Calendar.YEAR) == calNow.get(Calendar.YEAR) && 
						cal.get(Calendar.MONTH) == calNow.get(Calendar.MONTH) &&
						cal.get(Calendar.DAY_OF_MONTH) == calNow.get(Calendar.DAY_OF_MONTH)) {
					return "今天";
				} else if (cal.get(Calendar.YEAR) == calNow.get(Calendar.YEAR)) {
					return dateFormat2.format(cal.getTime());
				} else {
					return dateFormat.format(cal.getTime());
				}
			} else {
				return items[index].toString();
			}
			
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return items.length;
	}

	@Override
	public int getMaximumLength() {
		return length;
	}

}
