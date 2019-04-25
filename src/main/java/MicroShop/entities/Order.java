package MicroShop.entities;
import java.util.List;
public class Order {
 private String date;
 public String getDate(){return date;}
 public void setDate(String newVal){date = newVal;}

 private Integer total;
 public Integer getTotal(){return total;}
 public void setTotal(Integer newVal){total = newVal;}

 private List<OrderLine> lines;
 public List<OrderLine> getLines(){return lines;}
 public void setLines(List<OrderLine> newVal){lines = newVal;}

 private Customer customer;
 public Customer getCustomer(){return customer;}
 public void setCustomer(Customer newVal){customer = newVal;}

}

