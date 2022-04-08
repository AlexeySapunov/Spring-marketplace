import { Component, OnInit } from '@angular/core';
import {CartService} from "../../service/cart.service";
import {AllCart} from "../../model/allCart";
import {OrderService} from "../../service/order.service";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-product-cart-page',
  templateUrl: './product-cart-page.component.html',
  styleUrls: ['./product-cart-page.component.less']
})
export class ProductCartPageComponent implements OnInit {

  content?: AllCart;

  constructor(private cartService: CartService,
              private orderService: OrderService,
              private authService: AuthService,
              private router: Router) { }

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

  private _createOrderCallback() {
    this.orderService.createOrder()
      .subscribe();
  }

  createOrder() {
    if (!this.authService.isAuthenticated()) {
      this.authService.redirectUrl = '/order';
      this.authService.callbackAfterSuccess = this._createOrderCallback.bind(this);
      this.router.navigateByUrl('/login');
      return;
    }
    this.orderService.createOrder()
      .subscribe(() => this.router.navigateByUrl('/order'));
  }
}
