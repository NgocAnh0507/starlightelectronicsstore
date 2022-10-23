package com.greenvn.starlightelectronicsstore.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.model.CartInfo;
import com.greenvn.starlightelectronicsstore.service.ProductService;
import com.greenvn.starlightelectronicsstore.utils.Utils;

@RestController
@RequestMapping(value = "/api/cart")
public class CartRestController {

	
	@Autowired
	private ProductService productService;
	@GetMapping("/update-quantity")
	public CartInfo updateProductQuantity(HttpServletRequest request,@RequestParam long productId,@RequestParam int quantity) {
		Product product = productService.findProductById(productId);
		CartInfo cartInfo = Utils.getCartInSession(request);
		if(product.getQuantityOrderMax() >= quantity) {
			cartInfo.updateProduct(productId, quantity);
			cartInfo.setMessage(null);
		}else {
			cartInfo.setMessage("Vuot qua so luong cho phep");
		}
		return cartInfo;
	}
}
