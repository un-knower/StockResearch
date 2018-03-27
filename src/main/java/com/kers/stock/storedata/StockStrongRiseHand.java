package com.kers.stock.storedata;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.collect.Maps;

import com.google.common.collect.Lists;
import com.kers.esmodel.QueryComLstData;
import com.kers.stock.bean.StockBaseInfo;
import com.kers.stock.bean.StockDetailInfoBean;
import com.kers.stock.strage.StockSelStrag;
import com.kers.stock.utils.CsvHandUtils;
import com.kers.stock.utils.FilePath;
import com.kers.stock.utils.TimeUtils;

public class StockStrongRiseHand {
	public static List<StockBaseInfo> getstockBaseInfoFile() throws Exception {
		final Map<String, StockDetailInfoBean> map = QueryComLstData.getDetailInfo(); // getInfoByCsv();

		List<StockBaseInfo> result = Lists.newArrayList();
		File[] files = new File(FilePath.savePathsuff).listFiles();

		Map<String, StockBaseInfo> mapSource = Maps.newConcurrentMap();
		for (File file : files) {

			String absPath = file.getAbsolutePath();
			CsvHandUtils csvHandUtils = new CsvHandUtils(absPath);
			List<List<String>> lstSourceStr = csvHandUtils.readCSVFile();
			List<List<String>> lstSource = Lists.newArrayList();
                   int maxNum=lstSourceStr.size()>=50?50:lstSourceStr.size();
                   
                   
			for (int j = 0; j < maxNum; j++) {
				try {
					
			
				List<String> objdata = lstSourceStr.get(j);
				// StockBaseInfo bean =getBeanFromStr(lstStr);
				if (!(objIsEmpty(objdata.get(6)) || objIsEmpty(objdata.get(3)) || objIsEmpty(objdata.get(4))
						|| objIsEmpty(objdata.get(5)) || objdata.get(9).equals("None"))) {
					lstSource.add(objdata);
				}
				
				
				
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			if (!lstSource.isEmpty() && lstSource.size() > 30) {
				StockBaseInfo objdata = getBeanFromStr(lstSource.get(1));
				StockBaseInfo objdata2 = getBeanFromStr(lstSource.get(2));
				StockBaseInfo objdata3 = getBeanFromStr(lstSource.get(3));
				StockBaseInfo objdata4 = getBeanFromStr(lstSource.get(4));
				StockBaseInfo objdata5 = getBeanFromStr(lstSource.get(5));
				double rise3 = objdata.getRises() + objdata2.getRises() + objdata3.getRises();
				double rise5 = objdata.getRises() + objdata2.getRises() + objdata3.getRises() + objdata4.getRises()
						+ objdata5.getRises();

				// if (lstSource.size() < 30) {// 次新股排除
				String stockCode = objdata.getStockCode().substring(1);
				if (!stockCode.startsWith("3")&&(objdata.getRises()+objdata2.getRises()>-5)) {
					String oup1 = objdata.getStockName();
					StockDetailInfoBean bean = map.get(stockCode);
					
					oup1 = null != bean ? bean.getIndustry() + "   " + objdata.getStockName()
					: objdata.getStockName();
					
					if (rise5 >= 33) {

						double rrr = rise5 - rise3;
						if (rrr < 1.5) {
							System.out.println(objdata.getStockName());
						} else {
							String output = ((objdata.getRises() >= 9.7 && objdata2.getRises() >= 9.7)) ? "rise52Up"
									: "rise5Up";
							if (objdata.getRises() >= 9.7 && objdata2.getRises() >= 9.7 && objdata3.getRises() >= 9.7)
								output = "rise53Up";
							else if (objdata.getRises() >= 9.7 && objdata2.getRises() >= 9.7 && objdata3.getRises() >= 0
									&& objdata4.getRises() >= 9.7)
								output = "rise54Up";
							else if (objdata.getRises() >= 9.7 && objdata2.getRises() >= 9.7 && objdata3.getRises() >= 0
									&& objdata4.getRises() >= 0 && objdata5.getRises() >= 9.7)
								output = "rise55Up";
							else {
								if(!(objdata.getRises()<0&&objdata2.getRises()<0&&(objdata.getRises()+objdata2.getRises()<-5))&&objdata.getRises()>-4&&objdata2.getRises()>-4){
									output = "rise51Up";
								}
							
							}
								

							 

							StockBaseInfo baseInfo = new StockBaseInfo();
							baseInfo.setSbType(output);
							baseInfo.setStockCode(stockCode);
							baseInfo.setLsImp(1);
							mapSource.put(stockCode, baseInfo);
							result.add(baseInfo);
							StockSelStrag.allBlckStockLst().remove(baseInfo.getStockCode());
							System.out.println(output + "  " + oup1 + "    " + stockCode + "   rise3:"
									+ String.format("%.2f", rise3) + "  rise5:" + String.format("%.2f", rise5));
						}
					} else if (rise3 >= 26) {
						String output = (objdata.getRises() >= 9.7 && objdata2.getRises() >= 9.7) ? "rise32Up"
								: "rise3Up";
						if (objdata.getRises() >= 9.7 && objdata2.getRises() >= 9.7 && objdata3.getRises() >= 9.7)
							output = "rise33Up";
						else
							output = "rise31Up";
							
						

						StockBaseInfo baseInfo = new StockBaseInfo();
						baseInfo.setSbType(output);
						baseInfo.setStockCode(stockCode);
						baseInfo.setLsImp(1);
						mapSource.put(stockCode, baseInfo);
						result.add(baseInfo);
						StockSelStrag.allBlckStockLst().remove(baseInfo.getStockCode());
						System.out.println(output + "  " + oup1 + "    " + stockCode + "   rise3:"
								+ String.format("%.2f", rise3) + "  rise5:" + String.format("%.2f", rise5));
					} else if ((objdata.getRises() >= 9.7 && objdata2.getRises() >= 9.7)) {

						String output = "rise2Up";
						 

						StockBaseInfo baseInfo = new StockBaseInfo();
						baseInfo.setSbType(output);
						baseInfo.setStockCode(stockCode);
						baseInfo.setLsImp(1);
						mapSource.put(stockCode, baseInfo);
						result.add(baseInfo);
						StockSelStrag.allBlckStockLst().remove(baseInfo.getStockCode());
						System.out.println(output + "  " + oup1 + "    " + stockCode + "   rise3:"
								+ String.format("%.2f", rise3) + "  rise5:" + String.format("%.2f", rise5));
					}else{
					StockBaseInfo objdata6 = getBeanFromStr(lstSource.get(6));
					StockBaseInfo objdata7 = getBeanFromStr(lstSource.get(7));
					StockBaseInfo objdata8 = getBeanFromStr(lstSource.get(8));
					
					
					float  totoalRise=objdata.getRises()+objdata2.getRises()+objdata3.getRises()+objdata4.getRises()+objdata5.getRises()+
							 objdata6.getRises()+objdata7.getRises()+objdata8.getRises();
					 int nnn=0;
					for (int i = 1; i <11; i++) {
						 float riseseach=	getBeanFromStr(lstSource.get(i)).getRises();
						 if(riseseach>=0)
							 nnn++;
						}
//					if(objdata.getStockCode().equals("'002631")){
//						System.out.println(totoalRise);
//						System.out.println(objdata.getRises()>=0&&objdata2.getRises()>=0&&objdata3.getRises()>=0&&objdata4.getRises()>=0&&objdata5.getRises()>=0
//							&&objdata6.getRises()>=0&&objdata7.getRises()>=0&&objdata8.getRises()>=0);
//					}
					if(objdata.getRises()>=0&&objdata2.getRises()>=0&&objdata3.getRises()>=0&&objdata4.getRises()>=0&&objdata5.getRises()>=0
							&&objdata6.getRises()>=0&&objdata7.getRises()>=0&&objdata8.getRises()>=0&&totoalRise>10){
						StockBaseInfo baseInfo = new StockBaseInfo();
						String output = "upRise8";
						baseInfo.setSbType(output);
						baseInfo.setStockCode(stockCode);
						baseInfo.setLsImp(2);
						result.add(baseInfo);
						StockSelStrag.allBlckStockLst().remove(baseInfo.getStockCode());
						System.out.println(output + "  " + oup1 + "    " + stockCode + "   rise3:"
								+ String.format("%.2f", rise3) + "  rise5:" + String.format("%.2f", rise5));
					}else if(nnn>=8&&totoalRise>=15&&rise5>=12&&rise3>=8&&(objdata.getRises()>0||objdata2.getRises()>0)&&(objdata.getRises()>-4&&objdata2.getRises()>-4)) {
						
						if((objdata.getClose()-objdata.getOpen())/objdata.getOpen()>0.03&&TimeUtils.countDays(TimeUtils.toDate(objdata.getDate(), TimeUtils.DEFAULT_DATEYMD_FORMAT), new Date())<=7){
						StockBaseInfo baseInfo = new StockBaseInfo();
						String output = "upRiseU8";
						baseInfo.setSbType(output);
						baseInfo.setStockCode(stockCode);
						baseInfo.setLsImp(22);
						result.add(baseInfo);
						StockSelStrag.allBlckStockLst().remove(baseInfo.getStockCode());
						System.out.println(output + "  " + oup1 + "    " + stockCode + "   rise3:"
								+ String.format("%.2f", rise3) + "  rise5:" + String.format("%.2f", rise5));
						}
					}
					// || (rise3 >= 22) || (rise5 >= 33)
					// }
				}}
				
				
			
			}
		}
		return result;

	}

	public static StockBaseInfo getBeanFromStr(List<String> objdata) throws ParseException {
		StockBaseInfo stockBaseInfo = new StockBaseInfo(objdata.get(0), objdata.get(6), objdata.get(4), objdata.get(5),
				objdata.get(3), objdata.get(11), objdata.get(9), objdata.get(1), objdata.get(2), objdata.get(10),
				objdata.get(12), objdata.get(13), objdata.get(14), objdata.size() < 15 ? null : objdata.get(14),
				TimeUtils.dayForWeek(objdata.get(0)) + "");
		return stockBaseInfo;
	}

	public static void main(String[] args) throws Exception {
		getstockBaseInfoFile();
	}

	public static boolean objIsEmpty(String str) {
		if (StringUtils.isEmpty(str) || str.equals("0") || str.equals("None")) {
			return true;
		}
		return false;
	}
}
