package com.greenvn.starlightelectronicsstore.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.model.CartInfo;
import com.greenvn.starlightelectronicsstore.model.ProductInfo;
import com.greenvn.starlightelectronicsstore.service.ProductService;
import com.greenvn.starlightelectronicsstore.utils.Utils;

@RestController
@RequestMapping(value = "/api/cart")
public class CartRestController {

	
	@GetMapping("update-quantity")
	public CartInfo updateProductQuantity(@RequestParam Long productId, @RequestParam int quantity,
			HttpServletRequest request) 
	{
		
		CartInfo cartInfo = Utils.getCartInSession(request);//
		cartInfo.updateProduct(productId, quantity);
		return cartInfo;
	}
}
