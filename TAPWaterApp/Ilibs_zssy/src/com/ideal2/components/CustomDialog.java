package com.ideal2.components;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ideal2.components.MulSelection.ICallJsBack;
import com.ideal2.demo.R;

/**
 * �Զ���Ի���
 * 
 * @author Administrator
 * 
 */
public class CustomDialog {

	// ��ѡ���Ƕ�ѡ
	private int type;
	// ���ݿ��ID
	private int tableID;
	// ��������
	private int sortType;
	// �Ի������
	private String title;
	// �ָ���
	private char Separator;
	// ��Ӧѡ�� ѡ��ʱΪ��
	private boolean[] select;
	// ѡ�����
	private int length;
	// �����Ķ���
	private Context context;
	// ѡ���ַ�������
	private String[] item;
	// ѡ��id����
	private String[] ids;
	// Ҫ�󶨵Ŀؼ�
	private TextView textview;
	// �ַ�����ID��ӳ���
	TreeMap<String, Integer> nameKey;
	// ID���ַ�����ӳ���
	TreeMap<Integer, String> idKey;

	// dialog��������
	private String itemId;
	private String showValue;
	private List<Object> list;

	private OnItemChangeListener onItemChangeListener;
	// �������
	private OnClearListener onClearListener;

	// Ĭ��ѡ�е�id
	private String selectedId;
	//Ĭ��ѡ�е�index
	private int selectedIndex = 0;
	
	
	private String reverseItem;
	
	private OnConfirmListener onConfirmListener;
	
	public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
		this.onConfirmListener = onConfirmListener;
	}

	/**
	 * ���캯��
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param type
	 *            0Ϊ��ѡ 1Ϊ��ѡ
	 * @param tableID
	 *            ���ݿ��ID
	 * @param sortType
	 *            0Ϊ��ƴ������ 1Ϊ��ID����
	 * @param title
	 *            ����
	 * @param Separator
	 *            �ָ���
	 * @param textview
	 *            �󶨵Ŀؼ�
	 */
	public CustomDialog(final Context context, final int type,
			final int tableID, final int sortType, final String title,
			final char Separator, final TextView textview) {
		this.type = type;
		this.tableID = tableID;
		this.sortType = sortType;
		this.title = title;
		this.Separator = Separator;
		this.context = context;
		this.textview = textview;
		this.nameKey = new TreeMap<String, Integer>();
		this.idKey = new TreeMap<Integer, String>();
		// ��SQLite��ȡ�ֶ�
		// this.item = getItems(this.tableID, this.sortType);
		// this.length = item.length;
		// this.select = new boolean[this.length];
	}

	// ����dialog��������
	public void setData(List<Object> list, String itemId, String showValue) {
		this.list = list;
		if (null != this.list && this.list.size() > 0) {
			this.length = list.size();
			this.select = new boolean[this.length];
			ids = new String[length];
			item = new String[length];
			
			if(selectedIndex<list.size()){
				select[selectedIndex] = true;
			}
			

			if ("string".equalsIgnoreCase(showValue)) {
				for (int i = 0; i < length; i++) {
					item[i] = (String) list.get(i);
				}
			} else {
				itemId = itemId.substring(0, 1).toUpperCase()
						+ itemId.substring(1);
				showValue = showValue.substring(0, 1).toUpperCase()
						+ showValue.substring(1);
				for (int i = 0; i < length; i++) {
					Object obj = list.get(i);
					ids[i] = getter(obj, itemId) + "";
					item[i] = getter(obj, showValue) + "";
				}
			}
		}else if(null == this.list ){
			this.length = 0;
			this.select = new boolean[0];
			ids = new String[0];
			item = new String[0];
			
		}else{
			this.length = 0;
			this.select = new boolean[0];
			ids = new String[0];
			item = new String[0];
		}

	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
		if (null != ids) {
			try {
				select[0] = false;
				String[] idsStrs = this.selectedId.split("[|]");
				for (int i = 0; i < ids.length; i++) {
					for (int j = 0; j < idsStrs.length; j++) {
						if (ids[i].equalsIgnoreCase(idsStrs[j])) {
							select[i] = true;
							this.textview.setTag(ids[i]);
							this.textview.setText(item[i]);
							break;
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Object getter(Object obj, String att) {
		try {
			Method method = obj.getClass().getMethod("get" + att);
			return method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * �����ݿ��ȡ�ַ���
	 * 
	 * @param tableID
	 *            ���ݿ��ID
	 * @param sortType
	 *            ��������
	 * @return
	 */
	private String[] getItems(int[] id, String[] strings, int sortType) {
		/**
		 * ��ȡSQLite�е�����
		 */
		// String[] strings = { "һ", "��", "��", "��" };
		// int[] id = { 3, 4, 2, 1 };
		/**
		 * ��Ҫ�滻
		 */

		String[] re = new String[strings.length];
		// ���������
		if (strings.length != id.length) {
			return null;
		} else {
			for (int i = 0; i < strings.length; i++) {
				this.nameKey.put(strings[i], id[i]);
				this.idKey.put(id[i], strings[i]);
			}
		}

		// ��ArrayList����
		if (sortType == 0) {
			// ����ƴ������
			ArrayList<String> s = new ArrayList<String>();
			s.addAll(this.nameKey.keySet());
			Collections.sort(s, new Comparator<String>() {
				public int compare(String o1, String o2) {
					try {
						return (new String(o1.getBytes("GBK"), "ISO-8859-1"))
								.compareTo(new String(o2.getBytes("GBK"),
										"ISO-8859-1"));
					} catch (Exception e) {
						return 0;
					}
				}
			});
			for (int i = 0; i < s.size(); i++) {
				re[i] = s.get(i);
			}
		} else if (sortType == 1) {
			// ����ID����
			ArrayList<Integer> ids = new ArrayList<Integer>();
			ids.addAll(this.idKey.keySet());
			Collections.sort(ids);
			for (int i = 0; i < ids.size(); i++) {
				re[i] = this.idKey.get(ids.get(i));
			}
		} else {
			return null;
		}

		return re;
	}

	/**
	 * ���TreeMap
	 * 
	 * @param t
	 */
	public static void outputTreemap(TreeMap<String, Integer> t) {
		Iterator<Integer> values = t.values().iterator();
		Iterator<String> keys = t.keySet().iterator();

		while (keys.hasNext()) {
			Log.i(keys.next() + "", values.next() + "");
		}
	}

	/**
	 * ��ʾ�Ի���
	 */
	public void show() {
		
		AlertDialog dialog = null;

		final boolean[] temp = new boolean[this.length];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = this.select[i];
		}
		String reverseId = "";
		if (this.type == 0) {
			// ���ɶ�ѡ�Ի���
			for(int i = 0;i<item.length;i++){
				if(null!=this.reverseItem&&item[i].indexOf(this.reverseItem)!=-1){
					reverseId = i+"";
					break;
				}
			}
			
			MulSelection mulSelection = new MulSelection(this.context, this.textview.getText()+"",this.textview.getTag()+"");
			mulSelection.parseXml(ids,item,reverseId);
			mulSelection.setiJsBack(new ICallJsBack() {
				
				@Override
				public void callJs(String arg0, String arg1) {
					
					textview.setTag(arg1 + "");
					textview.setText(arg0);
					if(null!=onConfirmListener){
						onConfirmListener.OnConfirm(textview, arg0, arg1);
					}
					if(null!=onItemChangeListener){
						onItemChangeListener.onItemChange(textview);
					}
				}
			});
			mulSelection.show();
			/*dialog = new AlertDialog.Builder(this.context)
					// .setIcon(R.drawable.logo)
					.setTitle(this.title)
					.setMultiChoiceItems(this.item, temp,
							new OnMultiChoiceClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which, boolean isChecked) {
									Log.d("+++++++", isChecked+"");
									String str = item[which];
									if(null!=CustomDialog.this.reverseItem&&isChecked){
										for(int i =0;i<CustomDialog.this.reverseItem.length;i++){
											if(CustomDialog.this.reverseItem[i].equals(str)){
												for(int j =0;j<temp.length;j++){
													if(j!=which){
														temp[j]=true;
													}
												}
												break;
											}
										}
										
									}
								}
							}).setPositiveButton("����", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					}).setNeutralButton("���", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							select = new boolean[length];
							textview.setText("");

						}
					}).setNegativeButton("ȷ��", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							select = temp;

							textview.setTag(getSelID() + "");

							// textview.setText(getSelectText() + "id:"
							// + getSelID());
							textview.setText(getSelectText());
						}
					}).create();*/
		} else if (this.type == 1) {
			// ��ѡ
			final int[] i = new int[1];
			i[0] = -1;
			for (int j = 0; j < this.length; j++) {
				if (this.select[j]) {
					i[0] = j;
					break;
				}
			}
			dialog = new AlertDialog.Builder(this.context)
					// .setIcon(R.drawable.logo)
					.setTitle(this.title)
					.setSingleChoiceItems(item, i[0], new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							select = new boolean[length];
							select[which] = true;
							textview.setText(getSelectText());
							textview.setTag(getSelID() + "");
							if (null != onItemChangeListener) {
								onItemChangeListener.onItemChange(list
										.get(which));
							}
							dialog.dismiss();
						}
					}).setPositiveButton("����", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					}).setNeutralButton("���", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							if (null != onClearListener) {
								if (onClearListener.onClear()) {
									return;
								}
							}
							select = new boolean[length];
							textview.setText("");
							textview.setTag("");
						}
					}).create();
		} else if (this.type == 2) {
			// ����
		
			String dateStr = textview.getText().toString().trim();
			int dateindex = dateStr.indexOf(" ");
			if(dateindex!=-1){
				dateStr = dateStr.substring(0, dateindex);
			}
			if (null == dateStr || "".equals(dateStr)) {
//				Calendar calendar = Calendar.getInstance();
//				dateStr = calendar.get(Calendar.YEAR) + "-"
//						+ (calendar.get(Calendar.MONTH)+1) + "-"
//						+ calendar.get(Calendar.DAY_OF_MONTH);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date(System.currentTimeMillis());
				dateStr = sdf.format(date);
				textview.setText(dateStr);
			}
			String[] dates = dateStr.split("-");
			int year = Integer.parseInt(dates[0]);
			int month = Integer.parseInt(dates[1])-1;
			int day = Integer.parseInt(dates[2]);

			DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker datePicker, int year,
						int month, int dayOfMonth) {
					String dateStr = year + "-" + (month+1) + "-"
							+ dayOfMonth;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					try {
						date = new SimpleDateFormat("yyyy-M-d").parse(dateStr);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					textview.setText(sdf.format(date));
				}
			};
			dialog = new DatePickerDialog(this.context, dateListener, year,
					month, day);
			
			dialog.setButton3(this.context.getString(R.string.clear), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					textview.setText("");
				}
			});
		}else if (this.type == 3) {
			// ʱ��
		
			String dateStr = textview.getText().toString().trim();
			int dateindex = dateStr.indexOf(" ");
			if(dateindex!=-1){
				dateStr = dateStr.substring(0, dateindex);
			}
			if (null == dateStr || "".equals(dateStr)) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				Date date = new Date(System.currentTimeMillis());
				dateStr = sdf.format(date);
				textview.setText(dateStr);
			}
			String[] dates = dateStr.split(":");
			int hourOfDay = Integer.parseInt(dates[0]);
			int minute = Integer.parseInt(dates[1]);
			
			
			TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener(){

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					String dateStr = (hourOfDay<10?"0"+hourOfDay:hourOfDay)+":"+(minute<10?"0"+minute:minute);
//					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//					Date date = new Date(view.);
//					String timeStr = sdf.format(date);
					textview.setText(dateStr);
				}
				
			};
			dialog = new TimePickerDialog(context, timeSetListener, hourOfDay, minute, true);
			
			dialog.setButton3(this.context.getString(R.string.clear), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					textview.setText("");
				}
			});
		}
		if(dialog!=null){
		try {
			dialog.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

	public String getSelID() {
		String re = "";
		for (int i = 0; i < select.length; i++) {
			if (select[i]) {
				re = re == "" ? this.ids[i] : re + this.Separator + this.ids[i];
			}
		}
		return re;
	}

	public String getSelectID() {
		String re = "";
		for (int i = 0; i < select.length; i++) {
			if (select[i]) {
				String string = this.nameKey.get(this.item[i]) + "";
				re = re == "" ? string : re + this.Separator + string;
			}
		}
		return re;
	}

	public String getSelectText() {
		String re = "";
		for (int i = 0; i < select.length; i++) {
			if (select[i]) {
				re = re == "" ? this.item[i] : re + this.Separator
						+ this.item[i];
			}
		}
		return re;
	}

	public ArrayList<Integer> getSelectIDArray() {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for (int i = 0; i < select.length; i++) {
			if (select[i]) {
				arrayList.add(this.nameKey.get(this.item[i]));
			}
		}
		return arrayList;
	}

	public ArrayList<String> getSelectTextArray() {
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < select.length; i++) {
			if (select[i]) {
				arrayList.add(this.item[i]);
			}
		}
		return arrayList;
	}

	public List<Object> getList() {
		return list;
	}

	public interface OnItemChangeListener {
		public void onItemChange(Object obj);
	}

	public interface OnClearListener {
		public boolean onClear();
	}

	public void setOnClearListener(OnClearListener onClearListener) {
		this.onClearListener = onClearListener;
	}

	public void setOnItemChangeListener(
			OnItemChangeListener onItemChangeListener) {
		this.onItemChangeListener = onItemChangeListener;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public void setReverseItem(String reverseItem) {
		this.reverseItem = reverseItem;
	}
	
	public interface OnConfirmListener{
		public void OnConfirm(View view,String arg0,String arg1);
	}
	
}
