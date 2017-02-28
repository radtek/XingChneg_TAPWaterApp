package com.ideal.achartengine.series;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;

public class LineDateSeriesDatase extends XYMultipleSeriesDataset{
	
	
	public LineDateSeriesDatase(String[] titles,List x,List y,int length){
		
		// �м�����
		for (int i = 0; i < length; i++){

			XYSeries series = new XYSeries(titles[i]);
			// ����ÿ���ߵ����ƴ���
			double[] xV = (double[]) x.get(i);
			// ��ȡ��i���ߵ�����
			double[] yV = (double[]) y.get(i);
			int seriesLength = xV.length;
			// �м�����
			for (int k = 0; k < seriesLength; k++){
				// ÿ�������м�����
				series.add(xV[k], yV[k]);
			}
			this.addSeries(series);
		}
	}
	
	public LineDateSeriesDatase(LineSet lineSet){
		
		// �м�����
		for (int i = 0; i <lineSet.size() ; i++){
			XYSeries series = null;
			if(null!=lineSet.getTitles()){
				series = new XYSeries(lineSet.getTitles().get(i));
			}else{
				series = new XYSeries((i+1)+"");
			}
			// ����ÿ���ߵ����ƴ���
			List<Double> xV = lineSet.getX().get(i);
			// ��ȡ��i���ߵ�����
			List<Double> yV = lineSet.getY().get(i);
			int seriesLength = xV.size();
			// �м�����
			for (int k = 0; k < seriesLength; k++){
				// ÿ�������м�����
				series.add(xV.get(k), yV.get(k));
			}
			this.addSeries(series);
		}
	}
}
