package com.example.amazncart.util;

import com.example.amazncart.dto.DiscountDTO;
import com.example.amazncart.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class PromotionUtil {

	public DiscountDTO applyPromotion(ProductDTO dto, String promotion) {
    	 switch (promotion) {
         case "SetA":
             return applySetAPromotions(dto);
         case "SetB":
             return applySetBPromotions(dto);
         default:
             return new DiscountDTO();
     }
    }

    private DiscountDTO applySetAPromotions(ProductDTO dto) {
        DiscountDTO discount = new DiscountDTO();
        double maxDiscount = 0;
        String discountTag = "";

        if ("UAE".equals(dto.getOrigin())) {
            maxDiscount = Math.max(maxDiscount, dto.getPrice() * 0.06);
            discountTag = "get 6% off";
        }

        if (dto.getRating() < 2) {
            maxDiscount = Math.max(maxDiscount, dto.getPrice() * 0.08);
            discountTag = "get 8% off";
        } else if (dto.getRating() == 2) {
            maxDiscount = Math.max(maxDiscount, dto.getPrice() * 0.04);
            discountTag = "get 4% off";
        }

        if (("electronics".equals(dto.getCategory()) || "furnishing".equals(dto.getCategory())) && dto.getPrice() >= 500) {
            double flatDiscount = 100;
            if (flatDiscount > maxDiscount) {
                maxDiscount = flatDiscount;
                discountTag = "get Rs 100 off";
            }
        }
        
        if (maxDiscount == 0) {
            return applyDefaultDiscount(dto);
        }

        discount.setAmount(maxDiscount);
        discount.setDiscountTag(discountTag);
        return discount;
    }

    private DiscountDTO applySetBPromotions(ProductDTO dto) {
        DiscountDTO discount = new DiscountDTO();
        double maxDiscount = 0;
        String discountTag = "";

        if (dto.getInventory() > 20) {
            maxDiscount = Math.max(maxDiscount, dto.getPrice() * 0.12);
            discountTag = "get 12% off";
        }

        if ("new".equals(dto.getOrigin())) {
            maxDiscount = Math.max(maxDiscount, dto.getPrice() * 0.07);
            discountTag = "get 7% off";
        }
        
        if (maxDiscount == 0) {
            return applyDefaultDiscount(dto);
        }

        discount.setAmount(maxDiscount);
        discount.setDiscountTag(discountTag);
        return discount;
    }
    
    public DiscountDTO applyDefaultDiscount(ProductDTO dto) {
        DiscountDTO discount = new DiscountDTO();
        double maxDiscount = 0;
        String discountTag = "";
        if (dto.getPrice() > 1000) {
            maxDiscount = dto.getPrice() * 0.02;
            discountTag = "get 2% off";
        }
        discount.setAmount(maxDiscount);
        discount.setDiscountTag(discountTag);
        return discount;
    }
}
