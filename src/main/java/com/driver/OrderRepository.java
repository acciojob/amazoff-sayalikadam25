package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    private HashMap<String,Order> orderDb;
    private HashMap<String,DeliveryPartner> partnerDb;
    private HashMap<String,String> orderPartnerPair;
    //private HashMap<String,String> partnerOrdersPair;

    public OrderRepository() {
        this.orderDb = new HashMap<>();
        this.partnerDb = new HashMap<>();
        this.orderPartnerPair = new HashMap<>();
        //this.partnerOrdersPair = new HashMap<>();
    }

    public void addOrder(String key, Order order){
        orderDb.put(key,order);
    }
    public void addPartner(String partnerId){
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
        partnerDb.put(partnerId,deliveryPartner);
    }
    public void addOrderPartnerPair(String orderId,String partnerId){
        orderPartnerPair.put(orderId,partnerId);
    }
//    public void addPartnerOrdersPair(String partnerId,String orderId){
//        List<Order> l=
//    }
    public Order getOrderById(String orderId){
        Order order=null;
        for(String id:orderDb.keySet()){
            if(id.equals(orderId)){
                order=orderDb.get(id);
            }
        }
        return order;
    }
    public DeliveryPartner getPartnerById(String partnerId){
        DeliveryPartner deliveryPartner=null;
        for(String id:partnerDb.keySet()){
            if(id.equals(partnerId)){
                deliveryPartner=partnerDb.get(id);
            }
        }
        return deliveryPartner;
    }
    public Integer getOrderCountByPartnerId(String partnerId){
        return (Integer)partnerDb.get(partnerId).getNumberOfOrders();
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> orders=new ArrayList<>();
        for(String s:orderPartnerPair.keySet()){
            if(partnerId.equals(orderPartnerPair.get(s))){
                orders.add(s);
            }
        }
        return orders;
    }
    public List<String> getAllOrders(){
        List<String> orders=new ArrayList<>();
        for(String s:orderDb.keySet()){
            orders.add(s);
        }
        return orders;
    }
    public Integer getCountOfUnassignedOrders(){
        int count=0;
        for(String s:orderDb.keySet()){
            if(!orderPartnerPair.containsKey(s)){
                count+=1;
            }
        }
        return (Integer)count;
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){
        int count=0;
        int T=Integer.valueOf(time.substring(0,1))*60+Integer.valueOf(time.substring(3));
        for(String s:orderDb.keySet()){
            if(T>orderDb.get(s).getDeliveryTime()){
                count+=1;
            }
        }
        return (Integer)count;
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        List<Integer> l=new ArrayList<>();
        for(String s:orderPartnerPair.keySet()){
            if(partnerId.equals(orderPartnerPair.get(s))){
                for(String x:orderDb.keySet()){
                    if(s.equals(x)){
                        l.add((Integer)orderDb.get(x).getDeliveryTime());
                    }
                }
            }
        }
        Collections.sort(l);
        int time=l.get(l.size()-1);
        String finalTime=(time/60)+":"+Integer.toString(time-(60*(time/60)));
        return finalTime;
    }
    public void deletePartnerById(String partnerId){
        for(String s:orderPartnerPair.keySet()){
            if(partnerId.equals(orderPartnerPair.get(s))){
                orderPartnerPair.remove(s);
            }
        }
        partnerDb.remove(partnerId);
    }
    public void deleteOrderById(String orderId){
        for(String s:orderPartnerPair.keySet()){
            if(orderId.equals(s)){
                orderPartnerPair.remove(s);
            }
        }
        orderDb.remove(orderId);
    }
}
