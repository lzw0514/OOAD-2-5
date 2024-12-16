package cn.edu.xmu.oomall.customer.service;

public class CartsService {
}

public void addCartItem(int userId, int productId, int quantity) {
    CartItem cartItem = cartItemDao.findCartItemByProductId(userId, productId);
    if (cartItem != null) {
        cartItem.auditCartItem();
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemDao.saveOrUpdateCartItem(cartItem);
    } else {
        cartItem = CartItem.createCartItem(userId, productId, quantity);
        cartItemDao.saveOrUpdateCartItem(cartItem);
    }
}
            cartItem = CartItem.createCartItem(userId, productId, quantity);
            if (productDao.isGroupPurchase(productId) || productDao.isPreSale(productId)) {
                throw new ProductException("error", 615);
            }
            cartItemDao.save(cartItem);
        }
    }
}
