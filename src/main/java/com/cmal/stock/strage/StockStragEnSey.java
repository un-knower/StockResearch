/*
 * Copyright (C) 2017 WordPlat Open Source Project
 *
 *      https://wordplat.com/InteractiveKLineView/
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

package com.cmal.stock.strage;

import io.searchbox.client.JestClient;

import java.util.ArrayList;
import java.util.List;

import com.cmal.stock.storedata.StoreStrategySet;
import com.cmall.stock.bean.StockBaseInfo;
import com.cmall.stock.bean.StockStrategyInfo;
import com.google.common.collect.Lists;
import com.kers.esmodel.BaseCommonConfig;

/**
 * <p>StockBaseInfoSet</p>
 * <p>Date: 2017/3/1</p>
 *
 * @author afon
 */

public class StockStragEnSey {

    /**
     * Y 轴上 StockBaseInfo 的最大值
     */
    private float maxY;

    /**
     * Y 轴上 StockBaseInfo 的最小值
     */
    private float minY;

    /**
     * Y 轴上 StockBaseInfo 的最大值索引
     */
    private int maxYIndex;

    /**
     * Y 轴上 StockBaseInfo 的最小值索引
     */
    private int minYIndex;

    /**
     * 高亮的 StockBaseInfo 索引
     */
    private int highlightIndex;

    /**
     * StockBaseInfo 列表
     */
    private final List<StockBaseInfo> entries = new ArrayList<StockBaseInfo>();

    /**
     * 列表第一个 StockBaseInfo 的昨日收盘价，用于判断当第一个 StockBaseInfo 的收盘价等于开盘价时，
     * 不好判断是涨停还是跌停还是不涨不跌，此值默认设置0，即一律视为涨停
     */
    private float preClose = 0;

    /**
     * 是否在加载中状态
     */
    private boolean loadingStatus = true;

    /**
     * 添加一个 StockBaseInfo 到尾部
     */
    public void addStockBaseInfo(StockBaseInfo StockBaseInfo) {
        entries.add(StockBaseInfo);
    }

    /**
     * 添加一组 StockBaseInfo 到尾部
     */
    public void addStockBaseInfos(List<StockBaseInfo> entries) {
        this.entries.addAll(entries);
    }

    /**
     * 添加一个 StockBaseInfo 到头部
     */
    public void insertFirst(StockBaseInfo StockBaseInfo) {
        entries.add(0, StockBaseInfo);
    }

    /**
     * 添加一组 StockBaseInfo 到头部
     */
    public void insertFirst(List<StockBaseInfo> entries) {
        this.entries.addAll(0, entries);
    }

    public List<StockBaseInfo> getStockBaseInfoList() {
        return entries;
    }

    public float getMinY() {
        return minY;
    }

    public float getMaxY() {
        return maxY;
    }

    public float getDeltaY() {
        return maxY - minY;
    }

    public int getMinYIndex() {
        return minYIndex;
    }

    public int getMaxYIndex() {
        return maxYIndex;
    }

    public int getHighlightIndex() {
        return highlightIndex;
    }

    public void setHighlightIndex(int highlightIndex) {
        this.highlightIndex = highlightIndex;
    }

    public StockBaseInfo getHighlightStockBaseInfo() {
        if (0 < highlightIndex && highlightIndex < entries.size()) {
            return entries.get(highlightIndex);
        }
        return null;
    }

    public float getPreClose() {
        return preClose;
    }

    public void setPreClose(float preClose) {
        this.preClose = preClose;
    }

    public boolean isLoadingStatus() {
        return loadingStatus;
    }

    public void setLoadingStatus(boolean loadingStatus) {
        this.loadingStatus = loadingStatus;
    }

    /**
     * 在给定的范围内，计算分时图 entries 的最大值和最小值
     *
     * @param start
     * @param end
     */
    public void computeTimeLineMinMax(int start, int end) {
        int endValue;
        if (end < 2 || end >= entries.size()) {
            endValue = entries.size() - 1;
        } else {
            endValue = end - 1; // 减去 1 是为了把边缘的 StockBaseInfo 排除
        }

        minY = Float.MAX_VALUE;
        maxY = -Float.MAX_VALUE;

        for (int i = start; i <= endValue; i++) {
            StockBaseInfo StockBaseInfo = entries.get(i);

            if (StockBaseInfo.getClose() < minY) {
                minY = StockBaseInfo.getClose();
                minYIndex = i;
            }

            if (StockBaseInfo.getClose() > maxY) {
                maxY = StockBaseInfo.getClose();
                maxYIndex = i;
            }
        }
    }

    /**
     * 在给定的范围内，计算 K 线图 entries 的最大值和最小值
     */
//    public void computeMinMax(int start, int end, List<StockIndex> stockIndexList) {
//        int endValue;
//        if (end < 2 || end >= entries.size()) {
//            endValue = entries.size() - 1;
//        } else {
//            endValue = end - 1; // 减去 1 是为了把边缘的 StockBaseInfo 排除
//        }
//
//        minY = Float.MAX_VALUE;
//        maxY = -Float.MAX_VALUE;
//
//        if (stockIndexList != null) {
//            for (StockIndex stockIndex : stockIndexList) {
//                if (stockIndex.isEnable()) {
//                    stockIndex.resetMinMax();
//                }
//            }
//        }
//
//        for (int i = start; i <= endValue; i++) {
//            StockBaseInfo StockBaseInfo = entries.get(i);
//
//            if (StockBaseInfo.getLow() < minY) {
//                minY = StockBaseInfo.getLow();
//                minYIndex = i;
//            }
//            minY = Math.min(minY, StockBaseInfo.getMa5());
//            minY = Math.min(minY, StockBaseInfo.getMa10());
//            minY = Math.min(minY, StockBaseInfo.getMa20());
//
//            if (StockBaseInfo.getHigh() > maxY) {
//                maxY = StockBaseInfo.getHigh();
//                maxYIndex = i;
//            }
//            maxY = Math.max(maxY, StockBaseInfo.getMa5());
//            maxY = Math.max(maxY, StockBaseInfo.getMa10());
//            maxY = Math.max(maxY, StockBaseInfo.getMa20());
//
//            if (stockIndexList != null) {
//                for (StockIndex stockIndex : stockIndexList) {
//                    if (stockIndex.isEnable()) {
//                        stockIndex.computeMinMax(i, StockBaseInfo);
//                    }
//                }
//            }
//        }
//    }

    /**
     * 计算 MA MACD BOLL RSI KDJ 指标
     */
    public void computeStockIndex() {
        computeMA();
        computeMACD();
        computeBOLL();
        computeRSI();
        computeKDJ();
        computeUpDateNum();
    }

    /**
     * 计算连续上涨天数
     */
    private void computeUpDateNum(){
    	final JestClient jestClient = BaseCommonConfig.clientConfig();
    	List<StockStrategyInfo> list = Lists.newArrayList();
    	for (int i = 0; i < entries.size(); i++) {
    		StockBaseInfo StockBaseInfo = entries.get(i);
    		if(i == 0){
    			if(StockBaseInfo.getMacd() >= 0){
    				StockBaseInfo.setMacdNum(1);
    			}
    		}
    		if(i != entries.size() - 1){
    			float nextRises = entries.get(i+1).getRises();
    			float nextJ = entries.get(i+1).getJ();
    			float nextMacd = entries.get(i+1).getMacd();
    			StockBaseInfo.setNextRises(nextRises);
    			StockBaseInfo.setNextJ(nextJ);
    			StockBaseInfo.setNextMacd(nextMacd);
    		}
    		if(i != 0){
    			float upRises = entries.get(i-1).getRises();
    			float upJ = entries.get(i-1).getJ();
    			float upMacds = entries.get(i-1).getMacd();
    			float upVom = entries.get(i-1).getVolume();
    			int openNum = entries.get(i-1).getOpenNum() + 1;
    			if(upVom != 0){
    				StockBaseInfo.setVolumeRises(StockBaseInfo.getVolume() / upVom);
    				if(StockBaseInfo.getVolumeRises() > 2){
    					StockStrategyInfo info = new StockStrategyInfo();
    					info.setStockCode(StockBaseInfo.getStockCode());
    					info.setStockName(StockBaseInfo.getStockName());
    					info.setF1(upVom);
    					info.setF2(StockBaseInfo.getVolume());
    					info.setF3(Float.parseFloat(StockBaseInfo.getVolumeRises()+""));
    					info.setType(1);
    					info.setDate(StockBaseInfo.getDate());
    					list.add(info);
    				}
    			}
    			StockBaseInfo.setUpRises(upRises);
    			StockBaseInfo.setUpJ(upJ);
    			StockBaseInfo.setUpMacd(upMacds);
    			int m = entries.get(i-1).getMacdNum();
    			if(StockBaseInfo.getMacd() >= 0){
    				StockBaseInfo.setMacdNum(m+1);
    			}
    			float upClose = entries.get(i-1).getClose();
    			float close = StockBaseInfo.getClose();
    			if(close >= upClose){
    				int n = entries.get(i-1).getUpDateNum() + 1;
    				StockBaseInfo.setUpDateNum(n);
    			}
    			int up5 = 0;
    			int up10 = 0;
    			int macdUp5 = 0;
    			int macdUp10 = 0;
    			float j3 =  StockBaseInfo.getJ();
    			float j5 =  StockBaseInfo.getJ();
        		Float macd = StockBaseInfo.getMacd();
        		Float macd5 = macd;
        		Float macd10 = macd;
        		Float Rises5 = StockBaseInfo.getRises();
        		Float Rises10 = StockBaseInfo.getRises();
        		Float maxRises3 = Rises5;
        		Float maxRises5 = Rises5;
        		Float maxRises10 = Rises5;
    			for (int j = 1; j <= 10; j++) {
					if(i-j < 0){
						break;
					}
					float upCloses = entries.get(i-j).getClose();
					float closes = entries.get(i-j+1).getClose();
					float macds = entries.get(i-j+1).getMacd();
					float upMacd = entries.get(i-j).getClose();
					float Risess = entries.get(i-j).getRises();
					float js = entries.get(i-j).getJ();
					if(j < 4){
						if(maxRises3 > Risess){
							maxRises3 = Risess;
						}
						j3 = j3 + js;
					}
					if(j < 6){
						if(maxRises5 > Risess){
							maxRises5 = Risess;
						}
						macd5 = macd5 + upMacd;
						Rises5 = Rises5 + Risess;
						j5 = j5 + js;
					}
					if(maxRises10 > Risess){
						maxRises10 = Risess;
					}
					macd10=macd10 + upMacd;
					Rises10 = Rises10 + Risess;
					if(macds > 0){
						if(j < 6){
							macdUp5++;
						}
						macdUp10++;
					}
					if(closes >= upCloses){  
						if(j < 6){
							up5++;
						}
						up10++;
					}
				}
    			float X = -10;
    			float upK = entries.get(i-1).getK();
    			float upD = entries.get(i-1).getD();
    			float K = StockBaseInfo.getK();
    			float D = StockBaseInfo.getD();
    			if(upK >= upD && K < D){
    				X = -1;
    			}else if(upK <= upD && K > D){
    				X = 0;
    			}else{
    				if(K-D < 5 && K-D > -5){
    					X = K-D;
    				}
    			}
    			StockBaseInfo.setX(X);
    			StockBaseInfo.setUp5(up5);
    			StockBaseInfo.setUp10(up10);
    			StockBaseInfo.setMacdUp5(macdUp5);
    			StockBaseInfo.setMacdUp10(macdUp10);
    			StockBaseInfo.setSumMacdUp5(macd5);
    			StockBaseInfo.setSumMacdUp10(macd10);
    			StockBaseInfo.setUpSumRises5(Rises5);
    			StockBaseInfo.setUpSumRises10(Rises10);
    			StockBaseInfo.setJ3(j3);
    			StockBaseInfo.setJ5(j5);
    			StockBaseInfo.setMinRises5(maxRises5);
    			StockBaseInfo.setMinRises10(maxRises10);
    			StockBaseInfo.setMinRises3(maxRises3);
//    			System.out.println(StockBaseInfo.getStockCode() + StockBaseInfo.getDate() +" 收盘价:" +
//    					StockBaseInfo.getRises() + " 前5天涨的次数" + StockBaseInfo.getUp5() + " 前10天涨的次数：" + StockBaseInfo.getUp10());
    		}
//    		System.out.println(StockBaseInfo.getDate()+" 当天涨幅：" + StockBaseInfo.getRises() 
//					+ " 上一天涨幅：" + StockBaseInfo.getNextRises());
    	}
    	if(list.size() > 0){
    		try {
				StoreStrategySet.insBatchEs(list, jestClient, "storestrateinfo");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    /**
     * 计算 MA
     */
    private void computeMA() {
        float ma5 = 0;
        float ma10 = 0;
        float ma20 = 0;
        float volumeMa5 = 0;
        float volumeMa10 = 0;

        for (int i = 0; i < entries.size(); i++) {
            StockBaseInfo StockBaseInfo = entries.get(i);

            ma5 += StockBaseInfo.getClose();
            ma10 += StockBaseInfo.getClose();
            ma20 += StockBaseInfo.getClose();

            volumeMa5 += StockBaseInfo.getVolume();
            volumeMa10 += StockBaseInfo.getVolume();

            if (i >= 5) {
                ma5 -= entries.get(i - 5).getClose();
                StockBaseInfo.setMa5(ma5 / 5f);

                volumeMa5 -= entries.get(i - 5).getVolume();
                StockBaseInfo.setVolumeMa5(volumeMa5 / 5f);
            } else {
                StockBaseInfo.setMa5(ma5 / (i + 1f));

                StockBaseInfo.setVolumeMa5(volumeMa5 / (i + 1f));
            }

            if (i >= 10) {
                ma10 -= entries.get(i - 10).getClose();
                StockBaseInfo.setMa10(ma10 / 10f);

                volumeMa10 -= entries.get(i - 10).getVolume();
                StockBaseInfo.setVolumeMa10(volumeMa10 / 5f);
            } else {
                StockBaseInfo.setMa10(ma10 / (i + 1f));

                StockBaseInfo.setVolumeMa10(volumeMa10 / (i + 1f));
            }

            if (i >= 20) {
                ma20 -= entries.get(i - 20).getClose();
                StockBaseInfo.setMa20(ma20 / 20f);
            } else {
                StockBaseInfo.setMa20(ma20 / (i + 1f));
            }
        }
    }

    /**
     * 计算 MACD
     */
    private void computeMACD() {
        float ema12 = 0;
        float ema26 = 0;
        float diff = 0;
        float dea = 0;
        float macd = 0;

        for (int i = 0; i < entries.size(); i++) {
            StockBaseInfo StockBaseInfo = entries.get(i);

            if (i == 0) {
                ema12 = StockBaseInfo.getClose();
                ema26 = StockBaseInfo.getClose();
            } else {
                // EMA（12） = 前一日EMA（12） X 11/13 + 今日收盘价 X 2/13
                // EMA（26） = 前一日EMA（26） X 25/27 + 今日收盘价 X 2/27
                ema12 = ema12 * 11f / 13f + StockBaseInfo.getClose() * 2f / 13f;
                ema26 = ema26 * 25f / 27f + StockBaseInfo.getClose() * 2f / 27f;
            }

            // DIF = EMA（12） - EMA（26） 。
            // 今日DEA = （前一日DEA X 8/10 + 今日DIF X 2/10）
            // 用（DIF-DEA）*2 即为 MACD 柱状图。
            diff = ema12 - ema26;
            dea = dea * 8f / 10f + diff * 2f / 10f;
            macd = (diff - dea) * 2f;

            StockBaseInfo.setDiff(retERRNAN(diff));
            StockBaseInfo.setDea(retERRNAN(dea));
            StockBaseInfo.setMacd(retERRNAN(macd));
        }
    }

    /**
     * 计算 BOLL 需要在计算 MA 之后进行
     */
    private void computeBOLL() {
        for (int i = 0; i < entries.size(); i++) {
            StockBaseInfo StockBaseInfo = entries.get(i);

            if (i == 0) {
                StockBaseInfo.setMb(StockBaseInfo.getClose());
                StockBaseInfo.setUp(0f);
                StockBaseInfo.setDn(0f);
            } else {
                int n = 20;
                if (i < 20) {
                    n = i + 1;
                }

                float md = 0;
                for (int j = i - n + 1; j <= i; j++) {
                    float c = entries.get(j).getClose();
                    float m = StockBaseInfo.getMa20();
                    float value = c - m;
                    md += value * value;
                }

                md = md / (n - 1);
                md = (float) Math.sqrt(md);

                StockBaseInfo.setMb(retERRNAN(StockBaseInfo.getMa20()));
                StockBaseInfo.setUp(retERRNAN(StockBaseInfo.getMb() + 2f * md));
                StockBaseInfo.setDn(retERRNAN(StockBaseInfo.getMb() - 2f * md));
            }
        }
    }

    /**
     * 计算 RSI
     */
    private void computeRSI() {
        float rsi1 = 0;
        float rsi2 = 0;
        float rsi3 = 0;
        float rsi1ABSEma = 0;
        float rsi2ABSEma = 0;
        float rsi3ABSEma = 0;
        float rsi1MaxEma = 0;
        float rsi2MaxEma = 0;
        float rsi3MaxEma = 0;

        for (int i = 0; i < entries.size(); i++) {
            StockBaseInfo StockBaseInfo = entries.get(i);

            if (i == 0) {
                rsi1 = 0;
                rsi2 = 0;
                rsi3 = 0;
                rsi1ABSEma = 0;
                rsi2ABSEma = 0;
                rsi3ABSEma = 0;
                rsi1MaxEma = 0;
                rsi2MaxEma = 0;
                rsi3MaxEma = 0;
            } else {
                float Rmax = Math.max(0, StockBaseInfo.getClose() - entries.get(i - 1).getClose());
                float RAbs = Math.abs(StockBaseInfo.getClose() - entries.get(i - 1).getClose());

                rsi1MaxEma = (Rmax + (6f - 1) * rsi1MaxEma) / 6f;
                rsi1ABSEma = (RAbs + (6f - 1) * rsi1ABSEma) / 6f;

                rsi2MaxEma = (Rmax + (12f - 1) * rsi2MaxEma) / 12f;
                rsi2ABSEma = (RAbs + (12f - 1) * rsi2ABSEma) / 12f;

                rsi3MaxEma = (Rmax + (24f - 1) * rsi3MaxEma) / 24f;
                rsi3ABSEma = (RAbs + (24f - 1) * rsi3ABSEma) / 24f;

                rsi1 = (rsi1MaxEma / rsi1ABSEma) * 100;
                rsi2 = (rsi2MaxEma / rsi2ABSEma) * 100;
                rsi3 = (rsi3MaxEma / rsi3ABSEma) * 100;
            }
            
            StockBaseInfo.setRsi1(retERRNAN(rsi1));
            StockBaseInfo.setRsi2(retERRNAN(rsi2));
            StockBaseInfo.setRsi3(retERRNAN(rsi3));
        }
    }

    /**
     * 计算 KDJ
     */
    private void computeKDJ() {
        float k = 0;
        float d = 0;

        for (int i = 0; i < entries.size(); i++) {
            StockBaseInfo StockBaseInfo = entries.get(i);

            int startIndex = i - 8;
            if (startIndex < 0) {
                startIndex = 0;
            }

            float max9 = Float.MIN_VALUE;
            float min9 = Float.MAX_VALUE;
            for (int index = startIndex; index <= i; index++) {
                max9 = Math.max(max9, entries.get(index).getHigh());
                min9 = Math.min(min9, entries.get(index).getLow());
            }
            
            float rsv = 100f * (StockBaseInfo.getClose() - min9) / (max9 - min9);
            if (i == 0) {
                k = retERRNAN(rsv);
                d = retERRNAN(rsv);
            } else {
                k = (rsv + 2f * k) / 3f;
                d = (k + 2f * d) / 3f;
            }
          
            StockBaseInfo.setK(retERRNAN(k));
            StockBaseInfo.setD(retERRNAN(d));
            float j = 0f;
            if(retERRNAN(3f * k - 2 * d) > 0){
            	j = retERRNAN(3f * k - 2 * d);
            }
            StockBaseInfo.setJ(j);
            
//            if(StockBaseInfo.getStockCode().equals("601669")){
//            	System.out.println(StockBaseInfo.getDate()+" K:"+StockBaseInfo.getK() + " D:" + StockBaseInfo.getD() + " J:" + StockBaseInfo.getJ());
//            }
        }
    }
    
    
	public    float retERRNAN(float num){
		if((num+"").equals("NaN"))
			return 0;
		else 
			return num;
	}
}
