package leoguedex.com.github.API_Sales_Java.repository;

import leoguedex.com.github.API_Sales_Java.model.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedItemRepository extends JpaRepository<OrderedItem, Integer> {

}