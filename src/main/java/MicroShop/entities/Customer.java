package MicroShop.entities;
import java.util.List;
public class Customer {
 private String name;
 public String getName(){return name;}
 public void setName(String newVal){name = newVal;}

 private List<Order> orders;
 public List<Order> getOrders(){return orders;}
 public void setOrders(List<Order> newVal){orders = newVal;}

}

