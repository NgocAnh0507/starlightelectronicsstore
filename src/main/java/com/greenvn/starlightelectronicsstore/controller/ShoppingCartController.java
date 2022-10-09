package com.greenvn.starlightelectronicsstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.model.CartInfo;
import com.greenvn.starlightelectronicsstore.model.ProductInfo;
import com.greenvn.starlightelectronicsstore.service.ProductService;
import com.greenvn.starlightelectronicsstore.utils.Utils;

@Controller
@RequestMapping(value = "/shop")
public class ShoppingCartController {
	@Autowired
	private ProductService proSer;

	@RequestMapping({ "/buyProduct" })
	public String listProductHandler(HttpServletRequest request, Model model,
			@RequestParam(value = "productID", defaultValue = "") Long productID) {
		Product product = null;
		if (productID != null) {
			product = proSer.findProductById(productID);
		}
		if (product != null) {
			CartInfo cartInfo = Utils.getCartInSession(request);
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.addProduct(productInfo, 1);
		}
		return "redirect:/shop/shoppingCart";
	}

	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
	public String shoppingCartUpdateQty(HttpServletRequest request, //
			Model model, //

			@ModelAttribute("cartForm") CartInfo cartForm)

	{
		CartInfo cartInfo = Utils.getCartInSession(request);
		cartInfo.updateQuantity(cartForm);
		return "redirect:/shop/shoppingCart";
	}

	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
	public String shoppingCartHandler(HttpServletRequest request, Model model) {
		CartInfo myCart = Utils.getCartInSession(request);
		model.addAttribute("cartForm", myCart);
		return "shop/shoppingCart";
	}

	@RequestMapping({ "/shoppingCartRemoveProduct" })
	public String removeProductHandler(HttpServletRequest request, Model model,
			//
			@RequestParam(value = "productID", defaultValue = "") Long productID) {
		Product product = null;
		if (productID != null) {
			product = proSer.findProductById(productID);
		}
		if (product != null) {
			CartInfo cartInfo = Utils.getCartInSession(request);
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.removeProduct(productInfo);
		}
		return "redirect:/shop/shoppingCart";
	}
	
}
