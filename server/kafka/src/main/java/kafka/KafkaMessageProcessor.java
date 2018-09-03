package esform.global.kafka;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.model.ShopInfo;
import com.roncoo.eshop.cache.service.CacheService;
import com.roncoo.eshop.cache.spring.SpringContext;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * kafka消息处理线程
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
public class KafkaMessageProcessor implements Runnable {

	private KafkaStream kafkaStream;
	private CacheService cacheService;
	
	public KafkaMessageProcessor(KafkaStream kafkaStream) {
		this.kafkaStream = kafkaStream;
		this.cacheService = (CacheService) SpringContext.getApplicationContext()
				.getBean("cacheService"); 
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
        while (it.hasNext()) {
        	String message = new String(it.next().message());
        	
        	// 首先将message转换成json对象
        	JSONObject messageJSONObject = JSONObject.parseObject(message);
        	
        	// 从这里提取出消息对应的服务的标识
        	String serviceId = messageJSONObject.getString("serviceId");  
        	
        	// 如果是商品信息服务
        	if("productInfoService".equals(serviceId)) {
        		processProductInfoChangeMessage(messageJSONObject);
        	} else if("shopInfoService".equals(serviceId)) {
        		processShopInfoChangeMessage(messageJSONObject);  
        	}
        }
	}
	
	/**
	 * 处理商品信息变更的消息
	 * @param messageJSONObject
	 */
	private void processProductInfoChangeMessage(JSONObject messageJSONObject) {
		// 提取出商品id
		Long productId = messageJSONObject.getLong("productId");
		
		// 调用商品信息服务的接口
		// 直接用注释模拟：getProductInfo?productId=1，传递过去
		// 商品信息服务，一般来说就会去查询数据库，去获取productId=1的商品信息，然后返回回来
		
		// 龙果有分布式事务的课程，主要讲解的分布式事务几种解决方案，里面也涉及到了一些mq，或者其他的一些技术，但是那些技术都是浅浅的给你搭建一下，使用
		// 你从一个课程里，还是学到的是里面围绕的讲解的一些核心的知识
		// 缓存架构：高并发、高性能、海量数据，等场景
		
		String productInfoJSON = "{\"id\": 1, \"name\": \"iphone7手机\", \"price\": 5599, \"pictureList\":\"a.jpg,b.jpg\", \"specification\": \"iphone7的规格\", \"service\": \"iphone7的售后服务\", \"color\": \"红色,白色,黑色\", \"size\": \"5.5\", \"shopId\": 1}";
		ProductInfo productInfo = JSONObject.parseObject(productInfoJSON, ProductInfo.class);
		cacheService.saveProductInfo2LocalCache(productInfo);
		System.out.println("===================获取刚保存到本地缓存的商品信息：" + cacheService.getProductInfoFromLocalCache(productId));  
		cacheService.saveProductInfo2ReidsCache(productInfo);  
	}
	
	/**
	 * 处理店铺信息变更的消息
	 * @param messageJSONObject
	 */
	private void processShopInfoChangeMessage(JSONObject messageJSONObject) {
		// 提取出商品id
		Long productId = messageJSONObject.getLong("productId");
		Long shopId = messageJSONObject.getLong("shopId"); 
		
		// 调用商品信息服务的接口
		// 直接用注释模拟：getProductInfo?productId=1，传递过去
		// 商品信息服务，一般来说就会去查询数据库，去获取productId=1的商品信息，然后返回回来
		
		// 龙果有分布式事务的课程，主要讲解的分布式事务几种解决方案，里面也涉及到了一些mq，或者其他的一些技术，但是那些技术都是浅浅的给你搭建一下，使用
		// 你从一个课程里，还是学到的是里面围绕的讲解的一些核心的知识
		// 缓存架构：高并发、高性能、海量数据，等场景
		
		String shopInfoJSON = "{\"id\": 1, \"name\": \"小王的手机店\", \"level\": 5, \"goodCommentRate\":0.99}";
		ShopInfo shopInfo = JSONObject.parseObject(shopInfoJSON, ShopInfo.class);
		cacheService.saveShopInfo2LocalCache(shopInfo);
		System.out.println("===================获取刚保存到本地缓存的店铺信息：" + cacheService.getShopInfoFromLocalCache(shopId));   
		cacheService.saveShopInfo2ReidsCache(shopInfo);  
	}

}
