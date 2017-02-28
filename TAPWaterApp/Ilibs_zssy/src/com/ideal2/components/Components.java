package com.ideal2.components;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal2.demo.R;



/*
 * ��װ�Ŀؼ� typeΪ0ʱ һ��TextView��һ��EidtText typeΪ1ʱ ������TextView ����һ����һ��������ѡ�Ի����¼�
 * typeΪ2ʱ ������TextView ����һ����һ��������ѡ�Ի����¼� typeΪ3ʱ ������TextView ����һ����һ���������ڶԻ����¼�
 * 
 * @author Administrator
 * 
 */
public class Components extends RelativeLayout implements ICheck{
	private TextView name, input;
	private RelativeLayout layout;
	
	private int width;
	private float scale;
	private int textSize;
	private int standHeight = 37;
	private String text;
	private int color;
	private int weight;
	private int inputWeight;
	private int maxLength;
	private int type;
	private boolean reverse;
	private int sortType;
	private char Separator;
	private String regular;
	private String title;
	private int addres;
	private Context context;
	private int inputBackground;
	private String inputFilerReg;
	private boolean checkEmpty;
	private String check;
	private Integer minNum;
	private Integer maxNum;
	private String numeric;
	private Integer listenerType;
	private boolean isSpace;
	private Integer space;
	
	private String reverseItem;
	
	private KeyListener defaultKeyListener;
	
	private CustomDialog.OnItemChangeListener onItemChangeListener;
	private OnFocusChangeListener onFocusChangeListener;
	//�������
	private CustomDialog.OnClearListener onClearListener;
	
	//Ĭ��ѡ�е�id
	private String selectedId = "";
	//Ĭ��ѡ�е�index
	private int selectedIndex = 0;
	
	private String inputType;

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
		if(null!=customDialog){
			customDialog.setSelectedId(this.selectedId);
		}
	}
	
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	// dialog��������
	private String itemId;
	private String showValue;
	private List list;
	private boolean isUpdateList = true;
	
	private OnClickListener onClickListener;
	
	//1��ʼ
	public String getSelectedIndex() {
		if(input.getText().toString().trim().equals("")){
			return "";
		}
		for(int i = 0;i<list.size();i++){
			if(input.getText().toString().trim().equals(list.get(i)+"")){
				return i+1+"";
			}
		}
		return "";
	}
	
	public void setData(List list,String itemId,String showValue){
		this.itemId = itemId;
		this.showValue = showValue;
		this.list = list;
		if(null!=list&&list.size()>0){
			if("string".equalsIgnoreCase(showValue)){
				if(selectedIndex<list.size()){
					this.input.setText(list.get(selectedIndex)+"");
				}
			}else{
				if(selectedIndex<list.size()){
					showValue = showValue.substring(0, 1).toUpperCase()+showValue.substring(1);
					itemId = itemId.substring(0, 1).toUpperCase()+itemId.substring(1);
					this.input.setText(getter(list.get(selectedIndex),showValue)+"");
					this.input.setTag(getter(list.get(selectedIndex),itemId)+"");
				}
			}
		}
		
		if(null!=customDialog){
			customDialog.setData(list, itemId, showValue);
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
	
	public Components(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;
		
		
		// ��ȡ������Ļ���� �� �� ����ܶ�
		ScreenParameter screenParameter = ScreenParameter
				.getInstance(this.context);
		this.width = screenParameter.getWidth();
		this.scale = screenParameter.getDensity() / 160;

		/**
		 * ������Ļ����ܶȼ��������С
		 */
		this.textSize = this.px2dip(18);
//		this.textSize = 12;
		/**
		 * �ɸ���ʵ���������
		 */

		TypedArray a = this.context.obtainStyledAttributes(attrs,
				R.styleable.Components);
		// ��ȡ�ı�������
		this.text = a.getString(R.styleable.Components_text);
		// ��ȡ���������
		this.type = a.getInteger(R.styleable.Components_type, 0);
		// ��ȡ�ı�����ɫ
		this.color = a.getColor(R.styleable.Components_textColor, Color.BLACK);
		// ��ȡ�ؼ�ռ��Ļ��ȵİٷֱ�
		this.weight = a.getInteger(R.styleable.Components_weight, 50);
		// ��ȡ�����ռ�ؼ���ȵİٷֱ�
		this.inputWeight = a.getInteger(R.styleable.Components_inputWeight, 60);
		// �Ƿ�ת
		this.reverse = a.getBoolean(R.styleable.Components_reverse, false);
		// ����������������
		this.maxLength = a.getInteger(R.styleable.Components_maxLength, 30);
		// ��ȡ���ݵ�ַ
		this.addres = a.getInteger(R.styleable.Components_address, -1);
		// ��ȡ�ָ���
		this.Separator = a.getString(R.styleable.Components_separator)
				.charAt(0);
		// ��ȡ������ʽ
		this.regular = a.getString(R.styleable.Components_regular);
		// ��ȡ����
		this.title = a.getString(R.styleable.Components_title);
		// ��ȡ��������
		this.sortType = a.getInteger(R.styleable.Components_sortType, 0);
		// ��ȡ���ɵ�������ʽ
		this.inputFilerReg = a.getString(R.styleable.Components_inputFilerReg);
		if(null==inputFilerReg||"".equals(inputFilerReg)){
			inputFilerReg = "^[<>?/\\*]+$";
		}
		
		this.listenerType = a.getInteger(R.styleable.Components_listenerType, 0);
		
		//���������������
		this.numeric = a.getString(R.styleable.Components_numeric);
		//������С��
		this.minNum = a.getInteger(R.styleable.Components_minNum,0);
		//���������
		this.maxNum = a.getInteger(R.styleable.Components_maxNum,this.maxLength);
		//��ȡ����Ƿ�Ϊ��
		this.checkEmpty = a.getBoolean(R.styleable.Components_checkEmpty, false);
		this.check = a.getString(R.styleable.Components_check);
		//���뱳����ʽ
		this.inputBackground = a.getResourceId(
				R.styleable.Components_inputBackground, R.drawable.d_cp_input);
		this.inputType = a.getString(R.styleable.Components_inputType);
		this.isSpace = a.getBoolean(R.styleable.Components_isSpace, true);
		this.space = a.getInteger(R.styleable.Components_space, 0);
		a.recycle();

		/**
		 * �����ı��������йز���
		 */
		if(this.space!=0){
			this.name.setWidth(this.space);
		}
		this.name = new TextView(this.context);
		this.name.setTextColor(this.color);
		this.name.setTextSize(this.textSize);
		this.name.setGravity(Gravity.CENTER_VERTICAL);
		if(null!=this.text){
			int index = this.text.indexOf("*");
			if(index>=0){
				this.text = this.text.replace("*","<font color=#E61A6B>*</font>");
				this.name.setText(Html.fromHtml(this.text));
			}else{
				this.name.setText(this.text);
			}
		}
		// this.name.setBackgroundColor(R.color.transparent);
		this.name.setPadding(10, 0, 10, 0);
		/**
		 * �ɸ���ʵ���������
		 */

		switch (this.type) {
		case 0: {
			/**
			 * ��������������йز���
			 */
			this.input = new EditText(this.context);
			
			this.input.setSingleLine();
			this.input.setTextSize(this.textSize);
			this.input.setGravity(Gravity.CENTER_VERTICAL);
			this.input.setTextColor(this.color);
			
			if(null!=inputType&&"textVisiblePassword".equalsIgnoreCase(inputType)){
				Log.d("asd", "asdas");
				this.input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}
			if(null!=numeric&&"integer".equalsIgnoreCase(numeric)){
//				this.input.setRawInputType(InputType.TYPE_CLASS_NUMBER);
				this.input.setInputType(InputType.TYPE_CLASS_NUMBER);
			}else if(null!=numeric&&"decimal".equalsIgnoreCase(numeric)){
//				this.input.setRawInputType(InputType.TYPE_CLASS_NUMBER);
				this.input.setKeyListener(new com.ideal2.components.NumberKeyListener(){

					@Override
					public int getInputType() {
						// TODO Auto-generated method stub
						return InputType.TYPE_CLASS_NUMBER;
					}

					@Override
					protected char[] getAcceptedChars() {
						char[] numberChars={'1','2','3','4','5','6','7','8','9','0','.'};
						return numberChars;
					}

				});
				
			}
		
			
			InputFilter specialfilter = new InputFilter() {
				public CharSequence filter(CharSequence source, int start,
						int end, Spanned dest, int dstart, int dend) {
					/*
					 * CharSequence source, //��������� int start, //��ʼλ�� int end,
					 * //����λ�� Spanned dest, //��ǰ��ʾ������ int dstart, //��ǰ��ʼλ�� int
					 * dend //��ǰ����λ��
					 */
					if ("".equals(source.toString())) {
						return null;
					}
					String regex = inputFilerReg;
					Pattern mPattern = Pattern.compile(regex);
					Matcher matcher = mPattern.matcher(source.toString());
					if (matcher.matches()) {
						return "";
					} else if(!matcher.matches()){
						return source;
					}
					return null;
				}
			};
			// �����ı���������볤��,�����ַ�����
			this.input
					.setFilters(new InputFilter[] {
							new InputFilter.LengthFilter(this.maxLength),
							specialfilter });

			this.input.setBackgroundResource(this.inputBackground);
			this.input.setSingleLine(true);
			this.input.setPadding(10, 0, 10, 0);
			/**
			 * �ɸ���ʵ���������
			 */
			switch(listenerType){
			case 1:
				this.setDialog(1,listenerType);
				break;
			}
			defaultKeyListener =  this.input.getKeyListener();
			break;
		}

		case 1: {
			/**
			 * ������ʾ��ѡ�����ݵ��ı���������ز���
			 */
			this.input = new TextView(this.context);
			this.input.setSingleLine();
			this.input.setTextColor(this.color);
			this.input.setTextSize(this.textSize);
			this.input.setGravity(Gravity.LEFT);
			this.input.setGravity(Gravity.CENTER_VERTICAL);
			this.input.setBackgroundResource(this.inputBackground);
			this.input.setPadding(10, 0, 0, 0);
			this.input.setCompoundDrawablesWithIntrinsicBounds(null, null,
					context.getResources().getDrawable(R.drawable.xl), null);
			/**
			 * �ɸ���ʵ���������
			 */

			this.setDialog(0,0);
			break;
		}
		case 2: {
			/**
			 * ������ʾ��ѡ�����ݵ��ı���������ز���
			 */
			this.input = new TextView(this.context);
			this.input.setSingleLine();
			this.input.setTextColor(this.color);
			this.input.setTextSize(this.textSize);
			this.input.setGravity(Gravity.LEFT);
			this.input.setGravity(Gravity.CENTER_VERTICAL);
			this.input.setBackgroundResource(this.inputBackground);
			this.input.setPadding(10, 0, 0, 0);
			this.input.setCompoundDrawablesWithIntrinsicBounds(null, null,
					context.getResources().getDrawable(R.drawable.xl), null);
			/**
			 * �ɸ���ʵ���������
			 */

			this.setDialog(1,0);
			break;
		}
		case 3: {
			/**
			 * ������ʾ���ڿ����ݵ��ı���������ز���
			 */
			this.input = new TextView(this.context);
			this.input.setSingleLine();
			this.input.setTextColor(this.color);
			this.input.setTextSize(this.textSize);
			this.input.setGravity(Gravity.LEFT);
			this.input.setGravity(Gravity.CENTER_VERTICAL);
			this.input.setBackgroundResource(this.inputBackground);
			this.input.setPadding(10, 0, 0, 0);
			this.input.setCompoundDrawablesWithIntrinsicBounds(null, null,
					context.getResources().getDrawable(R.drawable.xl), null);
			//����Ĭ������
			String dateStr = this.input.getText().toString().trim();
			if(null==dateStr||"".equals(dateStr)){
//				Calendar calendar = Calendar.getInstance();
//				dateStr = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
//				this.input.setText(dateStr);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date(System.currentTimeMillis());
				dateStr = sdf.format(date);
				this.input.setText(dateStr);
			}
			/**
			 * �ɸ���ʵ���������
			 */

			this.setDialog(2,0);
			break;
		}
		case 4: {
			/**
			 * ������ʾ���ڿ����ݵ��ı���������ز���
			 */
			this.input = new TextView(this.context);
			this.input.setSingleLine();
			this.input.setTextColor(this.color);
			this.input.setTextSize(this.textSize);
			this.input.setGravity(Gravity.LEFT);
			this.input.setGravity(Gravity.CENTER_VERTICAL);
			this.input.setBackgroundResource(this.inputBackground);
			this.input.setPadding(10, 0, 0, 0);
			this.input.setCompoundDrawablesWithIntrinsicBounds(null, null,
					context.getResources().getDrawable(R.drawable.xl), null);
			//����Ĭ������
			String dateStr = this.input.getText().toString().trim();
			if(null==dateStr||"".equals(dateStr)){
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				Date date = new Date(System.currentTimeMillis());
				dateStr = sdf.format(date);
				this.input.setText(dateStr);
			}
			/**
			 * �ɸ���ʵ���������
			 */

			this.setDialog(3,0);
			break;
		}
		default: {
			break;
		}
		}

		// ��������ʢ���������ؼ���RelativeLayout
		this.layout = new RelativeLayout(this.context);

		this.name.setId(111);
		RelativeLayout.LayoutParams layoutParams = new LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		// �����ı���Ŀ��
		// this.width * this.weight * (100 - this.inputWeight) / 10000
		RelativeLayout.LayoutParams layoutParamsName = new LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT, this.standHeight);

		this.input.setId(112);
		// ���������Ŀ��
		RelativeLayout.LayoutParams layoutParamsInput = new LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT, this.standHeight);

		// �����Ƿ�ת������Ӧ����
		if (this.reverse) {
			layoutParamsName.addRule(RelativeLayout.RIGHT_OF, this.input
					.getId());
			this.layout.addView(this.input, layoutParamsInput);
			this.layout.addView(this.name, layoutParamsName);

		} else {
			layoutParamsInput.addRule(RelativeLayout.RIGHT_OF, this.name
					.getId());
			this.layout.addView(this.name, layoutParamsName);
			this.layout.addView(this.input, layoutParamsInput);

		}
		
		input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(null!=onFocusChangeListener){
					onFocusChangeListener.onFocusChange(v, hasFocus);
				}
			}
		});
		
		
	
		
		this.addView(this.layout, layoutParams);

	}

	/**
	 * �����ַ���֤ ֻ�����뺺����ĸ������
	 * 
	 * @param password
	 *            ��Ҫ��֤���ַ���
	 * @return
	 */
	private boolean limitString(String password) {
		String string = "^[A-Za-z0-9@#$%&*\u4e00-\u9fa5]+$";
		Pattern mPattern = Pattern.compile(string);
		Matcher matcher = mPattern.matcher(password);
		return matcher.matches();
	}

	/**
	 * ������Ч����֤
	 * 
	 * @param string
	 *            ������ʽ
	 * @param password
	 *            ��Ҫ��֤���ַ���
	 * @return
	 */
	private boolean regularCheck(String string, String password) {
		Pattern mPattern = Pattern.compile(string);
		Matcher matcher = mPattern.matcher(password);
		return matcher.matches();
	}
	
	private CustomDialog customDialog;
	
	public CustomDialog getCustomDialog() {
		return customDialog;
	}
	private boolean isshow = true;
	
	public boolean isIsshow() {
		return isshow;
	}

	public void setIsshow(boolean isshow) {
		this.isshow = isshow;
	}

	public void setDialog(int type,int lstenerType) {
		customDialog = new CustomDialog(this.context,
				type, this.addres, this.sortType, this.title,
				this.Separator, this.input);
		switch(lstenerType){
		case 0:
			this.input.setClickable(true);
			this.input.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
//					Components.this.input.setFocusable(true);
					if(null!=onClickListener){
						onClickListener.onClick(input);
					}
					if(null!=Components.this.list&&null==customDialog.getList()){
						customDialog.setData(list, itemId, showValue);
					}else if(null==Components.this.list){
						customDialog.setData(list, itemId, showValue);
					}
					if(null!=onItemChangeListener){
						customDialog.setOnItemChangeListener(onItemChangeListener);
					}
					if(null!=onClearListener){
						customDialog.setOnClearListener(onClearListener);
					}
					if(null!=reverseItem){
						customDialog.setReverseItem(reverseItem);
					}
					if(isshow){
						try {
							customDialog.show();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			break;
		case 1:
			if(null!=Components.this.list&&null==customDialog.getList()){
				customDialog.setData(list, itemId, showValue);
			}else if(null==Components.this.list){
				customDialog.setData(list, itemId, showValue);
			}
			break;
		}
	}
	
	public void showAdderDialog(){
		customDialog.setData(list, itemId, showValue);
		if(null!=customDialog){
			customDialog.show();
		}
	}
	
	public void showDialog(){
	
		if(null!=customDialog){
			customDialog.show();
		}
	}
	

	private int px2dip(float pxValue) {
		return (int) (pxValue / this.scale + 0.5f);
	}

	/**
	 * ��ȡ������Ի����ֵ
	 * 
	 * @return
	 */
	public String getText() {
		String string = this.input.getText().toString();
//		if (this.type == 0) {
//			if (!this.limitString(string)) {
//				Toast.makeText(this.context, "��������", Toast.LENGTH_LONG).show();
//				string = "";
//				this.input.setText(string);
//			} else if (this.regular != "" && this.regular != null) {
//				if (this.regularCheck(this.regular, string)) {
//					Toast.makeText(this.context, "��������", Toast.LENGTH_LONG)
//							.show();
//					string = "";
//					this.input.setText(string);
//				}
//			}
//		}
		return string;
	}

	@Override
	public Boolean check() {
		if(null==this.check||"".equals(this.check)){
			return false;
		}else{
			CheckUtil obj = new CheckUtil();
			try {
				this.check = this.check.toUpperCase();
				Method method = CheckUtil.class.getMethod("check"+this.check, String.class);
				Object objmsg = method.invoke(obj, getText());
				String msg = objmsg.toString();
				if("y".equalsIgnoreCase(msg)){
					return false;
				}else{
					showDialog(Html.fromHtml(text+msg).toString());
					return true;
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
	}

	@Override
	public Boolean checkEmpty() {
		if(this.checkEmpty){
			if(null==this.getText()||"".equals(this.getText().toString().trim())){
				showDialog(Html.fromHtml(text+this.context.getString(R.string.noisnull)).toString());
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean checklength() {
		if(minNum<=0&&maxNum>=this.maxLength){
			return false;
		}else if(minNum>0&&maxNum>=this.maxLength){
			if(this.input.getText().length()<this.minNum){
				showDialog(Html.fromHtml(text+"��СֵΪ"+minNum+"λ").toString());
				return true;
			}
		}else if(minNum<=0&&maxNum<this.maxLength){
			if(this.input.getText().length()>maxNum){
				showDialog(Html.fromHtml(text+"���ֵΪ"+maxNum+"λ").toString());
				return true;
			}
		}else if(minNum>0&&maxNum<this.maxLength){
			if(this.input.getText().length()<this.minNum||this.input.getText().length()>maxNum){
				showDialog(Html.fromHtml(text+"��СֵΪ"+minNum+"λ��"+"���ֵΪ"+maxNum+"λ").toString());
				return true;
			}
		}else{
			return false;
		}
		return false;
	}
	
	
	public void showDialog(String text){
		final Builder dia = new AlertDialog.Builder(this.context);
		dia.setMessage(text);
		dia.setNegativeButton(this.context.getString(R.string.ok), new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		} );
		dia.create().show();
	}

	public void setText(String text) {
		this.input.setText(text);
	}

	public void setEnabled(boolean enabled){
//		this.input.setEnabled(enabled);
//		this.input.setFocusable(enabled);
		
//		 if (enabled) {   
//			 this.input.setFilters(new InputFilter[] { new InputFilter() {   
//	                @Override  
//	                public CharSequence filter(CharSequence source, int start,   
//	                        int end, Spanned dest, int dstart, int dend) {   
//	                    return source.length() < 1 ? dest.subSequence(dstart, dend)   
//	                            : "";   
//	                }   
//	            } });   
//	        } else {   
//	        	this.input.setFilters(new InputFilter[] { new InputFilter() {   
//	                @Override  
//	                public CharSequence filter(CharSequence source, int start,   
//	                        int end, Spanned dest, int dstart, int dend) {   
//	                    return null;   
//	                }   
//	            } });   
//	        }  
		
		
		if(this.input instanceof EditText){
	        if(!enabled){
	        	this.input.setKeyListener(null);
	        	this.input.setBackgroundDrawable(this.context.getResources().getDrawable(R.drawable.cp_shape_down_hui));
	        }else{
	        	this.input.setKeyListener(defaultKeyListener);
	        	this.input.setBackgroundDrawable(this.context.getResources().getDrawable(inputBackground));
	        }
        }else{
        	this.input.setEnabled(enabled);
        }
	}
	
	public String getSelectedId(){
		if(null!= input.getTag()&&!"".equals(input.getText().toString().trim())){
			String selectid = (String)input.getTag();
			if (null!=selectid&&(selectid.endsWith("|"))) {
				selectid = selectid.substring(0, selectid.length()-1);
			}
			return selectid;
		}
		return this.selectedId;
	}
	
	public void clearInputTag(){
		input.setTag(null);
	}
	
	public interface OnClickListener{
		public void onClick(View v);
	}
	
	public OnClickListener getOnClickListener() {
		return onClickListener;
	}

	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
	
	public void setOnItemChangeListener(
			CustomDialog.OnItemChangeListener onItemChangeListener) {
		this.onItemChangeListener = onItemChangeListener;
	}
	
	public interface OnFocusChangeListener{
		public void onFocusChange(View v, boolean hasFocus);
	}
	
	public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
		this.onFocusChangeListener = onFocusChangeListener;
	}

	public boolean isUpdateList() {
		return isUpdateList;
	}

	public void setUpdateList(boolean isUpdateList) {
		this.isUpdateList = isUpdateList;
	}

	public void setOnClearListener(CustomDialog.OnClearListener onClearListener) {
		this.onClearListener = onClearListener;
	}

	public List getList() {
		return list;
	}

	public Integer getListenerType() {
		return listenerType;
	}

	public void setListenerType(Integer listenerType) {
		this.listenerType = listenerType;
	}
	
	public void setTitle(String text){
		this.name.setText(text);
	}
	
	public void setTitle(int strID){
		String text = getContext().getResources().getString(strID);
		this.name.setText(text);
	}

	public TextView getInput() {
		return input;
	}

	public void setReverseItem(String reverseItem) {
		this.reverseItem = reverseItem;
	}

	public boolean isCheckEmpty() {
		return checkEmpty;
	}

	public void setCheckEmpty(boolean checkEmpty) {
		this.checkEmpty = checkEmpty;
	}

	public TextView getName() {
		return name;
	}

	public void setName(TextView name) {
		this.name = name;
	}

	public boolean isSpace() {
		return isSpace;
	}

	public void setSpace(boolean isSpace) {
		this.isSpace = isSpace;
	}
	
}
