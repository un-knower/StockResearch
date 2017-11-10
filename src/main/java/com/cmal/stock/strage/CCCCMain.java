package com.cmal.stock.strage;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.cmall.stock.bean.StockBaseInfo;

public class CCCCMain {
	public static StockStragEnSey parseKLineData(String data) {
        final StockStragEnSey stockStragEnSey = new StockStragEnSey();

        final String[] candleDatas = data.split(",");

        for (String candleData : candleDatas) {
            try {

                String[] v = candleData.split("[|]");

                float open = Float.parseFloat(v[0]);
                float high = Float.parseFloat(v[1]);
                float low = Float.parseFloat(v[2]);
                float close = Float.parseFloat(v[3]);

                int volume = Integer.parseInt(v[4]);

                stockStragEnSey.addStockBaseInfo(new StockBaseInfo(open, high, low, close, volume, v[5]));
            }catch (Exception e){
                System.out.println( "parseKLineData: " + candleData);
                e.printStackTrace();
            }

        }

        return stockStragEnSey;
    }
	
	public static void main(String[] args) throws IOException {
		String path="/opt/stockAcd";
		StockStragEnSey stockStragEnSey=parseKLineData(FileUtils.readFileToString(new File(path)));
		stockStragEnSey.computeStockIndex();
		
		for(StockBaseInfo  bean :stockStragEnSey.getStockBaseInfoList()){
			System.out.println(bean.getDate()+"  "+bean.getK() + "---" + bean.getD() + "---" + bean.getJ());
//			System.out.println(bean.getXLabel()+"  "+bean.getDiff() + "---" + bean.getDea() + "---" + bean.getMacd());
//			System.out.println(bean.getXLabel()+"  "+bean.getRsi1() + "---" + bean.getRsi2() + "---" + bean.getRsi3());
		}
	}
}
