/**
 * Copyright (c) 2016-2019 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redisson.example.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RedissonClient;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;
import org.redisson.liveobject.resolver.LongGenerator;
import org.redisson.liveobject.resolver.UUIDGenerator;

public class LiveObjectServiceExamples {

    @REntity
    public static class Product {

        @RId
        private Long id;

        private String name;

        private Map<String, Integer> itemName2Amount;

        private BigDecimal price;

        private Integer unitsInStock;

        protected Product() {
        }

        public Product(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getPrice() {
            return price;

        }

        public void setUnitsInStock(Integer unitsInStock) {
            this.unitsInStock = unitsInStock;

        }

        public Integer getUnitsInStock() {
            return unitsInStock;

        }

        public String getName() {
            return name;

        }

        public Map<String, Integer> getItemName2Amount() {
            return itemName2Amount;
        }

    }

    @REntity
    public static class OrderDetail {

        @RId(generator = LongGenerator.class)
        private Long id;

        private Order order;

        private Product product;

        private BigDecimal price;

        private Integer quantity;

        private BigDecimal discount;

        protected OrderDetail() {
        }

        public OrderDetail(Order order, Product product) {
            super();
            this.order = order;
            this.product = product;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getDiscount() {
            return discount;
        }

        public void setDiscount(BigDecimal discount) {
            this.discount = discount;
        }

        public Long getId() {
            return id;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public Product getProduct() {
            return product;
        }

    }

    @REntity

    public static class Customer {

        @RId(generator = UUIDGenerator.class)
        private String id;

        private List<Order> orders;

        private String name;

        private String address;

        private String phone;

        protected Customer() {
        }

        public Customer(String id) {
            super();
            this.id = id;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }

        public void addOrder(Order order) {
            orders.add(order);
        }

        public List<Order> getOrders() {
            return orders;
        }

        public String getId() {
            return id;
        }
    }
    
    @REntity
    public static class Order {

        @RId(generator = LongGenerator.class)
        private Long id;

        private List<OrderDetail> orderDetails;

        private Customer customer;

        private Date date;

        private Date shippedDate;

        private String shipName;

        private String shipAddress;

        private String shipPostalCode;

        protected Order() {
        }

        public Order(Customer customer) {
            super();
            this.customer = customer;
        }

        public List<OrderDetail> getOrderDetails() {
            return orderDetails;
        }

        public Customer getCustomer() {
            return customer;
        }

        public Long getId() {
            return id;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Date getShippedDate() {
            return shippedDate;
        }

        public void setShippedDate(Date shippedDate) {
            this.shippedDate = shippedDate;
        }

        public String getShipName() {
            return shipName;
        }

        public void setShipName(String shipName) {
            this.shipName = shipName;
        }

        public String getShipAddress() {
            return shipAddress;
        }

        public void setShipAddress(String shipAddress) {
            this.shipAddress = shipAddress;
        }

        public String getShipPostalCode() {
            return shipPostalCode;
        }

        public void setShipPostalCode(String shipPostalCode) {
            this.shipPostalCode = shipPostalCode;
        }

    }

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        RLiveObjectService liveObjectService = redisson.getLiveObjectService();

        Customer customer = new Customer("12");
        // customer object is becoming "live" object
        customer = liveObjectService.merge(customer);
        
        customer.setName("Alexander Pushkin");
        customer.setPhone("+7193127489123");
        customer.setAddress("Moscow, Tverskaya str");

        Product product = new Product(1L, "FoodBox");
        // product object is becoming "live" object
        product = liveObjectService.merge(product);
        
        product.getItemName2Amount().put("apple", 1);
        product.getItemName2Amount().put("banana", 12);
        product.setPrice(BigDecimal.valueOf(10));
        product.setUnitsInStock(12);

        Order order = new Order(customer);
        // order object is becoming "live" object
        order = liveObjectService.merge(order);
        
        order.setDate(new Date());
        order.setShipAddress("Moscow, Gasheka str");
        order.setShipName("James Bond");
        order.setShipPostalCode("141920");

        OrderDetail od = new OrderDetail(order, product);
        // OrderDetail object is becoming "live" object
        od = liveObjectService.merge(od);
        od.setPrice(BigDecimal.valueOf(9));
        od.setQuantity(1);
        order.getOrderDetails().add(od);
        customer.getOrders().add(order);

        // "live" object could be get on other JVM.

        Customer attachedCustomer = liveObjectService.get(Customer.class, "12");
        for (Order attachedOrder : attachedCustomer.getOrders()) {
            for (OrderDetail orderDetail : attachedOrder.getOrderDetails()) {
                 // ...

            }

        }

        Product attachedProduct = liveObjectService.get(Product.class, 1L);

        // ...
        redisson.shutdown();
    }

}
