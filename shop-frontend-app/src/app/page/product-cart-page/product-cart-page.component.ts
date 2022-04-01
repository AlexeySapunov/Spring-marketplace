import { Component, OnInit } from '@angular/core';
import {CartService} from "../../service/cart.service";
import {AllCart} from "../../model/allCart";
import {OrderService} from "../../service/order.service";

@Component({
  selector: 'app-product-cart-page',
  templateUrl: './product-cart-page.component.html',
  styleUrls: ['./product-cart-page.component.less']
})
export class ProductCartPageComponent implements OnInit {

  content?: AllCart;

  constructor(private cartService: CartService,
              private orderService: OrderService) { }

  ngOnInit(): void {
    this.cartUpdated();
  }

  cartUpdated() {
    this.cartService.findAll().subscribe(
      res => {
        this.content = res;
      }
    )
  }

  createOrder() {
    this.orderService.createOrder().subscribe();
  }
}
