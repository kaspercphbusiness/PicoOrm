package MicroShop.entities;
import java.util.List;
public class OrderLine {
 private Product product;
 public Product getProduct(){return product;}
 public void setProduct(Product newVal){product = newVal;}

 private Integer total;
 public Integer getTotal(){return total;}
 public void setTotal(Integer newVal){total = newVal;}

 private Integer count;
 public Integer getCount(){return count;}
 public void setCount(Integer newVal){count = newVal;}

 private Order order;
 public Order getOrder(){return order;}
 public void setOrder(Order newVal){order = newVal;}

}

