package vn.fs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DongTHD
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

	private Long id;
	private String name;
	private double unitPrice;// đơn vị giá
	private int quantity;// Số lượng 
	private double totalPrice;// Tổng thành tiền
	private ProductEntity product;
}
