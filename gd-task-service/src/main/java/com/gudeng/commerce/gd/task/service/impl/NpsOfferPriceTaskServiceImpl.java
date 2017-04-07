package com.gudeng.commerce.gd.task.service.impl;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.OfferPriceDTO;
import com.gudeng.commerce.gd.customer.service.NpsOfferPriceService;
import com.gudeng.commerce.gd.task.service.NpsOfferPriceTaskService;
import com.gudeng.commerce.gd.task.util.GdProperties;
import com.gudeng.paltform.pushmsg.GdMessageDTO;
import com.gudeng.paltform.pushmsg.umeng.UMengPushMessage;

public class NpsOfferPriceTaskServiceImpl implements NpsOfferPriceTaskService {

  public Logger logger = LoggerFactory.getLogger(NpsOfferPriceTaskServiceImpl.class);
  @Autowired
  public GdProperties gdProperties;
  @Autowired
  private static NpsOfferPriceService npsOfferPriceService;

  @Override
  public void execute() throws Exception {
    logger.info("通知友盟推送-------------------------------------");
    List<OfferPriceDTO> list=everyDayMinPriceList();
    logger.info("通知友盟推送-------------------------------------"+list.size());
    for(OfferPriceDTO dto:list){
      UMengPushMessage pushMessage = new UMengPushMessage();
      GdMessageDTO gdMessage = new GdMessageDTO();
      Map<String,String> extraMap = new HashMap<String,String>();
      extraMap.put("openmenu", "NPS_PURCHASE_PRICE_DETAIL");
      extraMap.put("purchaseId", ""+dto.getPurchaseId());
      extraMap.put("goodsName", ""+dto.getGoodsName());
      gdMessage.setExtraMap(extraMap);
      gdMessage.setSendApp("3");
      gdMessage.setSendType("1");
      gdMessage.setTicket("农批商");
      gdMessage.setTitle("农批商");
      gdMessage.setContent("你发布的"+dto.getGoodsName()+"有供应商报价，报价为"+new  BigDecimal(dto.getOfferPrice())+"，请点击查看！【谷登科技】");
      gdMessage.setDevice_tokens(dto.getDevice_tokens());
      gdMessage.setProduction_mode(false);
      logger.info("友盟推送参数：" + gdMessage);
      pushMessage.pushMessage(gdMessage);
      
      
    }
  }


  @Override
  public List<OfferPriceDTO> everyDayMinPriceList()throws Exception  {
    return getHessianNpsPurchaseService().everyDayMinPriceList();
  }
  

  protected NpsOfferPriceService getHessianNpsPurchaseService() throws MalformedURLException {
    String url = gdProperties.getProperties().getProperty("gd.npsOfferPrice.url");
    logger.info("NpsOfferPriceService url: " + url);
    if (npsOfferPriceService == null) {
      HessianProxyFactory factory = new HessianProxyFactory();
      factory.setOverloadEnabled(true);
      npsOfferPriceService = (NpsOfferPriceService) factory.create(NpsOfferPriceService.class, url);
    }
    return npsOfferPriceService;
  }






}
