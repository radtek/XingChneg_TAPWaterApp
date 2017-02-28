package com.ideal2.components;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.ideal2.adapter.IMySingleChoice;
import com.ideal2.adapter.MySingleChoiceAdapter;
import com.ideal2.base.IDomainObject;
import com.ideal2.demo.R;
import com.ideal2.http.XmlNode;
import com.ideal2.log.ILog;
import com.ideal2.services.SelService;
import com.ideal2.util.ReflectUtil;

/***
 * ��ѡ�ؼ�
 * @author xufeng 
 * E ��List��Ķ��� 
 * DO����Ķ���
 */
public class MySingleChoice <E,DO extends IDomainObject> extends MyTextView implements IMySingleChoice<E> {
	
	private final static String TAG = "MySingleChoice";
	private MyDialog myDialog;
	private MyListView myListView;
	private MySingleChoiceAdapter<E> mySingleChoiceAdapter;
	private MyButton btn_quit;
	private MyButton btn_clear;
	private MyButton btn_ok;
	private MyButton btn_search;
	private MyTextView text_title;
	private String text;
	private SelService<DO> selService;
	private LinearLayout ll;
	private OnItemClickListener<E> onItemClickListener;
	private OnQuitListener onQuitListener;
	private OnClearListener onClearListener;
	private OnOkListener onOkListener;
	private OnSearchListener onSearchListener;
	private boolean isEdit;//false:���ɱ༭ true:�ɱ༭  Ĭ�ϣ�false
	private boolean showClear = false;
	
	
	/*
		status
		0:����Զ�̲�ѯ����
		1:�ر�Զ�̲�ѯ����
		2:�������ز�ѯ����  �Զ���ѯ
		3:�������ز�ѯ����  �رղ�ѯ
		4:�رձ��ز�ѯ����
		5:
		6:
		7:
		8:
		9:
	 */
	private int status;
	

	public MySingleChoice(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		isEdit = false;//Ĭ�ϣ�false
		
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.Components);

		myDialog = new MyDialog(getContext(), R.style.proDialog);
		ll = (LinearLayout) LinearLayout.inflate(context,
				R.layout.my_singlechoice_dialog, null);

		text_title = (MyTextView) ll.findViewById(R.id.text_title);
		btn_quit = (MyButton) ll.findViewById(R.id.btn_quit);
		btn_clear = (MyButton) ll.findViewById(R.id.btn_clear);
		btn_ok = (MyButton) ll.findViewById(R.id.btn_ok);
		myListView = (MyListView) ll.findViewById(R.id.list);
		mySingleChoiceAdapter = new MySingleChoiceAdapter<E>(context);
		myListView.setAdapter(mySingleChoiceAdapter);
		myDialog.setContentView(ll);

		// ���ñ���
		text = a.getString(R.styleable.Components_text);
		if (null != text && !"".equals(text)) {
			text_title.setText(text_title.getText() + "[" + text + "]");
		}

		myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					setSelectedIndex(position);
					if(null!=onItemClickListener){
						onItemClickListener.onItemClick(mySingleChoiceAdapter.getItem(position),parent,view,
								position,id);
					}
					if(isEdit){
						LinearLayout searchll = (LinearLayout)ll.findViewById(R.id.layout_search);
						MyEditText text_search = (MyEditText)searchll.findViewById(R.id.text_search);
						text_search.setText(mySingleChoiceAdapter.getSelectedValue());
					}else{
						setText(mySingleChoiceAdapter.getSelectedValue());
						myDialog.dismiss();
					}
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btn_quit();
		btn_clear();
		btn_ok();
		setSearchable(false);
	}
	
	public void btn_quit(){
		btn_quit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if(null!=onQuitListener){
						onQuitListener.onQuit(MySingleChoice.this);
					}
					myDialog.dismiss();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public void btn_clear(){
		btn_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					mySingleChoiceAdapter.clear();
					setText("");
					if(null!=onClearListener){
						onClearListener.onClear(MySingleChoice.this);
					}
					myDialog.dismiss();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public void btn_ok(){
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if(null!=onOkListener){
						onOkListener.onOk(MySingleChoice.this);					
					}
					setText(getSelectedValue());
					myDialog.dismiss();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * �Ƿ��в�ѯ����
	 * @param Searchable
	 * true ��
	 * false ��
	 */
	public void setSearchable(boolean Searchable){
		LinearLayout searchll = (LinearLayout)ll.findViewById(R.id.layout_search);
		if(Searchable){
			searchll.setVisibility(VISIBLE);
		}else{
			searchll.setVisibility(GONE);
		}
	}
	
	
	
	/**
	 * �ر�Զ�̲�ѯ����
	 */
	public void closeWebSearchable(){
		setWebSearchable(false, null,null, null, null, null);
	}
	
	public void clearAll(){
		mySingleChoiceAdapter.clearAll();
		myListView.setAdapter(mySingleChoiceAdapter);
	}
	
	/**
	 * ����Զ�̲�ѯ����
	 * @param result
	 * true ����
	 * false �ر�
	 */
	public void setWebSearchable(boolean result,final List<E> dataScorce,final DO domainObject,final String likeName,final String showId,
			final String showValue){
		LinearLayout searchll = (LinearLayout)ll.findViewById(R.id.layout_search);
		final MyEditText text_search = (MyEditText)searchll.findViewById(R.id.text_search);
		btn_search = (MyButton)searchll.findViewById(R.id.btn_search);
		if(null==selService){
			selService = new SelService<DO>(getContext());
		}
		if(result){
			this.status = 0;
			mySingleChoiceAdapter.setShowId(showId);
			mySingleChoiceAdapter.setShowValue(showValue);
			searchll.setVisibility(VISIBLE);
			
			btn_search.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dataScorce.clear();
					if(onSearchListener.onSearch(text_search.getText().toString().trim())){
						return;
					}
					ReflectUtil.setter(domainObject, likeName, text_search.getText().toString().trim(), String.class);
					selService.doRequest(domainObject);
					selService.setSelResponseListener(new SelService.SelResponseListener<DO>() {

						@Override
						public boolean sel_ok(DO domainObject, XmlNode xNode) {
							ILog.d(TAG, "dataScorce.size:"+dataScorce.size());
							if(dataScorce.size()<=0){
								MyToast.makeText(getContext(), "δ��ѯ������", 1).show();
							}else{
								mySingleChoiceAdapter.setDataScorce(dataScorce);
								myListView.setAdapter(mySingleChoiceAdapter);
							}
							return false;
						}

						@Override
						public boolean sel_err(String errmsg) {
							// TODO Auto-generated method stub
							return false;
						}
					});
				}
			});
		}else{
			this.status = 1;
			searchll.setVisibility(GONE);
			btn_search.setOnClickListener(null);
		}
	}
	
	/**
	 * �������ز�ѯ����
	 * @param result
	 * true ����
	 * false �ر�
	 * @param timelySearch
	 * true �Զ���ѯ
	 */
	public void setLocalSearchable(boolean result,boolean timelySearch){
		LinearLayout searchll = (LinearLayout)ll.findViewById(R.id.layout_search);
		final MyEditText text_search = (MyEditText)searchll.findViewById(R.id.text_search);
		btn_search = (MyButton)searchll.findViewById(R.id.btn_search);
		if(result){
			final List<E> listAll = mySingleChoiceAdapter.getDataScorce();
			final String[] arrValuesAll = mySingleChoiceAdapter.getArrValues();
			searchll.setVisibility(VISIBLE);
			if(timelySearch){
				this.status = 2;
				btn_search.setVisibility(GONE);
				text_search.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						List<E> list;
						if("".equals(s.toString().trim())){
							mySingleChoiceAdapter.setDataScorce(listAll);
							myListView.setAdapter(mySingleChoiceAdapter);
						}else{
							list = new ArrayList<E>();
							String str_search = s.toString().trim();
							for(int i = 0;i<arrValuesAll.length;i++){
								if(arrValuesAll[i].contains(str_search)){
									list.add(listAll.get(i));
								}
							}
							mySingleChoiceAdapter.setDataScorce(list);
							myListView.setAdapter(mySingleChoiceAdapter);
						}
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						
					}
				});
			}else{
				this.status = 3;
				btn_search.setVisibility(VISIBLE);
				btn_search.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(onSearchListener.onSearch(text_search.getText().toString().trim())){
							return;
						}
						List<E> list;
						if("".equals(text_search.getText().toString().trim())){
							mySingleChoiceAdapter.setDataScorce(listAll);
							myListView.setAdapter(mySingleChoiceAdapter);
						}else{
							list = new ArrayList<E>();
							String str_search = text_search.getText().toString().trim();
							for(int i = 0;i<arrValuesAll.length;i++){
								if(arrValuesAll[i].contains(str_search)){
									list.add(listAll.get(i));
								}
							}
							mySingleChoiceAdapter.setDataScorce(list);
							myListView.setAdapter(mySingleChoiceAdapter);
						}
					}
				});
			}
		}else{
			this.status = 4;
			searchll.setVisibility(GONE);
			btn_search.setOnClickListener(null);
		}
		
		
	}
	
	/**
	 * �ر�dialog
	 */
	public void dismiss() {
		myDialog.dismiss();
	}
	
	/**
	 * ������ʾdialog
	 * @param result
	 * true ��ʾ
	 * false ����ʾ
	 */
	public void setOnClickShow(boolean result) {
		setOnClickShow(result,false);
	}
	/**
	 * ������ʾdialog
	 * @param result
	 * true ��ʾ
	 * false ����ʾ
	 * showClear Ĭ��Ϊfalse
	 */
	public void setOnClickShow(boolean result,final boolean showClear) {
		if (result) {
			setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(showClear){
						LinearLayout searchll = (LinearLayout)ll.findViewById(R.id.layout_search);
						MyEditText text_search = (MyEditText)searchll.findViewById(R.id.text_search);
						text_search.setText("");
						mySingleChoiceAdapter.clearDate();
						myListView.setAdapter(mySingleChoiceAdapter);
					}
					show();
				}
			});
		} else {
			setOnClickListener(null);
		}
	}
	
	/**
	 * ��ʾdialog
	 */
	public void show() {
		try {
			myListView.setSelectionFromTop(getSelectedIndex(), 0);
			myDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʾdialog
	 * ������������
	 * isRepeat  true�ظ�����  false����һ��
	 * isLocalSerchable true�������ز�ѯ  false�رձ��ز�ѯ
	 */

	public void setOnClickShow(final boolean isRepeat,final boolean isLocalSerchable,final boolean timelySearch,final List<E> dataScorce,final DO domainObject,final String showId,
			final String showValue) {
		setOnClickListener(new View.OnClickListener() {
			boolean isfirst = true;
			@Override
			public void onClick(View v) {
				
				if(isRepeat){
					if(null==selService){
						selService = new SelService<DO>(getContext());
					}
					selService.doRequest(domainObject);
					selService.setSelResponseListener(new SelService.SelResponseListener<DO>() {

						@Override
						public boolean sel_ok(DO domainObject, XmlNode xNode) {
							setData(dataScorce, showId, showValue);
							setLocalSearchable(isLocalSerchable,timelySearch);
							myListView.setAdapter(mySingleChoiceAdapter);
							show();
							return false;
						}

						@Override
						public boolean sel_err(String errmsg) {
							// TODO Auto-generated method stub
							return false;
						}
					});
					
				}else{
					
					if(isfirst){
						
						if(null==selService){
							selService = new SelService<DO>(getContext());
						}
						selService.doRequest(domainObject);
						selService.setSelResponseListener(new SelService.SelResponseListener<DO>() {
							
							@Override
							public boolean sel_ok(DO domainObject, XmlNode xNode) {
								isfirst = false;
								setData(dataScorce, showId, showValue);
								setLocalSearchable(isLocalSerchable,timelySearch);
								myListView.setAdapter(mySingleChoiceAdapter);
								show();
								return false;
							}
							
							@Override
							public boolean sel_err(String errmsg) {
								isfirst = true;
								return false;
							}
						});
						
					}else{
						show();
					}
				}
			}
		});
	}
	
	/**
	 * ͨ�������ȡ����
	 */
	public void setDate(final List<E> dataScorce,DO domainObject, final String showId,
			final String showValue){
		if(null==selService){
			selService = new SelService<DO>(getContext());
		}
		selService.doRequest(domainObject);
		selService.setSelResponseListener(new SelService.SelResponseListener<DO>() {
			
			@Override
			public boolean sel_ok(DO domainObject, XmlNode xNode) {
				setData(dataScorce, showId, showValue);
				return false;
			}
			
			@Override
			public boolean sel_err(String errmsg) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}
	
	
	
	/**
	 * @return 
	 * true:���Ա༭
	 * false:�����Ա༭
	 */
	public boolean isEdit() {
		return isEdit;
	}
	/**
	 * 
	 * @param isEdit
	 * true:���Ա༭
	 * false:�����Ա༭
	 */
	public void setEdit(boolean isEdit) {
		LinearLayout searchll = (LinearLayout)ll.findViewById(R.id.layout_search);
		final MyEditText text_search = (MyEditText)searchll.findViewById(R.id.text_search);
		searchll.setVisibility(VISIBLE);
		if(isEdit){
			btn_ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						setText(text_search.getText().toString().trim());
						myDialog.dismiss();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}else{
			switch(this.status){
			case 0:
			case 2:
			case 3:
				break;
				default:
					searchll.setVisibility(GONE);
					break;
			}
			btn_ok();
		}
		this.isEdit = isEdit;
	}

	public interface OnSearchListener{
		public boolean onSearch(String searchContext);
	}
	public interface OnQuitListener{
		public void onQuit(MySingleChoice v);
	}
	
	public interface OnClearListener{
		public void onClear(MySingleChoice v);
	}
	
	public interface OnOkListener{
		public void onOk(MySingleChoice v);
	}
	
	public interface OnItemClickListener <E>{
		public void onItemClick(E obj,AdapterView<?> parent, View view,
				int position, long id);
	}
	/**
	 * 
	 * @param onItemClickListener
	 * ���õ���ѡ�����
	 */
	
	public void setOnItemClickListener(OnItemClickListener<E> onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public OnSearchListener getOnSearchListener() {
		return onSearchListener;
	}

	public void setOnSearchListener(OnSearchListener onSearchListener) {
		this.onSearchListener = onSearchListener;
	}

	public void setOnClearListener(OnClearListener onClearListener) {
		this.onClearListener = onClearListener;
	}

	public void setOnQuitListener(OnQuitListener onQuitListener) {
		this.onQuitListener = onQuitListener;
	}

	public void setOnOkListener(OnOkListener onOkListener) {
		this.onOkListener = onOkListener;
	}

	public void setData(List<E> dataScorce) {
		setData(getContext(), dataScorce, "", "string");
	}

	public void setData(List<E> dataScorce, String showId, String showValue) {
		setData(getContext(), dataScorce, showId, showValue);
	}

	@Override
	public  void setData(Context context, List<E> dataScorce, String showId,
			String showValue) {
			mySingleChoiceAdapter.setData(context, dataScorce, showId, showValue);
	}
	
	public void refreshList(){
		myListView.setAdapter(mySingleChoiceAdapter);
	}

	@Override
	public String getSelectedId() {
		// TODO Auto-generated method stub
		return mySingleChoiceAdapter.getSelectedId();
	}

	@Override
	public void setSelectedId(String selectedId) {
		// TODO Auto-generated method stub
		mySingleChoiceAdapter.setSelectedId(selectedId);
	}

	@Override
	public int getSelectedIndex() {
		// TODO Auto-generated method stub
		return mySingleChoiceAdapter.getSelectedIndex();
	}

	@Override
	public void setSelectedIndex(int selectedIndex) {
		// TODO Auto-generated method stub
		mySingleChoiceAdapter.setSelectedIndex(selectedIndex);
	}

	@Override
	public String getSelectedValue() {
		// TODO Auto-generated method stub
		return mySingleChoiceAdapter.getSelectedValue();
	}

	@Override
	public void setSelectedValue(String selectedValue) {
		// TODO Auto-generated method stub
		mySingleChoiceAdapter.setSelectedValue(selectedValue);
	}

}
